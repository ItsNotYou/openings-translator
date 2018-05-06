package de.unipotsdam.elis.core.grammar;

import static org.junit.Assert.assertEquals;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;

import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.OpeningsContext;

public class ParseResultVisitorTest {

	private final String shortRule = "Fr:             09:00 - 18:00";
	private OpeningsContext shortRuleContext;

	private final String longRule = "\n  \n   Montag - Donnerstag:08.00 - 18.00 Uhr\nFreitag:08.00 - 14.30 Uhr\n  \n   ";
	private OpeningsContext longRuleContext;

	private ParseTreeWalker walker;
	private DayTranslatable defaultTranslator = new DayTranslatable() {

		@Override
		public String translate(String day) {
			return day.substring(0, 2);
		}
	};

	@Before
	public void before() {
		walker = new ParseTreeWalker();

		ParserRunner parser = new ParserRunner();
		shortRuleContext = parser.createParser(shortRule);
		longRuleContext = parser.createParser(longRule);
	}

	@Test
	public void shouldCollectCanteenShortRule() {
		StringBuilder result = new StringBuilder();

		ParseResultVisitor visitor = new ParseResultVisitor(result, defaultTranslator);
		walker.walk(visitor, shortRuleContext);

		assertEquals("Fr 09:00-18:00; ", result.toString());
	}

	@Test
	public void shouldCollectCanteenLongRule() {
		StringBuilder result = new StringBuilder();

		ParseResultVisitor visitor = new ParseResultVisitor(result, defaultTranslator);
		walker.walk(visitor, longRuleContext);

		assertEquals("Mo-Do 08:00-18:00; Fr 08:00-14:30; ", result.toString());
	}
}
