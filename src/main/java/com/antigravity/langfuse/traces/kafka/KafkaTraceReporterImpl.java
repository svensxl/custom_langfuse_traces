package com.antigravity.langfuse.traces.kafka;

import com.antigravity.langfuse.traces.config.TraceConfig;
import com.antigravity.langfuse.traces.dto.Trace;
import com.antigravity.langfuse.traces.dto.Observation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Kafka 上报器实现类
 */
public class KafkaTraceReporterImpl implements KafkaTraceReporter {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTraceReporterImpl.class);
    
    private final TraceConfig.KafkaConfig kafkaConfig;
    private KafkaProducer<String, String> producer;
    private ObjectMapper objectMapper;
    
    public KafkaTraceReporterImpl(TraceConfig.KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }
    
    @Override
    public void initialize() {
        // 初始化 ObjectMapper
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        
        // 配置 Kafka 生产者
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        
        // 添加额外配置
        if (kafkaConfig.getAdditionalProperties() != null) {
            props.putAll(kafkaConfig.getAdditionalProperties());
        }
        
        this.producer = new KafkaProducer<>(props);
        logger.info("KafkaTraceReporter initialized with bootstrap servers: {}", kafkaConfig.getBootstrapServers());
    }
    
    @Override
    public void reportTrace(Trace trace) {
        if (producer == null) {
            logger.error("Kafka producer not initialized");
            return;
        }
        
        try {
            String traceJson = objectMapper.writeValueAsString(trace);
            ProducerRecord<String, String> record = new ProducerRecord<>(
                kafkaConfig.getTopicName(), 
                "trace_" + trace.getId(), 
                traceJson
            );
            
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    logger.error("Failed to send trace to Kafka", exception);
                } else {
                    logger.debug("Trace sent to Kafka topic: {}, partition: {}, offset: {}", 
                               metadata.topic(), metadata.partition(), metadata.offset());
                }
            });
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize trace to JSON", e);
        }
    }
    
    @Override
    public void reportObservation(Observation observation) {
        if (producer == null) {
            logger.error("Kafka producer not initialized");
            return;
        }
        
        try {
            String observationJson = objectMapper.writeValueAsString(observation);
            ProducerRecord<String, String> record = new ProducerRecord<>(
                kafkaConfig.getTopicName(), 
                "observation_" + observation.getId(), 
                observationJson
            );
            
            producer.send(record, (metadata, exception) -> {
                if (exception != null) {
                    logger.error("Failed to send observation to Kafka", exception);
                } else {
                    logger.debug("Observation sent to Kafka topic: {}, partition: {}, offset: {}", 
                               metadata.topic(), metadata.partition(), metadata.offset());
                }
            });
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize observation to JSON", e);
        }
    }
    
    @Override
    public void close() {
        if (producer != null) {
            producer.close();
            logger.info("KafkaTraceReporter closed");
        }
    }
}