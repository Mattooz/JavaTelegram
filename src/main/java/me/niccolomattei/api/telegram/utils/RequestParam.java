package me.niccolomattei.api.telegram.utils;

public class RequestParam {

	private String key;
	private Object value;
	
	public RequestParam(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
	
}
