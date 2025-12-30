package com.antigravity.langfuse.reporter;

import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.TraceModel;

import java.util.Arrays;
import java.util.List;

public class CompositeReporter implements Reporter {

    private final List<Reporter> reporters;

    public CompositeReporter(Reporter... reporters) {
        this.reporters = Arrays.asList(reporters);
    }

    @Override
    public void report(TraceModel trace) {
        for (Reporter reporter : reporters) {
            try {
                reporter.report(trace);
            } catch (Exception e) {
                System.err.println(
                        "Failed to report trace to " + reporter.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void report(ObservationModel observation) {
        for (Reporter reporter : reporters) {
            try {
                reporter.report(observation);
            } catch (Exception e) {
                System.err.println("Failed to report observation to " + reporter.getClass().getSimpleName() + ": "
                        + e.getMessage());
            }
        }
    }

    @Override
    public void close() throws Exception {
        Exception lastException = null;
        for (Reporter reporter : reporters) {
            try {
                reporter.close();
            } catch (Exception e) {
                lastException = e;
            }
        }
        if (lastException != null) {
            throw lastException;
        }
    }
}
