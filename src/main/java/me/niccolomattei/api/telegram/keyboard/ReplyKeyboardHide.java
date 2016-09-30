package me.niccolomattei.api.telegram.keyboard;

import org.json.JSONObject;

import me.niccolomattei.api.telegram.serialization.JSONSerializer;

public class ReplyKeyboardHide implements ReplyMarkup {

	boolean hide_keyboard = true;
	boolean selective;
	
	public ReplyKeyboardHide(boolean selective) {
		this.selective = selective;
	}
	
	@Override
	public String serialize() {
		return JSONSerializer.serialize(this);
	}

	@Override
	public JSONObject serializeJson() {
		return JSONSerializer.serializeJson(this);
	}

}
