package de.unipotsdam.elis.core;

public class ClientCreationException extends Exception {

	private static final long serialVersionUID = 1519915118114831194L;

	public ClientCreationException(String message) {
		super(message);
	}

	public ClientCreationException(String message, Throwable e) {
		super(message, e);
	}
}
