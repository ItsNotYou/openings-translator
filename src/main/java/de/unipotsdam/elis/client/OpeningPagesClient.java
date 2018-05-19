package de.unipotsdam.elis.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.htmlcleaner.BaseToken;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import de.unipotsdam.elis.OpeningsParserConfig;

public class OpeningPagesClient implements OpeningsPageClient {

	private static final String MENSA_AM_NEUEN_PALAIS = "http://www.studentenwerk-potsdam.de/mensa-am-neuen-palais.html";

	private final Client client;

	public OpeningPagesClient(Client client) {
		this.client = client;
	}

	public OpeningPagesClient(Client client, OpeningsParserConfig config) {
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
		StringBuilder buffer = new StringBuilder();

		TagNode tagNode = new HtmlCleaner().clean(stream);
		try {
			Object[] result = tagNode.evaluateXPath("//div[@id='c1064']");
			if (result.length == 0) {
				// TODO
				System.err.println("Not enough nodes");
			} else if (result.length >= 2) {
				// TODO
				System.err.println("Too many nodes");
			} else if (!(result[0] instanceof TagNode)) {
				// TODO
				System.err.println("Wrong node");
			} else {
				extractContent((TagNode) result[0], buffer);
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Arrays.asList(buffer.toString().trim().split("\\n")).stream().map(x -> x.trim()).collect(Collectors.toList());
	}

	/**
	 * Extracts {@link ContentNode} content recursively
	 * 
	 * @param node
	 *            Start node
	 * @param buffer
	 *            Target buffer
	 */
	private static void extractContent(final TagNode node, final StringBuilder buffer) {
		for (BaseToken b : node.getAllChildren()) {
			if (b instanceof ContentNode) {
				ContentNode c = (ContentNode) b;
				buffer.append(c.getContent());
			} else if (b instanceof TagNode) {
				extractContent((TagNode) b, buffer);
			}
		}
	}

	public List<String> readCanteenOpening() throws IOException {
		Response response = readPage();
		if (response.getStatus() != 200) {
			// TODO: throw PageNotFound exception
		}

		return extractOpeningLines((InputStream) response.getEntity());
	}

	@Override
	public String readOpeningLines() throws IOException {
		List<String> result = readCanteenOpening();
		return result.stream().reduce((a, b) -> a + "\n" + b).get();
	}
}
