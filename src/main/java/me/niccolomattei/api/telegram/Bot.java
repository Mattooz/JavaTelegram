package me.niccolomattei.api.telegram;

import me.niccolomattei.api.telegram.commands.Commands;
import me.niccolomattei.api.telegram.configuration.Configuration;
import me.niccolomattei.api.telegram.events.EventManager;
import me.niccolomattei.api.telegram.events.defaults.*;
import me.niccolomattei.api.telegram.inline.CallbackQuery;
import me.niccolomattei.api.telegram.inline.ChosenInlineResult;
import me.niccolomattei.api.telegram.inline.InlineQuery;
import me.niccolomattei.api.telegram.inline.InlineQueryResult;
import me.niccolomattei.api.telegram.keyboard.ReplyMarkup;
import me.niccolomattei.api.telegram.logger.Logger;
import me.niccolomattei.api.telegram.parsing.Parser;
import me.niccolomattei.api.telegram.permission.PermissionManager;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import me.niccolomattei.api.telegram.utils.MultipartUtility;
import me.niccolomattei.api.telegram.utils.RequestUtility;
import me.niccolomattei.api.telegram.utils.text.ParsingMode;
import me.niccolomattei.api.telegram.utils.text.RawText;
import me.niccolomattei.api.telegram.utils.text.TextBase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Utente on 21/10/2015.
 */
public class Bot {

    public static final String API = "https://api.telegram.org/bot";
    public static final String USER_AGENT = "Mozilla/5.0";
    public static final String MULTIPART = "multipart/form-data";
    private Parser parser = new Parser();
    private Logger logger;
    private Configuration config;
    private PermissionManager manager;
    private volatile ExecutorService executor;
    private volatile ScheduledExecutorService schedulerService;
    private boolean running = false;
    private boolean controlThreadRunning = false;
    private int timesRestarted = 0;

    private Message latestMessage = null;

    private String token;
    public static Bot currentBot = null;

    public Bot(String token, boolean enableConfig, boolean enablePermission) {
        this.token = token;
        currentBot = this;
        executor = Executors.newFixedThreadPool(10);
        schedulerService = Executors.newScheduledThreadPool(10);
        logger = new Logger("Bot") {

            @Override
            public void handleIncomingMessage(Message message) {
                info("Message received! Text: " + message.getText() + " - Sender: [First name = "
                        + message.getFrom().getFirst_name() + ", Second name (if present) = "
                        + message.getFrom().getLast_name() + ", Username (if present) = "
                        + message.getFrom().getUsername() + ", Id = " + message.getFrom().getId()
                        + ", ChatType = " + message.getChat().getType().toString() + ", ChatId = "
                        + message.getChat().getId() + "]");
            }

            @Override
            public void handleIncomingModifiedMessage(Message message) {
                info("A message was edited! Text: " + message.getText() + " - Sender: [First name = "
                        + message.getFrom().getFirst_name() + ", Second name (if present) = "
                        + message.getFrom().getLast_name() + ", Username (if present) = "
                        + message.getFrom().getUsername() + ", Id = " + message.getFrom().getId()
                        + ", ChatType = " + message.getChat().getType().toString() + ", ChatId = "
                        + message.getChat().getId() + "]");
            }

            @Override
            public void handleIncomingCallbackQuery(CallbackQuery callbackQuery) {
                info("Callback Query Received!");
            }

            @Override
            public void handleIncomingInlineQuery(InlineQuery inlineQuery) {
                info("Inline Query Received!");
            }

            @Override
            public void handleIncomingChosenInlineResult(ChosenInlineResult chosenInlineResult) {
                info("ChosenInlineResult Received!");
            }

        };

        if (enableConfig) {
            Configuration.generateDefault("config.json");
            config = new Configuration(Configuration.defaultPath + "config.json");
        }
        if (enablePermission) {
            manager = new PermissionManager(this);
        }
    }

    public Bot(String token) {
        this(token, true, true);
    }

    public PermissionManager getPermissionManager() {
        return manager;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Configuration getConfig() {
        return config;
    }

    public boolean isRunning() {
        return running;
    }

    public ScheduledExecutorService getSchedulerService() {
        return schedulerService;
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
                if (base != null) {
                    if (base.getParsingMode() != ParsingMode.NONE)
                        utils.addParameter("parse_mode", base.getParsingMode().getParsingMethodName());
                    utils.addParameter("text", base.make());
                    logger.info("Message sent: " + base.make());
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

    public void banChatMember(String chat_id, int user_id) {
        executor.submit(() -> {
            String url = API + token + "/kickChatMember";
            RequestUtility requestUtility = null;

            try {
                requestUtility = new RequestUtility(url);

                requestUtility.addParameter("chat_id", chat_id);
                requestUtility.addParameter("user_id", user_id);

                requestUtility.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void leaveChat(String chat_id) {
        executor.submit(() -> {
            String url = API + token + "/leaveChat";
            RequestUtility requestUtility = null;

            try {
                requestUtility = new RequestUtility(url);

                requestUtility.addParameter("chat_id", chat_id);

                requestUtility.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void unbanChatMember(String chat_id, int user_id) {
        executor.submit(() -> {
            String url = API + token + "/unbanChatMember";
            RequestUtility requestUtility = null;

            try {
                requestUtility = new RequestUtility(url);

                requestUtility.addParameter("chat_id", chat_id);
                requestUtility.addParameter("user_id", user_id);

                requestUtility.close();
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
            } catch (IOException e) {
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
            } catch (IOException e) {
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
            } catch (IOException e) {
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
            } catch (IOException e) {
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

    public void editTextMessage(String chat_id, int message_id, TextBase component, boolean disable_web_page_preview, ReplyMarkup replyMarkup) {
        executor.submit(() -> {
            try {
                RequestUtility requestUtility = new RequestUtility(API + token + "/editMessageText");

                requestUtility.addParameter("chat_id", chat_id);
                requestUtility.addParameter("message_id", String.valueOf(message_id));
                requestUtility.addParameter("text", component.make());
                requestUtility.addParameter("parsing_mode", component.getParsingMode().getParsingMethodName());
                requestUtility.addParameter("disable_web_page_preview", String.valueOf(disable_web_page_preview));
                requestUtility.addParameter("reply_markup", replyMarkup.serialize());

                requestUtility.close();
            } catch (IOException e) {
                logger.severe("It looks like your bot threw an exception!");
                logger.processException(e);
            }
        });
    }

    public void editMessageCaption(String chat_id, int message_id, String caption, boolean disable_web_page_preview, ReplyMarkup replyMarkup) {
        executor.submit(() -> {
            try {
                RequestUtility requestUtility = new RequestUtility(API + token + "/editMessageCaption");

                requestUtility.addParameter("chat_id", chat_id);
                requestUtility.addParameter("message_id", String.valueOf(message_id));
                requestUtility.addParameter("caption", caption);
                requestUtility.addParameter("disable_web_page_preview", String.valueOf(disable_web_page_preview));
                requestUtility.addParameter("reply_markup", replyMarkup.serialize());

                requestUtility.close();
            } catch (IOException e) {
                logger.severe("It looks like your bot threw an exception!");
                logger.processException(e);
            }
        });
    }

    public void editMessageReplyMarkup(String chat_id, int message_id, ReplyMarkup replyMarkup) {
        executor.submit(() -> {
            try {
                RequestUtility requestUtility = new RequestUtility(API + token + "/editMessageReplyMarkup");

                requestUtility.addParameter("chat_id", chat_id);
                requestUtility.addParameter("message_id", String.valueOf(message_id));
                requestUtility.addParameter("reply_markup", replyMarkup.serialize());

                requestUtility.close();
            } catch (IOException e) {
                logger.severe("It looks like your bot threw an exception!");
                logger.processException(e);
            }
        });
    }

    public void answerInlineQuery(long inline_query_id, int cache_time, boolean is_personal, String next_offset, String switch_pm_text, String switch_pm_parameter, InlineQueryResult... results) {
        executor.submit(() -> {
            try {
                RequestUtility requestUtility = new RequestUtility(API + token + "/answerInlineQuery");

                List<InlineQueryResult> list = Arrays.asList(results);
                JSONArray jsonArray = new JSONArray(list);

                System.out.println(jsonArray.toString());

                requestUtility.addParameter("inline_query_id", inline_query_id);
                requestUtility.addParameter("cache_time", cache_time);
                requestUtility.addParameter("is_personal", is_personal);
                requestUtility.addParameter("next_offset", next_offset);
                requestUtility.addParameter("switch_pm_text", switch_pm_text);
                requestUtility.addParameter("switch_pm_parameter", switch_pm_parameter);
                requestUtility.addParameter("results", jsonArray);

                requestUtility.close();
            } catch (IOException e) {
                logger.severe("It looks like your bot threw an exception!");
                logger.processException(e);
            }
        });
    }

    public void answerCallbackQuery(String callback_query_id, String text, boolean show_alert) {
        executor.submit(() -> {
            try {
                RequestUtility requestUtility = new RequestUtility(API + token + "/answerCallbackQuery");
                requestUtility.addParameter("callback_query_id", callback_query_id);
                if (text != null || text != "") requestUtility.addParameter("text", text);
                requestUtility.addParameter("show_alert", show_alert);

                requestUtility.close();
            } catch (IOException e) {
                logger.severe("It looks like your bot threw an exception!");
                logger.processException(e);
            }
        });
    }

    public void init() {
        executor.submit(() -> {
            try {
                int last_update_id = -2;
                JSONArray responses = null;

                running = true;

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

                            logger.handleIncomingMessage(latestMessage);

                            EventManager.callEvent(new ReceiveMessageEvent(latestMessage));

                            if (latestMessage.getText().toLowerCase().startsWith("/")) {
                                Commands.trigger(latestMessage);
                            }
                        } else if (responses.getJSONObject(i).has("edited_message")) {
                            JSONObject message = responses.getJSONObject(i).getJSONObject("edited_message");

                            Message edited_message = parser.parseMessage(message, this);

                            logger.handleIncomingModifiedMessage(edited_message);

                            EventManager.callEvent(new ModifyMessageEvent(edited_message));
                        } else if (responses.getJSONObject(i).has("callback_query")) {
                            JSONObject callback_query = responses.getJSONObject(i).getJSONObject("callback_query");

                            CallbackQuery callbackQuery = parser.parseCallbackQuery(callback_query, this);

                            logger.handleIncomingCallbackQuery(callbackQuery);

                            EventManager.callEvent(new CallbackQueryReceivedEvent(callbackQuery));
                        } else if (responses.getJSONObject(i).has("chosen_inline_query")) {
                            JSONObject chosen_inline_query = responses.getJSONObject(i);

                            ChosenInlineResult chosenInlineResult = JSONSerializer.deserialize(ChosenInlineResult.class, chosen_inline_query);

                            EventManager.callEvent(new ChosenInlineResultReceivedEvent(chosenInlineResult));

                            logger.handleIncomingChosenInlineResult(chosenInlineResult);
                        } else if (responses.getJSONObject(i).has("inline_query")) {
                            JSONObject inline_query = responses.getJSONObject(i);

                            InlineQuery inlineQuery = JSONSerializer.deserialize(InlineQuery.class, inline_query);

                            logger.handleIncomingInlineQuery(inlineQuery);

                            EventManager.callEvent(new InlineQueryReceivedEvent(inlineQuery));
                        }
                    }
                }
            } catch (Exception e) {
                running = false;
                logger.severe("It looks like your bot crashed! Here's the detailed version of the crash: ");
                logger.processException(e);
            }
        });
        if(!controlThreadRunning) {
            executor.submit(() -> {
                controlThreadRunning = true;
                while (true) {
                    if (!running) {
                        if(timesRestarted <= 5) {
                            timesRestarted++;
                            logger.warning("Looks like your bot crashed! Let me restart it for you! Restart #" + timesRestarted);
                            init();
                        } else {
                            logger.severe("It looks like your bot is keeping crashing... Please fix your bot then start it back again.");
                            logger.warning("If you think that this is not your bot's fault, please make an issue on the github page!");
                            shutdown(1);
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        //do nothing
                    }
                }
            });
        }
    }

    public void shutdown(int exitCode) {
        executor.shutdownNow();
        System.exit(exitCode);
    }

}
