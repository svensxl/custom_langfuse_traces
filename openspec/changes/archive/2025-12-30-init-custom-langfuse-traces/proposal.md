# Proposal: Custom Langfuse Traces (Trace/Observation API)

## Goal

Provide a strongly-typed Java SDK for generating Langfuse traces and observations.
The API should follow OpenTelemetry-like ergonomics (Builders, Context propagation) but use **Trace** and **Observation** as the core entities instead of "Spans".

## Architecture

- **No global implicit context** (ThreadLocal). All context is passed explicitly.
- **Trace & Observation Entities**:
  - `Trace`: Represents the high-level operation. Can be updated.
  - `Observation`: Represents a unit of work (Span, Event, Generation). Can be nested.
- **Builders**:
  - `TraceBuilder`: Creates a `Trace`.
  - `ObservationBuilder`: Creates an `Observation`. Accepts `Trace` (as root) or `Observation` (as parent).

## Requirements

### Core API

- [NEW] **Langfuse** (Entry Point): Static factory or singleton to access builders.
- [NEW] **Trace** (SDK Entity):
  - Created via `TraceBuilder`.
  - Supports setting/updating all Trace fields (name, metadata, user_id, etc.).
  - Reports updates to the backend/reporter immediately or on change.
- [NEW] **Observation** (SDK Entity):
  - Created via `ObservationBuilder`.
  - **Context**: Must be linked to a parent `Trace` OR parent `Observation`.
  - **Type**: Supports types like `SPAN`, `GENERATION`, `EVENT`.
  - **Lifecycle**: Has `end()` method to finalize and trigger reporting.

### Data Model

- [MODIFIED] Rename existing `Trace`/`Observation` POJOs to `TraceModel`/`ObservationModel` (or internal equivalents) to avoid confusion with the new SDK entities.

### Reporting

- [UNCHANGED] Uses **FastJSON** for serialization.
- [UNCHANGED] `Reporter` interface (File/Kafka) accepts data objects.
- [MODIFIED] `Trace` creation/update triggers `reporter.report(traceData)`.
- [MODIFIED] `Observation.end()` triggers `reporter.report(observationData)`.

## Non-Functional Requirements

- Thread-safe objects.
- Minimal overhead.
