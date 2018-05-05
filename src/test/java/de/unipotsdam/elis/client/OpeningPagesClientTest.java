package de.unipotsdam.elis.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class OpeningPagesClientTest {

	private OpeningPagesClient sut;

	@Before
	public void before() throws IOException {
		this.sut = new OpeningPagesClient(null);
	}

	@Test
	public void shouldFindLinesForNeuesPalais() throws Exception {
		InputStream page = this.getClass().getClassLoader().getResourceAsStream("opening-pages/mensa-am-neuen-palais.html");
		List<String> result = sut.extractOpeningLines(page);

		assertEquals(2, result.size());
		assertEquals("Montag - Donnerstag:08.00 - 18.00 Uhr", result.get(0));
		assertEquals("Freitag:08.00 - 14.30 Uhr", result.get(1));
	}
}
