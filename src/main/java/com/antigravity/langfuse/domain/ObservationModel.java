package com.antigravity.langfuse.domain;

import com.antigravity.langfuse.utils.IdGenerator;
import com.alibaba.fastjson.annotation.JSONField;

import java.time.Instant;
import java.util.Map;

public class ObservationModel {

    private String id;

    @JSONField(name = "trace_id")
    private String traceId;

    @JSONField(name = "project_id")
    private String projectId;

    private String environment;

    @JSONField(name = "parent_observation_id")
    private String parentObservationId;

    @JSONField(name = "start_time")
    private Instant startTime;

    @JSONField(name = "end_time")
    private Instant endTime;

    private String name;
    private Map<String, String> metadata;
    private String level;

    @JSONField(name = "status_message")
    private String statusMessage;
    private String release;
    private String version;

    @JSONField(name = "public")
    private Boolean isPublic;

    private ObservationType type;
    private String model;

    @JSONField(name = "model_parameters")
    private Map<String, Object> modelParameters;

    private String input;
    private String output;

    @JSONField(name = "completion_start_time")
    private Instant completionStartTime;

    private Integer completion;
    private Integer prompt;
    private Integer total;
    private String unit;

    @JSONField(name = "created_at")
    private Instant createdAt;

    @JSONField(name = "updated_at")
    private Instant updatedAt;

    @JSONField(name = "event_ts")
    private Instant eventTs;

    @JSONField(name = "internal_model_id")
    private String internalModelId;

    @JSONField(name = "is_deleted")
    private Integer isDeleted;

    @JSONField(name = "provided_model_name")
    private String providedModelName;

    public ObservationModel() {
    }

    public ObservationModel(String id, String traceId, String parentObservationId, String name, Instant startTime,
            Instant endTime, Map<String, String> metadata, String release, String version, String projectId,
            String environment, Boolean isPublic, ObservationType type, String model,
            Map<String, Object> modelParameters,
            String input, String output, String level, String statusMessage, Instant completionStartTime,
            Integer completion, Integer prompt, Integer total, String unit, Instant createdAt, Instant updatedAt,
            Instant eventTs, String internalModelId, Integer isDeleted, String providedModelName) {

        this.id = id != null ? id : IdGenerator.generateObservationId();
        this.traceId = traceId;
        this.parentObservationId = parentObservationId;
        this.name = name;
        this.startTime = startTime != null ? startTime : Instant.now();
        this.endTime = endTime;
        this.metadata = metadata;
        this.release = release;
        this.version = version;
        this.projectId = projectId;
        this.environment = environment;
        this.isPublic = isPublic;
        this.type = type;
        this.model = model;
        this.modelParameters = modelParameters;
        this.input = input;
        this.output = output;
        this.level = level;
        this.statusMessage = statusMessage;
        this.completionStartTime = completionStartTime;
        this.completion = completion;
        this.prompt = prompt;
        this.total = total;
        this.unit = unit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.eventTs = eventTs != null ? eventTs : this.startTime;
        this.internalModelId = internalModelId;
        this.isDeleted = isDeleted != null ? isDeleted : 0;
        this.providedModelName = providedModelName;
    }

    public static ObservationModelBuilder builder() {
        return new ObservationModelBuilder();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
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

    public ObservationType getType() {
        return type;
    }

    public void setType(ObservationType type) {
        this.type = type;
    }

    public String getParentObservationId() {
        return parentObservationId;
    }

    public void setParentObservationId(String parentObservationId) {
        this.parentObservationId = parentObservationId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getProvidedModelName() {
        return providedModelName;
    }

    public void setProvidedModelName(String providedModelName) {
        this.providedModelName = providedModelName;
    }

    public String getInternalModelId() {
        return internalModelId;
    }

    public void setInternalModelId(String internalModelId) {
        this.internalModelId = internalModelId;
    }

    public Map<String, Object> getModelParameters() {
        return modelParameters;
    }

    public void setModelParameters(Map<String, Object> modelParameters) {
        this.modelParameters = modelParameters;
    }

    public Instant getCompletionStartTime() {
        return completionStartTime;
    }

    public void setCompletionStartTime(Instant completionStartTime) {
        this.completionStartTime = completionStartTime;
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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCompletion() {
        return completion;
    }

    public void setCompletion(Integer completion) {
        this.completion = completion;
    }

    public Integer getPrompt() {
        return prompt;
    }

    public void setPrompt(Integer prompt) {
        this.prompt = prompt;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public static class ObservationModelBuilder {
        private String id;
        private String traceId;
        private String parentObservationId;
        private String name;
        private Instant startTime;
        private Instant endTime;
        private Map<String, String> metadata;
        private String release;
        private String version;
        private String projectId;
        private String environment;
        private Boolean isPublic;
        private ObservationType type;
        private String model;
        private Map<String, Object> modelParameters;
        private String input;
        private String output;
        private String level;
        private String statusMessage;
        private Instant completionStartTime;
        private Integer completion;
        private Integer prompt;
        private Integer total;
        private String unit;
        private Instant createdAt;
        private Instant updatedAt;
        private Instant eventTs;
        private String internalModelId;
        private Integer isDeleted;
        private String providedModelName;

        ObservationModelBuilder() {
        }

        public ObservationModelBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ObservationModelBuilder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public ObservationModelBuilder parentObservationId(String parentObservationId) {
            this.parentObservationId = parentObservationId;
            return this;
        }

        public ObservationModelBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ObservationModelBuilder startTime(Instant startTime) {
            this.startTime = startTime;
            return this;
        }

        public ObservationModelBuilder endTime(Instant endTime) {
            this.endTime = endTime;
            return this;
        }

        public ObservationModelBuilder metadata(Map<String, String> metadata) {
            this.metadata = metadata;
            return this;
        }

        public ObservationModelBuilder release(String release) {
            this.release = release;
            return this;
        }

        public ObservationModelBuilder version(String version) {
            this.version = version;
            return this;
        }

        public ObservationModelBuilder projectId(String projectId) {
            this.projectId = projectId;
            return this;
        }

        public ObservationModelBuilder environment(String environment) {
            this.environment = environment;
            return this;
        }

        public ObservationModelBuilder isPublic(Boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public ObservationModelBuilder type(ObservationType type) {
            this.type = type;
            return this;
        }

        public ObservationModelBuilder model(String model) {
            this.model = model;
            return this;
        }

        public ObservationModelBuilder modelParameters(Map<String, Object> modelParameters) {
            this.modelParameters = modelParameters;
            return this;
        }

        public ObservationModelBuilder input(String input) {
            this.input = input;
            return this;
        }

        public ObservationModelBuilder output(String output) {
            this.output = output;
            return this;
        }

        public ObservationModelBuilder level(String level) {
            this.level = level;
            return this;
        }

        public ObservationModelBuilder statusMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ObservationModelBuilder completionStartTime(Instant completionStartTime) {
            this.completionStartTime = completionStartTime;
            return this;
        }

        public ObservationModelBuilder completion(Integer completion) {
            this.completion = completion;
            return this;
        }

        public ObservationModelBuilder prompt(Integer prompt) {
            this.prompt = prompt;
            return this;
        }

        public ObservationModelBuilder total(Integer total) {
            this.total = total;
            return this;
        }

        public ObservationModelBuilder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public ObservationModelBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ObservationModelBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ObservationModelBuilder eventTs(Instant eventTs) {
            this.eventTs = eventTs;
            return this;
        }

        public ObservationModelBuilder internalModelId(String internalModelId) {
            this.internalModelId = internalModelId;
            return this;
        }

        public ObservationModelBuilder isDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public ObservationModelBuilder providedModelName(String providedModelName) {
            this.providedModelName = providedModelName;
            return this;
        }

        public ObservationModel build() {
            return new ObservationModel(id, traceId, parentObservationId, name, startTime, endTime, metadata, release,
                    version, projectId, environment, isPublic, type, model, modelParameters, input, output, level,
                    statusMessage, completionStartTime, completion, prompt, total, unit, createdAt, updatedAt, eventTs,
                    internalModelId, isDeleted, providedModelName);
        }
    }
}
