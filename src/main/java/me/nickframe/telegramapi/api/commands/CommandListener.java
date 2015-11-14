package me.nickframe.telegramapi.api.commands;

import me.nickframe.telegramapi.api.User;

public interface CommandListener {

	void onCommand(String commandname, String[] args, User from, int chat_id);
	
}
