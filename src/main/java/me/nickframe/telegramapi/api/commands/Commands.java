package me.nickframe.telegramapi.api.commands;

import java.util.ArrayList;
import java.util.List;

public class Commands {
	
	private static List<CommandListener> cmds = new ArrayList<CommandListener>();
	
	public static void registerCommandListener(CommandListener commandListener) {
		cmds.add(commandListener);
	}
	
	public static List<CommandListener> getCommands() {
		return cmds;
	}
	
}
