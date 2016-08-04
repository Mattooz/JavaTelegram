package me.niccolomattei.api.telegram.utils.text;

public enum ParsingMode {
	
	MARKDOWN("Markdown"),
	HTML("HTML"),
	NONE("");
	
	private String parsingMethodName;
	
	ParsingMode(String parsingMethodName) {
		this.parsingMethodName = parsingMethodName;
	}
	
	public String getParsingMethodName() {
		return parsingMethodName;
	}

}
