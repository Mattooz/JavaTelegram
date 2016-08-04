package me.niccolomattei.api.telegram;

import java.util.List;

import me.niccolomattei.api.telegram.permission.Permission;
import me.niccolomattei.api.telegram.permission.PermissionGroup;
import me.niccolomattei.api.telegram.permission.Permissionable;

/**
 * Created by Utente on 23/10/2015.
 */
public class User implements BotObject, Permissionable {

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    List<Permission> permission;
    PermissionGroup group;

    public User(int id, String first_name) {
        this.id = id;
        this.first_name = first_name;
    }

    public User(int id, String first_name, String username) {
        this.id = id;
        this.first_name = first_name;
        this.username = username;
    }

    public User(int id, String first_name, String last_name, String username) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BotObject getObject() {
        return this;
    }

	@Override
	public List<Permission> getPermission() {
		return permission;
	}

	@Override
	public boolean hasPermission(Permission p) {
		return permission.contains(p);
	}

	@Override
	public PermissionGroup getPermissionGroup() {
		 return group;
	}

	@Override
	public void addPermission(Permission permission) {
		this.permission.add(permission);
	}

	@Override
	public void setPermissionGroup(PermissionGroup group) {
		this.group = group;
	}

	@Override
	public void addPermission(String s) {
		addPermission(new Permission(s));
	}

	@Override
	public boolean hasPermission(String s) {
		return hasPermission(new Permission(s));
	}

}
