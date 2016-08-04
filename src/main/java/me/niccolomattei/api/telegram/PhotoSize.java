package me.niccolomattei.api.telegram;

/**
 * Created by Utente on 24/10/2015.
 */
public class PhotoSize implements BotObject {
	
	private String file_id;
	private int width;
	private int height;
	private int file_size;
	
    public PhotoSize(String file_id, int width, int height, int file_size) {
		super();
		this.file_id = file_id;
		this.width = width;
		this.height = height;
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
