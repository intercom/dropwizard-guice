package io.intercom.dropwizard.app;

import com.codahale.metrics.health.HealthCheck;

import javax.inject.Inject;

public class TheHealthCheck extends HealthCheck {

    private final TheDependent theDependent;

    @Inject
    public TheHealthCheck(TheDependent theDependent) {
        this.theDependent = theDependent;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

    public TheDependent getTheDependent() {
        return theDependent;
    }
}
