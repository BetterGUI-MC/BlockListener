package me.hsgamer.bettergui.blocklistener.command;

import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.blocklistener.Main;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
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
    private final Main main;

    public Remove(Main main) {
        super("removeblockmenu", "Remove the linked block", "/removeblockmenu",
                Arrays.asList("removebmenu", "rbm"));
        this.main = main;
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) {
            return false;
        }
        if (!(commandSender instanceof Player)) {
            MessageUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().playerOnly);
            return false;
        }
        Block block = ((Player) commandSender).getTargetBlock(null, 5);
        if (block == null) {
            MessageUtils.sendMessage(commandSender, main.getMessageConfig().blockRequired);
            return false;
        }
        Location loc = block.getLocation();
        if (!main.getStorage().contains(loc)) {
            MessageUtils.sendMessage(commandSender, main.getMessageConfig().locNotFound);
            return false;
        }
        main.getStorage().remove(loc);
        MessageUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().success);
        return true;
    }
}
