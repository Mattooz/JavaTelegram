package me.niccolomattei.api.telegram.serialization;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JSONSerializator {

    public static JSONObject serializeJson(Object toSerialize) {
        JSONObject object = new JSONObject();
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = toSerialize.getClass();

        if (!ISerializable.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException("Object's class does not implement ISerializable!");

        String className = "default";

        if (clazz.getAnnotation(IgnoreClassName.class) == null) {

            className = clazz.getAnnotation(SerializationProperty.class) != null
                    ? clazz.getAnnotation(SerializationProperty.class).propertyName() : clazz.getSimpleName();

            for (int f = 0; f < clazz.getDeclaredFields().length; f++) {
                Field field = clazz.getDeclaredFields()[f];

                field.setAccessible(true);
                String fieldName = "default";
                if (field.getAnnotation(Ignorable.class) == null) {
                    fieldName = field.getAnnotation(SerializationProperty.class) != null
                            ? field.getAnnotation(SerializationProperty.class).propertyName() : field.getName();
                    try {
                        Object value = field.get(toSerialize);
                        if (ISerializable.class.isAssignableFrom(field.getType())) {
                            ISerializable serializable = (ISerializable) field.get(toSerialize);
                            map.put(fieldName, serializable.serializeJson());
                        } else {
                            map.put(fieldName, value);
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            object.put(className, map);
            return object;
        } else {
            for (int f = 0; f < clazz.getDeclaredFields().length; f++) {
                Field field = clazz.getDeclaredFields()[f];

                field.setAccessible(true);
                String fieldName = "default";
                if (field.getAnnotation(Ignorable.class) == null) {
                    fieldName = field.getAnnotation(SerializationProperty.class) != null
                            ? field.getAnnotation(SerializationProperty.class).propertyName() : field.getName();
                    try {
                        Object value = field.get(toSerialize);
                        if (ISerializable.class.isAssignableFrom(field.getType())) {
                            ISerializable serializable = (ISerializable) field.get(toSerialize);
                            object.put(fieldName, serializable.serializeJson());
                        } else {
                            object.put(fieldName, value);
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return object;
        }
    }

    public static String serialize(Object toSerialize) {
        return serializeJson(toSerialize).toString();
    }
    /**
     * TODO Finish method public static <T extends ISerializable> T
     * deserialize(Class<T> expected, JSONObject json) { if (expected == null)
     * throw new IllegalArgumentException("Expected cannot be null!"); if (json
     * == null || json.isNull(expected.getSimpleName())) throw new
     * IllegalArgumentException("Object cannot be null!");
     *
     * JSONObject object = json.getJSONObject(expected.getSimpleName());
     *
     * Object instance = null; try { instance = expected.newInstance(); } catch
     * (InstantiationException | IllegalAccessException e1) {
     * e1.printStackTrace(); } Class<?> clazz = instance.getClass();
     *
     * for (Field field : clazz.getFields()) { field.setAccessible(true); if
     * (field.getAnnotation(SerializationProperty.class) != null) { if
     * (object.has(field.getAnnotation(SerializationProperty.class).propertyName
     * ())) try { field.set(instance,
     * object.get(field.getAnnotation(SerializationProperty.class).propertyName(
     * ))); } catch (IllegalArgumentException | IllegalAccessException |
     * JSONException e) { e.printStackTrace(); } else try { if
     * (field.getType().isAssignableFrom(Integer.TYPE) ||
     * field.getType().isAssignableFrom(Short.TYPE) ||
     * field.getType().isAssignableFrom(Double.TYPE) ||
     * field.getType().isAssignableFrom(Long.TYPE) ||
     * field.getType().isAssignableFrom(Float.TYPE) ||
     * field.getType().isAssignableFrom(Byte.TYPE)) field.set(instance, 0); else
     * field.set(instance, null); } catch (IllegalArgumentException |
     * IllegalAccessException e) { e.printStackTrace(); } } else { if
     * (object.has(field.getName())) try { if(!(object.get(field.getName())
     * instanceof JSONArray)) { field.set(instance,
     * object.get(field.getName())); } else if(object.get(field.getName())
     * instanceof List) { List<Object> list = new ArrayList<>();
     *
     *
     *
     * field.set(instance, object.getJSONArray(field.getName())); } } catch
     * (IllegalArgumentException | IllegalAccessException | JSONException e) {
     * e.printStackTrace(); } else try { if
     * (field.getType().isAssignableFrom(Integer.TYPE) ||
     * field.getType().isAssignableFrom(Short.TYPE) ||
     * field.getType().isAssignableFrom(Double.TYPE) ||
     * field.getType().isAssignableFrom(Long.TYPE) ||
     * field.getType().isAssignableFrom(Float.TYPE) ||
     * field.getType().isAssignableFrom(Byte.TYPE)) field.set(instance, 0); else
     * field.set(instance, null); } catch (IllegalArgumentException |
     * IllegalAccessException e) { e.printStackTrace(); } } } return
     * expected.cast(instance); }
     */

}
