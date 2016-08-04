package me.niccolomattei.api.telegram;

import org.json.JSONObject;

/**
 * Created by Utente on 23/10/2015.
 */
public class Message implements BotObject {

	private int message_id;
	private User from;
	private int date;
	private Chat chat;
	private User forward_from;
	private Integer forward_date;
	private JSONObject reply_to_message;
	private String text;
	private Audio audio;
	private Document document;
	private PhotoSize[] photo;
	private Sticker sticker;
	private Video video;
	private Voice voice;
	private String caption;
	private Contact contact;
	private Location location;
	private Venue venue;
	private User new_chat_participant;
	private User left_chat_participant;
	private String new_chat_title;
	private PhotoSize[] new_chat_photo;
	private boolean delete_photo_chat;
	private boolean group_chat_created;

	public Message(int message_id, User from, int date, Chat chat, User forward_from, Integer forward_date,
			JSONObject reply_to_message, String text, Audio audio, Document document, PhotoSize[] photo,
			Sticker sticker, Video video, Voice voice, String caption, Contact contact, Location location, Venue venue,
			User new_chat_participant, User left_chat_participant, String new_chat_title, PhotoSize[] new_chat_photo,
			boolean delete_photo_chat, boolean group_chat_created) {
		super();
		this.message_id = message_id;
		this.from = from;
		this.date = date;
		this.chat = chat;
		this.forward_from = forward_from;
		this.forward_date = forward_date;
		this.reply_to_message = reply_to_message;
		this.text = text;
		this.audio = audio;
		this.document = document;
		this.photo = photo;
		this.sticker = sticker;
		this.video = video;
		this.voice = voice;
		this.caption = caption;
		this.contact = contact;
		this.location = location;
		this.venue = venue;
		this.new_chat_participant = new_chat_participant;
		this.left_chat_participant = left_chat_participant;
		this.new_chat_title = new_chat_title;
		this.new_chat_photo = new_chat_photo;
		this.delete_photo_chat = delete_photo_chat;
		this.group_chat_created = group_chat_created;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public User getForward_from() {
		return forward_from;
	}

	public void setForward_from(User forward_from) {
		this.forward_from = forward_from;
	}

	public Integer getForward_date() {
		return forward_date;
	}

	public void setForward_date(Integer forward_date) {
		this.forward_date = forward_date;
	}

	public JSONObject getReply_to_message() {
		return reply_to_message;
	}

	public void setReply_to_message(JSONObject reply_to_message) {
		this.reply_to_message = reply_to_message;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public PhotoSize[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(PhotoSize[] photo) {
		this.photo = photo;
	}

	public Sticker getSticker() {
		return sticker;
	}

	public void setSticker(Sticker sticker) {
		this.sticker = sticker;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public Venue getVenue() {
		return venue;
	}
	
	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public User getNew_chat_participant() {
		return new_chat_participant;
	}

	public void setNew_chat_participant(User new_chat_participant) {
		this.new_chat_participant = new_chat_participant;
	}

	public User getLeft_chat_participant() {
		return left_chat_participant;
	}

	public void setLeft_chat_participant(User left_chat_participant) {
		this.left_chat_participant = left_chat_participant;
	}

	public String getNew_chat_title() {
		return new_chat_title;
	}

	public void setNew_chat_title(String new_chat_title) {
		this.new_chat_title = new_chat_title;
	}

	public PhotoSize[] getNew_chat_photo() {
		return new_chat_photo;
	}

	public void setNew_chat_photo(PhotoSize[] new_chat_photo) {
		this.new_chat_photo = new_chat_photo;
	}

	public boolean isDelete_photo_chat() {
		return delete_photo_chat;
	}

	public void setDelete_photo_chat(boolean delete_photo_chat) {
		this.delete_photo_chat = delete_photo_chat;
	}

	public boolean isGroup_chat_created() {
		return group_chat_created;
	}

	public void setGroup_chat_created(boolean group_chat_created) {
		this.group_chat_created = group_chat_created;
	}

	public BotObject getObject() {
		return this;
	}
}
