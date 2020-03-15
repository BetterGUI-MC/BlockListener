package me.hsgamer.bettergui.blocklistener;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.blocklistener.Main.getStorage;

import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.CommonUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    if (event.hasBlock()) {
      Block block = event.getClickedBlock();
      if (block != null) {
        Location loc = block.getLocation();
        if (getStorage().contains(loc)) {
          String menu = getStorage().getMenuFromLocation(loc);
          if (getInstance().getMenuManager().contains(menu)) {
            getInstance().getMenuManager().openMenu(menu, player, false);
          } else {
            CommonUtils.sendMessage(player, getInstance().getMessageConfig().get(
                DefaultMessage.MENU_NOT_FOUND));
          }
        }
      }
    }
  }
}
