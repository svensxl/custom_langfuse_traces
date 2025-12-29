package com.antigravity.langfuse.traces.service;

import com.antigravity.langfuse.traces.config.TraceConfig;
import com.antigravity.langfuse.traces.context.TraceContext;
import com.antigravity.langfuse.traces.dto.Trace;
import com.antigravity.langfuse.traces.dto.Observation;
import com.antigravity.langfuse.traces.kafka.KafkaTraceReporter;
import com.antigravity.langfuse.traces.kafka.KafkaTraceReporterImpl;
import com.antigravity.langfuse.traces.file.FileTraceReporter;
import com.antigravity.langfuse.traces.file.FileTraceReporterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

/**
 * Langfuse Traces 服务实现类
 */
public class TraceServiceImpl implements TraceService {
    private static final Logger logger = LoggerFactory.getLogger(TraceServiceImpl.class);
    
    private TraceConfig config;
    private KafkaTraceReporter kafkaReporter;
    private FileTraceReporter fileReporter;
    
    @Override
    public void initialize(TraceConfig config) {
        this.config = config;
        
        // 根据配置初始化上报器
        if (config.getOutputType() == TraceConfig.OutputType.KAFKA || 
            config.getOutputType() == TraceConfig.OutputType.BOTH) {
            this.kafkaReporter = new KafkaTraceReporterImpl(config.getKafkaConfig());
            this.kafkaReporter.initialize();
        }
        
        if (config.getOutputType() == TraceConfig.OutputType.FILE || 
            config.getOutputType() == TraceConfig.OutputType.BOTH) {
            this.fileReporter = new FileTraceReporterImpl(config.getFileOutputConfig());
            this.fileReporter.initialize();
        }
        
        logger.info("TraceService initialized with output type: {}", config.getOutputType());
    }
    
    @Override
    public Trace createTrace(String traceId, String projectId) {
        return createTrace(traceId, projectId, null);
    }
    
    @Override
    public Trace createTrace(String traceId, String projectId, String name) {
        if (traceId == null || traceId.trim().isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }
        
        Trace trace = new Trace(traceId, projectId);
        trace.setName(name != null ? name : "default-trace");
        
        // 添加通用属性
        if (config.getCommonAttributes() != null && !config.getCommonAttributes().isEmpty()) {
            trace.setMetadata(config.getCommonAttributes());
        }
        
        logger.debug("Created trace with id: {} for project: {}", traceId, projectId);
        return trace;
    }
    
    @Override
    public Observation createObservation(TraceContext context, String observationId, String type) {
        return createObservation(context, observationId, type, null);
    }
    
    @Override
    public Observation createObservation(TraceContext context, String observationId, String type, String name) {
        if (observationId == null || observationId.trim().isEmpty()) {
            observationId = UUID.randomUUID().toString();
        }
        
        if (type == null || type.trim().isEmpty()) {
            type = "SPAN"; // 默认类型
        }
        
        Observation observation = new Observation(observationId, context.getTraceId(), context.getProjectId());
        observation.setType(type);
        observation.setName(name != null ? name : "default-observation");
        observation.setParentObservationId(context.getParentObservationId());
        
        // 添加通用属性
        if (config.getCommonAttributes() != null && !config.getCommonAttributes().isEmpty()) {
            observation.setMetadata(config.getCommonAttributes());
        }
        
        logger.debug("Created observation with id: {} for trace: {} and project: {}", 
                     observationId, context.getTraceId(), context.getProjectId());
        return observation;
    }
    
    @Override
    public void reportTrace(Trace trace) {
        if (trace == null) {
            logger.warn("Cannot report null trace");
            return;
        }
        
        // 根据配置进行上报
        if (config.getOutputType() == TraceConfig.OutputType.KAFKA || 
            config.getOutputType() == TraceConfig.OutputType.BOTH) {
            if (kafkaReporter != null) {
                kafkaReporter.reportTrace(trace);
            }
        }
        
        if (config.getOutputType() == TraceConfig.OutputType.FILE || 
            config.getOutputType() == TraceConfig.OutputType.BOTH) {
            if (fileReporter != null) {
                fileReporter.reportTrace(trace);
            }
        }
        
        logger.debug("Reported trace with id: {}", trace.getId());
    }
    
    @Override
    public void reportObservation(Observation observation) {
        if (observation == null) {
            logger.warn("Cannot report null observation");
            return;
        }
        
        // 根据配置进行上报
        if (config.getOutputType() == TraceConfig.OutputType.KAFKA || 
            config.getOutputType() == TraceConfig.OutputType.BOTH) {
            if (kafkaReporter != null) {
                kafkaReporter.reportObservation(observation);
            }
        }
        
        if (config.getOutputType() == TraceConfig.OutputType.FILE || 
            config.getOutputType() == TraceConfig.OutputType.BOTH) {
            if (fileReporter != null) {
                fileReporter.reportObservation(observation);
            }
        }
        
        logger.debug("Reported observation with id: {}", observation.getId());
    }
    
    @Override
    public void shutdown() {
        if (kafkaReporter != null) {
            kafkaReporter.close();
        }
        
        if (fileReporter != null) {
            fileReporter.close();
        }
        
        logger.info("TraceService shutdown completed");
    }
}