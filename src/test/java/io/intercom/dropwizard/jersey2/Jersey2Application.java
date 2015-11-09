package io.intercom.dropwizard.jersey2;

import com.google.common.collect.Lists;
import com.google.inject.Module;

import java.util.List;

import io.dropwizard.setup.Environment;
import io.intercom.dropwizard.app.TheApplicationConfiguration;
import io.intercom.dropwizard.guice.GuiceApplication;

public class Jersey2Application extends GuiceApplication<TheApplicationConfiguration> {


    public Jersey2Application(String... basePackages) {
        super(basePackages);
    }

    @Override
    protected List<Module> addModules(TheApplicationConfiguration configuration, Environment environment) {
        return Lists.newArrayList(
            new Jersey2Module()
        );
    }
}
