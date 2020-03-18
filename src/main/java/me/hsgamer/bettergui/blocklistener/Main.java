package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.blocklistener.command.Remove;
import me.hsgamer.bettergui.blocklistener.command.Set;
import me.hsgamer.bettergui.object.addon.Addon;

public final class Main extends Addon {

  private static BlockStorage storage;

  private Set setCommand;
  private Remove removeCommand;

  public static BlockStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    setupConfig();
    registerListener(new BlockListener());

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
    unregisterCommand(setCommand);
    unregisterCommand(removeCommand);
  }

  @Override
  public void onReload() {
    storage.save();
    storage.load();
  }
}
