package me.niccolomattei.api.telegram.commands;

import java.util.ArrayList;
import java.util.List;

import me.niccolomattei.api.telegram.Bot;
import me.niccolomattei.api.telegram.Message;

public class Commands {

	private static List<CommandListener> cmds = new ArrayList<CommandListener>();

	public static void registerCommandListener(CommandListener commandListener) {
		cmds.add(commandListener);
	}

	public static List<CommandListener> getCommands() {
		return cmds;
	}

	public static void trigger(Message latestMessage) {
		String splitted = latestMessage.getText().replaceFirst("/", "");
		String cmd = "";
		String[] args = new String[0];

		String[] splitted2 = splitted.split(" ");

		cmd = splitted2[0];
		String[] splitted3 = cmd.split("@");

		if (splitted3.length > 1) {
			if (Bot.currentBot.getMe().getUsername().toLowerCase().equalsIgnoreCase(splitted3[1].toLowerCase())) {
				if (splitted2.length >= 1) {
					args = new String[splitted2.length - 1];

					for (int z = 0; z < args.length; z++) {
						args[z] = splitted2[z + 1];
					}
				}

				for (int c = 0; c < Commands.getCommands().size(); c++) {
					Commands.getCommands().get(c).onCommand(splitted3[0], args, latestMessage.getFrom(),
							latestMessage.getChat().getId(), latestMessage.getChat().getType());
				}
			}
		} else {

			if (splitted2.length >= 1) {
				args = new String[splitted2.length - 1];

				for (int z = 0; z < args.length; z++) {
					args[z] = splitted2[z + 1];
				}
			}

			for (int c = 0; c < Commands.getCommands().size(); c++) {
				Commands.getCommands().get(c).onCommand(cmd, args, latestMessage.getFrom(),
						latestMessage.getChat().getId(), latestMessage.getChat().getType());
			}
		}
	}

}
