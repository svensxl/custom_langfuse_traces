# Custom Langfuse Traces (Java)

A custom Java module for Langfuse trace and observation data, designed to be lightweight and dependency-free (other than FastJSON).

## Features

// File Reporter
Reporter fileReporter = new FileReporter("traces.json");

// Kafka Reporter
Properties props = new Properties();
props.put("bootstrap.servers", "localhost:9092");
// ... check KafkaProducer docs for minimal props
Reporter kafkaReporter = new KafkaReporter(props);

// Combine them
Reporter reporter = new CompositeReporter(fileReporter, kafkaReporter);

````

### 3. Create and Report

```java
try (reporter) {
    // Create Trace
    Trace trace = Trace.builder()
        .name("chat-session")
        .userId("user-1")
        .projectId("my-project")
        .build();

    // Create Observation
    Observation obs = Observation.builder()
        .traceId(trace.getId())
        .type(ObservationType.GENERATION)
        .name("gpt-call")
        .input("Hello")
        .output("Hi there")
        .build();

    // Report
    reporter.report(trace);
    reporter.report(obs);
}
````

## Data Model

The library provides POJOs `Trace` and `Observation` that map to the Langfuse ClickHouse schema.

- **Trace**: Represents a full interaction.
- **Observation**: Represents a step within a trace (Span, Generation, Event, etc.).

## Build

```bash
mvn clean package
```

## Testing

```bash
mvn test
```
