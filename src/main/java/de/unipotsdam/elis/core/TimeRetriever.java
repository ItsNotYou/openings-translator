package de.unipotsdam.elis.core;

import java.io.IOException;
import java.util.List;

import de.unipotsdam.elis.api.OSMOpeningHours;
import de.unipotsdam.elis.client.OpeningPagesClient;

public class TimeRetriever {

	private final OpeningPagesClient client;

	public TimeRetriever(OpeningPagesClient client) {
		this.client = client;
	}

	public OSMOpeningHours readPalaisCanteen() throws UnknownFormatException, IOException {
		// Retrieve unformatted opening hours
		List<String> openingHours = client.readCanteenOpening();
		// Translate to formatted opening hours
		String result = new TimeInterpreter().translate(openingHours);
		// Create result
		return new OSMOpeningHours("Mensa Neues Palais", result);
	}
}
