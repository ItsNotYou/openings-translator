package de.unipotsdam.elis.resources;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;

import com.codahale.metrics.annotation.Timed;

import de.unipotsdam.elis.OpeningsParserConfig;
import de.unipotsdam.elis.client.OpeningsPageClient;
import de.unipotsdam.elis.core.ClientCreationException;
import de.unipotsdam.elis.core.OpeningsClientFactory;
import de.unipotsdam.elis.core.OpeningsTranslator;
import de.unipotsdam.elis.core.UnknownFormatException;

@Path("/openings")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
public class ReadResource {

	private OpeningsClientFactory factory;
	private List<OpeningsParserConfig> configs;
	private OpeningsTranslator translator;

	public ReadResource(OpeningsClientFactory factory, List<OpeningsParserConfig> configs, OpeningsTranslator translator) {
		this.factory = factory;
		this.configs = configs;
		this.translator = translator;
	}

	@GET
	@Timed
	public List<String> readLocations() {
		return configs.stream().map(x -> x.getName()).collect(Collectors.toList());
	}

	@GET
	@Timed
	@Path("/{name}")
	public String readOpenings(@PathParam("name") @NotEmpty String name) {
		// Select chosen configuration
		Optional<OpeningsParserConfig> result = configs.stream().filter(x -> x.getName().equals(name)).findFirst();
		if (!result.isPresent()) {
			throw new WebApplicationException("Name not found", 404);
		}

		try {
			// Create client
			OpeningsPageClient client = factory.createFor(result.get());
			// Read raw content
			String rawLines = client.readOpeningLines();
			// Translate raw content
			return translator.translate(rawLines);
		} catch (ClientCreationException e) {
			throw new WebApplicationException("Could not create client", 500);
		} catch (IOException e) {
			throw new WebApplicationException("Could not read openings", 500);
		} catch (UnknownFormatException e) {
			throw new WebApplicationException("Could not translate openings", 500);
		}
	}
}
