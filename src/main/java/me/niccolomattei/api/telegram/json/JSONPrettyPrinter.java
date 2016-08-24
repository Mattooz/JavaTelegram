package me.niccolomattei.api.telegram.json;

import org.json.JSONObject;

public class JSONPrettyPrinter {

	public static String prettyPrint(JSONObject object) {
		char[] chars = object.toString().toCharArray();
		StringBuilder builder = new StringBuilder();
		int spacesToAdd = 0;
		
		for(char ch : chars) {
			switch(ch) {
			case '{':
				spacesToAdd += 2;
				builder.append(ch);
				builder.append("\n");
				addSpaces(builder, spacesToAdd);
				break;
			case ':':
				builder.append(ch);
				addSpaces(builder, 1);
				break;
			case '}':
				spacesToAdd -= 2;
				builder.append("\n");
				addSpaces(builder, spacesToAdd);
				builder.append(ch);
				break;
			case ',':
				builder.append(ch);
				builder.append("\n");
				addSpaces(builder, spacesToAdd);
				break;
			case '[':
				spacesToAdd += 2;
				builder.append(ch);
				builder.append("\n");
				addSpaces(builder, spacesToAdd);
				break;
			case ']':
				spacesToAdd -= 2;
				builder.append("\n");
				addSpaces(builder, spacesToAdd);
				builder.append(ch);
				break;
			default:
				builder.append(ch);
				break;
			}
		}
		
		return builder.toString();
	}
	
	private static void addSpaces(StringBuilder builder, int spaces) {
		for(;spaces > 0;spaces--) {
			builder.append(' ');
		}
	}
	
}
