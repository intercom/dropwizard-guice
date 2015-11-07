package io.intercom.dropwizard.jersey2;

import com.google.inject.ProvisionException;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.AdminEnvironment;
import io.dropwizard.setup.Environment;
import io.intercom.dropwizard.app.TheApplicationConfiguration;
import io.intercom.dropwizard.app.TheResource;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Jersey2ApplicationTest {


    private Environment environment;
    private JerseyEnvironment jerseyEnvironment;
    private TheApplicationConfiguration configuration;

    @Before
    public void before() throws Exception {
        configuration = new TheApplicationConfiguration();
        environment = mock(Environment.class);
        jerseyEnvironment = mock(JerseyEnvironment.class);
        when(environment.metrics()).thenReturn(new MetricRegistry());
        when(environment.jersey()).thenReturn(jerseyEnvironment);
    }

    @Test
    public void resourceTest() throws Exception {
        try {
            new Jersey2Application("io.intercom.dropwizard.jersey2").run(configuration, environment);
            fail();
        } catch (ProvisionException e) { // no bridge to hk2
            assertTrue(true);
        }
    }
}
