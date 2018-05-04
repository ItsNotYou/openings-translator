package de.unipotsdam.elis.api;

public class OSMOpeningHours {

	private String location;
	private String hours;

	public OSMOpeningHours(String location, String hours) {
		this.location = location;
		this.hours = hours;
	}

	public String getLocation() {
		return location;
	}

	public String getHours() {
		return hours;
	}
}
