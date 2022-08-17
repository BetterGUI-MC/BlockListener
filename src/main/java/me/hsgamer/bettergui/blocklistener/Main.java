package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.blocklistener.command.Remove;
import me.hsgamer.bettergui.blocklistener.command.Set;
import me.hsgamer.bettergui.blocklistener.listener.NewBlockListener;
import me.hsgamer.bettergui.blocklistener.listener.OldBlockListener;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.utils.VersionUtils;
import me.hsgamer.hscore.config.Config;

import java.io.File;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main extends PluginAddon {

    private final ExtraMessageConfig messageConfig = new ExtraMessageConfig(new BukkitConfig(new File(getDataFolder(), "messages.yml")));
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final BlockStorage storage = new BlockStorage(this);
    private final Set set = new Set(this);
    private final Remove remove = new Remove(this);

    public BlockStorage getStorage() {
        return storage;
    }

    @Override
    public void onEnable() {
        config.setup();
        messageConfig.setup();
        getInstance().registerListener(VersionUtils.isAtLeast(9) ? new NewBlockListener(this) : new OldBlockListener(this));
        storage.load();
        getInstance().registerCommand(set);
        getInstance().registerCommand(remove);
    }

    @Override
    public void onDisable() {
        storage.save();
        getInstance().getCommandManager().unregister(set);
        getInstance().getCommandManager().unregister(remove);
    }

    @Override
    public void onReload() {
        storage.save();
        config.reload();
        messageConfig.reload();
        storage.load();
    }

    public Config getConfig() {
        return config;
    }

    public ExtraMessageConfig getMessageConfig() {
        return messageConfig;
    }
}
