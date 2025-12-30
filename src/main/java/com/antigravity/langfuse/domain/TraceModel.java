package com.antigravity.langfuse.domain;

import com.antigravity.langfuse.utils.IdGenerator;
import com.alibaba.fastjson.annotation.JSONField;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class TraceModel {

    private String id;
    private Instant timestamp;
    private String name;

    @JSONField(name = "user_id")
    private String userId;

    private Map<String, String> metadata;
    private String release;
    private String version;

    @JSONField(name = "project_id")
    private String projectId;

    private String environment;

    @JSONField(name = "public")
    private Boolean isPublic;

    private Boolean bookmarked;
    private List<String> tags;
    private String input;
    private String output;

    @JSONField(name = "session_id")
    private String sessionId;

    @JSONField(name = "created_at")
    private Instant createdAt;

    @JSONField(name = "updated_at")
    private Instant updatedAt;

    @JSONField(name = "event_ts")
    private Instant eventTs;

    @JSONField(name = "is_deleted")
    private Integer isDeleted;

    public TraceModel() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null);
    }

    public TraceModel(String id, Instant timestamp, String name, String userId, Map<String, String> metadata,
            String release,
            String version, String projectId, String environment, Boolean isPublic, Boolean bookmarked,
            List<String> tags, String input, String output, String sessionId, Instant createdAt, Instant updatedAt,
            Instant eventTs, Integer isDeleted) {

        this.id = id != null ? id : IdGenerator.generateTraceId();
        this.timestamp = timestamp != null ? timestamp : Instant.now();
        this.name = name;
        this.userId = userId;
        this.metadata = metadata;
        this.release = release;
        this.version = version;
        this.projectId = projectId;
        this.environment = environment;
        this.isPublic = isPublic != null ? isPublic : false;
        this.bookmarked = bookmarked;
        this.tags = tags;
        this.input = input;
        this.output = output;
        this.sessionId = sessionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.eventTs = eventTs != null ? eventTs : this.timestamp;
        this.isDeleted = isDeleted != null ? isDeleted : 0;
    }

    public static TraceModelBuilder builder() {
        return new TraceModelBuilder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getEventTs() {
        return eventTs;
    }

    public void setEventTs(Instant eventTs) {
        this.eventTs = eventTs;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public static class TraceModelBuilder {
        private String id;
        private Instant timestamp;
        private String name;
        private String userId;
        private Map<String, String> metadata;
        private String release;
        private String version;
        private String projectId;
        private String environment;
        private Boolean isPublic;
        private Boolean bookmarked;
        private List<String> tags;
        private String input;
        private String output;
        private String sessionId;
        private Instant createdAt;
        private Instant updatedAt;
        private Instant eventTs;
        private Integer isDeleted;

        TraceModelBuilder() {
        }

        public TraceModelBuilder id(String id) {
            this.id = id;
            return this;
        }

        public TraceModelBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TraceModelBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TraceModelBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public TraceModelBuilder metadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public TraceModelBuilder release(String release) {
            this.release = release;
            return this;
        }

        public TraceModelBuilder version(String version) {
            this.version = version;
            return this;
        }

        public TraceModelBuilder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public TraceModelBuilder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public TraceModelBuilder isPublic(Boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public TraceModelBuilder bookmarked(Boolean bookmarked) {
            this.bookmarked = bookmarked;
            return this;
        }

        public TraceModelBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public TraceModelBuilder input(String input) {
            this.input = input;
            return this;
        }

        public TraceModelBuilder output(String output) {
            this.output = output;
            return this;
        }

        public TraceModelBuilder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public TraceModelBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TraceModelBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public TraceModelBuilder eventTs(Instant eventTs) {
            this.eventTs = eventTs;
            return this;
        }

        public TraceModelBuilder isDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public TraceModel build() {
            return new TraceModel(id, timestamp, name, userId, metadata, release, version, projectId, environment,
                    isPublic,
                    bookmarked, tags, input, output, sessionId, createdAt, updatedAt, eventTs, isDeleted);
        }
    }
}
