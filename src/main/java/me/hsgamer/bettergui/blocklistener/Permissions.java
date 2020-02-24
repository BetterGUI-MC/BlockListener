package me.hsgamer.bettergui.blocklistener;

import static me.hsgamer.bettergui.Permissions.createPermission;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Permissions {

  public static final Permission SET = createPermission("bettergui.setblock", null,
      PermissionDefault.OP);
  public static final Permission REMOVE = createPermission("bettergui.removeblock", null,
      PermissionDefault.OP);
}
