package me.niccolomattei.api.telegram;

import me.niccolomattei.api.telegram.serialization.ISerializable;
import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import org.json.JSONObject;

/**
 * Created by Utente on 24/10/2015.
 */
@IgnoreClassName
public class Location implements BotObject, ISerializable{

    private double longitude;
    private double latitude;

    public Location() {}

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public BotObject getObject() {
        return this;
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
