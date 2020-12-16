package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.blocklistener.Main.getStorage;

public class BlockListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.hasBlock()) {
            Block block = event.getClickedBlock();
            if (block != null) {
                Optional<String> optional = getStorage().getMenuFromLocation(block.getLocation());
                if (optional.isPresent()) {
                    String menu = optional.get();
                    if (getInstance().getMenuManager().contains(menu)) {
                        event.setCancelled(true);
                        getInstance().getMenuManager()
                                .openMenu(menu, player, getStorage().getArgsFromLocation(block.getLocation()),
                                        false);
                    } else {
                        MessageUtils.sendMessage(player, MessageConfig.MENU_NOT_FOUND.getValue());
                    }
                }
            }
        }
    }
}
