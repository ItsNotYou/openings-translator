package de.unipotsdam.elis.core.grammar;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursLexer;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.OpeningsContext;

public class ParserRunner {

	public OpeningsContext createParser(String content) throws RecognitionException {
		InformalOpeningHoursLexer lexer = new InformalOpeningHoursLexer(CharStreams.fromString(content));
		lexer.removeErrorListeners();
		lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		InformalOpeningHoursParser parser = new InformalOpeningHoursParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(ThrowingErrorListener.INSTANCE);
		return parser.openings();
	}
}
