package de.unipotsdam.elis;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class openings translatorApplication extends Application<openings translatorConfiguration> {

    public static void main(final String[] args) throws Exception {
        new openings translatorApplication().run(args);
    }

    @Override
    public String getName() {
        return "openings translator";
    }

    @Override
    public void initialize(final Bootstrap<openings translatorConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final openings translatorConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
