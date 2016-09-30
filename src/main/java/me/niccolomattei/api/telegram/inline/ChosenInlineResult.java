package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.Location;
import me.niccolomattei.api.telegram.User;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import me.niccolomattei.api.telegram.serialization.SerializationProperty;
import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 07/09/2016.
 */
@SerializationProperty(propertyName = "chosen_inline_query")
public class ChosenInlineResult implements InlineResult {

    private String result_id;
    private User user;
    @SerializationProperty(propertyName = "location", required = false)
    private Location location;

    public ChosenInlineResult() {

    }

    public String getResult_id() {
        return result_id;
    }

    public User getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }

    public ChosenInlineResult(String result_id, User user, Location location) {
        this.result_id = result_id;
        this.user = user;
        this.location = location;
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
