package me.hsgamer.bettergui.blocklistener.command;

import java.util.Arrays;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.blocklistener.InteractiveLocation;
import me.hsgamer.bettergui.blocklistener.Main;
import me.hsgamer.bettergui.blocklistener.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.CommonUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class Set extends BukkitCommand {

  public Set() {
    super("setblockmenu", "Link the target block to a menu", "/setblockmenu <menu> [args]",
        Arrays.asList("setbmenu", "blockmenu", "sbm"));
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (commandSender instanceof Player) {
      if (commandSender.hasPermission(Permissions.SET)) {
        if (strings.length > 0) {
          String menu = strings[0];
          Block block = ((Player) commandSender).getTargetBlock(null, 5);
          if (block != null) {
            Location loc = block.getLocation();
            if (!Main.getStorage().contains(loc)) {
              InteractiveLocation interactiveLocation = new InteractiveLocation(loc);
              if (strings.length >= 2) {
                interactiveLocation
                    .setArgs(Arrays.asList(Arrays.copyOfRange(strings, 1, strings.length)));
              }
              Main.getStorage().set(interactiveLocation, menu);
              CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
                  DefaultMessage.SUCCESS));
              return true;
            } else {
              CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
                  String.class, "location-already-set", "&cThe location is already set"));
              return false;
            }
          } else {
            CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
                String.class, "block-required", "&cYou should look at a block"));
            return false;
          }
        } else {
          CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
              DefaultMessage.MENU_REQUIRED));
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
