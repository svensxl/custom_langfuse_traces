package com.antigravity.langfuse.demo;

import java.util.Arrays;
import java.util.Collections;

import com.antigravity.langfuse.Tracer;
import com.antigravity.langfuse.domain.ObservationType;
import com.antigravity.langfuse.sdk.Observation;
import com.antigravity.langfuse.sdk.Trace;

public class TracerKafka {
    public static void main(String[] args) {
        Tracer tracer = Tracer.builder()
                .kafkaReporter("localhost:9094", "traces")
                .defaultRelease("1.0.0")
                .defaultEnvironment("production")
                .build();
        Trace trace = tracer.traceBuilder()
                .name("user-checkout-flow")
                .userId("user-123456")
                .metadata(Collections.singletonMap("source", "mobile-app"))
                .tags(Arrays.asList("payment", "v1"))
                .build();
        trace.setInput("hello");
        trace.setOutput("hello world");
        trace.addTag("test");

        Observation observation = tracer.observationBuilder().parent(trace)
                .name("user-checkout-flow")
                .type(ObservationType.GENERATION)
                .model("gpt-3.5-turbo")
                .modelParameters(java.util.Collections.singletonMap("temperature", 0.7))
                .input("Generate a welcome email subject for user svensun")
                .output("Welcome svensun")
                .build();
        try {
            // Give some time for messages to be sent before closing
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tracer initialized. Writing to: langfuse-traces");

        observation.end();
        trace.end();
        tracer.flush();
        System.out.println("Tracer flushed");

        tracer.close();
        System.out.println("Tracer closed");
    }
}
