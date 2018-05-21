package de.unipotsdam.elis.core.grammar;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import de.unipotsdam.elis.core.OpeningsTranslator;
import de.unipotsdam.elis.core.UnknownFormatException;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.OpeningsContext;

public class GrammarTranslator implements OpeningsTranslator {

	@Override
	public String translate(String rawOpeningHours) throws UnknownFormatException {
		StringBuilder buffer = new StringBuilder();
		DefaultDayTranslator translator = new DefaultDayTranslator();
		ParseResultVisitor visitor = new ParseResultVisitor(buffer, translator);

		OpeningsContext context = new ParserRunner().createParser(rawOpeningHours);

		new ParseTreeWalker().walk(visitor, context);
		return buffer.toString() + "PH off";
	}
}
