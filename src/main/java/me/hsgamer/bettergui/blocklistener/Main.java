package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.event.HandlerList;

public final class Main extends Addon {

  private static BlockStorage storage;
  private BlockListener listener;
  private Command command;

  public static BlockStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    setupConfig();
    listener = new BlockListener();
    command = new Command();
    getPlugin().getServer().getPluginManager().registerEvents(listener, getPlugin());
    return true;
  }

  @Override
  public void onEnable() {
    storage = new BlockStorage(this);
    getPlugin().getCommandManager().register(command);
  }

  @Override
  public void onDisable() {
    storage.save();
    HandlerList.unregisterAll(listener);
    getPlugin().getCommandManager().unregister(command);
  }
}
