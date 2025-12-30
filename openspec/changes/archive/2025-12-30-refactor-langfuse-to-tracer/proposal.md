# Proposal: Refactor Langfuse to Configurable Tracer

## Goal

Refactor the static `Langfuse` entry point into an instantiable, configurable `Tracer` class.
This enables:

1. Multiple `Tracer` instances with different configurations (reporters, environments).
2. Explicit configuration of reporting backends (File, Kafka) and default trace attributes.
3. Decoupling of `Trace` and `Observation` from static global state.

## Architecture

- **`Tracer` Entity**:

  - Replaces `Langfuse` class.
  - Holds `Reporter` instance.
  - Holds default configuration (`release`, `version`, `env`, etc.).
  - Acts as a factory for `Trace` and `Observation` builders, injecting itself/dependencies.

- **`TracerBuilder`**:

  - Fluent API to configure and build `Tracer`.
  - Methods to specific reporter settings (`.fileReporter(...)`, `.kafkaReporter(...)`).
  - Methods to set global defaults (`.defaultRelease(...)`).

- **Deprecation/Removal**:
  - Static `Langfuse` class.
  - Singleton `LangfuseConfig` (merged into `Tracer` instance state).

## Requirements

### Core API

- [NEW] **Tracer** class:
  - `Tracer.builder()` -> `TracerBuilder`.
  - `tracer.traceBuilder()` -> `Trace.Builder`.
  - `tracer.observationBuilder()` -> `Observation.Builder`.

### Configuration

- [NEW] **Reporter Configuration**:
  - Support setting File path or Kafka bootstrap servers/topic via builder.
- [NEW] **Defaults Configuration**:
  - Support setting `release`, `version`, `environment`, `public` defaults.
