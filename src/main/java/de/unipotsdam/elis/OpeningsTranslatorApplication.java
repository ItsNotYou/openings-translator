package de.unipotsdam.elis;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OpeningsTranslatorApplication extends Application<OpeningsTranslatorConfiguration> {

	public static void main(final String[] args) throws Exception {
		new OpeningsTranslatorApplication().run(args);
	}

	@Override
	public String getName() {
		return "openings translator";
	}

	@Override
	public void initialize(final Bootstrap<OpeningsTranslatorConfiguration> bootstrap) {
		// TODO: application initialization
	}

	@Override
	public void run(final OpeningsTranslatorConfiguration configuration, final Environment environment) {
		// TODO: implement application
	}
}
