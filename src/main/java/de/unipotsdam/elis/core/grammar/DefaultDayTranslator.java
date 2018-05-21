package de.unipotsdam.elis.core.grammar;

import java.util.HashMap;
import java.util.Map;

public class DefaultDayTranslator implements DayTranslatable {

	@Override
	public String translate(String day) {
		Map<String, String> days = new HashMap<>();

		days.put("Montag", "Mo");
		days.put("Monday", "Mo");
		days.put("Mo", "Mo");

		days.put("Dienstag", "Tu");
		days.put("Tuesday", "Tu");
		days.put("Di", "Tu");
		days.put("Tu", "Tu");

		days.put("Mittwoch", "We");
		days.put("Wednesday", "We");
		days.put("Mi", "We");
		days.put("We", "We");

		days.put("Donnerstag", "Th");
		days.put("Thursday", "Th");
		days.put("Do", "Th");
		days.put("Th", "Th");

		days.put("Freitag", "Fr");
		days.put("Friday", "Fr");
		days.put("Fr", "Fr");

		days.put("Samstag", "Sa");
		days.put("Saturday", "Sa");
		days.put("Sa", "Sa");

		days.put("Sonntag", "Su");
		days.put("Sunday", "Su");
		days.put("So", "Su");
		days.put("Su", "Su");

		return days.get(day);
	}
}
