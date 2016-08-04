package me.niccolomattei.api.telegram.utils.text;

public class TextType {
	
	public static final TextType BOLD = new TextType("<b>", "</b>", "*");
	public static final TextType ITALIC = new TextType("<i>", "</i>", "_");
	public static final TextType CODE = new TextType("<code>", "</code>", "`");
	public static final TextType PRE_FORMATTED_CODE = new TextType("<pre>", "</pre>", "```");
	public static final TextType PLAIN = new TextType("", "", "");
	
	private String markdown_symbol;
	private String html_start;
	private String html_end;
	
	TextType(String html_start, String html_end, String markdown_symbol) {
		this.markdown_symbol = markdown_symbol;
		this.html_start = html_start;
		this.html_end = html_end;
	}
	
	public String getMarkdownSymbol() {
		return markdown_symbol;
	}
	
	public String getHtmlStart() {
		return html_start;
	}
	
	public String getHtmlEnd() {
		return html_end;
	}

}
