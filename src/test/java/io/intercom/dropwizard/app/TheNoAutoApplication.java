package io.intercom.dropwizard.app;

import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.google.inject.Module;

import java.util.List;

import io.dropwizard.setup.Environment;
import io.intercom.dropwizard.guice.GuiceApplication;
import io.intercom.dropwizard.module.NamedMapBinderModule;

public class TheNoAutoApplication extends GuiceApplication<TheApplicationConfiguration> {

    public TheNoAutoApplication(String... basePackages) {
        super(basePackages);
    }

    @Override
    protected List<Module> addModules(TheApplicationConfiguration configuration, Environment environment) {
        return Lists.newArrayList(
            new TheModule(configuration),
            new NamedMapBinderModule(configuration.namedStrings)
        );
    }

    @Override
    protected void applicationAtRun(TheApplicationConfiguration configuration, Environment environment, Injector injector) {
        environment.admin().addTask(injector.getInstance(TheTask.class));
        environment.healthChecks().register(injector.getInstance(TheHealthCheck.class).getClass()
            .getSimpleName(), injector.getInstance(TheHealthCheck.class));
        environment.lifecycle().manage(injector.getInstance(TheManaged.class));
        TheResource instance = injector.getInstance(TheResource.class);
        environment.jersey().register(instance);
    }
}
