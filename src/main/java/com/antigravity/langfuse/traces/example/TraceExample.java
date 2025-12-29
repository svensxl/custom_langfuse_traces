package com.antigravity.langfuse.traces.example;

import com.antigravity.langfuse.traces.config.TraceConfig;
import com.antigravity.langfuse.traces.context.TraceContext;
import com.antigravity.langfuse.traces.dto.Observation;
import com.antigravity.langfuse.traces.dto.Trace;
import com.antigravity.langfuse.traces.service.TraceService;
import com.antigravity.langfuse.traces.service.TraceServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 使用示例
 */
public class TraceExample {
    public static void main(String[] args) {
        // 创建配置
        TraceConfig config = new TraceConfig();
        config.setOutputType(TraceConfig.OutputType.FILE); // 使用文件输出便于查看
        
        // 文件输出配置
        TraceConfig.FileOutputConfig fileConfig = new TraceConfig.FileOutputConfig();
        fileConfig.setFilePath("./example-traces.json");
        config.setFileOutputConfig(fileConfig);
        
        // 添加通用属性
        Map<String, String> commonAttributes = new HashMap<>();
        commonAttributes.put("environment", "development");
        commonAttributes.put("service", "example-service");
        config.setCommonAttributes(commonAttributes);

        // 初始化服务
        TraceService traceService = new TraceServiceImpl();
        traceService.initialize(config);
        
        try {
            // 创建 trace
            String traceId = UUID.randomUUID().toString();
            String projectId = "example-project";
            Trace trace = traceService.createTrace(traceId, projectId, "Example Trace");
            traceService.reportTrace(trace);
            
            // 创建 trace 上下文
            TraceContext context = TraceContext.builder()
                .traceId(traceId)
                .projectId(projectId)
                .trace(trace)
                .build();
            
            // 创建 observation
            String observationId = UUID.randomUUID().toString();
            Observation observation = traceService.createObservation(context, observationId, "SPAN", "Example Observation");
            
            // 使用 ObjectMapper 将字符串转换为 JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode inputNode = objectMapper.valueToTree("Sample input data");
            JsonNode outputNode = objectMapper.valueToTree("Sample output data");
            
            observation.setInput(inputNode);
            observation.setOutput(outputNode);
            traceService.reportObservation(observation);
            
            System.out.println("Traces and observations created and reported successfully!");
            System.out.println("Check the file: ./example-traces.json for output");
            
        } finally {
            // 确保数据被写入
            try {
                Thread.sleep(1000); // 等待一秒确保数据写入
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // 关闭服务
            traceService.shutdown();
        }
    }
}