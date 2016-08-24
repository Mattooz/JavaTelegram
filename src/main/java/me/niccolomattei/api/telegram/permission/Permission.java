package me.niccolomattei.api.telegram.permission;

public class Permission {

	private String permission;
	
	public Permission(String string) {
		this.permission = string;
	}
	
	public String getPermission() {
		return permission;
	}

	@Override
	public String toString() {
		return permission;
	}
}
