package de.unipotsdam.elis.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeInterpreter {

	public String translate(String informalHours) throws UnknownFormatException {
		// Remove white spaces
		String shortened = informalHours.replaceAll("\\s", "");

		shortened = removeColonAfterDays(shortened, informalHours);
		shortened = translateDayPart(shortened, informalHours);
		shortened = translateCommentPart(shortened, informalHours);

		return shortened + "; PH off";
	}

	private String removeColonAfterDays(String noWhiteSpaces, String original) throws UnknownFormatException {
		// Remove colon after days
		// Matches dd-dd:hh:hh-hh:hh and dd:hh:hh-hh:hh with or without colon between days and hours
		// Day and hour part are a group 1 and 2 respectively
		String colonRemover = "^([A-Za-z][A-Za-z](?:-[A-Za-z][A-Za-z])?):?(\\d\\d:\\d\\d-\\d\\d:\\d\\d|\\w+)$";
		Matcher m = Pattern.compile(colonRemover).matcher(noWhiteSpaces);
		if (m.matches()) {
			return m.group(1) + " " + m.group(2);
		} else {
			// unknown content
			throw new UnknownFormatException(original);
		}
	}

	private String translateCommentPart(String shortened, String original) throws UnknownFormatException {
		String commentFinder = "^([\\w-]*)\\s(?:(\\d\\d:\\d\\d-\\d\\d:\\d\\d)|(\\w*))$";
		Matcher m = Pattern.compile(commentFinder).matcher(shortened);
		if (m.matches()) {
			return m.group(1) + " " + (m.group(2) != null ? m.group(2) : translateComment(m.group(3), original));
		} else {
			throw new UnknownFormatException(original);
		}
	}

	private String translateComment(String comment, String original) throws UnknownFormatException {
		List<String> germanComments = Arrays.asList("geschlossen");
		List<String> englishComments = Arrays.asList("off");

		int germanIndex = germanComments.indexOf(comment);
		if (germanIndex != -1) {
			return englishComments.get(germanIndex);
		} else {
			// unknown content
			throw new UnknownFormatException(original);
		}
	}

	private String translateDayPart(String shortened, String original) throws UnknownFormatException {
		String daysCatcher = "^([A-Za-z][A-Za-z])(?:-([A-Za-z][A-Za-z]))?(.*)$";
		Matcher m = Pattern.compile(daysCatcher).matcher(shortened);
		if (m.matches()) {
			List<String> groups = new ArrayList<>();
			for (int i = 0; i < m.groupCount(); i++) {
				groups.add(m.group(i));
			}

			return translateDay(m.group(1), original) + (m.group(2) == null ? m.group(3) : "-" + translateDay(m.group(2), original) + m.group(3));
		} else {
			// unknown content
			throw new UnknownFormatException(original);
		}
	}

	private String translateDay(String day, String original) throws UnknownFormatException {
		List<String> germanWeekdays = Arrays.asList("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So");
		List<String> englishWeekdays = Arrays.asList("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su");

		int germanIndex = germanWeekdays.indexOf(day);
		if (germanIndex != -1) {
			return englishWeekdays.get(germanIndex);
		} else if (englishWeekdays.contains(day)) {
			return day;
		} else {
			// unknown content
			throw new UnknownFormatException(original);
		}
	}
}
