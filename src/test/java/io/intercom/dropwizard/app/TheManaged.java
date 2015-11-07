package io.intercom.dropwizard.app;

import javax.inject.Inject;

import io.dropwizard.lifecycle.Managed;

public class TheManaged implements Managed {

    private final TheDependent theDependent;

    @Inject
    public TheManaged(TheDependent theDependent) {
        this.theDependent = theDependent;
    }

    @Override
    public void start() throws Exception {
        theDependent.toString();
    }

    @Override
    public void stop() throws Exception {
        theDependent.toString();
    }

    public TheDependent getTheDependent() {
        return theDependent;
    }
}
