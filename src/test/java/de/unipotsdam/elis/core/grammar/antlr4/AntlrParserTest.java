package de.unipotsdam.elis.core.grammar.antlr4;

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.junit.Test;

import de.unipotsdam.elis.core.grammar.ThrowingErrorListener;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.OpeningsContext;

public class AntlrParserTest {

	private OpeningsContext createParser(String content) throws RecognitionException {
		InformalOpeningHoursLexer lexer = new InformalOpeningHoursLexer(CharStreams.fromString(content));
		lexer.removeErrorListeners();
		lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		InformalOpeningHoursParser parser = new InformalOpeningHoursParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(ThrowingErrorListener.INSTANCE);
		return parser.openings();
	}

	@Test
	public void shouldParseSimpleFormat() {
		OpeningsContext result = createParser("Mo: 08:00-20:00");
		assertEquals("Mo:08:00-20:00", result.getText());
	}

	@Test(expected = ParseCancellationException.class)
	public void shouldNotParseRubish() {
		createParser("nicht geöffnet");
	}

	@Test
	public void shouldParseGermanTwoDayFormat() {
		OpeningsContext result = createParser("Mo-Do: 08:00-20:00");
		assertEquals("Mo-Do:08:00-20:00", result.getText());
	}

	@Test
	public void shouldParseEnglishTwoDayFormat() {
		OpeningsContext result = createParser("Tu-Su: 10:00-18:00");
		assertEquals("Tu-Su:10:00-18:00", result.getText());
	}

	@Test
	public void shouldParseGermanFullDayFormat() {
		OpeningsContext result = createParser("Montag - Donnerstag: 08.00 - 18.00 Uhr");
		assertEquals("Montag-Donnerstag:08.00-18.00Uhr", result.getText());
	}

	@Test
	public void shouldParseEnglishFullDayFormat() {
		OpeningsContext result = createParser("Monday - Saturday: 08.00 - 18.00");
		assertEquals("Monday-Saturday:08.00-18.00", result.getText());
	}

	@Test
	public void shouldParseMultipleLibraryLines() {
		OpeningsContext result = createParser("Montag - Donnerstag: 08.00 - 18.00 Uhr\nFreitag: 08.00 - 14.30 Uhr");
		assertEquals("Montag-Donnerstag:08.00-18.00Uhr\nFreitag:08.00-14.30Uhr", result.getText());
	}
}
