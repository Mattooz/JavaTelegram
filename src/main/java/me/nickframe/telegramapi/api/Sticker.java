package me.nickframe.telegramapi.api;

/**
 * Created by Utente on 24/10/2015.
 */
public class Sticker implements BotObject {

	private String file_id;
	private int width;
	private int height;
	private PhotoSize thumb;
	private int file_size;
	
    public Sticker(String file_id, int width, int height, PhotoSize thumb, int file_size) {
		super();
		this.file_id = file_id;
		this.width = width;
		this.height = height;
		this.thumb = thumb;
		this.file_size = file_size;
	}
    
	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public PhotoSize getThumb() {
		return thumb;
	}

	public void setThumb(PhotoSize thumb) {
		this.thumb = thumb;
	}

	public int getFile_size() {
		return file_size;
	}

	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}

	public BotObject getObject() {
        return getObject().getObject();
    }
}
