package me.hsgamer.bettergui.blocklistener.listener;

import me.hsgamer.bettergui.blocklistener.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class NewBlockListener extends BlockListener {

    public NewBlockListener(Main main) {
        super(main);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            interact(event);
        }
    }
}
