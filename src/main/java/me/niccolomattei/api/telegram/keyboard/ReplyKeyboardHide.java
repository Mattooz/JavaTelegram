package me.niccolomattei.api.telegram.keyboard;

import org.json.JSONObject;

import me.niccolomattei.api.telegram.serialization.JSONSerializator;

public class ReplyKeyboardHide implements ReplyMarkup {

	boolean hide_keyboard = true;
	boolean selective;
	
	public ReplyKeyboardHide(boolean selective) {
		this.selective = selective;
	}
	
	@Override
	public String serialize() {
		return JSONSerializator.serialize(this);
	}

	@Override
	public JSONObject serializeJson() {
		return JSONSerializator.serializeJson(this);
	}

}
