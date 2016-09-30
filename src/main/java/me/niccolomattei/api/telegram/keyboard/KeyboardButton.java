package me.niccolomattei.api.telegram.keyboard;

import me.niccolomattei.api.telegram.serialization.ISerializable;
import org.json.JSONObject;

import me.niccolomattei.api.telegram.serialization.JSONSerializer;

public class KeyboardButton implements ISerializable {

	public String text;
	public boolean request_contact;
	public boolean request_location;

	public KeyboardButton(String text, boolean request_contact, boolean request_location) {
		super();
		this.text = text;
		this.request_contact = request_contact;
		this.request_location = request_location;
	}

	public String getText() {
		return text;
	}

	public boolean isRequest_contact() {
		return request_contact;
	}

	public boolean isRequest_location() {
		return request_location;
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
