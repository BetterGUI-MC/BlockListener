package me.hsgamer.bettergui.blocklistener.command;

import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.blocklistener.Main;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Arrays;

public class Remove extends BukkitCommand {
    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".removeblock", PermissionDefault.OP);

    public Remove() {
        super("removeblockmenu", "Remove the linked block", "/removeblockmenu",
                Arrays.asList("removebmenu", "rbm"));
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) {
            return false;
        }
        if (!(commandSender instanceof Player)) {
            MessageUtils.sendMessage(commandSender, MessageConfig.PLAYER_ONLY.getValue());
            return false;
        }
        Block block = ((Player) commandSender).getTargetBlock(null, 5);
        if (block != null) {
            Location loc = block.getLocation();
            if (Main.getStorage().contains(loc)) {
                Main.getStorage().remove(loc);
                MessageUtils.sendMessage(commandSender, MessageConfig.SUCCESS.getValue());
                return true;
            } else {
                MessageUtils.sendMessage(commandSender, Main.LOC_NOT_FOUND.getValue());
                return false;
            }
        } else {
            MessageUtils.sendMessage(commandSender, Main.BLOCK_REQUIRED.getValue());
            return false;
        }
    }
}
