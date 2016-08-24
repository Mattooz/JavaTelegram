package me.niccolomattei.api.telegram.logger;

public class Logger {

	String prefix = "";
	
	public Logger(String prefix) {
		this.prefix = prefix;
	}
	
	public void info(String message) {
        System.out.println("[" + prefix + " - INFO] " + message);
	}
	
	public void severe(String message) {
		System.err.println("[" + prefix + " - SEVERE] " + message);
	}
	
	public void warning(String message) {
		System.out.println("[" + prefix + " - WARNING] " + message);
	}
	
}
