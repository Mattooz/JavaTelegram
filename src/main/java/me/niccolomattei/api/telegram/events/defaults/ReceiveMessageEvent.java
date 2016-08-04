package me.niccolomattei.api.telegram.events.defaults;

import me.niccolomattei.api.telegram.Message;
import me.niccolomattei.api.telegram.events.Event;

public class ReceiveMessageEvent extends Event {

	private Message message;

	public ReceiveMessageEvent(Message message) {
		this.message = message;
	}

	public Message getMessage() {
		return this.message;
	}

	@Override
	public String getEventName() {
		return super.getEventName();
	}

}
