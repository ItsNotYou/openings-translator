package de.unipotsdam.elis;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpeningsParserConfig {

	@NotNull
	private String url;

	@NotNull
	private String className;

	private String searchId;

	@NotNull
	private String name;

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("className")
	public String getClassName() {
		return className;
	}

	@JsonProperty("className")
	public void setClassName(String className) {
		this.className = className;
	}

	@JsonProperty("searchId")
	public String getSearchId() {
		return searchId;
	}

	@JsonProperty("searchId")
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
}
