package com.antigravity.langfuse.sdk;

import com.antigravity.langfuse.Tracer;
import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.TraceModel;
import com.antigravity.langfuse.reporter.Reporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.antigravity.langfuse.domain.ObservationType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class TraceSdkTest {

    private MockReporter mockReporter;
    private Tracer tracer;

    @BeforeEach
    void setUp() {
        mockReporter = new MockReporter();
        tracer = Tracer.builder()
                .customReporter(mockReporter)
                .defaultEnvironment("test-env")
                .build();
    }

    @Test
    void testTraceCreation() {
        Trace trace = tracer.traceBuilder()
                .id("sdk-trace-1")
                .name("SDK Trace")
                .build();

        Assertions.assertEquals("sdk-trace-1", trace.getId());
        Assertions.assertEquals(1, mockReporter.traces.size());
        Assertions.assertEquals("sdk-trace-1", mockReporter.traces.get(0).getId());
        // Verify default environment
        Assertions.assertEquals("test-env", mockReporter.traces.get(0).getEnvironment());
    }

    @Test
    void testObservationCreation() {
        Trace trace = tracer.traceBuilder()
                .id("sdk-trace-2")
                .build();

        Observation obs = tracer.observationBuilder()
                .parent(trace)
                .name("Root")
                .build();

        Assertions.assertEquals(0, mockReporter.observations.size());

        obs.end();

        Assertions.assertEquals(1, mockReporter.observations.size());
        Assertions.assertEquals("Root", mockReporter.observations.get(0).getName());
        Assertions.assertEquals("sdk-trace-2", mockReporter.observations.get(0).getTraceId());
        // Verify default environment inherited
        Assertions.assertEquals("test-env", mockReporter.observations.get(0).getEnvironment());
    }

    @Test
    void testTraceMutability() {
        Trace trace = tracer.traceBuilder()
                .id("sdk-trace-mut")
                .name("Original Name")
                .build();

        // Initial report
        Assertions.assertEquals(1, mockReporter.traces.size());
        Assertions.assertEquals("Original Name", mockReporter.traces.get(0).getName());

        // Update name
        trace.setName("Updated Name");

        // Should report again
        Assertions.assertEquals(2, mockReporter.traces.size());
        Assertions.assertEquals("Updated Name", mockReporter.traces.get(1).getName());

        // Update Metadata
        trace.addMetadata("key", "value");
        Assertions.assertEquals(3, mockReporter.traces.size());
        Assertions.assertEquals("value", mockReporter.traces.get(2).getMetadata().get("key"));

        // Update Bookmarked & Tags (New Fields)
        trace.setBookmarked(true);
        Assertions.assertEquals(4, mockReporter.traces.size());
        Assertions.assertTrue(mockReporter.traces.get(3).getBookmarked());

        trace.setTags(java.util.Arrays.asList("tag1", "tag2"));
        Assertions.assertEquals(5, mockReporter.traces.size());
        Assertions.assertEquals(2, mockReporter.traces.get(4).getTags().size());
    }

    @Test
    void testObservationMutability() {
        Trace trace = tracer.traceBuilder().id("t1").build();
        Observation obs = tracer.observationBuilder()
                .parent(trace)
                .name("ObsOriginal")
                .type(ObservationType.SPAN)
                .build();

        // No report on creation
        Assertions.assertEquals(0, mockReporter.observations.size());

        // Update properties
        obs.setName("ObsUpdated");
        obs.addMetadata("k", "v");
        obs.setUsage(100, 50, 150, "TOKENS");
        obs.setCompletionStartTime(Instant.now());
        obs.setPublic(true);

        // Still no report
        Assertions.assertEquals(0, mockReporter.observations.size());

        // End
        obs.end();

        Assertions.assertEquals(1, mockReporter.observations.size());
        ObservationModel reported = mockReporter.observations.get(0);
        Assertions.assertEquals("ObsUpdated", reported.getName());
        Assertions.assertEquals("v", reported.getMetadata().get("k"));
        Assertions.assertEquals(100, reported.getPrompt());
        Assertions.assertEquals(50, reported.getCompletion());
        Assertions.assertEquals(150, reported.getTotal());
        Assertions.assertEquals("TOKENS", reported.getUnit());
        Assertions.assertNotNull(reported.getCompletionStartTime());
        Assertions.assertTrue(reported.getPublic());
    }

    private static class MockReporter implements Reporter {
        List<TraceModel> traces = new ArrayList<>();
        List<ObservationModel> observations = new ArrayList<>();

        @Override
        public void report(TraceModel trace) {
            traces.add(trace);
        }

        @Override
        public void report(ObservationModel observation) {
            observations.add(observation);
        }
    }
}
