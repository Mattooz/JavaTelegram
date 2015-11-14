package me.nickframe.telegramapi.api;

/**
 * Created by Utente on 23/10/2015.
 */
public class RequestError implements BotObject {

    private String ok;
    private String error_code;
    private String description;

    public RequestError(String ok, String error_code, String description) {
        this.ok = ok;
        this.error_code = error_code;
        this.description = description;
    }

    public String getOk() {
        return ok;
    }

    public String getError_code() {
        return error_code;
    }

    public String getDescription() {
        return description;
    }

    public BotObject getObject() {
        return this;
    }

}
