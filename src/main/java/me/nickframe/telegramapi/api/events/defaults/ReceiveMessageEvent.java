package me.nickframe.telegramapi.api.events.defaults;

import me.nickframe.telegramapi.api.Message;
import me.nickframe.telegramapi.api.events.Event;

public class ReceiveMessageEvent extends Event {
	
	private String name;
	private Message message;
	
	public ReceiveMessageEvent(Message message) {
		this.message = message;
	}
	
	public Message getMessage() {
		return this.message;
	}
	
	@Override
	public String getEventName() {
		if(name == null) name = super.getEventName();
		return super.getEventName();
	}

}
