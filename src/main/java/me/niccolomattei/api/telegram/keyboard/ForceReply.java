package me.niccolomattei.api.telegram.keyboard;

import org.json.JSONObject;

import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;

@IgnoreClassName
public class ForceReply implements ReplyMarkup {

	public boolean force_reply = true;
	public boolean selective;

	public ForceReply(boolean selective) {
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
