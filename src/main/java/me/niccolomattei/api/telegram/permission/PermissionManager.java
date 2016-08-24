package me.niccolomattei.api.telegram.permission;

import me.niccolomattei.api.telegram.Bot;
import me.niccolomattei.api.telegram.User;
import me.niccolomattei.api.telegram.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niccolò Mattei on 22/08/2016.
 */
public class PermissionManager {

    private final Configuration permissionConfig;

    public PermissionManager(Bot bot) {
        Configuration.generateDefault(Configuration.defaultPath + "permission.json");
        permissionConfig = new Configuration(Configuration.defaultPath + "permission.json");

        permissionConfig.set("permission.users", null);
        permissionConfig.save();
    }

    public boolean hasPermission(User user, Permission permission) {
        if (permissionConfig.contains("permission.users." + user.getId()))
            return permissionConfig.getCollectionByClass(String.class, "permission.users." + user.getId()).contains(permission.getPermission());
        return false;
    }

    public List<Permission> getPermissions(User user) {
        List<Permission> permissions = new ArrayList<>();

        if (permissionConfig.contains("permission.users." + user.getId())) {
            permissionConfig.getCollectionByClass(String.class, "permission.users." + user.getId()).forEach(a -> permissions.add(new Permission(a)));
        }

        return permissions;
    }

    public void addUserToGroup(User user, String unlocalizedGroupID) {
        if (getPermissionGroup(unlocalizedGroupID) != null) {
            PermissionGroup toSet = getPermissionGroup(unlocalizedGroupID);
            toSet.getUsers().add(user.getId());
            setPermissionGroup(toSet);
        }
    }

    public void removeUserFromGroup(User user, String unlocalizedGroupID) {
        if (getPermissionGroup(unlocalizedGroupID) != null) {
            PermissionGroup toSet = getPermissionGroup(unlocalizedGroupID);
            toSet.getUsers().remove(user.getId());
            setPermissionGroup(toSet);
        }
    }

    public void setUser(User user, Permission... permissions) {
        permissionConfig.set("permission.users." + user.getId(), permissions);
        permissionConfig.save();
    }

    public void setPermissionGroup(PermissionGroup group) {
        permissionConfig.set("permission.groups." + group.getUnlocalizedID() + ".name", group.getName());
        permissionConfig.set("permission.groups." + group.getUnlocalizedID() + ".users", group.getUsers());
        permissionConfig.set("permission.groups." + group.getUnlocalizedID() + ".permissions", group.getPermissions());
        permissionConfig.save();
    }

    public PermissionGroup getPermissionGroup(String unlocalizedID) {
        if (!permissionConfig.contains("permission.groups." + unlocalizedID + ".name")) return null;
        String name = permissionConfig.getString("permission.groups." + unlocalizedID + ".name");
        List<Integer> users = (List<Integer>) permissionConfig.getCollectionByClass(Integer.class, "permission.groups." + unlocalizedID + ".users");
        List<Permission> permissions = new ArrayList<>();

        permissionConfig.getCollectionByClass(String.class, "permission.groups." + unlocalizedID + "").forEach(a -> permissions.add(new Permission(a)));

        PermissionGroup res = new PermissionGroup(unlocalizedID, name, (Permission[]) permissions.toArray());
        res.addUserIds(users.toArray(new Integer[users.size()]));

        return res;
    }

    public PermissionGroup getUserPermissionGroup(int userId) {
        for (String key : permissionConfig.getSection("permission.group").keySet()) {
            PermissionGroup group = getPermissionGroup(key);
            if (group.getUsers().contains(userId)) return group;
        }
        return null;
    }
}
