package me.niccolomattei.api.telegram.utils.text;

import java.util.Arrays;
import java.util.List;

public class TextComponent implements TextBase {

    private List<TextArea> areas = null;
    private ParsingMode mode = null;

    public TextComponent(ParsingMode mode, TextArea... areas) {
        this.areas = Arrays.asList(areas);
        this.mode = mode;
    }

    public ParsingMode getParsingMode() {
        return mode;
    }

    @Override
    public String make() {
        StringBuilder builder = new StringBuilder();

        areas.stream().forEach(a ->
                builder.append(a.getRawString(mode)));

        return builder.toString();
    }

}
