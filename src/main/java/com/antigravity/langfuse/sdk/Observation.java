package com.antigravity.langfuse.sdk;

import com.antigravity.langfuse.Tracer;
import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.ObservationType;
import com.antigravity.langfuse.utils.IdGenerator;

import java.time.Instant;
import java.util.Map;

public class Observation {

    private final ObservationModel model;
    private final Tracer tracer;

    Observation(ObservationModel model, Tracer tracer) {
        this.model = model;
        this.tracer = tracer;
    }

    public String getId() {
        return model.getId();
    }

    public String getTraceId() {
        return model.getTraceId();
    }

    public void update(Map<String, String> metadata, String version, String release, String name, String input,
            String output,
            String level, String statusMessage, String model, Map<String, Object> modelParameters,
            Integer completionTokens, Integer promptTokens, Integer totalTokens, String unit) {
        if (metadata != null)
            this.model.setMetadata(metadata);
        if (version != null)
            this.model.setVersion(version);
        if (release != null)
            this.model.setRelease(release);
        if (name != null)
            this.model.setName(name);
        if (input != null)
            this.model.setInput(input);
        if (output != null)
            this.model.setOutput(output);
        if (level != null)
            this.model.setLevel(level);
        if (statusMessage != null)
            this.model.setStatusMessage(statusMessage);
        if (model != null)
            this.model.setModel(model);
        if (modelParameters != null)
            this.model.setModelParameters(modelParameters);
        if (completionTokens != null)
            this.model.setCompletion(completionTokens);
        if (promptTokens != null)
            this.model.setPrompt(promptTokens);
        if (totalTokens != null)
            this.model.setTotal(totalTokens);
        if (unit != null)
            this.model.setUnit(unit);

        this.model.setUpdatedAt(Instant.now());
    }

    public void setName(String name) {
        this.model.setName(name);
        touch();
    }

    public void setInput(String input) {
        this.model.setInput(input);
        touch();
    }

    public void setOutput(String output) {
        this.model.setOutput(output);
        touch();
    }

    public void setLevel(String level) {
        this.model.setLevel(level);
        touch();
    }

    public void setStatusMessage(String statusMessage) {
        this.model.setStatusMessage(statusMessage);
        touch();
    }

    public void setModel(String model) {
        this.model.setModel(model);
        touch();
    }

    public void setModelParameters(Map<String, Object> modelParameters) {
        this.model.setModelParameters(modelParameters);
        touch();
    }

    public void setVersion(String version) {
        this.model.setVersion(version);
        touch();
    }

    public void setRelease(String release) {
        this.model.setRelease(release);
        touch();
    }

    public void setUsage(Integer prompt, Integer completion, Integer total, String unit) {
        if (prompt != null)
            this.model.setPrompt(prompt);
        if (completion != null)
            this.model.setCompletion(completion);
        if (total != null)
            this.model.setTotal(total);
        if (unit != null)
            this.model.setUnit(unit);
        touch();
    }

    public void setCompletionStartTime(Instant completionStartTime) {
        this.model.setCompletionStartTime(completionStartTime);
        touch();
    }

    public void setPublic(Boolean isPublic) {
        this.model.setPublic(isPublic);
        touch();
    }

    public void addMetadata(String key, String value) {
        if (this.model.getMetadata() == null) {
            this.model.setMetadata(new java.util.HashMap<>());
        }
        this.model.getMetadata().put(key, value);
        touch();
    }

    private void touch() {
        this.model.setUpdatedAt(Instant.now());
    }

    public void end() {
        if (this.model.getEndTime() == null) {
            this.model.setEndTime(Instant.now());
        }
        // Calculate duration or other metrics if needed
        this.tracer.getReporter().report(this.model);
    }

    public static class Builder {
        private final ObservationModel.ObservationModelBuilder modelBuilder = ObservationModel.builder();
        private final Tracer tracer;

        public Builder(Tracer tracer) {
            this.tracer = tracer;
            if (tracer != null) {
                modelBuilder.release(tracer.getDefaultRelease());
                modelBuilder.version(tracer.getDefaultVersion());
                modelBuilder.environment(tracer.getDefaultEnvironment());
                modelBuilder.isPublic(tracer.isPublic());
            }
            modelBuilder.id(IdGenerator.generateObservationId());
        }

        public Builder parent(Trace trace) {
            modelBuilder.traceId(trace.getId());
            return this;
        }

        public Builder parent(Observation observation) {
            modelBuilder.traceId(observation.getTraceId());
            modelBuilder.parentObservationId(observation.getId());
            return this;
        }

        // Temporary fix: allowing manual traceId/parentId setting or we need to expose
        // getters in Observation SDK

        public Builder traceId(String traceId) {
            modelBuilder.traceId(traceId);
            return this;
        }

        public Builder parentObservationId(String parentObservationId) {
            modelBuilder.parentObservationId(parentObservationId);
            return this;
        }

        public Builder id(String id) {
            modelBuilder.id(id);
            return this;
        }

        public Builder name(String name) {
            modelBuilder.name(name);
            return this;
        }

        public Builder startTime(Instant startTime) {
            modelBuilder.startTime(startTime);
            return this;
        }

        public Builder type(ObservationType type) {
            modelBuilder.type(type);
            return this;
        }

        public Builder metadata(Map<String, String> metadata) {
            modelBuilder.metadata(metadata);
            return this;
        }

        public Builder input(String input) {
            modelBuilder.input(input);
            return this;
        }

        public Builder output(String output) {
            modelBuilder.output(output);
            return this;
        }

        public Builder level(String level) {
            modelBuilder.level(level);
            return this;
        }

        public Builder statusMessage(String statusMessage) {
            modelBuilder.statusMessage(statusMessage);
            return this;
        }

        public Builder model(String model) {
            modelBuilder.model(model);
            return this;
        }

        public Builder modelParameters(Map<String, Object> modelParameters) {
            modelBuilder.modelParameters(modelParameters);
            return this;
        }

        public Builder usage(Integer prompt, Integer completion, Integer total, String unit) {
            modelBuilder.prompt(prompt);
            modelBuilder.completion(completion);
            modelBuilder.total(total);
            modelBuilder.unit(unit);
            return this;
        }

        public Observation build() {
            return new Observation(modelBuilder.build(), tracer);
        }
    }
}
