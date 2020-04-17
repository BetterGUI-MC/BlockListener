package me.hsgamer.bettergui.blocklistener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class InteractiveLocation implements ConfigurationSerializable {

  private final Location location;
  private final List<String> args = new ArrayList<>();

  public InteractiveLocation(Location location) {
    this.location = location;
  }

  @SuppressWarnings("unchecked")
  public static InteractiveLocation deserialize(Map<String, Object> map) {
    InteractiveLocation loc = new InteractiveLocation(Location.deserialize(map));
    if (map.containsKey("args")) {
      loc.setArgs((List<String>) map.get("args"));
    }
    return loc;
  }

  public Location getLocation() {
    return location;
  }

  public List<String> getArgs() {
    return args;
  }

  public void setArgs(List<String> input) {
    this.args.addAll(input);
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> map = location.serialize();
    if (!args.isEmpty()) {
      map.put("args", args);
    }
    return map;
  }
}
