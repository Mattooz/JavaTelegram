package me.niccolomattei.api.telegram.permission;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PermissionGroup {

	Collection<Permission> permissions = null;
	String unlocalizedID = null;
	
	public PermissionGroup(String unlocalizedID, Permission... permissions) {
		this.permissions = Arrays.asList(permissions);
		this.unlocalizedID = unlocalizedID;
	}
	
	public PermissionGroup(Collection<Permission> permissions) {
		this.permissions = (List<Permission>) permissions;
	}
	
	public void addPermission(Permission... permissions) {
		this.permissions.addAll(Arrays.asList(permissions));
	}

	public void removePermission(Permission permission) {
		this.permissions.remove(permission);
	}
	
	public Permission forName(String s) {
		for(Permission p : permissions) {
			if(p.getPermission().equalsIgnoreCase(s)) {
				return p;
			}
		}
		return null;
	}
}
