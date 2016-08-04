package me.niccolomattei.api.telegram.events.defaults;

import me.niccolomattei.api.telegram.Message;
import me.niccolomattei.api.telegram.events.Event;

public class ModifyMessageEvent extends Event {

	private Message edited_message;

	public ModifyMessageEvent(Message edited_message) {
		this.edited_message = edited_message;
	}
	
	public Message getEditedMessage() {
		return edited_message;
	}

	@Override
	public String getEventName() {
		return super.getEventName();
	}

}
