package me.hsgamer.bettergui.blocklistener.command;

import java.util.Arrays;
import me.hsgamer.bettergui.blocklistener.Main;
import me.hsgamer.bettergui.blocklistener.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig;
import me.hsgamer.bettergui.util.CommonUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class Remove extends BukkitCommand {

  public Remove() {
    super("removeblockmenu", "Remove the linked block", "/removeblockmenu",
        Arrays.asList("removebmenu", "rbm"));
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (!(commandSender instanceof Player)) {
      CommonUtils.sendMessage(commandSender, MessageConfig.PLAYER_ONLY.getValue());
      return false;
    }
    if (!commandSender.hasPermission(Permissions.REMOVE)) {
      CommonUtils.sendMessage(commandSender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }
    Block block = ((Player) commandSender).getTargetBlock(null, 5);
    if (block != null) {
      Location loc = block.getLocation();
      if (Main.getStorage().contains(loc)) {
        Main.getStorage().remove(loc);
        CommonUtils.sendMessage(commandSender, MessageConfig.SUCCESS.getValue());
        return true;
      } else {
        CommonUtils.sendMessage(commandSender, Main.LOC_NOT_FOUND.getValue());
        return false;
      }
    } else {
      CommonUtils.sendMessage(commandSender, Main.BLOCK_REQUIRED.getValue());
      return false;
    }
  }
}
