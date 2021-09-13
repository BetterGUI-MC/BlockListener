package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.blocklistener.command.Remove;
import me.hsgamer.bettergui.blocklistener.command.Set;
import me.hsgamer.bettergui.blocklistener.listener.NewBlockListener;
import me.hsgamer.bettergui.blocklistener.listener.OldBlockListener;
import me.hsgamer.bettergui.lib.core.config.path.StringConfigPath;
import me.hsgamer.bettergui.lib.xseries.XMaterial;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main extends BetterGUIAddon {

    public static final StringConfigPath LOC_NOT_FOUND = new StringConfigPath("location-not-found", "&cThe location is not found");
    public static final StringConfigPath LOC_ALREADY_SET = new StringConfigPath("location-already-set", "&cThe location is already set");
    public static final StringConfigPath BLOCK_REQUIRED = new StringConfigPath("block-required", "&cYou should look at a block");

    private final BlockStorage storage = new BlockStorage(this);

    public BlockStorage getStorage() {
        return storage;
    }

    @Override
    public boolean onLoad() {
        setupConfig();
        registerListener(XMaterial.supports(9) ? new NewBlockListener(this) : new OldBlockListener(this));

        LOC_NOT_FOUND.setConfig(getInstance().getMessageConfig());
        LOC_ALREADY_SET.setConfig(getInstance().getMessageConfig());
        BLOCK_REQUIRED.setConfig(getInstance().getMessageConfig());
        getInstance().getMessageConfig().save();

        return true;
    }

    @Override
    public void onEnable() {
        storage.load();
        registerCommand(new Set(this));
        registerCommand(new Remove(this));
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
