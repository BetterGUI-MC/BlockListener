package me.hsgamer.bettergui.blocklistener.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class OldBlockListener implements BlockListener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        interact(event);
    }
}
