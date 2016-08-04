package me.niccolomattei.api.telegram.permission;

import java.util.List;

public interface Permissionable {
	
	List<Permission> getPermission();
	
	void addPermission(Permission permission);
	
	void addPermission(String s);
	
	boolean hasPermission(Permission p);
	
	boolean hasPermission(String s);
	
	/**
	 * @return null if the user is not on a group.
	 */
	PermissionGroup getPermissionGroup();
	
	void setPermissionGroup(PermissionGroup group);

}
