package me.niccolomattei.api.telegram.permission;

import java.util.List;

public interface Permissionable {
	
	List<Permission> getPermissions();
	
	void setPermission(Permission... permission);
	
	void setPermission(String... s);
	
	boolean hasPermission(Permission p);
	
	boolean hasPermission(String s);
	
	/**
	 * @return null if the user is not in a group.
	 */
	PermissionGroup getPermissionGroup();
	
	void setPermissionGroup(PermissionGroup group);

}
