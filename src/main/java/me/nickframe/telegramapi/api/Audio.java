package me.nickframe.telegramapi.api;

/**
 * Created by Utente on 24/10/2015.
 */
public class Audio implements BotObject {

	private String file_id;
	private int duration;
	private String performer;
	private String title;
	private String mime_type;
	private int file_size;
	
    public Audio(String file_id, int duration, String performer, String title, String mime_type, int file_size) {
		this.file_id = file_id;
		this.duration = duration;
		this.performer = performer;
		this.title = title;
		this.mime_type = mime_type;
		this.file_size = file_size;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMime_type() {
		return mime_type;
	}

	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}

	public int getFile_size() {
		return file_size;
	}

	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}

	public BotObject getObject() {
        return this;
    }
}
