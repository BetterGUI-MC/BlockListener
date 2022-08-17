package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.config.annotated.AnnotatedConfig;
import me.hsgamer.hscore.config.annotation.ConfigPath;

public class ExtraMessageConfig extends AnnotatedConfig {
    public final @ConfigPath("location-not-found") String locNotFound;
    public final @ConfigPath("location-already-set") String locAlreadySet;
    public final @ConfigPath("block-required") String blockRequired;

    public ExtraMessageConfig(Config config) {
        super(config);
        locNotFound = "&cThe location is not found";
        locAlreadySet = "&cThe location is already set";
        blockRequired = "&cYou should look at a block";
    }
}
