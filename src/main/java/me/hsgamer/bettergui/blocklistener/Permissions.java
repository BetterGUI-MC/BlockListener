package me.hsgamer.bettergui.blocklistener;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import static me.hsgamer.bettergui.lib.core.bukkit.utils.PermissionUtils.createPermission;

public class Permissions {

    public static final Permission SET = createPermission("bettergui.setblock", null,
            PermissionDefault.OP);
    public static final Permission REMOVE = createPermission("bettergui.removeblock", null,
            PermissionDefault.OP);
}
