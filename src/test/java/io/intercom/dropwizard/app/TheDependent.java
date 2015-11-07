package io.intercom.dropwizard.app;

import javax.inject.Inject;
import javax.inject.Named;

public class TheDependent {

    private TheDependency theDependency;
    private String a;

    @Inject
    public TheDependent(TheDependency theDependency, @Named("a") String a) {
        this.theDependency = theDependency;
        this.a = a;
    }

    public TheDependency getTheDependency() {
        return theDependency;
    }

    public String getA() {
        return a;
    }
}
