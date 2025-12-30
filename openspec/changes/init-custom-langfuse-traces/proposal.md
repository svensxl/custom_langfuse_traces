# Proposal: Init Custom Langfuse Traces

## Goal

Provide a lightweight Java module for generating Langfuse-compliant trace and observation data, supporting multiple output methods (Kafka, Local File) to trace the execution of Large Model Agents.

## Background

The existing Langfuse Java SDK binds to a fixed project effectively and doesn't support flexible project ID specification during trace creation, which limits multi-project tracing support. A custom, lightweight solution is required to allow manual creation and reporting of traces with full control over IDs and output channels.

## Requirements

- **Core Functionality**:

  - [NEW] Create `Trace` and `Observation` objects with custom IDs.
  - [NEW] Support manual assignment or automatic generation of `trace_id`, `observation_id`, and `project_id`.
  - [NEW] Thread-safe context management for traces.
  - [NEW] Global default configuration for `release`, `version`, `environment`, and `public` fields.
  - [NEW] Auto-population of timestamps (`event_ts`, `start_time`, `timestamp`) at creation.
  - [NEW] Trace/Observation `created_at` timestamp auto-set at reporting time.
  - [NEW] Support specific `ObservationType` enum values (EVENT, SPAN, etc.).

- **Reporting**:

  - [NEW] `Reporter` interface for pluggable output destinations.
  - [NEW] `KafkaReporter` for pushing data to Kafka topics.
  - [NEW] `FileReporter` for saving data to local JSON files.
  - [NEW] Support asynchronous and batched reporting.

- **Configuration**:
  - [NEW] Configurable via `application.yaml` or builder.

## Non-Functional Requirements

- **Performance**: High throughput for trace generation and reporting; non-blocking I/O preferred.
- **Compatibility**: Java 21, Kafka 2.0+.
