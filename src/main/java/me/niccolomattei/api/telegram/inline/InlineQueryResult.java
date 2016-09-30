package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.keyboard.inline.InlineKeyboardMarkup;
import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import me.niccolomattei.api.telegram.serialization.SerializationProperty;
import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 06/09/2016.
 */
@IgnoreClassName
public class InlineQueryResult implements InlineResult {

    private String type;
    private String id;
    private InputMessageContent input_message_content;
    @SerializationProperty(propertyName = "reply_markup", required = false)
    private InlineKeyboardMarkup reply_markup;

    public InlineQueryResult() {
    }

    public InlineQueryResult(String type, String id, InputMessageContent input_message_content, InlineKeyboardMarkup reply_markup) {
        this.type = type;
        this.id = id;
        this.input_message_content = input_message_content;
        this.reply_markup = reply_markup;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public InputMessageContent getInput_message_content() {
        return input_message_content;
    }

    public InlineKeyboardMarkup getReply_markup() {
        return reply_markup;
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
