package com.antigravity.langfuse.reporter;

import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.TraceModel;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

class KafkaReporterTest {

        @Test
        void testReportTrace() throws Exception {
                MockProducer<String, String> mockProducer = new MockProducer<>(true, new StringSerializer(),
                                new StringSerializer());
                KafkaReporter reporter = new KafkaReporter(mockProducer, "traces-topic", "observations-topic");

                TraceModel trace = TraceModel.builder()
                                .id("trace-1")
                                .name("test-trace")
                                .timestamp(Instant.now())
                                .build();

                reporter.report(trace);

                List<ProducerRecord<String, String>> history = mockProducer.history();
                Assertions.assertEquals(1, history.size());
                Assertions.assertEquals("traces-topic", history.get(0).topic());
                Assertions.assertEquals("trace-1", history.get(0).key());
                Assertions.assertTrue(history.get(0).value().contains("test-trace"));

                reporter.close();
        }

        @Test
        void testReportObservation() throws Exception {
                MockProducer<String, String> mockProducer = new MockProducer<>(true, new StringSerializer(),
                                new StringSerializer());
                KafkaReporter reporter = new KafkaReporter(mockProducer, "traces-topic", "observations-topic");

                ObservationModel observation = ObservationModel.builder()
                                .id("obs-1")
                                .traceId("trace-1")
                                .name("obs-name")
                                .startTime(Instant.now())
                                .build();

                reporter.report(observation);

                List<ProducerRecord<String, String>> history = mockProducer.history();
                Assertions.assertEquals(1, history.size());
                Assertions.assertEquals("observations-topic", history.get(0).topic());
        }
}
