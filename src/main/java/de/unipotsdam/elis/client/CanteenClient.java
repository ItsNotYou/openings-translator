package de.unipotsdam.elis.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.htmlcleaner.BaseToken;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import de.unipotsdam.elis.OpeningsParserConfig;

public class CanteenClient implements OpeningsPageClient {

	private Client client;
	private OpeningsParserConfig config;

	public CanteenClient(Client client, OpeningsParserConfig config) {
		this.client = client;
		this.config = config;
	}

	private Response readPage() {
		return client.target(config.getUrl()).request().buildGet().invoke();
	}

	String extractOpeningLines(InputStream stream) throws IOException {
		StringBuilder buffer = new StringBuilder();

		TagNode tagNode = new HtmlCleaner().clean(stream);
		try {
			Object[] result = tagNode.evaluateXPath("//div[@id='" + config.getSearchId() + "']");
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

		return buffer.toString().replace("&nbsp;", "").trim();
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
		List<String> breakers = Arrays.asList("h1", "h2", "h3", "h4", "h5");

		for (BaseToken b : node.getAllChildren()) {
			if (b instanceof ContentNode) {
				buffer.append(((ContentNode) b).getContent());
			} else if (b instanceof TagNode && breakers.contains(((TagNode) b).getName())) {
				break;
			} else if (b instanceof TagNode) {
				buffer.append(((TagNode) b).getText());
			}
		}
	}

	@Override
	public String readOpeningLines() throws IOException {
		Response response = readPage();
		if (response.getStatus() != 200) {
			throw new IOException("Could not read URL, status code " + response.getStatus());
		}

		return extractOpeningLines((InputStream) response.getEntity());
	}
}
