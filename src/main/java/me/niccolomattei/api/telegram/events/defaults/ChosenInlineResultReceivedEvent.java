package me.niccolomattei.api.telegram.events.defaults;

import me.niccolomattei.api.telegram.events.Event;
import me.niccolomattei.api.telegram.inline.ChosenInlineResult;

/**
 * Created by Niccolò Mattei on 13/09/2016.
 */
public class ChosenInlineResultReceivedEvent extends Event {

    private ChosenInlineResult chosenInlineResult;

    public ChosenInlineResultReceivedEvent(ChosenInlineResult chosenInlineResult) {
        this.chosenInlineResult = chosenInlineResult;
    }

    public ChosenInlineResult getChosenInlineResult() {
        return chosenInlineResult;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }
}
