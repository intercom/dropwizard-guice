package io.intercom.dropwizard.app;

import com.google.common.collect.Lists;
import com.google.inject.Module;

import java.util.List;

import io.dropwizard.setup.Environment;
import io.intercom.dropwizard.guice.GuiceApplication;
import io.intercom.dropwizard.module.NamedMapBinderModule;

public class TheApplication extends GuiceApplication<TheApplicationConfiguration> {


    public TheApplication(String... basePackages) {
        super(basePackages);
    }

    @Override
    protected List<Module> addModules(TheApplicationConfiguration configuration, Environment environment) {
        return Lists.newArrayList(
            new TheModule(configuration),
            new NamedMapBinderModule(configuration.namedStrings)
        );
    }
}
