package io.intercom.dropwizard.jersey2;

import com.google.inject.AbstractModule;

import io.intercom.dropwizard.app.TheApplicationConfiguration;
import io.intercom.dropwizard.app.TheDependency;
import io.intercom.dropwizard.app.TheDependencyDefault;
import io.intercom.dropwizard.app.TheDependent;

public class Jersey2Module extends AbstractModule {

    private TheApplicationConfiguration configuration;

    public Jersey2Module() {
    }

    @Override
    protected void configure() {
    }
}
