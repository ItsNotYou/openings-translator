package de.unipotsdam.elis.core;

public class UnknownFormatException extends Exception {

	private static final long serialVersionUID = 4822931855735380122L;

	public UnknownFormatException(String pattern) {
		super("Unknown format for pattern: " + pattern);
	}
}
