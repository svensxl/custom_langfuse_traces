package com.antigravity.langfuse.traces.file;

import com.antigravity.langfuse.traces.config.TraceConfig;
import com.antigravity.langfuse.traces.dto.Trace;
import com.antigravity.langfuse.traces.dto.Observation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件上报器实现类
 */
public class FileTraceReporterImpl implements FileTraceReporter {
    private static final Logger logger = LoggerFactory.getLogger(FileTraceReporterImpl.class);
    
    private final TraceConfig.FileOutputConfig fileConfig;
    private ObjectMapper objectMapper;
    private PrintWriter writer;
    
    public FileTraceReporterImpl(TraceConfig.FileOutputConfig fileConfig) {
        this.fileConfig = fileConfig;
    }
    
    @Override
    public void initialize() {
        try {
            // 初始化 ObjectMapper
            this.objectMapper = new ObjectMapper();
            this.objectMapper.registerModule(new JavaTimeModule());
            
            // 创建目录（如果不存在）
            Path path = Paths.get(fileConfig.getFilePath());
            Files.createDirectories(path.getParent());
            
            // 初始化文件写入器
            this.writer = new PrintWriter(new FileWriter(fileConfig.getFilePath(), fileConfig.isAppend()));
            logger.info("FileTraceReporter initialized with file: {}", fileConfig.getFilePath());
        } catch (IOException e) {
            logger.error("Failed to initialize FileTraceReporter", e);
            throw new RuntimeException("Failed to initialize FileTraceReporter", e);
        }
    }
    
    @Override
    public void reportTrace(Trace trace) {
        if (writer == null) {
            logger.error("File writer not initialized");
            return;
        }
        
        try {
            String traceJson = objectMapper.writeValueAsString(trace);
            synchronized (this) {
                writer.println(traceJson);
                writer.flush(); // 确保立即写入
            }
            logger.debug("Trace written to file: {}", trace.getId());
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize trace to JSON", e);
        }
    }
    
    @Override
    public void reportObservation(Observation observation) {
        if (writer == null) {
            logger.error("File writer not initialized");
            return;
        }
        
        try {
            String observationJson = objectMapper.writeValueAsString(observation);
            synchronized (this) {
                writer.println(observationJson);
                writer.flush(); // 确保立即写入
            }
            logger.debug("Observation written to file: {}", observation.getId());
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize observation to JSON", e);
        }
    }
    
    @Override
    public void close() {
        if (writer != null) {
            writer.close();
            logger.info("FileTraceReporter closed");
        }
    }
}