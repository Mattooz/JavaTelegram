package me.niccolomattei.api.telegram.logger;

import me.niccolomattei.api.telegram.Message;
import me.niccolomattei.api.telegram.inline.CallbackQuery;
import me.niccolomattei.api.telegram.inline.ChosenInlineResult;
import me.niccolomattei.api.telegram.inline.InlineQuery;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Logger {

	Object lock = new Object();
	List<String> logged;
	String prefix = "";
	boolean logging;
	
	public Logger(String prefix) {
		this.prefix = prefix;
		logged = new ArrayList<>();
		logging = true;
	}
	
	public void info(String message) {
		if(logging) {
			synchronized (lock) {
				System.out.println("[" + prefix + " - INFO] " + message);
				logged.add("[" + prefix + " - INFO] " + message);
			}
		}
	}
	
	public void severe(String message) {
		if(logging) {
			synchronized (lock) {
				System.err.println("[" + prefix + " - SEVERE] " + message);
				logged.add("[" + prefix + " - SEVERE] " + message);
			}
		}
	}
	
	public void warning(String message) {
		if(logging) {
			synchronized (lock) {
				System.out.println("[" + prefix + " - WARNING] " + message);
				logged.add("[" + prefix + " - WARNING] " + message);
			}
		}
	}

	public List<String> getLogged() {
		return logged;
	}

	public void processException(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exception = sw.toString();

		for (String s : exception.split("\n")) {
			severe(s);
		}
	}

	public void enableLogging(boolean logging) {
		this.logging = logging;
	}

	public abstract void handleIncomingMessage(Message message);

	public abstract void handleIncomingModifiedMessage(Message message);

	public abstract void handleIncomingCallbackQuery(CallbackQuery callbackQuery);

	public abstract void handleIncomingInlineQuery(InlineQuery inlineQuery);

	public abstract void handleIncomingChosenInlineResult(ChosenInlineResult chosenInlineResult);
	
}
