package de.unipotsdam.elis.core.grammar;

import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursBaseListener;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.DayContext;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.Day_partContext;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.Day_span_separatorContext;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.HourContext;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.LineContext;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.MinuteContext;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.TimeContext;
import de.unipotsdam.elis.core.grammar.antlr4.InformalOpeningHoursParser.Time_span_separatorContext;

public class ParseResultVisitor extends InformalOpeningHoursBaseListener {

	private StringBuilder buffer;
	private DayTranslatable translator;

	public ParseResultVisitor(StringBuilder buffer, DayTranslatable translator) {
		this.buffer = buffer;
		this.translator = translator;
	}

	@Override
	public void exitLine(LineContext ctx) {
		buffer.append("; ");
	}

	@Override
	public void enterDay(DayContext ctx) {
		buffer.append(translator.translate(ctx.getText()));
	}

	@Override
	public void enterDay_span_separator(Day_span_separatorContext ctx) {
		buffer.append("-");
	}

	@Override
	public void exitDay_part(Day_partContext ctx) {
		buffer.append(" ");
	}

	@Override
	public void enterTime(TimeContext ctx) {
		buffer.append(ctx.getChild(HourContext.class, 0).getText());
		buffer.append(":");
		buffer.append(ctx.getChild(MinuteContext.class, 0).getText());
	}

	@Override
	public void enterTime_span_separator(Time_span_separatorContext ctx) {
		buffer.append("-");
	}
}
