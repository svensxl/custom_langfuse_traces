package com.antigravity.langfuse.traces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

/**
 * Observation DTO matching ClickHouse table schema
 */
public class Observation {
    private String id;
    private String traceId;
    private String projectId;
    private String environment = "default";
    private String type;
    private String parentObservationId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant endTime;
    private String name;
    private Map<String, String> metadata;
    private String level = "INFO";
    private String statusMessage;
    private String version;
    private JsonNode input;
    private JsonNode output;
    private String providedModelName;
    private String internalModelId;
    private String modelParameters;
    private Map<String, Long> providedUsageDetails;
    private Map<String, Long> usageDetails;
    private Map<String, BigDecimal> providedCostDetails;
    private Map<String, BigDecimal> costDetails;
    private BigDecimal totalCost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant completionStartTime;
    private String promptId;
    private String promptName;
    private Integer promptVersion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant createdAt = Instant.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant updatedAt = Instant.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Instant eventTs;
    private Boolean isDeleted = false;

    // Constructors
    public Observation() {}

    public Observation(String id, String traceId, String projectId) {
        this.id = id;
        this.traceId = traceId;
        this.projectId = projectId;
        this.startTime = Instant.now();
        this.eventTs = Instant.now();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public JsonNode getInput() {
        return input;
    }

    public void setInput(JsonNode input) {
        this.input = input;
    }

    public JsonNode getOutput() {
        return output;
    }

    public void setOutput(JsonNode output) {
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

    public String getModelParameters() {
        return modelParameters;
    }

    public void setModelParameters(String modelParameters) {
        this.modelParameters = modelParameters;
    }

    public Map<String, Long> getProvidedUsageDetails() {
        return providedUsageDetails;
    }

    public void setProvidedUsageDetails(Map<String, Long> providedUsageDetails) {
        this.providedUsageDetails = providedUsageDetails;
    }

    public Map<String, Long> getUsageDetails() {
        return usageDetails;
    }

    public void setUsageDetails(Map<String, Long> usageDetails) {
        this.usageDetails = usageDetails;
    }

    public Map<String, BigDecimal> getProvidedCostDetails() {
        return providedCostDetails;
    }

    public void setProvidedCostDetails(Map<String, BigDecimal> providedCostDetails) {
        this.providedCostDetails = providedCostDetails;
    }

    public Map<String, BigDecimal> getCostDetails() {
        return costDetails;
    }

    public void setCostDetails(Map<String, BigDecimal> costDetails) {
        this.costDetails = costDetails;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Instant getCompletionStartTime() {
        return completionStartTime;
    }

    public void setCompletionStartTime(Instant completionStartTime) {
        this.completionStartTime = completionStartTime;
    }

    public String getPromptId() {
        return promptId;
    }

    public void setPromptId(String promptId) {
        this.promptId = promptId;
    }

    public String getPromptName() {
        return promptName;
    }

    public void setPromptName(String promptName) {
        this.promptName = promptName;
    }

    public Integer getPromptVersion() {
        return promptVersion;
    }

    public void setPromptVersion(Integer promptVersion) {
        this.promptVersion = promptVersion;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}