package io.intercom.dropwizard.app;

import com.google.inject.AbstractModule;

public class TheModule extends AbstractModule {

    private TheApplicationConfiguration configuration;

    public TheModule(TheApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        bind(TheApplicationConfiguration.class).toInstance(configuration);
        bind(TheDependency.class).to(TheDependencyDefault.class);
        bind(TheDependent.class);
    }
}
