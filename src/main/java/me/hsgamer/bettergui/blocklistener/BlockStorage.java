package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import org.bukkit.Location;

import java.util.*;

public class BlockStorage {

    private final Map<Location, String> locToMenuMap = new HashMap<>();
    private final Map<Location, String> locToArgsMap = new HashMap<>();
    private final BetterGUIAddon addon;

    public BlockStorage(BetterGUIAddon addon) {
        this.addon = addon;
        load();
    }

    @SuppressWarnings("unchecked")
    public void load() {
        addon.getConfig().getKeys(false).forEach(s -> addon.getConfig().getMapList(s)
                .forEach(map -> {
                    Location location = Location.deserialize((Map<String, Object>) map);
                    locToMenuMap.put(location, s + ".yml");
                    if (map.containsKey("args")) {
                        locToArgsMap.put(location, (String) map.get("args"));
                    }
                }));
    }

    public void save() {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        locToMenuMap.forEach((loc, s) -> {
            s = s.replace(".yml", "");
            Map<String, Object> serialized = loc.serialize();
            if (locToArgsMap.containsKey(loc)) {
                serialized.put("args", locToArgsMap.get(loc));
            }

            if (!map.containsKey(s)) {
                map.put(s, new ArrayList<>());
            }
            map.get(s).add(serialized);
        });

        // Clear old config
        addon.getConfig().getKeys(false).forEach(s -> addon.getConfig().set(s, null));

        map.forEach((s, list) -> addon.getConfig().set(s, list));
        addon.saveConfig();
    }

    public void set(Location loc, String menu) {
        locToMenuMap.put(loc, menu);
    }

    public void setArgs(Location loc, String args) {
        locToArgsMap.put(loc, args);
    }

    public void remove(Location loc) {
        locToMenuMap.remove(loc);
        locToArgsMap.remove(loc);
    }

    public boolean contains(Location loc) {
        return getMenuFromLocation(loc).isPresent();
    }

    public Optional<String> getMenuFromLocation(Location loc) {
        return Optional.ofNullable(locToMenuMap.get(loc));
    }

    public String[] getArgsFromLocation(Location loc) {
        if (locToArgsMap.containsKey(loc)) {
            return locToArgsMap.get(loc).split(" ");
        }
        return new String[0];
    }
}
