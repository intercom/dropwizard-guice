package io.intercom.dropwizard.guice;

import com.google.inject.Injector;

import com.squarespace.jersey2.guice.BootstrapUtils;

import org.glassfish.hk2.api.ServiceLocator;

import javax.inject.Inject;

/**
 * This hooks up Guice to Jersey2's HK2 based injection system. <p>
 *
 * Derived from <a href="https://github.com/Squarespace/jersey2-guice">jersey2guice</a>, <a
 * href="https://github .com/stickfigure/gwizard">gwizard-jersey</a> and <a
 * href="dropwizard-guice">https://github.com/HubSpot/dropwizard-guice</a>.
 */
public class Hk2Linker {

    @Inject
    public Hk2Linker(Injector injector, ServiceLocator locator) {
        BootstrapUtils.link(locator, injector);
        BootstrapUtils.install(locator);
    }
}