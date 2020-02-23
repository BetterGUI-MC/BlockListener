package me.hsgamer.bettergui.blocklistener;

import java.util.ArrayList;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.CommonUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class Command extends BukkitCommand {

  public Command() {
    super("block", "Command for Block", "", new ArrayList<>());
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (commandSender instanceof Player) {
      if (strings.length > 0) {
        if (strings[0].equalsIgnoreCase("set")) {
          if (strings.length > 1) {
            String menu = strings[1];
            Location loc = ((Player) commandSender).getTargetBlock(null, 5).getLocation();
            if (!Main.getStorage().contains(loc)) {
              Main.getStorage().set(loc, menu);
              CommonUtils.sendMessage(commandSender, "Success");
            } else {
              CommonUtils.sendMessage(commandSender, "Loc Already Set");
            }
          }
        } else if (strings[0].equalsIgnoreCase("remove")) {
          Location loc = ((Player) commandSender).getTargetBlock(null, 5).getLocation();
          if (Main.getStorage().contains(loc)) {
            Main.getStorage().remove(loc);
            CommonUtils.sendMessage(commandSender, "Success");
          } else {
            CommonUtils.sendMessage(commandSender, "Not Found Loc");
          }
        }
      }
    } else {
      CommonUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().get(
          DefaultMessage.PLAYER_ONLY));
    }
    return false;
  }
}
