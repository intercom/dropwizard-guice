package io.intercom.dropwizard.module;

import javax.inject.Inject;
import javax.inject.Named;

public class Bar {

    private final String a1;
    private final String b1;

    @Inject
    public Bar(@Named("a1") String a1, @Named("b1") String b1) {
        this.a1 = a1;
        this.b1 = b1;
    }


    public String getA1() {
        return a1;
    }

    public String getB1() {
        return b1;
    }
}
