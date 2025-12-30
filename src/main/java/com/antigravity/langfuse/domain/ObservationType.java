package com.antigravity.langfuse.domain;

/**
 * Enumeration of supported observation types in Langfuse.
 */
public enum ObservationType {
    EVENT,
    SPAN,
    GENERATION,
    AGENT,
    TOOL,
    CHAIN,
    RETRIEVER,
    EVALUATOR,
    EMBEDDING,
    GUARDRAIL;

    @Override
    public String toString() {
        return name();
    }
}
