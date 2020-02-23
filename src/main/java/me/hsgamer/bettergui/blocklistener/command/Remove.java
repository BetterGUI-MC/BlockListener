package me.hsgamer.bettergui.blocklistener.command;

import java.util.Arrays;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.blocklistener.Main;
import me.hsgamer.bettergui.blocklistener.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.CommonUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class Remove extends BukkitCommand {

  public Remove() {
    super("removeblockmenu", "Remove the linked block", "removeblockmenu",
        Arrays.asList("removebmenu", "rbm"));
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (commandSender instanceof Player) {
      if (commandSender.hasPermission(Permissions.REMOVE)) {
        Block block = ((Player) commandSender).getTargetBlock(null, 5);
        if (block != null) {
          Location loc = block.getLocation();
          if (Main.getStorage().contains(loc)) {
            Main.getStorage().remove(loc);
            CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
                DefaultMessage.SUCCESS));
            return true;
          } else {
            CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
                String.class, "location-not-found", "&cThe location is not found"));
            return false;
          }
        } else {
          CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
              String.class, "block-required", "&cYou should look at a block"));
          return false;
        }
      } else {
        CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
            DefaultMessage.NO_PERMISSION));
        return false;
      }
    } else {
      CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
          DefaultMessage.PLAYER_ONLY));
      return false;
    }
  }
}
