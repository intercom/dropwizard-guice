package io.intercom.dropwizard.module;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NamedJsonBinderModuleTest {


    @Test
    public void testModule() {

        String location = "classpath:configuration-test.json";

        final Injector injector = Guice.createInjector(new NamedJsonBinderModule(location), new
            AbstractModule() {
            @Override
            protected void configure() {
                bind(Bar.class).asEagerSingleton();
            }
        });

        assertEquals("a", injector.getInstance(Bar.class).getA1());
        assertEquals("b", injector.getInstance(Bar.class).getB1());
    }

}