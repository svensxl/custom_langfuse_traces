package com.antigravity.langfuse.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

class TraceModelTest {

    @Test
    void testTraceModelBuilder() {
        Instant now = Instant.now();
        TraceModel trace = TraceModel.builder()
                .id("test-id")
                .name("test-trace")
                .userId("test-user")
                .metadata(Collections.singletonMap("key", "value"))
                .release("1.0.0")
                .version("2.0.0")
                .timestamp(now)
                .build();

        Assertions.assertEquals("test-id", trace.getId());
        Assertions.assertEquals("test-trace", trace.getName());
        Assertions.assertEquals("test-user", trace.getUserId());
        Assertions.assertEquals("value", trace.getMetadata().get("key"));
        Assertions.assertEquals("1.0.0", trace.getRelease());
        Assertions.assertEquals("2.0.0", trace.getVersion());
        Assertions.assertEquals(now, trace.getTimestamp());
    }

    @Test
    void testDefaultValues() {
        TraceModel trace = TraceModel.builder()
                .build();

        Assertions.assertNotNull(trace.getId());
        Assertions.assertNotNull(trace.getTimestamp());
    }
}
