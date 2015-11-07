package io.intercom.dropwizard.app;

import com.google.common.collect.ImmutableMultimap;

import java.io.PrintWriter;

import javax.inject.Inject;

import io.dropwizard.servlets.tasks.Task;

public class TheTask extends Task {

    public static final String NAME = "TheTask";

    private final TheDependent theDependent;

    @Inject
    public TheTask(TheDependent theDependent) {
        super(NAME);
        this.theDependent = theDependent;
    }

    @Override
    public void execute(ImmutableMultimap<String, String> parameters,
                        PrintWriter output) throws Exception {
        output.println("ok");
    }

    public TheDependent getTheDependent() {
        return theDependent;
    }
}
