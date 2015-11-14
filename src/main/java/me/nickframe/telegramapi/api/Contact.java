package me.nickframe.telegramapi.api;

/**
 * Created by Utente on 24/10/2015.
 */
public class Contact implements BotObject {

	private String phone_number;
	private String first_name;
	private String last_name;
	private int user_id;
	
	public Contact(String phone_number, String first_name, String last_name, int user_id) {
		super();
		this.phone_number = phone_number;
		this.first_name = first_name;
		this.last_name = last_name;
		this.user_id = user_id;
	}
	
	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public BotObject getObject() {
        return this;
    }
}
