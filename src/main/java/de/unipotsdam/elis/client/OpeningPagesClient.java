package de.unipotsdam.elis.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class OpeningPagesClient {

	private static final String MENSA_AM_NEUEN_PALAIS = "http://www.studentenwerk-potsdam.de/mensa-am-neuen-palais.html";

	private final Client client;

	public OpeningPagesClient(Client client) {
		this.client = client;
	}

	public int testConnectionStatus() {
		Response response = readPage();
		return response.getStatus();
	}

	private Response readPage() {
		return client.target(MENSA_AM_NEUEN_PALAIS).request().buildGet().invoke();
	}

	List<String> extractOpeningLines(InputStream stream) throws IOException {
		TagNode rootNode = new HtmlCleaner().clean(stream);
		try {
			// Object[] nodes = rootNode.evaluateXPath("//div[@id='c1063']");
			Object[] nodes = rootNode.evaluateXPath("//div[@class]");
			if (nodes.length == 0) {
				// TODO throw error
				System.err.println("No node found");
			} else if (nodes.length >= 2) {
				// TODO throw error
				System.err.println("Too many nodes");
			} else {
				TagNode div = (TagNode) nodes[0];
				System.out.println(div);
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Arrays.asList();
	}

	public List<String> readCanteenOpening() throws IOException {
		Response response = readPage();
		if (response.getStatus() != 200) {
			// TODO: throw PageNotFound exception
		}

		return extractOpeningLines((InputStream) response.getEntity());
	}
}
