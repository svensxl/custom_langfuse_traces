package com.antigravity.langfuse.traces.service;

import com.antigravity.langfuse.traces.config.TraceConfig;
import com.antigravity.langfuse.traces.context.TraceContext;
import com.antigravity.langfuse.traces.dto.Observation;
import com.antigravity.langfuse.traces.dto.Trace;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TraceServiceIntegrationTest {
    
    @Test
    void testTraceServiceLifecycle() {
        // 创建配置
        TraceConfig config = new TraceConfig();
        config.setOutputType(TraceConfig.OutputType.FILE); // 使用文件输出
        
        // 文件输出配置
        TraceConfig.FileOutputConfig fileConfig = new TraceConfig.FileOutputConfig();
        fileConfig.setFilePath("./test-traces.json");
        config.setFileOutputConfig(fileConfig);
        
        // 添加通用属性
        Map<String, String> commonAttributes = new HashMap<>();
        commonAttributes.put("environment", "test");
        commonAttributes.put("service", "test-service");
        config.setCommonAttributes(commonAttributes);

        // 初始化服务
        TraceService traceService = new TraceServiceImpl();
        
        assertDoesNotThrow(() -> traceService.initialize(config));
        
        try {
            // 创建 trace
            String traceId = "test-trace-id";
            String projectId = "test-project";
            Trace trace = traceService.createTrace(traceId, projectId, "Test Trace");
            
            assertNotNull(trace);
            assertEquals(traceId, trace.getId());
            assertEquals(projectId, trace.getProjectId());
            assertEquals("Test Trace", trace.getName());
            
            // 创建 trace 上下文
            TraceContext context = TraceContext.builder()
                    .traceId(traceId)
                    .projectId(projectId)
                    .trace(trace)
                    .build();
            
            // 创建 observation
            String observationId = "test-observation-id";
            Observation observation = traceService.createObservation(context, observationId, "SPAN", "Test Observation");
            
            assertNotNull(observation);
            assertEquals(observationId, observation.getId());
            assertEquals(traceId, observation.getTraceId());
            assertEquals(projectId, observation.getProjectId());
            assertEquals("SPAN", observation.getType());
            assertEquals("Test Observation", observation.getName());
            
            // 上报 trace 和 observation (不会抛出异常)
            assertDoesNotThrow(() -> traceService.reportTrace(trace));
            assertDoesNotThrow(() -> traceService.reportObservation(observation));
            
        } finally {
            // 关闭服务
            assertDoesNotThrow(() -> traceService.shutdown());
        }
    }
}