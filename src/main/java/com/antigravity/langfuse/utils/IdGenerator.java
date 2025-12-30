package com.antigravity.langfuse.utils;

import java.util.UUID;

/**
 * Utility for generating unique IDs for Traces and Observations.
 */
public class IdGenerator {

    public static String generateTraceId() {
        return UUID.randomUUID().toString();
    }

    public static String generateObservationId() {
        return UUID.randomUUID().toString();
    }
}
