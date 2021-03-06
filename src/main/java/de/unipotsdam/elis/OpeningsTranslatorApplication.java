package de.unipotsdam.elis;

import javax.ws.rs.client.Client;

import de.unipotsdam.elis.client.OpeningPagesClient;
import de.unipotsdam.elis.core.OpeningsClientFactory;
import de.unipotsdam.elis.core.OpeningsTranslator;
import de.unipotsdam.elis.core.grammar.GrammarTranslator;
import de.unipotsdam.elis.health.OpeningPagesHealthCheck;
import de.unipotsdam.elis.health.OpeningsClientFactoryHealtCheck;
import de.unipotsdam.elis.health.OpeningsSourcesHealthCheck;
import de.unipotsdam.elis.resources.ReadResource;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OpeningsTranslatorApplication extends Application<OpeningsTranslatorConfiguration> {

	public static void main(final String[] args) throws Exception {
		new OpeningsTranslatorApplication().run(args);
	}

	@Override
	public String getName() {
		return "openings-translator";
	}

	@Override
	public void initialize(final Bootstrap<OpeningsTranslatorConfiguration> bootstrap) {
	}

	@Override
	public void run(final OpeningsTranslatorConfiguration config, final Environment environment) {
		Client client = new JerseyClientBuilder(environment).using(config.getJerseyClientConfiguration()).build(getName());
		OpeningPagesClient pagesClient = new OpeningPagesClient(client);
		OpeningsClientFactory factory = new OpeningsClientFactory(client);
		OpeningsTranslator translator = new GrammarTranslator();

		environment.jersey().register(new ReadResource(factory, config.getOpeningParsers(), translator));
		environment.healthChecks().register("opening-pages", new OpeningPagesHealthCheck(pagesClient));
		environment.healthChecks().register("openings-client-factory", new OpeningsClientFactoryHealtCheck(factory, config.getOpeningParsers()));
		environment.healthChecks().register("openings-sources", new OpeningsSourcesHealthCheck(client, config.getOpeningParsers()));
	}
}
