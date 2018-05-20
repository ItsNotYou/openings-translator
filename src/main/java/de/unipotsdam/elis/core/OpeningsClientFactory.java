package de.unipotsdam.elis.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;

import de.unipotsdam.elis.OpeningsParserConfig;
import de.unipotsdam.elis.client.OpeningsPageClient;

public class OpeningsClientFactory {

	private Client restClient;

	public OpeningsClientFactory(Client restClient) {
		this.restClient = restClient;
	}

	public OpeningsPageClient createFor(OpeningsParserConfig config) throws ClientCreationException {
		return createClient(restClient, config);
	}

	public int createAll(List<OpeningsParserConfig> configs) throws ClientCreationException {
		ArrayList<OpeningsPageClient> result = new ArrayList<>(configs.size());
		for (OpeningsParserConfig config : configs) {
			result.add(createClient(restClient, config));
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
