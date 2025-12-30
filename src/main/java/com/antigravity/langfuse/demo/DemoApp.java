package com.antigravity.langfuse.demo;

import com.antigravity.langfuse.Tracer;
import com.antigravity.langfuse.domain.ObservationType;
import com.antigravity.langfuse.sdk.Observation;
import com.antigravity.langfuse.sdk.Trace;

import java.time.Instant;

public class DemoApp {

    public static void main(String[] args) {
        // Initialize Tracer (Configurable)
        Tracer tracer = Tracer.builder()
                .fileReporter("demo-traces.json")
                .defaultRelease("1.0.0")
                .defaultEnvironment("production")
                .build();
        System.out.println("Tracer initialized. Writing to: demo-traces.json");

        // 1. Create a Trace
        Trace trace = tracer.traceBuilder()
                .name("user-checkout-flow")
                .userId("user-123456")
                .metadata(java.util.Collections.singletonMap("source", "mobile-app"))
                .tags(java.util.Arrays.asList("payment", "v1"))
                .build();

        // 2. Add an Observation (Span - e.g., database call)
        Observation dbSpan = tracer.observationBuilder()
                .parent(trace)
                .name("fetch-user-profile")
                .type(ObservationType.SPAN)
                .startTime(Instant.now())
                .build(); // No report yet

        try {
            // Simulate work
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dbSpan.end(); // Reports updated observation

        // 3. Add an Observation (Generation - e.g., LLM call)
        Observation llmGen = tracer.observationBuilder()
                .parent(trace)
                .name("generate-email-subject")
                .type(ObservationType.GENERATION)
                .model("gpt-3.5-turbo")
                .modelParameters(java.util.Collections.singletonMap("temperature", 0.7))
                .input("Generate a welcome email subject for user svensun")
                .build();

        // Simulate LLM response
        llmGen.setOutput("Welcome to Antigravity!");
        llmGen.setUsage(10, 5, 15, "TOKENS");
        llmGen.end(); // Reports

        // Flush (optional if needed immediately)
        tracer.flush();
        System.out.println("DemoApp finished.");
    }
}
