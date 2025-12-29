package com.antigravity.langfuse.traces.context;

import com.antigravity.langfuse.traces.dto.Trace;

/**
 * Trace上下文对象，用于在方法之间传递，避免使用ThreadLocal
 */
public class TraceContext {
    private final String traceId;
    private final String projectId;
    private final Trace trace;
    private final String parentObservationId;

    private TraceContext(Builder builder) {
        this.traceId = builder.traceId;
        this.projectId = builder.projectId;
        this.trace = builder.trace;
        this.parentObservationId = builder.parentObservationId;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getProjectId() {
        return projectId;
    }

    public Trace getTrace() {
        return trace;
    }

    public String getParentObservationId() {
        return parentObservationId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String traceId;
        private String projectId;
        private Trace trace;
        private String parentObservationId;

        public Builder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public Builder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public Builder trace(Trace trace) {
            this.trace = trace;
            return this;
        }

        public Builder parentObservationId(String parentObservationId) {
            this.parentObservationId = parentObservationId;
            return this;
        }

        public TraceContext build() {
            if (this.traceId == null) {
                throw new IllegalArgumentException("traceId is required");
            }
            if (this.projectId == null) {
                throw new IllegalArgumentException("projectId is required");
            }
            return new TraceContext(this);
        }
    }
}