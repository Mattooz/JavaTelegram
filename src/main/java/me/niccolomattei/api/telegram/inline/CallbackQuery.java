package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.Message;
import me.niccolomattei.api.telegram.User;
import me.niccolomattei.api.telegram.serialization.ISerializable;
import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 13/09/2016.
 */
public class CallbackQuery implements ISerializable {

    private String id;
    private User sender;
    private Message message;
    private String inline_message_id;
    private String data;

    public CallbackQuery() {
    }

    public CallbackQuery(String id, User sender, Message message, String inline_message_id, String data) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.inline_message_id = inline_message_id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public Message getMessage() {
        return message;
    }

    public String getInline_message_id() {
        return inline_message_id;
    }

    public String getData() {
        return data;
    }

    @Override
    public String serialize() {
        return null;
    }

    @Override
    public JSONObject serializeJson() {
        return null;
    }
}
