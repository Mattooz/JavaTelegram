package me.niccolomattei.api.telegram.inline;

import me.niccolomattei.api.telegram.serialization.IgnoreClassName;
import me.niccolomattei.api.telegram.serialization.JSONSerializer;
import me.niccolomattei.api.telegram.utils.text.ParsingMode;
import me.niccolomattei.api.telegram.utils.text.TextBase;
import org.json.JSONObject;

/**
 * Created by Niccolò Mattei on 07/09/2016.
 */
@IgnoreClassName
public class InputTextMessageContent implements InputMessageContent {

    public String message_text;
    public String parse_mode;
    public boolean disable_web_page_preview;

    public InputTextMessageContent() {}

    public InputTextMessageContent(TextBase base, boolean disable_web_page_preview) {
        this.message_text = base.make();
        this.parse_mode = base.getParsingMode().getParsingMethodName();
        this.disable_web_page_preview = disable_web_page_preview;
    }

    public InputTextMessageContent(String text, ParsingMode mode, boolean disable_web_page_preview) {
        this.message_text = text;
        this.parse_mode = mode.getParsingMethodName();
        this.disable_web_page_preview = disable_web_page_preview;
    }

    public String getMessage_text() {
        return message_text;
    }

    public String getParse_mode() {
        return parse_mode;
    }

    public boolean isDisable_web_page_preview() {
        return disable_web_page_preview;
    }

    @Override
    public String serialize() {
        return JSONSerializer.serialize(this);
    }

    @Override
    public JSONObject serializeJson() {
        return JSONSerializer.serializeJson(this);
    }
}
