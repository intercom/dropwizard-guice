package io.intercom.dropwizard.app;

import javax.inject.Inject;

public class TheDependencyDefault implements TheDependency {

    private final TheApplicationConfiguration configuration;

    @Inject
    public TheDependencyDefault(TheApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getB() {
        return configuration.b;
    }
}
