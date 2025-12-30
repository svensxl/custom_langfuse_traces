# Design: Custom Langfuse Traces Module

## Overview

The module uses a clear separation of concerns:

- **Domain Layer**: Defines `Trace` and `Observation` entities matching Langfuse schema.
- **Service Layer**: `TraceService` or `TraceContext` to manage lifecycle and global defaults.
- **Infrastructure Layer**: `Reporter` implementations for data persistence/transport.

## Architecture

### 1. Domain Entities

- **Trace**: POJO containing `id`, `timestamp`, `name`, `userId`, `input`, `output`, etc.
- **Observation**: POJO containing `id`, `traceId`, `type` (Enum), `startTime`, `endTime`, `modelParameters`, etc.
- **Data Schema**: Strictly follows Langfuse ClickHouse schema types.

### 2. Observation Types (Enum)

Enum `ObservationType` strictly defined as:

- `EVENT`, `SPAN`, `GENERATION`, `AGENT`, `TOOL`, `CHAIN`, `RETRIEVER`, `EVALUATOR`, `EMBEDDING`, `GUARDRAIL`.

### 3. Logic & Automation

- **Global Defaults**:

  - `TraceContext` or a static global config holder stores default values for:
    - `release`
    - `version`
    - `environment` (default: "default")
    - `public` (default: false)
  - During `Trace`/`Observation` creation, if these fields are not provided, defaults are applied.

- **Timestamp Automation**:
  - **Creation Time**: `event_ts`, `start_time`, `timestamp` are set to `Instant.now()` in the constructor/builder if null.
  - **Report Time**: `created_at` is updated to `Instant.now()` inside `Reporter.report()` before serialization/dispatch.

### 4. Reporter Interface

```java
public interface Reporter extends AutoCloseable {
    void report(Trace trace);
    void report(Observation observation);
    void flush();
}
```

### 5. Configuration

Properties based configuration:

```yaml
langfuse:
  custom:
    enabled: true
    defaults:
      environment: "production"
      version: "1.0.0"
      release: "REL-2025-01"
      public: false
    reporters:
      - kafka
      - file
    kafka:
      bootstrap-servers: "localhost:9092"
      topic-prefix: "langfuse-"
    file:
      path: "./logs/traces.json"
```
