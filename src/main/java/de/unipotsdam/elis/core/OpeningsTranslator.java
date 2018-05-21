package de.unipotsdam.elis.core;

public interface OpeningsTranslator {

	public String translate(String rawOpeningHours) throws UnknownFormatException;
}
