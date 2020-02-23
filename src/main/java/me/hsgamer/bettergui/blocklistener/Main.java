package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.blocklistener.command.Remove;
import me.hsgamer.bettergui.blocklistener.command.Set;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.event.HandlerList;

public final class Main extends Addon {

  private static BlockStorage storage;
  private BlockListener listener;

  private Set setCommand;
  private Remove removeCommand;

  public static BlockStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    setupConfig();
    listener = new BlockListener();
    getPlugin().getServer().getPluginManager().registerEvents(listener, getPlugin());

    getPlugin().getMessageConfig().getConfig()
        .addDefault("location-not-found", "&cThe location is not found");
    getPlugin().getMessageConfig().getConfig()
        .addDefault("location-already-set", "&cThe location is already set");
    getPlugin().getMessageConfig().getConfig()
        .addDefault("block-required", "&cYou should look at a block");
    getPlugin().getMessageConfig().saveConfig();

    return true;
  }

  @Override
  public void onEnable() {
    storage = new BlockStorage(this);
    setCommand = new Set();
    removeCommand = new Remove();
    registerCommand(setCommand);
    registerCommand(removeCommand);
  }

  @Override
  public void onDisable() {
    storage.save();
    HandlerList.unregisterAll(listener);
    unregisterCommand(setCommand);
    unregisterCommand(removeCommand);
  }
}
