package me.niccolomattei.api.telegram;

/**
 * Created by Utente on 23/10/2015.
 */
public class Chat implements BotObject {

    private long id;
    private ChatType type;
    private String title;
    private String username;
    private String first_name;
    private String last_name;

    public Chat(long id, ChatType type, String title, String first_name, String username, String last_name) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.first_name = first_name;
        this.username = username;
        this.last_name = last_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public BotObject getObject() {
        return this;
    }

    public enum ChatType {
        PRIVATE, GROUP, CHANNEL, SUPERGROUP;
    }
}
