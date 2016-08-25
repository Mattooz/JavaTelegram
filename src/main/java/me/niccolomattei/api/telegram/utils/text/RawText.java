package me.niccolomattei.api.telegram.utils.text;

public class RawText implements TextBase {
	
	String text = "";
	ParsingMode mode = null;
	
	public RawText(String string, ParsingMode mode) {
		this.text = string;
		this.mode = mode;
	}
	
	public ParsingMode getParsingMode() {
		return mode;
	}

	@Override
	public String make() {
		return text;
	}
	
}
