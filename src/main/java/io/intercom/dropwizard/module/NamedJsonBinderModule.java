package io.intercom.dropwizard.module;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.Map;

import javax.annotation.Nonnull;

import io.dropwizard.jackson.Jackson;

/**
 * Bind strings from one or more json objects.
 */
public class NamedJsonBinderModule extends AbstractModule {

    private static final Logger logger = LoggerFactory.getLogger("NamedJsonBinderModule");
    private static final ObjectMapper theMapper = Jackson.newObjectMapper();

    @Nonnull
    private final String[] locations;

    public NamedJsonBinderModule(String... locations) {
        this.locations = locations;
    }

    @Override
    protected void configure() {
        for (String location : locations) {
            Names.bindProperties(binder(), marshalJson(location));
        }
    }

    Map<String, String> marshalJson(String location) {
        String rawJson;
        try {
            if (location.startsWith("classpath:")) {
                final URL url =
                    Resources.getResource(location.substring("classpath:".length(), location.length()));
                rawJson = Resources.toString(url, Charsets.UTF_8);
            } else {
                rawJson = Files.toString(new File(location), Charsets.UTF_8);
            }

            return theMapper.readValue(
                rawJson,
                new TypeReference<Map<String, String>>() {
                });

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
