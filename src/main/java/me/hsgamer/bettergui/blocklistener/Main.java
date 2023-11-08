package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.extra.expansion.DataFolder;
import me.hsgamer.hscore.logger.common.LogLevel;
import me.hsgamer.hscore.logger.common.Logger;
import me.hsgamer.hscore.logger.provider.LoggerProvider;
import org.bukkit.Bukkit;

import java.io.File;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main implements Expansion, DataFolder {
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final BlockStorage storage = new BlockStorage(this);
    private final Convert convert = new Convert(this);

    public BlockStorage getStorage() {
        return storage;
    }

    @Override
    public boolean onLoad() {
        Logger logger = LoggerProvider.getLogger("BlockListener");
        if (!Bukkit.getPluginManager().isPluginEnabled("BloCommands")) {
            logger.log(LogLevel.ERROR, "The addon is deprecated and will be removed in the future");
            logger.log(LogLevel.ERROR, "Please use BloCommands instead");
            logger.log(LogLevel.ERROR, "https://github.com/Folia-Inquisitors/BloCommands/releases/latest");
            logger.log(LogLevel.ERROR, "Install it and use /convertblockmenu to convert the menus");
            return false;
        } else {
            logger.log(LogLevel.WARN, "The addon is deprecated and will be removed in the future");
            logger.log(LogLevel.WARN, "Please use BloCommands instead");
            logger.log(LogLevel.WARN, "Use /convertblockmenu to convert the menus");
            return true;
        }
    }

    @Override
    public void onEnable() {
        config.setup();
        storage.load();
        getInstance().registerCommand(convert);
    }

    @Override
    public void onDisable() {
        storage.save();
        getInstance().getCommandManager().unregister(convert);
    }

    public Config getConfig() {
        return config;
    }
}
