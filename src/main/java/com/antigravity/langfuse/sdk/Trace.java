package com.antigravity.langfuse.sdk;

import com.antigravity.langfuse.Tracer;
import com.antigravity.langfuse.domain.TraceModel;
import com.antigravity.langfuse.utils.IdGenerator;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Trace {

    private final TraceModel model;
    private final Tracer tracer;

    Trace(TraceModel model, Tracer tracer) {
        this.model = model;
        this.tracer = tracer;
        // Report immediately upon creation
        this.tracer.getReporter().report(this.model);
    }

    public String getId() {
        return model.getId();
    }

    public void update(Map<String, String> metadata, List<String> tags, String version, String release,
            String userId, String sessionId, Boolean isPublic) {
        if (metadata != null)
            this.model.setMetadata(metadata);
        if (tags != null)
            this.model.setTags(tags);
        if (version != null)
            this.model.setVersion(version);
        if (release != null)
            this.model.setRelease(release);
        if (userId != null)
            this.model.setUserId(userId);
        if (sessionId != null)
            this.model.setSessionId(sessionId);
        if (isPublic != null)
            this.model.setIsPublic(isPublic);

        triggerUpdate();
    }

    public void setName(String name) {
        this.model.setName(name);
        triggerUpdate();
    }

    public void setUserId(String userId) {
        this.model.setUserId(userId);
        triggerUpdate();
    }

    public void setSessionId(String sessionId) {
        this.model.setSessionId(sessionId);
        triggerUpdate();
    }

    public void setVersion(String version) {
        this.model.setVersion(version);
        triggerUpdate();
    }

    public void setRelease(String release) {
        this.model.setRelease(release);
        triggerUpdate();
    }

    public void setIsPublic(Boolean isPublic) {
        this.model.setIsPublic(isPublic);
        triggerUpdate();
    }

    public void setTags(List<String> tags) {
        this.model.setTags(tags);
        triggerUpdate();
    }

    public void setBookmarked(Boolean bookmarked) {
        this.model.setBookmarked(bookmarked);
        triggerUpdate();
    }

    public void setInput(String input) {
        this.model.setInput(input);
        triggerUpdate();
    }

    public void setOutput(String output) {
        this.model.setOutput(output);
        triggerUpdate();
    }

    public void addMetadata(String key, String value) {
        if (this.model.getMetadata() == null) {
            this.model.setMetadata(new java.util.HashMap<>());
        }
        this.model.getMetadata().put(key, value);
        triggerUpdate();
    }

    public void addTag(String tag) {
        if (this.model.getTags() == null) {
            this.model.setTags(new java.util.ArrayList<>());
        }
        this.model.getTags().add(tag);
        triggerUpdate();
    }

    private void triggerUpdate() {
        this.model.setUpdatedAt(Instant.now());
        this.tracer.getReporter().report(this.model);
    }

    public static class Builder {
        private final TraceModel.TraceModelBuilder modelBuilder = TraceModel.builder();
        private final Tracer tracer;

        public Builder(Tracer tracer) {
            this.tracer = tracer;
            // Apply defaults from Tracer
            if (tracer != null) {
                modelBuilder.release(tracer.getDefaultRelease());
                modelBuilder.version(tracer.getDefaultVersion());
                modelBuilder.environment(tracer.getDefaultEnvironment());
                modelBuilder.isPublic(tracer.isPublic());
            }
        }

        // ... existing setters ...

        public Builder id(String id) {
            modelBuilder.id(id);
            return this;
        }

        public Builder name(String name) {
            modelBuilder.name(name);
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            modelBuilder.timestamp(timestamp);
            return this;
        }

        public Builder userId(String userId) {
            modelBuilder.userId(userId);
            return this;
        }

        public Builder metadata(Map<String, String> metadata) {
            modelBuilder.metadata(metadata);
            return this;
        }

        public Builder tags(List<String> tags) {
            modelBuilder.tags(tags);
            return this;
        }

        public Builder version(String version) {
            modelBuilder.version(version);
            return this;
        }

        public Builder release(String release) {
            modelBuilder.release(release);
            return this;
        }

        public Builder sessionId(String sessionId) {
            modelBuilder.sessionId(sessionId);
            return this;
        }

        public Builder isPublic(Boolean isPublic) {
            modelBuilder.isPublic(isPublic);
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

        public Trace build() {
            TraceModel model = modelBuilder.build();
            return new Trace(model, tracer);
        }
    }
}
