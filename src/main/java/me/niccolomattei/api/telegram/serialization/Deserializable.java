package me.niccolomattei.api.telegram.serialization;

import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 06/09/2016.
 */
public abstract class Deserializable implements ISerializable {

    public static Deserializable deserialize(JSONObject object) {
        return JSONSerializer.deserialize(Deserializable.class, object);
    }

}
