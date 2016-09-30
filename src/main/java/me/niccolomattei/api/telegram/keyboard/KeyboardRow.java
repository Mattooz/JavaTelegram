package me.niccolomattei.api.telegram.keyboard;

import me.niccolomattei.api.telegram.serialization.ISerializable;
import me.niccolomattei.api.telegram.serialization.Ignorable;
import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

@IgnoreClassName
public class KeyboardRow implements ISerializable {

	@Ignorable
	private List<KeyboardButton> list;

	public KeyboardButton[] buttons;

	public KeyboardRow(KeyboardButton... buttons) {
		list = Arrays.asList(buttons);
	}

	public void addButton(KeyboardButton button) {
		list.add(button);
	}

	public KeyboardButton[] getButtonsArray() {
		return (KeyboardButton[]) list.toArray();
	}

	@Override
	public String serialize() {
		buttons = getButtonsArray();
		return JSONSerializer.serialize(this);
	}

	@Override
	public JSONObject serializeJson() {
		buttons = getButtonsArray();
		return JSONSerializer.serializeJson(this);
	}

}
