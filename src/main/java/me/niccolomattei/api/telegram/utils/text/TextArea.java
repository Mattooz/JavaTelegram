package me.niccolomattei.api.telegram.utils.text;

public class TextArea {
	
	String text = "";
	TextType type = null;
	
	public TextArea(String string, TextType type) {
		this.text = string;
		this.type = type;
	}
	
	public String getText() {
		return text;
	}
	
	public TextType getType() {
		return type;
	}
	
	public String getRawString(ParsingMode mode) {
		String res = "";
		switch(mode) {
		case HTML:
			StringBuilder builder = new StringBuilder();
			
			builder.append(type.getHtmlStart());
			builder.append(text.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
			builder.append(type.getHtmlEnd());
			
			res = builder.toString();
			break;
		case MARKDOWN:
			StringBuilder builder1 = new StringBuilder();
			
			builder1.append(type.getMarkdownSymbol());
			builder1.append(text);
			builder1.append(type.getMarkdownSymbol());
			
			res = builder1.toString();
			break;
		default:
			break;
		}
		return res;
	}

}
