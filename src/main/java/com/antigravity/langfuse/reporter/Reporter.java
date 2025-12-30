package com.antigravity.langfuse.reporter;

import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.TraceModel;

/**
 * Interface for reporting Langfuse traces and observations.
 */
public interface Reporter extends AutoCloseable {
    void report(TraceModel trace);

    void report(ObservationModel observation);

    default void flush() {
    }

    @Override
    default void close() throws Exception {
    }
}
