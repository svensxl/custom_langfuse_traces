# reporting Specification

## Purpose
TBD - created by archiving change init-custom-langfuse-traces. Update Purpose after archive.
## Requirements
### Requirement: File Reporting

The system MUST support writing Trace and Observation data to local files in JSON format.
The system MUST update the `created_at` timestamp upon reporting.

#### Scenario: Write trace to file with timestamp update

Given a FileReporter configured with path "traces.json"
And a Trace object created 5 minutes ago
When I report the Trace
Then the `created_at` field of the Trace is updated to the current time
And the file "traces.json" contains the updated JSON

### Requirement: Kafka Reporting

The system MUST support publishing Trace and Observation data to Kafka topics.
The system MUST update the `created_at` timestamp upon reporting.

#### Scenario: Publish trace to Kafka with timestamp update

Given a KafkaReporter configured with topic "traces"
When I report a Trace "t-1"
Then the `created_at` field is updated to the current time
And the message is published to topic "traces"

### Requirement: Reporter Configuration

The system MUST be configurable to use one or multiple reporters.

#### Scenario: Multiple reporters

Given a configuration enabling both File and Kafka reporters
When I report a Trace
Then the Trace is written to the file
And the Trace is published to Kafka

