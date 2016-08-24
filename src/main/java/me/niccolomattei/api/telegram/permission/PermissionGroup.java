package me.niccolomattei.api.telegram.permission;

import me.niccolomattei.api.telegram.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PermissionGroup {

	private List<Permission> permissions = null;
	private String unlocalizedID = null;
	private List<Integer> users = null;
	private String name = null;

	public PermissionGroup(String unlocalizedID, String name, Permission... permissions) {
		this.permissions = Arrays.asList(permissions);
		this.name = name;
		this.unlocalizedID = unlocalizedID;
		this.users = new ArrayList<>();
	}
	
	public void addPermission(Permission... permissions) {
		this.permissions.addAll(Arrays.asList(permissions));
	}

	public void removePermission(Permission permission) {
		this.permissions.remove(permission);
	}

	public void addUser(User... users) {
		for(User user : users) this.users.add(user.getId());
	}

	public void addUserIds(Integer... userIds) {
		this.users.addAll(Arrays.asList(userIds));
	}

	public Collection<Integer> getUsers() {
		return users;
	}

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public String getUnlocalizedID() {
		return unlocalizedID;
	}

	public Permission forName(String s) {
		for(Permission p : permissions) {
			if(p.getPermission().equalsIgnoreCase(s)) {
				return p;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}
}
