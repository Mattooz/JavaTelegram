package me.nickframe.telegramapi.api;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.istack.internal.NotNull;

import me.nickframe.telegramapi.api.commands.CommandListener;
import me.nickframe.telegramapi.api.commands.Commands;
import me.nickframe.telegramapi.api.events.EventManager;
import me.nickframe.telegramapi.api.events.defaults.ReceiveMessageEvent;
import me.nickframe.telegramapi.api.parsing.Parser;

/**
 * Created by Utente on 21/10/2015.
 */
@SuppressWarnings("restriction")
public class Bot {

    public static final String API = "https://api.telegram.org/bot";
    public static final String USER_AGENT = "Mozilla/5.0";
    private Parser parser = new Parser();

    private Message latestMessage = null;
    
    private String token;

    public Bot(String token) {
        this.token = token;
    }

    public Message sendMessage(@NotNull String message, @NotNull String chat_id) throws RequestErrorException {

        String prm = "chat_id=" + chat_id + "&text=" + message;
        String uri = "https://api.telegram.org/bot" + token + "/sendMessage";
        URL url = null;
        HttpsURLConnection connection = null;
        int responsecode = -1;
        boolean noerrors;

        try {
            url = new URL(uri);
            connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(prm);
            wr.flush();
            wr.close();

            responsecode = connection.getResponseCode();
            noerrors = true;

        } catch (MalformedURLException e1) {
            noerrors = false;
            throw new RequestErrorException("false", responsecode + "", "Malformed Url Exception!");
        } catch (IOException e2) {
            noerrors = false;
            throw new RequestErrorException("false", responsecode + "", "IO Exception: " + e2.getMessage());
        }

        String str = "";

        
        if(noerrors) {
            try {
                str = Utils.getStringFromInputStream(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }

    private JSONArray getUpdates(int offset) throws RequestErrorException {
        String url = API + token + "/getUpdates";
        URL u = null;
        HttpsURLConnection con = null;
        String result = "";
        int responsecode = 0;
        JSONObject obj = null;
        JSONArray array = null;

        try {
            u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setDoOutput(true);

            DataOutputStream stream = new DataOutputStream(con.getOutputStream());
            stream.writeBytes("offset=" + offset);
            stream.flush();
            stream.close();

            result = Utils.getStringFromInputStream(con.getInputStream());

            obj = new JSONObject(result);
            array = obj.getJSONArray("result");

            return array;

        } catch (MalformedURLException e1) {
            throw new RequestErrorException("false", responsecode + "", "Malformed Url Exception!");
        } catch (IOException e2) {
            throw new RequestErrorException("false", responsecode + "", "IO Exception: " + e2.getMessage());
        }
    }

    public void on() throws RequestErrorException {
        int last_update_id = 0;
        JSONArray responses = null;

        while (true) {
            responses = getUpdates(last_update_id++);

            if(responses.isNull(0)) {
                continue;
            } else {
                last_update_id = responses.getJSONObject(responses.length() - 1).getInt("update_id") + 1;
            }

            for(int i = 0; i < responses.length(); i++) {
                //message releated
            		JSONObject message = responses.getJSONObject(i).getJSONObject("message");
            		int message_id = message.has("message_id") ? message.getInt("message_id") : -1;
            		JSONObject from = message.has("from") ? message.getJSONObject("from") : null;
            		int date = message.has("date") ? message.getInt("date") : 0;
            		JSONObject chat = message.has("chat") ? message.getJSONObject("chat") : null;
            		JSONObject forward_from = message.has("forward_from") ? message.getJSONObject("forward_from") : null;
            		int forward_date = message.has("forward_date") ? message.getInt("forward_date") : 0;
            		JSONObject reply_to_message = message.has("reply_to_message") ? message.getJSONObject("reply_to_message") : null;
            		String text = message.has("text") ? message.getString("text") : "null";
            		JSONObject audio = message.has("audio") ? message.getJSONObject("audio") : null;
            		JSONObject document = message.has("document") ? message.getJSONObject("document") : null;
            		JSONArray photo = message.has("photo") ? message.getJSONArray("photo") : null;
            		JSONObject sticker = message.has("sticker") ? message.getJSONObject("sticker") : null;
            		JSONObject video = message.has("video") ? message.getJSONObject("video") : null;
            		JSONObject voice = message.has("voice") ? message.getJSONObject("voice") : null;
            		String caption = message.has("caption") ? message.getString("caption") : "null";
            		JSONObject contact = message.has("contact") ? message.getJSONObject("contact") : null;
            		JSONObject location = message.has("location") ? message.getJSONObject("location") : null;
            		JSONObject new_chat_participant = message.has("new_chat_participant") ? message.getJSONObject("new_chat_participant") : null;
            		JSONObject left_chat_participant = message.has("left_chat_participant") ? message.getJSONObject("left_chat_participant") : null;
            		String new_chat_title = message.has("new_chat_title") ? message.getString("new_chat_title") : "null";
            		JSONArray new_chat_photo = message.has("new_chat_photo") ? message.getJSONArray("new_chat_photo") : null;
            		boolean delete_chat_photo = message.has("delete_chat_photo") && message.getBoolean("delete_chat_photo");
            		boolean group_chat_created = message.has("group_chat_created") &&  message.getBoolean("group_chat_created");
            		
            		
            	latestMessage = parser.parseMessage(message_id, from, date, chat, forward_from, forward_date, new JSONMessage(), text, audio, document, photo, sticker, video, voice, caption, contact, location, new_chat_participant, left_chat_participant, new_chat_title, new_chat_photo, delete_chat_photo, group_chat_created);
            		
            	EventManager.callEvent(new ReceiveMessageEvent(latestMessage));
            	
            	if(text.toLowerCase().startsWith("/")) {
            		String splitted = text.replaceFirst("/", "");
            		String cmd = "";
            		String[] args = new String[0];
            		
            			String[] splitted2 = splitted.split(" ");
            			
            			cmd = splitted2[0];
            			
            			if(splitted2.length >= 1) {
            				args = new String[splitted2.length - 1];
            			
            				for(int z = 0; z < args.length; z++) {
            					args[z] = splitted2[z + 1];
            				}
            			}
            			
            			for(int c = 0; c < Commands.getCommands().size(); c++) {
            				Commands.getCommands().get(c).onCommand(cmd, args, latestMessage.getFrom(), latestMessage.getChat().getId());
            			}
            	}
            }
        }
    }

}
