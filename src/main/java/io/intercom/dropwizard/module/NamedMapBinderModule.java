package io.intercom.dropwizard.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.Map;

import javax.annotation.Nonnull;

/**
 * Bind strings from one or more maps to Named values.<p>
 */
public class NamedMapBinderModule extends AbstractModule {

    @Nonnull
    private final Map<String, String>[] configs;

    @SafeVarargs
    public NamedMapBinderModule(Map<String, String>... configs) {
        this.configs = configs;
    }

    @Override
    protected void configure() {
        for (Map<String, String> config : configs) {
            Names.bindProperties(binder(), config);
        }
    }
}
