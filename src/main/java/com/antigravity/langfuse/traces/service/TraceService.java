package com.antigravity.langfuse.traces.service;

import com.antigravity.langfuse.traces.config.TraceConfig;
import com.antigravity.langfuse.traces.context.TraceContext;
import com.antigravity.langfuse.traces.dto.Trace;
import com.antigravity.langfuse.traces.dto.Observation;

/**
 * Langfuse Traces 主服务接口
 */
public interface TraceService {
    
    /**
     * 初始化服务
     * @param config 配置对象
     */
    void initialize(TraceConfig config);
    
    /**
     * 创建 Trace
     * @param traceId Trace ID
     * @param projectId 项目 ID
     * @return Trace 对象
     */
    Trace createTrace(String traceId, String projectId);
    
    /**
     * 创建 Trace
     * @param traceId Trace ID
     * @param projectId 项目 ID
     * @param name Trace 名称
     * @return Trace 对象
     */
    Trace createTrace(String traceId, String projectId, String name);
    
    /**
     * 创建 Observation
     * @param context Trace 上下文
     * @param observationId Observation ID
     * @param type Observation 类型 (SPAN, GENERATION, EVENT)
     * @return Observation 对象
     */
    Observation createObservation(TraceContext context, String observationId, String type);
    
    /**
     * 创建 Observation
     * @param context Trace 上下文
     * @param observationId Observation ID
     * @param type Observation 类型 (SPAN, GENERATION, EVENT)
     * @param name Observation 名称
     * @return Observation 对象
     */
    Observation createObservation(TraceContext context, String observationId, String type, String name);
    
    /**
     * 上报 Trace
     * @param trace Trace 对象
     */
    void reportTrace(Trace trace);
    
    /**
     * 上报 Observation
     * @param observation Observation 对象
     */
    void reportObservation(Observation observation);
    
    /**
     * 关闭服务，清理资源
     */
    void shutdown();
}