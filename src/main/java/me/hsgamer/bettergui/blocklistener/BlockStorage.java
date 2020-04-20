package me.hsgamer.bettergui.blocklistener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.Location;

public class BlockStorage {

  private final Map<InteractiveLocation, String> locToMenuMap = new HashMap<>();
  private final Addon addon;

  public BlockStorage(Addon addon) {
    this.addon = addon;
    load();
  }

  @SuppressWarnings("unchecked")
  public void load() {
    addon.getConfig().getKeys(false).forEach(s -> addon.getConfig().getMapList(s)
        .forEach(map -> locToMenuMap
            .put(InteractiveLocation.deserialize((Map<String, Object>) map), s + ".yml")));
  }

  public void save() {
    Map<String, List<Map<String, Object>>> map = new HashMap<>();
    locToMenuMap.forEach((loc, s) -> {
      s = s.replace(".yml", "");
      if (!map.containsKey(s)) {
        map.put(s, new ArrayList<>());
      }
      map.get(s).add(loc.serialize());
    });

    // Clear old config
    addon.getConfig().getKeys(false).forEach(s -> addon.getConfig().set(s, null));

    map.forEach((s, list) -> addon.getConfig().set(s, list));
    addon.saveConfig();
  }

  public void set(InteractiveLocation loc, String menu) {
    locToMenuMap.put(loc, menu);
  }

  public void remove(Location loc) {
    locToMenuMap.entrySet().removeIf(entry -> entry.getKey().getLocation().equals(loc));
  }

  public boolean contains(Location loc) {
    return getMenuFromLocation(loc).isPresent();
  }

  public Optional<Map.Entry<InteractiveLocation, String>> getMenuFromLocation(Location loc) {
    return locToMenuMap.entrySet().stream()
        .filter(entry -> entry.getKey().getLocation().equals(loc)).findFirst();
  }
}
