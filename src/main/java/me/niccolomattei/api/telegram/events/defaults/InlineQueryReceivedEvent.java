package me.niccolomattei.api.telegram.events.defaults;

import me.niccolomattei.api.telegram.events.Event;
import me.niccolomattei.api.telegram.inline.InlineQuery;

/**
 * Created by Niccolò Mattei on 13/09/2016.
 */
public class InlineQueryReceivedEvent extends Event {

    private InlineQuery inlineQuery;

    public InlineQueryReceivedEvent(InlineQuery inlineQuery) {
        this.inlineQuery = inlineQuery;
    }

    public InlineQuery getInlineQuery() {
        return inlineQuery;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }
}
