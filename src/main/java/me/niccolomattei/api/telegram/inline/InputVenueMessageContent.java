package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.SerializationProperty;

/**
 * Created by Niccolò Mattei on 07/09/2016.
 */
@IgnoreClassName
public class InputVenueMessageContent extends InputLocationMessageContent {

    public String title;
    public String address;
    @SerializationProperty(propertyName = "foursquare_id", required = false)
    public String foursquare_id;

    public InputVenueMessageContent() {}

    public InputVenueMessageContent(float latitude, float longitude, String title, String address, String foursquare_id) {
        super(latitude, longitude);
        this.title = title;
        this.address = address;
        this.foursquare_id = foursquare_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getFoursquare_id() {
        return foursquare_id;
    }
}
