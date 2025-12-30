# Spec: Trace Generation

## ADDED Requirements

### Requirement: Trace Object Creation

The system MUST allow creating `Trace` objects with custom IDs and metadata.

#### Scenario: Create a simple trace

Given the Trace Generator is initialized
When I create a trace with name "chat-trace" and project ID "p-123"
Then a Trace object is returned
And the Trace ID is not null
And the project ID is "p-123"

#### Scenario: Global Defaults

Given the global configuration has version "1.0.0" and environment "prod"
When I create a new Trace without specifying version or environment
Then the Trace version is "1.0.0"
And the Trace environment is "prod"

#### Scenario: Automatic Timestamps on Creation

When I create a new Trace
Then the `timestamp` field is set to the current time
And the `event_ts` field is set to the current time

### Requirement: Observation Object Creation

The system MUST support creating `Observation` objects linked to a Trace.

#### Scenario: Observation Types

Given I want to create an Observation
When I specify type "GENERATION"
Then the Observation is created with type "GENERATION"

#### Scenario: Invalid Observation Type

When I try to create an Observation with type "INVALID_TYPE"
Then an error is raised (or compilation fails)
