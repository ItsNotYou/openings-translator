package de.unipotsdam.elis.health;

import com.codahale.metrics.health.HealthCheck;

import de.unipotsdam.elis.client.OpeningPagesClient;

public class OpeningPagesHealthCheck extends HealthCheck {

	private final OpeningPagesClient client;

	public OpeningPagesHealthCheck(OpeningPagesClient client) {
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		int status = client.testConnectionStatus();
		if (status != 200) {
			return Result.unhealthy("Client cannot connect, status code " + status);
		}
		return Result.healthy();
	}
}
