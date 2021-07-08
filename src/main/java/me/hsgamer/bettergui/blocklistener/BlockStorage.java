package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.lib.core.config.Config;
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
        Config config = addon.getConfig();
        for (String s : config.getKeys(false)) {
            Optional.ofNullable(config.getInstance(s, List.class))
                    .ifPresent(list -> list.forEach(o -> {
                        if (o instanceof Map) {
                            Map<String, Object> map = (Map<String, Object>) o;
                            Location location = Location.deserialize(map);
                            locToMenuMap.put(location, s + ".yml");
                            if (map.containsKey("args")) {
                                locToArgsMap.put(location, (String) map.get("args"));
                            }
                        }
                    }));
        }
    }

    public void save() {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        locToMenuMap.forEach((loc, s) -> {
            s = s.replace(".yml", "");
            Map<String, Object> serialized = loc.serialize();
            if (locToArgsMap.containsKey(loc)) {
                serialized.put("args", locToArgsMap.get(loc));
            }
            map.computeIfAbsent(s, s1 -> new ArrayList<>()).add(serialized);
        });

        // Clear old config
        addon.getConfig().getKeys(false).forEach(addon.getConfig()::remove);

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
