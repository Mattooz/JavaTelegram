package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.Location;
import me.niccolomattei.api.telegram.User;
import me.niccolomattei.api.telegram.serialization.ISerializable;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import me.niccolomattei.api.telegram.serialization.SerializationProperty;
import org.json.JSONObject;

@SerializationProperty(propertyName = "inline_query")
public class InlineQuery implements ISerializable {

    private String id;
    private User from;
    @SerializationProperty(propertyName = "location", required = false)
    private Location location;
    private String query;
    private String offset;

    public InlineQuery() {}

    public InlineQuery(String id, User from, Location location, String query, String offset) {
        this.id = id;
        this.from = from;
        this.location = location;
        this.query = query;
        this.offset = offset;
    }

    public String getId() {
        return id;
    }

    public User getFrom() {
        return from;
    }

    public Location getLocation() {
        return location;
    }

    public String getQuery() {
        return query;
    }

    public String getOffset() {
        return offset;
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
        return "InlineQuery{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", location=" + location +
                ", query='" + query + '\'' +
                ", offset='" + offset + '\'' +
                '}';
    }
}
