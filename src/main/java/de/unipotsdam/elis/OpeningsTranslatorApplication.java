package de.unipotsdam.elis;

import javax.ws.rs.client.Client;

import de.unipotsdam.elis.client.OpeningPagesClient;
import de.unipotsdam.elis.core.OpeningsClientFactory;
import de.unipotsdam.elis.core.TimeRetriever;
import de.unipotsdam.elis.health.OpeningPagesHealthCheck;
import de.unipotsdam.elis.health.OpeningsClientFactoryHealtCheck;
import de.unipotsdam.elis.health.OpeningsSourcesHealthCheck;
import de.unipotsdam.elis.resources.UpdateResource;
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
		TimeRetriever times = new TimeRetriever(pagesClient);
		OpeningsClientFactory factory = new OpeningsClientFactory(config.getOpeningParsers(), client);

		environment.jersey().register(new UpdateResource(times));
		environment.healthChecks().register("opening-pages", new OpeningPagesHealthCheck(pagesClient));
		environment.healthChecks().register("openings-client-factory", new OpeningsClientFactoryHealtCheck(factory));
		environment.healthChecks().register("openings-sources", new OpeningsSourcesHealthCheck(client, config.getOpeningParsers()));
	}
}
