package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.blocklistener.command.Remove;
import me.hsgamer.bettergui.blocklistener.command.Set;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public final class Main extends Addon {

  private static BlockStorage storage;

  public static BlockStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    ConfigurationSerialization.registerClass(InteractiveLocation.class);
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
