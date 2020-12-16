package me.hsgamer.bettergui.blocklistener.command;

import me.hsgamer.bettergui.blocklistener.Main;
import me.hsgamer.bettergui.blocklistener.Permissions;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Set extends BukkitCommand {

    public Set() {
        super("setblockmenu", "Link the target block to a menu", "/setblockmenu <menu> [args]",
                Arrays.asList("setbmenu", "blockmenu", "sbm"));
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            MessageUtils.sendMessage(commandSender, MessageConfig.PLAYER_ONLY.getValue());
            return false;
        }
        if (!commandSender.hasPermission(Permissions.SET)) {
            MessageUtils.sendMessage(commandSender, MessageConfig.NO_PERMISSION.getValue());
            return false;
        }
        if (strings.length <= 0) {
            MessageUtils.sendMessage(commandSender, MessageConfig.MENU_REQUIRED.getValue());
            return false;
        }
        String menu = strings[0];
        Block block = ((Player) commandSender).getTargetBlock(null, 5);
        if (block != null) {
            Location loc = block.getLocation();
            if (!Main.getStorage().contains(loc)) {
                if (strings.length >= 2) {
                    Main.getStorage()
                            .setArgs(loc, String.join(" ", Arrays.copyOfRange(strings, 1, strings.length)));
                }
                Main.getStorage().set(loc, menu);
                MessageUtils.sendMessage(commandSender, MessageConfig.SUCCESS.getValue());
                return true;
            } else {
                MessageUtils.sendMessage(commandSender, Main.LOC_ALREADY_SET.getValue());
                return false;
            }
        } else {
            MessageUtils.sendMessage(commandSender, Main.BLOCK_REQUIRED.getValue());
            return false;
        }
    }
}
