package com.antigravity.langfuse.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;

class ObservationModelTest {

    @Test
    void testObservationModelBuilder() {
        Instant now = Instant.now();
        ObservationModel observation = ObservationModel.builder()
                .id("obs-id")
                .traceId("trace-id")
                .name("obs-name")
                .startTime(now)
                .type(ObservationType.SPAN)
                .metadata(Collections.singletonMap("k", "v"))
                .build();

        Assertions.assertEquals("obs-id", observation.getId());
        Assertions.assertEquals("trace-id", observation.getTraceId());
        Assertions.assertEquals("obs-name", observation.getName());
        Assertions.assertEquals(now, observation.getStartTime());
        Assertions.assertEquals(ObservationType.SPAN, observation.getType());
        Assertions.assertEquals("v", observation.getMetadata().get("k"));
    }

    @Test
    void testDefaults() {
        ObservationModel observation = ObservationModel.builder().build();
        Assertions.assertNotNull(observation.getId());
        Assertions.assertNotNull(observation.getStartTime());
    }
}
