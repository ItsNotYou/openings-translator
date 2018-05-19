package de.unipotsdam.elis.health;

import java.util.List;

import javax.ws.rs.client.Client;

import com.codahale.metrics.health.HealthCheck;

import de.unipotsdam.elis.OpeningsParserConfig;

/**
 * Checks whether all configured URLs are reachable (status code 200)
 * 
 * @author hgessner
 *
 */
public class OpeningsSourcesHealthCheck extends HealthCheck {

	private Client caller;
	private List<OpeningsParserConfig> parserConfigs;

	public OpeningsSourcesHealthCheck(Client caller, List<OpeningsParserConfig> parserConfigs) {
		this.caller = caller;
		this.parserConfigs = parserConfigs;
	}

	@Override
	protected Result check() throws Exception {
		int resultCount = 0;
		for (OpeningsParserConfig conf : parserConfigs) {
			String url = conf.getUrl();
			int status = caller.target(url).request().buildGet().invoke().getStatus();
			if (status != 200) {
				return Result.unhealthy("Could not connect, status code " + status + ", url " + url);
			}
			resultCount++;
		}

		return Result.healthy("Contacted " + resultCount + " URLs");
	}
}
