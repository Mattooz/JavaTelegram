package me.nickframe.telegramapi.api;

/**
 * Created by Utente on 24/10/2015.
 */
public class Location implements BotObject{

    private double longitude;
    private double latitude;

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
}
