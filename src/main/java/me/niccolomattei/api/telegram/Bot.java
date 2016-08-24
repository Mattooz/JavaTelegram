package me.niccolomattei.api.telegram;

import me.niccolomattei.api.telegram.commands.Commands;
import me.niccolomattei.api.telegram.configuration.Configuration;
import me.niccolomattei.api.telegram.events.EventManager;
import me.niccolomattei.api.telegram.events.defaults.ModifyMessageEvent;
import me.niccolomattei.api.telegram.events.defaults.ReceiveMessageEvent;
import me.niccolomattei.api.telegram.keyboard.ReplyMarkup;
import me.niccolomattei.api.telegram.logger.Logger;
import me.niccolomattei.api.telegram.parsing.Parser;
import me.niccolomattei.api.telegram.permission.PermissionManager;
import me.niccolomattei.api.telegram.scheduling.Scheduler;
import me.niccolomattei.api.telegram.scheduling.SyncScheduler;
import me.niccolomattei.api.telegram.utils.MultipartUtility;
import me.niccolomattei.api.telegram.utils.RequestUtility;
import me.niccolomattei.api.telegram.utils.text.ParsingMode;
import me.niccolomattei.api.telegram.utils.text.RawText;
import me.niccolomattei.api.telegram.utils.text.TextBase;
import me.niccolomattei.api.telegram.utils.text.TextComponent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by Utente on 21/10/2015.
 */
public class Bot {

	public static final String API = "https://api.telegram.org/bot";
	public static final String USER_AGENT = "Mozilla/5.0";
	public static final String MULTIPART = "multipart/form-data";
	private Parser parser = new Parser();
	private Logger logger = new Logger("Bot");
	private Configuration config;
	private PermissionManager manager;
	private volatile ExecutorService executor;

	private Message latestMessage = null;
	private Scheduler scheduler;

	private String token;
	public static Bot currentBot = null;

	public Bot(String token, boolean enableConfig, boolean enablePermission) {
		this.token = token;
		scheduler = new SyncScheduler();
		currentBot = this;
		executor = Executors.newFixedThreadPool(10);
		if(enableConfig) {
			Configuration.generateDefault("config.json");
			config = new Configuration(Configuration.defaultPath + "config.json");
		}
		if(enablePermission) {
			manager = new PermissionManager(this);
		}
	}
	
	public Bot(String token) {
		this(token, true, true);
	}

	public PermissionManager getPermissionManager() {
		return manager;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public Logger getLogger() {
		return logger;
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public void executeSafely(Runnable runnable) {
		executor.submit(runnable);
	}
	
	public <E> E executeSafely(Callable<E> callable) {
		Future<E> f = executor.submit(callable);
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			System.err.println("Cannot fetch result from callable: an exception occurred!");
			return null;
		}
	}

	public User getMe() {
		Future<User> future = executor.submit(() -> {
			String url = API + token + "/getMe";
			RequestUtility utils = null;

			try {
				utils = new RequestUtility(url);

				String res = utils.close();

				return parser.parseUser(new JSONObject(res).getJSONObject("result"), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		});
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			return null;
		}

	}

	private JSONArray getUpdates(int offset) {
		String url = API + token + "/getUpdates";
		RequestUtility utils = null;

		try {
			utils = new RequestUtility(url);

			utils.addParameter("offset", offset + "");

			String res = utils.close();
			return new JSONObject(res).getJSONArray("result");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void sendMessage(TextBase base, String chat_id, String reply_to_message_id, boolean disable_web_preview,
			boolean disable_notification, ReplyMarkup markup) {
		executor.submit(() -> {
			String url = "https://api.telegram.org/bot" + token + "/sendMessage";
			RequestUtility utils = null;

			try {
				utils = new RequestUtility(url);

				utils.addParameter("chat_id", chat_id);
				if (disable_web_preview)
					utils.addParameter("disable_web_preview", disable_web_preview + "");
				if (disable_notification)
					utils.addParameter("disable_notification", disable_notification + "");
				if (reply_to_message_id != null && !reply_to_message_id.equalsIgnoreCase(""))
					utils.addParameter("reply_to_message_id", reply_to_message_id);
				if (markup != null)
					utils.addParameter("reply_markup", markup.serialize());
				if (base instanceof TextComponent) {
					TextComponent tc = (TextComponent) base;
					if (tc.getMode() != ParsingMode.NONE)
						utils.addParameter("parse_mode", tc.getMode().getParsingMethodName());
					utils.addParameter("text", tc.toString());
					logger.info("Message sent: " + tc.toString());
				} else if (base instanceof RawText) {
					RawText text = (RawText) base;
					if (text.getMode() != ParsingMode.NONE)
						utils.addParameter("parse_mode", text.getMode().getParsingMethodName());
					utils.addParameter("text", text.getText());
					logger.info("Message sent: " + text.getText());
				}
				utils.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	public void sendMessage(TextBase base, String chat_id) {
		sendMessage(base, chat_id, null, false, false, null);
	}

	public void sendMessage(String text, String chat_id, String reply_to_message_id, boolean disable_web_preview,
			boolean disable_notification, ReplyMarkup markup) {
		sendMessage(new RawText(text, ParsingMode.NONE), chat_id, reply_to_message_id, disable_web_preview,
				disable_notification, markup);
	}

	public void sendMessage(String message, String chat_id) {
		sendMessage(new RawText(message, ParsingMode.NONE), chat_id, null, false, false, null);
	}

	public void forwardMessage(String chat_id, String from_chat_id, int message_id) {
		executor.submit(() -> {
			String url = API + token + "/forwardMessage";
			RequestUtility utils = null;

			try {
				utils = new RequestUtility(url);

				utils.addParameter("chat_id", chat_id);
				utils.addParameter("from_chat_id", from_chat_id);
				utils.addParameter("message_id", message_id + "");
				utils.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	public void sendPhoto(String chat_id, String photo, String caption) {
		executor.submit(() -> {
			String url = API + token + "/sendPhoto";
			RequestUtility utils = null;

			try {
				utils = new RequestUtility(url);

				utils.addParameter("chat_id", chat_id);
				utils.addParameter("photo", photo);
				if (caption != null)
					utils.addParameter("caption", caption);
				utils.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	public void sendPhoto(String chat_id, File photo, String caption) {
		executor.submit(() -> {
			String url = API + token + "/sendPhoto";
			MultipartUtility util = null;
			try {
				util = new MultipartUtility(url, "UTF-8");

				util.addFormField("chat_id", chat_id);
				util.addFilePart("photo", photo);
				if (caption != null)
					util.addFormField("caption", caption);

				util.finish();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	public void sendAudio(String chat_id, File audio, int duration, String performer, String title) {
		executor.submit(() -> {
			String url = API + token + "/sendAudio";
			MultipartUtility util = null;

			try {
				util = new MultipartUtility(url, "UTF-8");

				util.addFormField("chat_id", chat_id);
				util.addFilePart("audio", audio);
				if (duration > 0)
					util.addFormField("duration", duration + "");
				if (performer != null)
					util.addFormField("performer", performer);
				if (title != null)
					util.addFormField("title", title);

				util.finish();

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	public void sendAudio(String chat_id, String audio) {
		executor.submit(() -> {
			String url = API + token + "/sendAudio";
			RequestUtility utility = null;

			try {
				utility = new RequestUtility(url);

				utility.addParameter("chat_id", chat_id);
				utility.addParameter("audio", audio);

				utility.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}
	
	public Chat getChat(String chat_id) {
		Future<Chat> f = executor.submit(() -> {
            RequestUtility utility = new RequestUtility(API + token + "/getChat");

            utility.addParameter("chat_id", chat_id);
            String close = null;
            try {
                close = utility.close();
            } catch (IOException e){
                logger.severe("Chat id is either null or the chat is non-existant");
                return null;
            }
            return parser.parseChat(new JSONObject(close));
        });
		
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			logger.severe("Cannot fetch chat object: an exception occurred!");
			return null;
		}
	}
	
	public ChatMember[] getChatAdministrator(String chat_id) {
		Future<ChatMember[]> f = executor.submit(() -> {
            RequestUtility utility = new RequestUtility(API + token + "/getChat");

            utility.addParameter("chat_id", chat_id);
            String close = null;
            try {
                close = utility.close();
            } catch (IOException e){
                logger.severe("Chat id is either null or the chat is non-existant");
                return null;
            }
            JSONArray member = new JSONObject(close).getJSONArray("result");

            return parser.parseMultipleChatMembers(member);
        });
		
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			logger.severe("Cannot fetch chat object: an exception occurred!");
			return null;
		}
	}
	
	public int getChatMemberCount(String chat_id) {
		Future<Integer> f = executor.submit(() -> {
            RequestUtility utility = new RequestUtility(API + token + "/getChat");

            utility.addParameter("chat_id", chat_id);
            String close = null;
            try {
                close = utility.close();
            } catch (IOException e){
                logger.severe("Chat id is either null or the chat is non-existant");
                return null;
            }
            return new JSONObject(close).getInt("result");
        });
		
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			logger.severe("Cannot fetch chat object: an exception occurred!");
			return 0;
		}
	}
	
	public ChatMember getChatMember(String chat_id, String user_id) {
		Future<ChatMember> f = executor.submit(() -> {
            RequestUtility utility = new RequestUtility(API + token + "/getChat");

            utility.addParameter("chat_id", chat_id);
            utility.addParameter("user_id", user_id);
            String close = null;
            try {
                close = utility.close();
            } catch (IOException e){
                logger.severe("Chat id is either null or the chat is non-existant");
                return null;
            }
            return parser.parseChatMember(new JSONObject(close).getJSONObject("result"));
        });
		
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			logger.severe("Cannot fetch chat object: an exception occurred!");
			return null;
		}
	}

	public void init() {
		executor.submit(() -> {
			int last_update_id = 0;
			JSONArray responses = null;

			while (true) {
				responses = getUpdates(last_update_id++);

				if (responses.isNull(0)) {
					continue;
				} else {
					last_update_id = responses.getJSONObject(responses.length() - 1).getInt("update_id") + 1;
				}

				for (int i = 0; i < responses.length(); i++) {
					if (responses.getJSONObject(i).has("message")) {
						JSONObject message = responses.getJSONObject(i).getJSONObject("message");

						latestMessage = parser.parseMessage(message, this);

						logger.info("Message received! Text: " + latestMessage.getText() + " - Sender: [First name = "
								+ latestMessage.getFrom().getFirst_name() + ", Second name (if present) = "
								+ latestMessage.getFrom().getLast_name() + ", Username (if present) = "
								+ latestMessage.getFrom().getUsername() + ", Id = " + latestMessage.getFrom().getId()
								+ ", ChatType = " + latestMessage.getChat().getType().toString() + ", ChatId = "
								+ latestMessage.getChat().getId() + "]");

						EventManager.callEvent(new ReceiveMessageEvent(latestMessage));

						if (latestMessage.getText().toLowerCase().startsWith("/")) {
							Commands.trigger(latestMessage);
						}
					} else if (responses.getJSONObject(i).has("edited_message")) {
						JSONObject message = responses.getJSONObject(i).getJSONObject("edited_message");

						Message edited_message = parser.parseMessage(message, this);

						logger.info("Message Modified: " + edited_message.getText());

						EventManager.callEvent(new ModifyMessageEvent(edited_message));
					}
				}
			}
		});
	}
	
	public void shutdown(int exitCode) {
		executor.shutdownNow();
		System.exit(exitCode);
	}

}
