package io.intercom.dropwizard.module;

import javax.inject.Inject;
import javax.inject.Named;

public class Foo {

    private final String a;

    @Inject
    public Foo(@Named("a") String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }
}
