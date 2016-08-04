package me.niccolomattei.api.telegram;

public class MessageEntity {

	public static enum EntityType {
		// @username
		MENTION("mention"),

		HASHTAG("hashtag"),

		BOT_COMMAND("bot_command"),

		URL("url"),

		EMAIL("email"),

		BOLD("bold"),

		ITALIC("italic"),

		CODE("code"),

		PRE("pre"),

		TEXT_LINK("text_link"),

		TEXT_MENTION("text_mention");

		private String nameExact;

		private EntityType(String nameExact) {
			this.nameExact = nameExact;
		}

		public String getExactName() {
			return nameExact;
		}

		public static EntityType getFromName(String name) {
			String a = name.trim().toLowerCase();
			for(EntityType type : values()) if(a.equalsIgnoreCase(type.getExactName())) return type;
			return null;
		}

	}

}
