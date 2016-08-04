package me.niccolomattei.api.telegram.utils;

import me.niccolomattei.api.telegram.utils.text.TextBase;

public interface MessengerClass {

	void sendMessage(TextBase base, String reply_to_message_id, boolean disable_web_preview,
			boolean disable_notification);
	
	void sendMessage(TextBase base);
	
	void sendText(String text, String reply_to_message_id, boolean disable_web_preview,
			boolean disable_notification);
	
	void sendText(String text);

}
