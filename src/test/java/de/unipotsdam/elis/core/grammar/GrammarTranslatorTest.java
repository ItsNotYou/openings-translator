package de.unipotsdam.elis.core.grammar;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.unipotsdam.elis.core.UnknownFormatException;

public class GrammarTranslatorTest {

	@Test
	public void shouldTranslateCanteenShort() throws UnknownFormatException {
		GrammarTranslator sut = new GrammarTranslator();
		String result = sut.translate("Montag-Freitag: 10.00-15.00 Uhr");
		assertEquals("Mo-Fr 10:00-15:00; PH off", result);
	}

	@Test
	public void shouldTranslateCanteenLong() throws UnknownFormatException {
		GrammarTranslator sut = new GrammarTranslator();
		String result = sut.translate("Montag-Donnerstag: 08.00-18.00 Uhr\r\nFreitag: 08.00-14.30 Uhr");
		assertEquals("Mo-Th 08:00-18:00; Fr 08:00-14:30; PH off", result);
	}
}
