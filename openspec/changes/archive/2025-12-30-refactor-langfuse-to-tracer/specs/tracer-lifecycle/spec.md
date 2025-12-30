# Spec: Tracer Lifecycle

## ADDED Requirements

### Requirement: Tracer Instantiation

The system MUST allow creating a `Tracer` instance via a builder pattern.

#### Scenario: Instantiate minimal Tracer

Given the `Tracer` class
When I call `Tracer.builder().build()`
Then a new `Tracer` instance is returned
And it uses a default (noop or log) reporter if none configured

### Requirement: Trace Factory

The `Tracer` instance MUST provide a way to create `Trace` objects linked to its configuration.

#### Scenario: Create Trace from Tracer

Given a configured `Tracer`
When I call `tracer.traceBuilder().name("t1").build()`
Then a `Trace` is returned
And the `Trace` reports to the `Tracer`'s reporter

### Requirement: Observation Factory

The `Tracer` instance MUST provide a way to create `Observation` objects.

#### Scenario: Create Observation from Tracer

Given a configured `Tracer`
And an active `Trace` "t-parent"
When I call `tracer.observationBuilder().parent(t-parent).name("o1").build()`
Then an `Observation` is returned
And the `Observation` reports to the `Tracer`'s reporter upon completion
