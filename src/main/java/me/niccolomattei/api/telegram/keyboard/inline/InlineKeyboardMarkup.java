package me.niccolomattei.api.telegram.keyboard.inline;

import me.niccolomattei.api.telegram.keyboard.ReplyMarkup;
import me.niccolomattei.api.telegram.serialization.Ignorable;
import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niccolò Mattei on 07/09/2016.
 */
@IgnoreClassName
public class InlineKeyboardMarkup implements ReplyMarkup {

    private InlineKeyboardButton[][] inline_keyboard;

    @Ignorable
    protected List<InlineKeyboardRow> keyboardRows;

    public InlineKeyboardMarkup() {
        keyboardRows = new ArrayList<>();
    }

    public InlineKeyboardRow addRow() {

        if(!keyboardRows.isEmpty()) {
            for(InlineKeyboardRow row : keyboardRows) {
                for(InlineKeyboardButton button : row.toButtonArray()) {
                    System.out.println(button.serialize());
                }
            }
        }

        return new InlineKeyboardRow(this);
    }

    private void setupInlineArray() {
        inline_keyboard = new InlineKeyboardButton[keyboardRows.size()][];
        for (int i = 0; i < keyboardRows.size(); i++) {
            inline_keyboard[i] = keyboardRows.get(i).toButtonArray();
        }
    }

    @Override
    public String serialize() {
        setupInlineArray();
        return JSONSerializer.serialize(this);
    }

    @Override
    public JSONObject serializeJson() {
        setupInlineArray();
        return JSONSerializer.serializeJson(this);
    }
}
