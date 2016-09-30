package me.niccolomattei.api.telegram.keyboard.inline;

import me.niccolomattei.api.telegram.serialization.ISerializable;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import me.niccolomattei.api.telegram.serialization.SerializationProperty;
import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 11/09/2016.
 */
public class InlineKeyboardButton implements ISerializable{

    private String text;
    @SerializationProperty(propertyName = "url", required = false)
    private String url;
    @SerializationProperty(propertyName = "callback_data", required = false)
    private String callback_data;
    @SerializationProperty(propertyName = "switch_inline_query", required = false)
    private String switch_inline_query;

    public InlineKeyboardButton() {

    }

    public InlineKeyboardButton(String text) {
        this.text = text;
    }

    public InlineKeyboardButton(String text, String url, String callback_data, String switch_inline_query) {
        this.text = text;
        this.url = url;
        this.callback_data = callback_data;
        this.switch_inline_query = switch_inline_query;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getCallback_data() {
        return callback_data;
    }

    public String getSwitch_inline_query() {
        return switch_inline_query;
    }

    @Override
    public String serialize() {
        return JSONSerializer.serialize(this);
    }

    @Override
    public JSONObject serializeJson() {
        return JSONSerializer.serializeJson(this);
    }

    @Override
    public String toString() {
        return serialize();
    }
}
