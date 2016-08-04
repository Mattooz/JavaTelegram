package me.niccolomattei.api.telegram.configuration;

import java.util.Collection;
import java.util.Set;

import org.json.JSONObject;

public interface ConfigurationSection {
	
	Object get(String key);
	
	<E extends Object> E get(Class<E> expected, String key);
	
	String getString(String key);
	
	int getInt(String key);
	
	double getDouble(String key);
	
	long getLong(String key);
	
	short getShort(String key);
	
	float getFloat(String key);
	
	byte getByte(String key);
	
	Collection<Object> getList(String key);
	
	ConfigurationSection getSubSection(String key);
	
	boolean contains(String key);
	
	Set<String> keySet();
	
	JSONObject toJsonObject();
	
}
