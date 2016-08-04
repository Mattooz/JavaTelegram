package me.niccolomattei.api.telegram.utils.text;

public class TextHyperlink extends TextArea {

	String href = "google.com";

	public TextHyperlink(String string, String href) {
		super(string, TextType.PLAIN);
	}

	public String getHref() {
		return href;
	}

	@Override
	public String getText() {
		return super.getText();
	}

	@Override
	public String getRawString(ParsingMode mode) {
		StringBuilder builder = new StringBuilder();
		String res = "";
		switch (mode) {
		case HTML:
			builder.append("<a href=\"");
			builder.append(href);
			builder.append("\">");
			builder.append(text.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("&", "&amp;"));
			builder.append("</a>");
			
			res = builder.toString();
			break;
		case MARKDOWN:
			builder.append("[" + text +"]"  + "(" + href + ")");
			
			res = builder.toString();
			break;
		default:
			break;
		}
		return res;
	}

}
