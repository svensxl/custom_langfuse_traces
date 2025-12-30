package com.antigravity.langfuse.reporter;

import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.TraceModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Instant;
import java.util.Properties;

public class KafkaReporter implements Reporter {

    private final Producer<String, String> producer;
    private final String traceTopic;
    private final String observationTopic;

    public KafkaReporter(Producer<String, String> producer, String traceTopic, String observationTopic) {
        this.producer = producer;
        this.traceTopic = traceTopic != null ? traceTopic : "langfuse-traces";
        this.observationTopic = observationTopic != null ? observationTopic : "langfuse-observations";
    }

    public KafkaReporter(Properties kafkaProperties, String traceTopic, String observationTopic) {
        this(new KafkaProducer<>(kafkaProperties), traceTopic, observationTopic);
    }

    public KafkaReporter(Properties kafkaProperties) {
        this(kafkaProperties, null, null);
    }

    @Override
    public void report(TraceModel trace) {
        trace.setCreatedAt(Instant.now());
        send(traceTopic, trace.getId(), trace);
    }

    @Override
    public void report(ObservationModel observation) {
        observation.setCreatedAt(Instant.now());
        send(observationTopic, observation.getId(), observation);
    }

    private void send(String topic, String key, Object value) {
        try {
            String json = JSON.toJSONString(value, SerializerFeature.UseISO8601DateFormat);
            producer.send(new ProducerRecord<>(topic, key, json));
        } catch (Exception e) {
            System.err.println("Failed to serialize trace/observation: " + e.getMessage());
        }
    }

    @Override
    public void flush() {
        producer.flush();
    }

    @Override
    public void close() {
        producer.close();
    }
}
