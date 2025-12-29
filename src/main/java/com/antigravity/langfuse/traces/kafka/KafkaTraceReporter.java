package com.antigravity.langfuse.traces.kafka;

import com.antigravity.langfuse.traces.dto.Trace;
import com.antigravity.langfuse.traces.dto.Observation;

/**
 * Kafka 上报接口
 */
public interface KafkaTraceReporter {
    
    /**
     * 初始化 Kafka 上报器
     */
    void initialize();
    
    /**
     * 上报 Trace 到 Kafka
     * @param trace Trace 对象
     */
    void reportTrace(Trace trace);
    
    /**
     * 上报 Observation 到 Kafka
     * @param observation Observation 对象
     */
    void reportObservation(Observation observation);
    
    /**
     * 关闭并清理资源
     */
    void close();
}