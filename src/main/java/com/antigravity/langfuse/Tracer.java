package com.antigravity.langfuse;

import com.antigravity.langfuse.reporter.FileReporter;
import com.antigravity.langfuse.reporter.KafkaReporter;
import com.antigravity.langfuse.reporter.Reporter;
import com.antigravity.langfuse.sdk.Observation;
import com.antigravity.langfuse.sdk.Trace;

import java.io.IOException;

public class Tracer {

    private final Reporter reporter;
    private final String defaultRelease;
    private final String defaultVersion;
    private final String defaultEnvironment;
    private final boolean isPublic;

    private Tracer(Reporter reporter, String defaultRelease, String defaultVersion, String defaultEnvironment,
            boolean isPublic) {
        this.reporter = reporter;
        this.defaultRelease = defaultRelease;
        this.defaultVersion = defaultVersion;
        this.defaultEnvironment = defaultEnvironment;
        this.isPublic = isPublic;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Reporter getReporter() {
        return reporter;
    }

    public String getDefaultRelease() {
        return defaultRelease;
    }

    public String getDefaultVersion() {
        return defaultVersion;
    }

    public String getDefaultEnvironment() {
        return defaultEnvironment;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Trace.Builder traceBuilder() {
        return new Trace.Builder(this);
    }

    public Observation.Builder observationBuilder() {
        return new Observation.Builder(this);
    }

    public void flush() {
        if (reporter != null) {
            reporter.flush();
        }
    }

    public static class Builder {
        private Reporter reporter;
        private String defaultRelease;
        private String defaultVersion;
        private String defaultEnvironment = "default";
        private boolean isPublic = false;

        public Builder fileReporter(String path) {
            try {
                this.reporter = new FileReporter(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to initialize FileReporter", e);
            }
            return this;
        }

        public Builder kafkaReporter(String bootstrapServers, String topic) {
            java.util.Properties props = new java.util.Properties();
            props.put("bootstrap.servers", bootstrapServers);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            this.reporter = new KafkaReporter(props, topic, topic);
            return this;
        }

        public Builder customReporter(Reporter reporter) {
            this.reporter = reporter;
            return this;
        }

        public Builder defaultRelease(String release) {
            this.defaultRelease = release;
            return this;
        }

        public Builder defaultVersion(String version) {
            this.defaultVersion = version;
            return this;
        }

        public Builder defaultEnvironment(String environment) {
            this.defaultEnvironment = environment;
            return this;
        }

        public Builder isPublic(boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public Tracer build() {
            if (reporter == null) {
                // Default fallback as per requirement, or exception?
                // Proposal said "uses a default (noop or log) reporter if none configured"
                // For now, let's default to a dummy FileReporter or warning, but maybe
                // FileReporter is safe for now
                try {
                    this.reporter = new FileReporter("langfuse-traces.json");
                } catch (IOException e) {
                    System.err.println("Warning: Failed to create default reporter: " + e.getMessage());
                }
            }
            return new Tracer(reporter, defaultRelease, defaultVersion, defaultEnvironment, isPublic);
        }
    }
}
