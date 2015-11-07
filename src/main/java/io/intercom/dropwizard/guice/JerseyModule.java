package io.intercom.dropwizard.guice;

import com.google.inject.servlet.ServletModule;

import com.squarespace.jersey2.guice.BootstrapModule;
import com.squarespace.jersey2.guice.BootstrapUtils;

/**
 * Register a Hk2Linker to allow Guice and Jersey2 to integrate. <p>
 *
 * Derived from <a href="https://github.com/Squarespace/jersey2-guice">jersey2guice</a>, <a
 * href="https://github .com/stickfigure/gwizard">gwizard-jersey</a> and <a
 * href="dropwizard-guice">https://github.com/HubSpot/dropwizard-guice</a>.
 */
public class JerseyModule extends ServletModule {

    @Override
    protected void configureServlets() {
        install(new BootstrapModule(BootstrapUtils.newServiceLocator()));
        bind(Hk2Linker.class).asEagerSingleton();
    }
}
