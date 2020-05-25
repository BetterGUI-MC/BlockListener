package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.blocklistener.command.Remove;
import me.hsgamer.bettergui.blocklistener.command.Set;
import me.hsgamer.bettergui.config.ConfigPath;
import me.hsgamer.bettergui.object.addon.Addon;

public final class Main extends Addon {

  public static final ConfigPath<String> LOC_NOT_FOUND = new ConfigPath<>(String.class,
      "location-not-found", "&cThe location is not found");
  public static final ConfigPath<String> LOC_ALREADY_SET = new ConfigPath<>(String.class,
      "location-already-set", "&cThe location is already set");
  public static final ConfigPath<String> BLOCK_REQUIRED = new ConfigPath<>(String.class,
      "block-required", "&cYou should look at a block");

  private static BlockStorage storage;

  public static BlockStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    setupConfig();
    registerListener(new BlockListener());

    LOC_NOT_FOUND.setConfig(getPlugin().getMessageConfig());
    LOC_ALREADY_SET.setConfig(getPlugin().getMessageConfig());
    BLOCK_REQUIRED.setConfig(getPlugin().getMessageConfig());
    getPlugin().getMessageConfig().saveConfig();

    return true;
  }

  @Override
  public void onEnable() {
    storage = new BlockStorage(this);
    registerCommand(new Set());
    registerCommand(new Remove());
  }

  @Override
  public void onDisable() {
    storage.save();
  }

  @Override
  public void onReload() {
    storage.save();
    reloadConfig();
    storage.load();
  }
}
