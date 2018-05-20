package de.unipotsdam.elis.health;

import java.util.List;

import com.codahale.metrics.health.HealthCheck;

import de.unipotsdam.elis.OpeningsParserConfig;
import de.unipotsdam.elis.client.OpeningsPageClient;
import de.unipotsdam.elis.core.ClientCreationException;
import de.unipotsdam.elis.core.OpeningsClientFactory;

/**
 * Checks whether all configured {@link OpeningsPageClient} instances can be created
 * 
 * @author hgessner
 *
 */
public class OpeningsClientFactoryHealtCheck extends HealthCheck {

	private final OpeningsClientFactory factory;
	private List<OpeningsParserConfig> configs;

	public OpeningsClientFactoryHealtCheck(OpeningsClientFactory factory, List<OpeningsParserConfig> configs) {
		this.factory = factory;
		this.configs = configs;
	}

	@Override
	protected Result check() throws Exception {
		try {
			int resultCount = factory.createAll(configs);
			return Result.healthy("Created " + resultCount + " clients");
		} catch (ClientCreationException e) {
			return Result.unhealthy(e);
		}
	}
}
