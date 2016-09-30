package me.niccolomattei.api.telegram.serialization;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class JSONSerializer {

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
                    boolean required = field.getAnnotation(SerializationProperty.class) != null ? field.getAnnotation(SerializationProperty.class).required() : true;

                    try {
                        Object value = field.get(toSerialize);
                        if (!required && value != null) {
                            if (ISerializable.class.isAssignableFrom(field.getType())) {
                                ISerializable serializable = (ISerializable) field.get(toSerialize);
                                map.put(fieldName, serializable.serializeJson());
                            } else {
                                map.put(fieldName, value);
                            }
                        } else if (value instanceof Collection) {
                            if(value != null) {
                                Collection collection = (Collection) value;
                                JSONArray array = new JSONArray();

                                for(Object o : collection) {
                                    if(o instanceof ISerializable) {
                                        array.put(((ISerializable) o).serialize());
                                    } else array.put(o);
                                }

                                object.put(fieldName, array);
                            }
                        } else {
                            if (ISerializable.class.isAssignableFrom(field.getType())) {
                                ISerializable serializable = (ISerializable) field.get(toSerialize);
                                if (serializable != null)
                                    map.put(fieldName, serializable.serializeJson());
                            } else {
                                if (value != null)
                                    map.put(fieldName, value);
                            }
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

                    boolean required = field.getAnnotation(SerializationProperty.class) != null ? field.getAnnotation(SerializationProperty.class).required() : true;

                    try {
                        Object value = field.get(toSerialize);
                        if (!required && value != null) {
                            if (ISerializable.class.isAssignableFrom(field.getType())) {
                                ISerializable serializable = (ISerializable) field.get(toSerialize);
                                object.put(fieldName, serializable.serializeJson());
                            } else if (value instanceof Collection) {
                                if(value != null) {
                                    Collection collection = (Collection) value;
                                    JSONArray array = new JSONArray();

                                    for(Object o : collection) {
                                        if(o instanceof ISerializable) {
                                            array.put(((ISerializable) o).serialize());
                                        } else array.put(o);
                                    }

                                    object.put(fieldName, array);
                                }
                            } else {
                                object.put(fieldName, value);
                            }
                        } else {
                            if (ISerializable.class.isAssignableFrom(field.getType())) {
                                ISerializable serializable = (ISerializable) field.get(toSerialize);
                                if (serializable != null)
                                    object.put(fieldName, serializable.serializeJson());
                            } else {
                                if (value != null)
                                    object.put(fieldName, value);
                            }
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

    public static <E extends ISerializable> E deserialize(Class<E> expected, JSONObject deserialize) {
        if (expected.isInterface())
            throw new IllegalArgumentException("expected class cannot be an interface!");
        if (expected.isEnum())
            throw new IllegalArgumentException("expected class cannot be an enum!");
        if (expected.isAnnotation())
            throw new IllegalArgumentException("expected class cannot be an annotation!");
        try {
            expected.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("expected class does not have an empty constructor!");
        }

        JSONObject toDeserialize;

        String className = expected.getAnnotation(SerializationProperty.class) != null
                ? expected.getAnnotation(SerializationProperty.class).propertyName() : expected.getSimpleName();

        if (expected.getAnnotation(IgnoreClassName.class) == null) {
            toDeserialize = deserialize.getJSONObject(className);
        } else {
            toDeserialize = deserialize;
        }


        Object object = null;

        try {
            object = expected.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Field field : expected.getDeclaredFields()) {
            if (field.getAnnotation(Ignorable.class) != null) continue;
            field.setAccessible(true);
            String fieldName;

            if (field.getAnnotation(SerializationProperty.class) != null)
                fieldName = field.getAnnotation(SerializationProperty.class).propertyName();
            else fieldName = field.getName();

            if (field.getType().isArray()) {
                if (toDeserialize.has(fieldName)) {
                    if (toDeserialize.opt(fieldName) instanceof JSONArray) {
                        JSONArray array = toDeserialize.optJSONArray(fieldName);

                        Object arrayInstance;

                        if (array == null || array.length() == 0)
                            arrayInstance = Array.newInstance(field.getType().getComponentType(), 0);
                        else {
                            arrayInstance = Array.newInstance(field.getType().getComponentType(), array.length());

                            for (int i = 0; i < array.length(); i++) {
                                Class<?> type = field.getType().getComponentType();
                                Object val = array.get(i);
                                if(Short.TYPE.isAssignableFrom(type)) {
                                    String toParse = String.valueOf(val);

                                    Array.set(arrayInstance, i, Short.parseShort(toParse));
                                } else if (Byte.TYPE.isAssignableFrom(type)) {
                                    String toParse = String.valueOf(val);

                                    Array.set(arrayInstance, i, Byte.parseByte(toParse));
                                } else Array.set(arrayInstance, i, val);
                            }
                        }

                        try {
                            field.set(object, arrayInstance);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else if (toDeserialize.opt(fieldName).getClass().isArray()) {
                        try {
                            field.set(object, toDeserialize.opt(fieldName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                Collection collection = new ArrayList();

                Object obj = toDeserialize.opt(fieldName);
                if (obj != null) {
                    if (obj instanceof JSONArray) {
                        JSONArray array = (JSONArray) obj;
                        for (int i = 0; i < array.length(); i++) {
                            System.out.println(1);
                            Object val = array.get(i);
                            Class<?> persistentClass = !getTypeParameters(field).isEmpty() ? getTypeParameters(field).get(0) : null;
                            if (persistentClass != null) {
                                if (ISerializable.class.isAssignableFrom(persistentClass)) {
                                    if(!(val instanceof ISerializable)) {
                                        Object biscuit = deserialize(((Class<ISerializable>) persistentClass), (JSONObject) val);

                                        System.out.println(String.valueOf(biscuit));

                                        collection.add(biscuit);
                                    } else {
                                        collection.add(val);
                                    }
                                }
                            } else collection.add(val);
                        }
                        try {
                            field.set(object, collection);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else if (obj.getClass().isArray()) {
                        System.out.println(2);
                        Object[] array = (Object[]) obj;

                        for (int i = 0; i < array.length; i++) {
                            Object val = array[i];
                            Class<?> persistentClass = !getTypeParameters(field).isEmpty() ? getTypeParameters(field).get(0) : null;
                            if (persistentClass != null) {
                                if (ISerializable.class.isAssignableFrom(persistentClass)) {
                                    if(!(val instanceof ISerializable)) {
                                        collection.add(deserialize(((Class<ISerializable>) persistentClass), (JSONObject) val));
                                    } else {
                                        collection.add(val);
                                    }
                                }
                            } else collection.add(val);
                        }
                        try {
                            field.set(object, collection);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } else if (Collection.class.isAssignableFrom(obj.getClass())) {
                        Class<?> persistentClass = !getTypeParameters(field).isEmpty() ? getTypeParameters(field).get(0) : null;
                        System.out.println(3);
                        for (Object val : (Collection) obj) {
                            if (persistentClass != null) {
                                if (ISerializable.class.isAssignableFrom(persistentClass)) {
                                    if(!(val instanceof ISerializable)) {
                                        collection.add(deserialize(((Class<ISerializable>) persistentClass), (JSONObject) val));
                                    } else {
                                        collection.add(persistentClass.cast(val));
                                    }
                                }
                            } else collection.add(val);
                        }

                        try {
                            field.set(object, collection);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (ISerializable.class.isAssignableFrom(field.getType())) {
                JSONObject val = toDeserialize.optJSONObject(fieldName);
                Class<ISerializable> clazz = (Class<ISerializable>) field.getType();
                if (val != null) {
                    try {
                        field.set(object, deserialize(clazz, val));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Object val = toDeserialize.opt(fieldName);
                Class<?> type = field.getType();
                if (val != null)
                    try {
                        if(Short.TYPE.isAssignableFrom(type)) {
                            String toParse = String.valueOf(val);

                            field.set(object, Short.parseShort(toParse));
                        } else if (Byte.TYPE.isAssignableFrom(type)) {
                            String toParse = String.valueOf(val);

                            field.set(object, Byte.parseByte(toParse));
                        } else field.set(object, val);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        continue;
                    }
            }
        }
        return expected.cast(object);
    }

    private static List<Class<?>> getTypeParameters(Field field) {
        List<Class<?>> classes = new ArrayList<>();
        Type type = field.getGenericType();

        if (type instanceof ParameterizedType) {

            ParameterizedType pType = (ParameterizedType) type;
            Type[] arr = pType.getActualTypeArguments();

            for (Type tp : arr) {
                Class<?> clazz = (Class<?>) tp;
                classes.add(clazz);
            }
        }

        return classes;
    }

}
