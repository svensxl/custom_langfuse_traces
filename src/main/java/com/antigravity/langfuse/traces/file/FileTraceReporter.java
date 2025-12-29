package com.antigravity.langfuse.traces.file;

import com.antigravity.langfuse.traces.dto.Trace;
import com.antigravity.langfuse.traces.dto.Observation;

/**
 * 文件上报接口
 */
public interface FileTraceReporter {
    
    /**
     * 初始化文件上报器
     */
    void initialize();
    
    /**
     * 上报 Trace 到文件
     * @param trace Trace 对象
     */
    void reportTrace(Trace trace);
    
    /**
     * 上报 Observation 到文件
     * @param observation Observation 对象
     */
    void reportObservation(Observation observation);
    
    /**
     * 关闭并清理资源
     */
    void close();
}