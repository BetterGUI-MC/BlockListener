package me.hsgamer.bettergui.blocklistener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class BlockStorage {

  private Map<Location, String> locToMenuMap = new HashMap<>();
  private Addon addon;
  private FileConfiguration config;

  public BlockStorage(Addon addon) {
    this.addon = addon;
    this.config = addon.getConfig();
    load();
  }

  @SuppressWarnings("unchecked")
  public void load() {
    config.getKeys(false).forEach(s -> config.getMapList(s)
        .forEach(map -> locToMenuMap.put(Location.deserialize((Map<String, Object>) map), s + ".yml")));
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
    map.forEach(config::set);
    addon.saveConfig();
  }

  public void set(Location loc, String menu) {
    locToMenuMap.put(loc, menu);
  }

  public void remove(Location loc) {
    locToMenuMap.remove(loc);
  }

  public boolean contains(Location loc) {
    return locToMenuMap.containsKey(loc);
  }

  public String getMenuFromLocation(Location loc) {
    return locToMenuMap.get(loc);
  }
}
