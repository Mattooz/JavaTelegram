package me.niccolomattei.api.telegram.parsing;

import me.niccolomattei.api.telegram.*;
import me.niccolomattei.api.telegram.Chat.ChatType;
import me.niccolomattei.api.telegram.inline.CallbackQuery;
import org.json.JSONArray;
import org.json.JSONObject;

public class Parser {

	public CallbackQuery parseCallbackQuery(JSONObject callback, Bot bot) {
		String id = callback.getString("id");
		User from = callback.has("from") ? parseUser(callback.getJSONObject("from"), bot) : null;
		Message message = callback.has("message") ? parseMessage(callback.getJSONObject("message"), bot) : null;
		String inline_message_id = callback.has("inline_message_id") ? callback.getString("inline_message_id") : "null";
		String data = callback.getString("data");

		return new CallbackQuery(id, from, message, inline_message_id, data);
	}

	public Message parseMessage(JSONObject message, Bot bot) {
		int message_id = message.has("message_id") ? message.getInt("message_id") : -1;
		JSONObject from = message.has("from") ? message.getJSONObject("from") : null;
		int date = message.has("date") ? message.getInt("date") : 0;
		JSONObject chat = message.has("chat") ? message.getJSONObject("chat") : null;
		JSONObject forward_from = message.has("forward_from") ? message.getJSONObject("forward_from") : null;
		int forward_date = message.has("forward_date") ? message.getInt("forward_date") : 0;
		JSONObject reply_to_message = message.has("reply_to_message") ? message.getJSONObject("reply_to_message")
				: null;
		String text = message.has("text") ? message.getString("text") : "empty-msg";
		JSONObject audio = message.has("audio") ? message.getJSONObject("audio") : null;
		JSONObject document = message.has("document") ? message.getJSONObject("document") : null;
		JSONArray photo = message.has("photo") ? message.getJSONArray("photo") : null;
		JSONObject sticker = message.has("sticker") ? message.getJSONObject("sticker") : null;
		JSONObject video = message.has("video") ? message.getJSONObject("video") : null;
		JSONObject voice = message.has("voice") ? message.getJSONObject("voice") : null;
		String caption = message.has("caption") ? message.getString("caption") : "null";
		JSONObject contact = message.has("contact") ? message.getJSONObject("contact") : null;
		JSONObject location = message.has("location") ? message.getJSONObject("location") : null;
		JSONObject venue = message.has("venue") ? message.getJSONObject("venue") : null;
		JSONObject new_chat_participant = message.has("new_chat_participant")
				? message.getJSONObject("new_chat_participant") : null;
		JSONObject left_chat_participant = message.has("left_chat_participant")
				? message.getJSONObject("left_chat_participant") : null;
		String new_chat_title = message.has("new_chat_title") ? message.getString("new_chat_title") : "null";
		JSONArray new_chat_photo = message.has("new_chat_photo") ? message.getJSONArray("new_chat_photo") : null;
		boolean delete_chat_photo = message.has("delete_chat_photo") && message.getBoolean("delete_chat_photo");
		boolean group_chat_created = message.has("group_chat_created") && message.getBoolean("group_chat_created");

		return parseMessage(message_id, from, date, chat, forward_from, forward_date, reply_to_message, text, audio,
				document, photo, sticker, video, voice, caption, contact, location, venue, new_chat_participant,
				left_chat_participant, new_chat_title, new_chat_photo, delete_chat_photo, group_chat_created, bot);
	}

	public Message parseMessage(int message_id, JSONObject from, int date, JSONObject chat, JSONObject forward_from,
			int forward_date, JSONObject reply_to_message, String text, JSONObject audio, JSONObject document,
			JSONArray photo, JSONObject sticker, JSONObject video, JSONObject voice, String caption, JSONObject contact,
			JSONObject location, JSONObject venue, JSONObject new_chat_participant, JSONObject left_chat_participant,
			String new_chat_title, JSONArray new_chat_photo, boolean delete_photo_chat, boolean group_chat_created, Bot bot) {
		Message result = null;

		User r_from = parseUser(from, bot);
		Chat r_chat = parseChat(chat);
		User r_forward_from = parseUser(forward_from, bot);
		Audio r_audio = parseAudio(audio);
		Document r_document = parseDocument(document);
		PhotoSize[] r_photo = parseMultiplePhotoSizes(photo);
		Sticker r_sticker = parseSticker(sticker);
		Video r_video = parseVideo(video);
		Voice r_voice = parseVoice(voice);
		Venue r_venue = parseVenue(venue);
		Contact r_contact = parseContact(contact);
		Location r_location = parseLocation(location);
		User r_new_chat_participant = parseUser(new_chat_participant, bot);
		User r_left_chat_participant = parseUser(left_chat_participant, bot);
		PhotoSize[] r_new_chat_photo = parseMultiplePhotoSizes(new_chat_photo);

		result = new Message(message_id, r_from, date, r_chat, r_forward_from, forward_date, reply_to_message, text,
				r_audio, r_document, r_photo, r_sticker, r_video, r_voice, caption, r_contact, r_location, r_venue,
				r_new_chat_participant, r_left_chat_participant, new_chat_title, r_new_chat_photo, delete_photo_chat,
				group_chat_created);

		return result;
	}

	public Audio parseAudio(JSONObject audio) {
		Audio result = null;
		if (audio != null) {
			String file_id = audio.has("file_id") ? audio.getString("file_id") : "null";
			int duration = audio.has("duration") ? audio.getInt("duration") : -1;
			String performer = audio.has("performer") ? audio.getString("performer") : "null";
			String title = audio.has("title") ? audio.getString("title") : "null";
			String mime_type = audio.has("mime_type") ? audio.getString("mime_type") : "null";
			int file_size = audio.has("file_size") ? audio.getInt("file_size") : -1;

			result = new Audio(file_id, duration, performer, title, mime_type, file_size);

			return result;
		}
		return null;
	}

	public Chat parseChat(JSONObject chat) {
		Chat result = null;
		if (chat != null) {
			long id = chat.has("id") ? chat.getLong("id") : -1;
			ChatType type = chat.has("type") ? ChatType.valueOf(chat.getString("type").toUpperCase()) : null;
			String title = chat.has("title") ? chat.getString("title") : "null";
			String username = chat.has("username") ? chat.getString("username") : "null";
			String first_name = chat.has("first_name") ? chat.getString("first_name") : "null";
			String last_name = chat.has("last_username") ? chat.getString("last_name") : "null";

			result = new Chat(id, type, title, first_name, username, last_name);

			return result;
		}
		return null;
	}

	public Contact parseContact(JSONObject contact) {
		Contact result = null;
		if (contact != null) {
			String phone_number = contact.has("phone_number") ? contact.getString("phone_number") : "null";
			String first_name = contact.has("first_name") ? contact.getString("first_name") : "null";
			String last_name = contact.has("last_name") ? contact.getString("last_name") : "null";
			int user_id = contact.has("user_id") ? contact.getInt("user_id") : -1;

			result = new Contact(phone_number, first_name, last_name, user_id);

			return result;
		}
		return null;
	}

	public Document parseDocument(JSONObject document) {
		Document result = null;
		if (document != null) {
			String file_id = document.has("file_id") ? document.getString("file_id") : "null";
			PhotoSize thumb = document.has("thumb") ? parsePhotoSize(document.getJSONObject("thumb")) : null;
			String file_name = document.has("file_name") ? document.getString("file_name") : "null";
			String mime_type = document.has("mime_type") ? document.getString("mime_type") : "null";
			int file_size = document.has("file_size") ? document.getInt("file_size") : -1;

			result = new Document(file_id, thumb, file_name, mime_type, file_size);

			return result;
		}
		return null;
	}

	public Location parseLocation(JSONObject location) {
		Location result = null;
		if (location != null) {
			double longitude = location.has("longitude") ? location.getDouble("longitude") : -1;
			double latitude = location.has("latitude") ? location.getDouble("latitude") : -1;

			new Location(longitude, latitude);

			return result;
		}
		return null;
	}
	
	public Venue parseVenue(JSONObject venue) {
		Venue result = null;
		if(venue != null) {
			Location location = parseLocation(venue.getJSONObject("location"));
			String title = venue.getString("title");
			String address = venue.getString("address");
			String foursquare_id = venue.has("foursquare_id") ? venue.getString("foursquare_id") : "null";
			
			result = new Venue(location, title, address, foursquare_id);
		}
		return result;
	}

	public PhotoSize parsePhotoSize(JSONObject photo) {
		PhotoSize result = null;
		if (photo != null) {
			String file_id = photo.has("file_id") ? photo.getString("file_id") : "null";
			int width = photo.has("width") ? photo.getInt("width") : -1;
			int height = photo.has("height") ? photo.getInt("height") : -1;
			int file_size = photo.has("file_size") ? photo.getInt("file_size") : -1;

			result = new PhotoSize(file_id, width, height, file_size);

			return result;
		}
		return null;
	}

	public Sticker parseSticker(JSONObject sticker) {
		Sticker result = null;
		if (sticker != null) {
			String file_id = sticker.has("file_id") ? sticker.getString("file_id") : "null";
			int width = sticker.has("width") ? sticker.getInt("width") : -1;
			int height = sticker.has("height") ? sticker.getInt("height") : -1;
			PhotoSize thumb = sticker.has("thumb") ? parsePhotoSize(sticker.getJSONObject("thumb")) : null;
			int file_size = sticker.has("file_size") ? sticker.getInt("file_size") : -1;

			result = new Sticker(file_id, width, height, thumb, file_size);

			return result;
		}
		return null;
	}

	public User parseUser(JSONObject user, Bot bot) {
		User result = null;
		if (user != null) {
			int id = user.has("id") ? user.getInt("id") : -1;
			String first_name = user.has("first_name") ? user.getString("first_name") : "null";
			String last_name = user.has("last_name") ? user.getString("last_name") : "null";
			String username = user.has("username") ? user.getString("username") : "null";

			result = new User(id, first_name, last_name, username, bot);

			return result;
		}
		return null;
	}

	public Video parseVideo(JSONObject video) {
		Video result = null;
		if (video != null) {
			String file_id = video.has("file_id") ? video.getString("file_id") : "null";
			int width = video.has("width") ? video.getInt("width") : -1;
			int height = video.has("height") ? video.getInt("height") : -1;
			int duration = video.has("duration") ? video.getInt("duration") : -1;
			PhotoSize thumb = video.has("thumb") ? parsePhotoSize(video.getJSONObject("thumb")) : null;
			String mime_type = video.has("mime_type") ? video.getString("mime_type") : null;
			int file_size = video.has("file_size") ? video.getInt("file_size") : -1;

			result = new Video(file_id, width, height, duration, thumb, mime_type, file_size);

			return result;
		}
		return null;
	}

	public Voice parseVoice(JSONObject voice) {
		Voice result = null;
		if (voice != null) {
			String file_id = voice.has("file_id") ? voice.getString("file_id") : "null";
			int duration = voice.has("duration") ? voice.getInt("duration") : -1;
			String mime_type = voice.has("mime_type") ? voice.getString("mime_type") : null;
			int file_size = voice.has("file_size") ? voice.getInt("file_size") : -1;

			result = new Voice(file_id, duration, mime_type, file_size);

			return result;
		}
		return null;
	}

	public PhotoSize[] parseMultiplePhotoSizes(JSONArray array) {
		PhotoSize[] result = new PhotoSize[0];

		if (array != null) {
			if (array.length() > 0) {
				result = new PhotoSize[array.length()];
				for (int i = 0; i < array.length(); i++) {
					PhotoSize size = parsePhotoSize(array.getJSONObject(i));
					result[i] = size;
				}
			}

			return result;
		}
		return null;
	}

	public ChatMember parseChatMember(JSONObject chatMember) {
		ChatMember member = null;
		
		String chat_id = chatMember.getString("chat_id");
		String status = chatMember.getString("status");
		
		member = new ChatMember(chat_id, status);
		
		return member;
	}
	
	public ChatMember[] parseMultipleChatMembers(JSONArray array) {
		ChatMember[] result = null;
		
		if(array != null) {
			if(array.length() > 0) {
				result = new ChatMember[array.length()];
				for(int i = 0; i < array.length(); i++) {
					result[i] = parseChatMember(array.getJSONObject(i));
				}
			}
		}
		
		return result;
	}

}
