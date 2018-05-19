package de.unipotsdam.elis.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import de.unipotsdam.elis.OpeningsParserConfig;
import de.unipotsdam.elis.client.OpeningsPageClient;

public class OpeningsClientFactory {

	private final List<OpeningsParserConfig> config;
	private Client restClient;

	public OpeningsClientFactory(List<OpeningsParserConfig> config, Client restClient) {
		this.config = config;
		this.restClient = restClient;
	}

	public OpeningsPageClient createForUrl(String url) throws ClientCreationException {
		Optional<OpeningsParserConfig> result = config.stream().filter(c -> c.getUrl().equals(url)).findFirst();
		if (!result.isPresent()) {
			throw new ClientCreationException("Could not find matching config entry for URL " + url);
		}
		return createClient(restClient, result.get());
	}

	public int createAll() throws ClientCreationException {
		ArrayList<OpeningsPageClient> result = new ArrayList<>(config.size());
		for (OpeningsParserConfig c : config) {
			result.add(createClient(restClient, c));
		}
		return result.size();
	}

	private OpeningsPageClient createClient(Client restClient, OpeningsParserConfig config) throws ClientCreationException {
		String className = config.getClassName();
		try {
			Class<?> targetClass = Class.forName(className);
			Constructor<?> constructor = targetClass.getConstructor(Client.class, OpeningsParserConfig.class);
			return (OpeningsPageClient) constructor.newInstance(restClient, config);
		} catch (ClassNotFoundException e) {
			throw new ClientCreationException("Could not find class " + className, e);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new ClientCreationException("Could not find suitable constructor in class " + className, e);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ClientCreationException("Could not create instance of class " + className, e);
		}
	}
}
