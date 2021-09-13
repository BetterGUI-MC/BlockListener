package me.hsgamer.bettergui.blocklistener.listener;

import me.hsgamer.bettergui.blocklistener.Main;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public abstract class BlockListener implements Listener {
    private final Main main;

    protected BlockListener(Main main) {
        this.main = main;
    }

    public void interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.hasBlock()) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Location location = block.getLocation();
        Optional<String> optional = main.getStorage().getMenuFromLocation(location);
        if (!optional.isPresent()) {
            return;
        }
        String menu = optional.get();
        if (getInstance().getMenuManager().contains(menu)) {
            String[] args = main.getStorage().getArgsFromLocation(location);
            event.setCancelled(true);
            getInstance().getMenuManager().openMenu(menu, player, args, false);
        } else {
            MessageUtils.sendMessage(player, MessageConfig.MENU_NOT_FOUND.getValue());
        }
    }
}
