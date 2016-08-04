package me.niccolomattei.api.telegram;

public class ChatMember {

	String chat_id;
	String status;
	
	public ChatMember(String chat_id, String status) {
		super();
		this.chat_id = chat_id;
		this.status = status;
	}
	
	public String getChat_id() {
		return chat_id;
	}
	
	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
