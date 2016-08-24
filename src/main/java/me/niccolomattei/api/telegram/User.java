package me.niccolomattei.api.telegram;

import me.niccolomattei.api.telegram.permission.Permission;
import me.niccolomattei.api.telegram.permission.PermissionGroup;
import me.niccolomattei.api.telegram.permission.Permissionable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Utente on 23/10/2015.
 */
public class User implements BotObject, Permissionable {

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private Bot currentBot;

    public User(int id, String first_name) {
        this.id = id;
        this.first_name = first_name;
    }

    public User(int id, String first_name, String username) {
        this.id = id;
        this.first_name = first_name;
        this.username = username;
    }

    public User(int id, String first_name, String last_name, String username, Bot bot) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.currentBot = bot;
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
	public List<Permission> getPermissions() {
        return currentBot.getPermissionManager().getPermissions(this);
	}

	@Override
	public boolean hasPermission(Permission p) {
		return currentBot.getPermissionManager().hasPermission(this, p);
	}

	@Override
	public PermissionGroup getPermissionGroup() {
		 return currentBot.getPermissionManager().getUserPermissionGroup(id);
	}

	@Override
	public void setPermission(Permission... permission) {
		currentBot.getPermissionManager().setUser(this, permission);
	}

	@Override
	public void setPermissionGroup(PermissionGroup group) {
		currentBot.getPermissionManager().addUserToGroup(this, group.getUnlocalizedID());
	}

	@Override
	public void setPermission(String... s) {
		List<Permission> toAdd = new ArrayList<>();
        for(String str : s) {
            toAdd.add(new Permission(str));
        }
        setPermission(toAdd.toArray(new Permission[toAdd.size()]));
	}

	@Override
	public boolean hasPermission(String s) {
		return hasPermission(new Permission(s));
	}

}
