package de.unipotsdam.elis.core.grammar;

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Before;
import org.junit.Test;

import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.OpeningsContext;

public class AntlrParserTest {

	private ParserRunner sut;

	@Before
	public void before() {
		this.sut = new ParserRunner();
	}

	@Test
	public void shouldParseSimpleFormat() {
		OpeningsContext result = sut.createParser("Mo: 08:00-20:00");
		assertEquals("Mo:08:00-20:00", result.getText());
	}

	@Test(expected = ParseCancellationException.class)
	public void shouldNotParseRubish() {
		sut.createParser("nicht geöffnet");
	}

	@Test
	public void shouldParseGermanTwoDayFormat() {
		OpeningsContext result = sut.createParser("Mo-Do: 08:00-20:00");
		assertEquals("Mo-Do:08:00-20:00", result.getText());
	}

	@Test
	public void shouldParseEnglishTwoDayFormat() {
		OpeningsContext result = sut.createParser("Tu-Su: 10:00-18:00");
		assertEquals("Tu-Su:10:00-18:00", result.getText());
	}

	@Test
	public void shouldParseGermanFullDayFormat() {
		OpeningsContext result = sut.createParser("Montag - Donnerstag: 08.00 - 18.00 Uhr");
		assertEquals("Montag-Donnerstag:08.00-18.00Uhr", result.getText());
	}

	@Test
	public void shouldParseEnglishFullDayFormat() {
		OpeningsContext result = sut.createParser("Monday - Saturday: 08.00 - 18.00");
		assertEquals("Monday-Saturday:08.00-18.00", result.getText());
	}

	@Test
	public void shouldParseMultipleLibraryLines() {
		OpeningsContext result = sut.createParser("Montag - Donnerstag: 08.00 - 18.00 Uhr\nFreitag: 08.00 - 14.30 Uhr");
		assertEquals("Montag-Donnerstag:08.00-18.00Uhr\nFreitag:08.00-14.30Uhr", result.getText());
	}
}
