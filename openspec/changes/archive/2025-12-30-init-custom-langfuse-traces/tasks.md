# Tasks: Trace & Observation API

- [ ] Domain Model Updates <!-- id: 1 -->

  - [ ] Rename existing `Trace`/`Observation` POJOs (e.g., to `internal.TraceAttributes`) <!-- id: 2 -->
  - [ ] Ensure FastJSON compatibility on internal models <!-- id: 3 -->

- [ ] SDK API Implementation <!-- id: 4 -->

  - [ ] [NEW] Create `Langfuse` entry point (factory) <!-- id: 5 -->
  - [ ] [NEW] Create `Trace` entity (stateful SDK object) <!-- id: 6 -->
  - [ ] [NEW] Create `TraceBuilder` <!-- id: 7 -->
  - [ ] [NEW] Create `Observation` entity (stateful SDK object, replaces SdkSpan) <!-- id: 8 -->
  - [ ] [NEW] Create `ObservationBuilder` <!-- id: 9 -->
  - [ ] [NEW] Implement logic to link parent (Trace or Observation) <!-- id: 10 -->

- [ ] Reporting Integration <!-- id: 11 -->

  - [ ] Update `Reporter` to accept internal model objects <!-- id: 12 -->
  - [ ] Wire `Trace` creation to Reporter <!-- id: 13 -->
  - [ ] Wire `Observation.end()` to Reporter <!-- id: 14 -->

- [ ] Testing & Validation <!-- id: 15 -->

  - [ ] Update Unit Tests for new API <!-- id: 16 -->
  - [ ] Update Demo App to use `Langfuse.traceBuilder()` / `observationBuilder()` <!-- id: 17 -->
  - [ ] Verify serialization correctness <!-- id: 18 -->

- [ ] Cleanup <!-- id: 19 -->
  - [ ] Remove `Span`, `Tracer`, `SpanProcessor` classes <!-- id: 20 -->
