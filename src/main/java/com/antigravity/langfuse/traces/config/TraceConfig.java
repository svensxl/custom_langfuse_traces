package com.antigravity.langfuse.traces.config;

import java.util.Map;

/**
 * Langfuse Traces 模块配置类
 */
public class TraceConfig {
    // 通用属性
    private Map<String, String> commonAttributes;
    
    // 上报方式配置
    private OutputType outputType;
    
    // Kafka 配置
    private KafkaConfig kafkaConfig;
    
    // 文件输出配置
    private FileOutputConfig fileOutputConfig;
    
    public enum OutputType {
        KAFKA, FILE, BOTH
    }
    
    public TraceConfig() {
        this.outputType = OutputType.KAFKA; // 默认使用 Kafka
    }
    
    // Getters and Setters
    public Map<String, String> getCommonAttributes() {
        return commonAttributes;
    }
    
    public void setCommonAttributes(Map<String, String> commonAttributes) {
        this.commonAttributes = commonAttributes;
    }
    
    public OutputType getOutputType() {
        return outputType;
    }
    
    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }
    
    public KafkaConfig getKafkaConfig() {
        return kafkaConfig;
    }
    
    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }
    
    public FileOutputConfig getFileOutputConfig() {
        return fileOutputConfig;
    }
    
    public void setFileOutputConfig(FileOutputConfig fileOutputConfig) {
        this.fileOutputConfig = fileOutputConfig;
    }
    
    /**
     * Kafka 配置内部类
     */
    public static class KafkaConfig {
        private String bootstrapServers;
        private String topicName;
        private Map<String, Object> additionalProperties;
        
        // Getters and Setters
        public String getBootstrapServers() {
            return bootstrapServers;
        }
        
        public void setBootstrapServers(String bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }
        
        public String getTopicName() {
            return topicName;
        }
        
        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }
        
        public Map<String, Object> getAdditionalProperties() {
            return additionalProperties;
        }
        
        public void setAdditionalProperties(Map<String, Object> additionalProperties) {
            this.additionalProperties = additionalProperties;
        }
    }
    
    /**
     * 文件输出配置内部类
     */
    public static class FileOutputConfig {
        private String filePath;
        private boolean append;
        
        // Getters and Setters
        public String getFilePath() {
            return filePath;
        }
        
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
        
        public boolean isAppend() {
            return append;
        }
        
        public void setAppend(boolean append) {
            this.append = append;
        }
    }
}