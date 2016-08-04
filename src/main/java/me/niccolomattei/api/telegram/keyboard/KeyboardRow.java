package me.niccolomattei.api.telegram.keyboard;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import me.niccolomattei.api.telegram.serialization.Ignorable;
import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializator;

@IgnoreClassName
public class KeyboardRow implements ReplyMarkup {

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
		return JSONSerializator.serialize(this);
	}

	@Override
	public JSONObject serializeJson() {
		buttons = getButtonsArray();
		return JSONSerializator.serializeJson(this);
	}

}
