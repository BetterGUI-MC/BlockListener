package me.hsgamer.bettergui.blocklistener;

import me.hsgamer.bettergui.Permissions;
import me.hsgamer.blocommands.BloCommands;
import me.hsgamer.blocommands.action.ConsoleAction;
import me.hsgamer.blocommands.api.block.ActionBlock;
import me.hsgamer.blocommands.api.block.BlockInteractType;
import me.hsgamer.blocommands.manager.BlockManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class Convert extends BukkitCommand {
    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".convertblockmenu", PermissionDefault.OP);
    private final Main main;

    public Convert(Main main) {
        super("convertblockmenu");
        this.main = main;
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) {
            return false;
        }

        Map<Location, String> locToMenuMap = main.getStorage().getLocToMenuMap();
        Map<Location, String> locToArgsMap = main.getStorage().getLocToArgsMap();

        BlockManager blockManager = JavaPlugin.getPlugin(BloCommands.class).getBlockManager();

        int count = 0;
        for (Map.Entry<Location, String> menuEntry : locToMenuMap.entrySet()) {
            Location location = menuEntry.getKey();
            String menu = menuEntry.getValue();
            String args = locToArgsMap.getOrDefault(location, "");

            ActionBlock actionBlock = blockManager.createBlock("bettergui-" + count++);
            actionBlock.setLocation(location);
            actionBlock.getActionBundle(BlockInteractType.LEFT_CLICK).addAction(new ConsoleAction("openmenu " + menu + " {player} " + args));
        }
        blockManager.save();

        commandSender.sendMessage("Converted " + count + " block menus");

        return true;
    }
}
