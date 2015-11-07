package io.intercom.dropwizard.app;

import com.google.common.collect.Maps;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.AdminEnvironment;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit.ResourceTestRule;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TheApplicationTest {

    public static final String VALUE_OF_A = "A";
    public static final String VALUE_OF_B = "B";
    public static final String KEY_OF_A = "a";

    private Environment environment;
    private HealthCheckRegistry healthCheckRegistry;
    private JerseyEnvironment jerseyEnvironment;
    private TheApplicationConfiguration configuration;
    private AdminEnvironment adminEnvironment;
    private LifecycleEnvironment lifecycleEnvironment;

    @Before
    public void before() throws Exception {
        configuration = new TheApplicationConfiguration();
        configuration.b = VALUE_OF_B;
        HashMap<String, String> map = Maps.newHashMap();
        map.put(KEY_OF_A, VALUE_OF_A);
        configuration.namedStrings = map;
        environment = mock(Environment.class);
        jerseyEnvironment = mock(JerseyEnvironment.class);
        healthCheckRegistry = mock(HealthCheckRegistry.class);
        adminEnvironment = mock(AdminEnvironment.class);
        lifecycleEnvironment = mock(LifecycleEnvironment.class);
        when(environment.metrics()).thenReturn(new MetricRegistry());
        when(environment.healthChecks()).thenReturn(healthCheckRegistry);
        when(environment.jersey()).thenReturn(jerseyEnvironment);
        when(environment.admin()).thenReturn(adminEnvironment);
        when(environment.lifecycle()).thenReturn(lifecycleEnvironment);
    }

    @Test
    public void resourceTest() throws Exception {
        new TheApplication("io.intercom.dropwizard.app").run(configuration, environment);
        assertResourceSetup();
    }

    @Test
    public void resourceTestPackage() throws Exception {
        new TheNoAutoApplication().run(configuration, environment);
        assertResourceSetup();
    }

    private void assertResourceSetup() {
        final ArgumentCaptor<TheResource> resource = ArgumentCaptor.forClass(TheResource.class);
        verify(jerseyEnvironment).register(resource.capture());
        final TheResource value = resource.getValue();
        assertEquals(value.getTheDependent().getTheDependency().getB(), VALUE_OF_B);
        assertEquals(value.getTheDependent().getA(), VALUE_OF_A);
    }

    @Test
    public void healthCheckTest() throws Exception {
        new TheApplication("io.intercom.dropwizard.app").run(configuration, environment);
        final ArgumentCaptor<? extends TheHealthCheck> healthCheck = ArgumentCaptor.forClass(TheHealthCheck.class);
        verify(healthCheckRegistry).register(eq("TheHealthCheck"), healthCheck.capture());
        final TheHealthCheck value = healthCheck.getValue();
        assertThat(value, instanceOf(TheHealthCheck.class));
        assertEquals(value.getTheDependent().getTheDependency().getB(), VALUE_OF_B);
        assertEquals(value.getTheDependent().getA(), VALUE_OF_A);
    }

    @Test
    public void taskTest() throws Exception {
        new TheApplication("io.intercom.dropwizard.app").run(configuration, environment);
        final ArgumentCaptor<? extends TheTask> task = ArgumentCaptor.forClass(TheTask.class);
        verify(adminEnvironment).addTask(task.capture());
        final TheTask value = task.getValue();
        assertEquals(value.getName(), TheTask.NAME);
        assertEquals(value.getTheDependent().getTheDependency().getB(), VALUE_OF_B);
        assertEquals(value.getTheDependent().getA(), VALUE_OF_A);
    }

    @Test
    public void managedTest() throws Exception {
        new TheApplication("io.intercom.dropwizard.app").run(configuration, environment);
        final ArgumentCaptor<? extends TheManaged> managed = ArgumentCaptor.forClass(TheManaged.class);
        verify(lifecycleEnvironment).manage(managed.capture());
        final TheManaged value = managed.getValue();
        assertThat(value, instanceOf(TheManaged.class));
        assertEquals(value.getTheDependent().getTheDependency().getB(), VALUE_OF_B);
        assertEquals(value.getTheDependent().getA(), VALUE_OF_A);
    }
}
