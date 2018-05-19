package de.unipotsdam.elis.client;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import de.unipotsdam.elis.OpeningsParserConfig;

public class CanteenClientTest {

	private InputStream input;
	private OpeningsParserConfig configCanteen;
	private OpeningsParserConfig configCafeteria;

	@Before
	public void before() {
		this.input = this.getClass().getClassLoader().getResourceAsStream("opening-pages/mensa-am-neuen-palais.html");

		this.configCanteen = new OpeningsParserConfig();
		this.configCanteen.setSearchId("c1063");

		this.configCafeteria = new OpeningsParserConfig();
		this.configCafeteria.setSearchId("c1064");
	}

	@Test
	public void shouldFindCanteenOpenings() throws Exception {
		CanteenClient sut = new CanteenClient(null, configCanteen);
		String result = sut.extractOpeningLines(input);
		assertEquals("Montag-Freitag:10.00-15.00Uhr", result.replaceAll(Pattern.quote(" "), ""));
	}

	@Test
	public void shouldFindCafeteriaOpenings() throws Exception {
		CanteenClient sut = new CanteenClient(null, configCafeteria);
		String result = sut.extractOpeningLines(input);
		assertEquals("Montag-Donnerstag:08.00-18.00Uhr\r\nFreitag:08.00-14.30Uhr", result.replaceAll(Pattern.quote(" "), ""));
	}
}
