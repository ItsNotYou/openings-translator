package de.unipotsdam.elis.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TimeInterpreterTest {

	private TimeInterpreter sut = null;

	@Before
	public void before() {
		sut = new TimeInterpreter();
	}

	@Test
	public void shouldGivePublicHolidayOff() throws Exception {
		String result = sut.translate("Mo-Fr 10:00-20:00");
		assertTrue(result.endsWith("PH off"));
	}

	@Test
	public void shouldRemoveWhitespaces() throws Exception {
		String result = sut.translate("Mo - Th:  09:00 - 22:00");
		assertEquals("Mo-Th 09:00-22:00; PH off", result);
	}

	@Test(expected = UnknownFormatException.class)
	public void shouldThrowOnUnknownFormat() throws Exception {
		sut.translate("Mo and Tu: 12:00-14:00");
	}

	@Test
	public void shouldTranslateLibraryLineWeek() throws Exception {
		String result = sut.translate("Mo - Do:  09:00 - 22:00");
		assertEquals("Mo-Th 09:00-22:00; PH off", result);
	}

	@Test
	public void shouldTranslateLibraryLineDay() throws Exception {
		String result = sut.translate("Fr:             09:00 - 18:00");
		assertEquals("Fr 09:00-18:00; PH off", result);
	}
}
