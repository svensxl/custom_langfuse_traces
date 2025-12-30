# Tasks: Tracer Refactor

- [ ] Core Implementation <!-- id: 1 -->

  - [ ] Rename `Langfuse` to `Tracer` (or create new `Tracer` class) <!-- id: 2 -->
  - [ ] Implement `Tracer` class with internal fields (`reporter`, defaults) <!-- id: 3 -->
  - [ ] Implement `Tracer.Builder` <!-- id: 4 -->

- [ ] Configuration Support <!-- id: 5 -->

  - [ ] Implement `.fileReporter(...)` in Builder <!-- id: 6 -->
  - [ ] Implement `.kafkaReporter(...)` in Builder <!-- id: 7 -->
  - [ ] Implement default setters (`defaultEnvironment`, etc.) in Builder <!-- id: 8 -->

- [ ] SDK Integration <!-- id: 9 -->

  - [ ] Update `Trace` constructor to accept `Tracer` (or config/reporter) <!-- id: 10 -->
  - [ ] Update `Observation` constructor to accept `Tracer` (or config/reporter) <!-- id: 11 -->
  - [ ] Expose `traceBuilder()` / `observationBuilder()` instance methods on `Tracer` <!-- id: 12 -->

- [ ] Cleanup & Migration <!-- id: 13 -->
  - [ ] Remove static `LangfuseConfig` <!-- id: 14 -->
  - [ ] Remove static `Langfuse` entry point <!-- id: 15 -->
  - [ ] Update `DemoApp` to use `Tracer` <!-- id: 16 -->
  - [ ] Update Tests to use `Tracer` <!-- id: 17 -->
