package me.niccolomattei.api.telegram.inline.objects;

import me.niccolomattei.api.telegram.inline.InlineQueryResult;
import me.niccolomattei.api.telegram.inline.InputMessageContent;
import me.niccolomattei.api.telegram.keyboard.inline.InlineKeyboardMarkup;
import me.niccolomattei.api.telegram.serialization.SerializationProperty;

/**
 * Created by Niccolò Mattei on 12/09/2016.
 */
public class InlineQueryResultArticle extends InlineQueryResult {

    @SerializationProperty(propertyName = "url", required = false)
    private String url;
    private boolean hide_url;
    @SerializationProperty(propertyName = "description", required = false)
    private String description;
    @SerializationProperty(propertyName = "thumb_url", required = false)
    private String thumb_url;
    private int thumb_width;
    private int thumb_height;
    private String title;

    public InlineQueryResultArticle() {
    }

    public InlineQueryResultArticle(String id, InputMessageContent input_message_content, String title) {
        this(id, input_message_content, null, null, false, null, null, 0, 0, title);
    }

    public InlineQueryResultArticle(String id, InputMessageContent input_message_content, InlineKeyboardMarkup reply_markup, String url, boolean hide_url, String description, String thumb_url, int thumb_width, int thumb_height, String title) {
        super("article", id, input_message_content, reply_markup);
        this.url = url;
        this.hide_url = hide_url;
        this.description = description;
        this.thumb_url = thumb_url;
        this.thumb_width = thumb_width;
        this.thumb_height = thumb_height;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public boolean isHide_url() {
        return hide_url;
    }

    public String getDescription() {
        return description;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public int getThumb_width() {
        return thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }
}
