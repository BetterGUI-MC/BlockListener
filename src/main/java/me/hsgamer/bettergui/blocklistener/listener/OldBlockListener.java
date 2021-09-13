package me.hsgamer.bettergui.blocklistener.listener;

import me.hsgamer.bettergui.blocklistener.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class OldBlockListener extends BlockListener {

    public OldBlockListener(Main main) {
        super(main);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        interact(event);
    }
}
