package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import me.niccolomattei.api.telegram.serialization.SerializationProperty;
import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 07/09/2016.
 */
public class InlineContactMessageContent implements InputMessageContent {

    private String phone_number;
    private String first_name;
    @SerializationProperty(propertyName = "last_name", required = false)
    private String last_name;

    public InlineContactMessageContent() {
    }

    public InlineContactMessageContent(String phone_number, String first_name, String last_name) {
        this.phone_number = phone_number;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
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
