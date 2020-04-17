package me.hsgamer.bettergui.blocklistener;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.blocklistener.Main.getStorage;

import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.CommonUtils;
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
        Optional<Map.Entry<InteractiveLocation, String>> optional = getStorage()
            .getMenuFromLocation(block.getLocation());
        if (optional.isPresent()) {
          Map.Entry<InteractiveLocation, String> entry = optional.get();
          String menu = entry.getValue();
          if (getInstance().getMenuManager().contains(menu)) {
            event.setCancelled(true);
            getInstance().getMenuManager()
                .openMenu(menu, player, entry.getKey().getArgs().toArray(new String[0]), false);
          } else {
            CommonUtils.sendMessage(player, getInstance().getMessageConfig().get(
                DefaultMessage.MENU_NOT_FOUND));
          }
        }
      }
    }
  }
}
