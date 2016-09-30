package me.niccolomattei.api.telegram.keyboard;

import me.niccolomattei.api.telegram.serialization.Ignorable;
import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

@IgnoreClassName
public class Keyboard implements ReplyMarkup {

	@Ignorable
	List<KeyboardRow> list;

	public boolean resize_keyboard;
	public boolean one_time_keyboard;
	public boolean selective;
	public KeyboardButton[][] keyboard;

	public Keyboard(boolean resize_keyboard, boolean one_time_keyboard, boolean selective, KeyboardRow... list) {
		this.list = Arrays.asList(list);
		this.resize_keyboard = resize_keyboard;
		this.one_time_keyboard = one_time_keyboard;
		this.selective = selective;
	}

	public void addRow(KeyboardRow... keyboardRows) {
		list.addAll(Arrays.asList(keyboardRows));
	}

	public KeyboardRow[] getRowsArray() {
		return (KeyboardRow[]) list.toArray();
	}

	public int getTotalButtonSize() {
		int res = 0;
		for (KeyboardRow row : list) {
			res += row.getButtonsArray().length;
		}
		return res;
	}

	public KeyboardButton[][] getBidimensionalButtonsArray() {
		KeyboardButton[][] res;

		res = new KeyboardButton[list.size()][];

		for (int a = 0; a < getRowsArray().length; a++) {
			res[a] = new KeyboardButton[getRowsArray()[a].getButtonsArray().length];
			for (int b = 0; b < getRowsArray()[a].getButtonsArray().length; b++) {
				res[a][b] = getRowsArray()[a].getButtonsArray()[b];
			}
		}

		return res;
	}

	@Override
	public String serialize() {
		keyboard = getBidimensionalButtonsArray();
		return JSONSerializer.serialize(this);
	}

	@Override
	public JSONObject serializeJson() {
		keyboard = getBidimensionalButtonsArray();
		return JSONSerializer.serializeJson(this);
	}

}
