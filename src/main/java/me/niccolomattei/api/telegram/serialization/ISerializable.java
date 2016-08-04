package me.niccolomattei.api.telegram.serialization;

import org.json.JSONObject;

public interface ISerializable {

	String serialize();
	
	JSONObject serializeJson();

}
