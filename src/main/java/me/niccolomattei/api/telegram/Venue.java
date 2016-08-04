package me.niccolomattei.api.telegram;

public class Venue {

	Location location;
	String title;
	String address;
	String foursquare_id;
	
	public Venue(Location location, String title, String address, String foursquare_id) {
		super();
		this.location = location;
		this.title = title;
		this.address = address;
		this.foursquare_id = foursquare_id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFoursquare_id() {
		return foursquare_id;
	}

	public void setFoursquare_id(String foursquare_id) {
		this.foursquare_id = foursquare_id;
	}
	
}
