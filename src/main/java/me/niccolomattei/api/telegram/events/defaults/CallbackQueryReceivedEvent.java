package me.niccolomattei.api.telegram.events.defaults;

import me.niccolomattei.api.telegram.events.Event;
import me.niccolomattei.api.telegram.inline.CallbackQuery;

/**
 * Created by Niccolò Mattei on 13/09/2016.
 */
public class CallbackQueryReceivedEvent extends Event {

    private CallbackQuery callbackQuery;

    public CallbackQueryReceivedEvent(CallbackQuery e) {
        this.callbackQuery = e;
    }

    public CallbackQuery getCallbackQuery() {
        return callbackQuery;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }
}
