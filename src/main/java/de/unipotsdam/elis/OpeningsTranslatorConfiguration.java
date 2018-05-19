package de.unipotsdam.elis;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

public class OpeningsTranslatorConfiguration extends Configuration {

	@Valid
	@NotNull
	private JerseyClientConfiguration jerseyClient = new JerseyClientConfiguration();

	@Valid
	@NotNull
	@Size(min = 1)
	private List<OpeningsParserConfig> openingParsers = new LinkedList<OpeningsParserConfig>();

	@JsonProperty("jerseyClient")
	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return jerseyClient;
	}

	@JsonProperty("jerseyClient")
	public void setJerseyClientConfiguration(JerseyClientConfiguration jerseyClient) {
		this.jerseyClient = jerseyClient;
	}

	@JsonProperty("openingParsers")
	public List<OpeningsParserConfig> getOpeningParsers() {
		return openingParsers;
	}

	@JsonProperty("openingParsers")
	public void setOpeningParsers(List<OpeningsParserConfig> openingParsers) {
		this.openingParsers = openingParsers;
	}
}
