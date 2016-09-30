package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 07/09/2016.
 */
@IgnoreClassName
public class InputLocationMessageContent implements InputMessageContent {

    public float latitude;
    public float longitude;

    public InputLocationMessageContent() {}

    public InputLocationMessageContent(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
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
