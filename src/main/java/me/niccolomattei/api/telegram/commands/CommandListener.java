package me.niccolomattei.api.telegram.commands;

import me.niccolomattei.api.telegram.Chat.ChatType;
import me.niccolomattei.api.telegram.User;

public interface CommandListener {

	void onCommand(String commandname, String[] args, User from, long chat_id, ChatType chatType);
	
}
