package io.intercom.dropwizard.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

import io.dropwizard.Configuration;

public class TheApplicationConfiguration extends Configuration {

    @JsonProperty
    public String b;

    @JsonProperty
    public Map<String, String> namedStrings;

}
