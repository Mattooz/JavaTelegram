package me.niccolomattei.api.telegram.keyboard.inline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niccolò Mattei on 11/09/2016.
 */
public class InlineKeyboardRow {

    private InlineKeyboardMarkup parent;
    private List<InlineKeyboardButton> buttons;

    protected InlineKeyboardRow(InlineKeyboardMarkup parent) {
        this.parent = parent;
        this.buttons = new ArrayList<>();
    }

    public InlineKeyboardRow addButton(InlineKeyboardButton button) {
        buttons.add(button);
        return this;
    }

    public void add() {
        parent.keyboardRows.add(this);
    }

    protected InlineKeyboardButton[] toButtonArray() {
        return buttons.toArray(new InlineKeyboardButton[buttons.size()]);
    }

}
