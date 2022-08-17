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

public class Set extends BukkitCommand {
    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".setblock", PermissionDefault.OP);
    private final Main main;

    public Set(Main main) {
        super("setblockmenu", "Link the target block to a menu", "/setblockmenu <menu> [args]",
                Arrays.asList("setbmenu", "blockmenu", "sbm"));
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
        if (strings.length == 0) {
            MessageUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().menuRequired);
            return false;
        }
        String menu = strings[0];
        Block block = ((Player) commandSender).getTargetBlock(null, 5);
        if (block == null) {
            MessageUtils.sendMessage(commandSender, main.getMessageConfig().blockRequired);
            return false;
        }
        Location loc = block.getLocation();
        if (main.getStorage().contains(loc)) {
            MessageUtils.sendMessage(commandSender, main.getMessageConfig().locAlreadySet);
            return false;
        }
        if (strings.length >= 2) {
            main.getStorage().setArgs(loc, String.join(" ", Arrays.copyOfRange(strings, 1, strings.length)));
        }
        main.getStorage().set(loc, menu);
        MessageUtils.sendMessage(commandSender, BetterGUI.getInstance().getMessageConfig().success);
        return true;
    }
}
