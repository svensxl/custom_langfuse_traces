package com.antigravity.langfuse.reporter;

import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.TraceModel;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

class FileReporterTest {

    @TempDir
    Path tempDir;

    @Test
    void testReportTrace() throws Exception {
        Path reportFile = tempDir.resolve("trace.json");
        try (FileReporter reporter = new FileReporter(reportFile.toString())) {
            TraceModel trace = TraceModel.builder()
                    .id("trace-1")
                    .name("test-trace")
                    .timestamp(Instant.now())
                    .build();
            reporter.report(trace);
        }

        Assertions.assertTrue(Files.exists(reportFile));
        List<String> lines = Files.readAllLines(reportFile);
        Assertions.assertEquals(1, lines.size());

        TraceModel readTrace = JSON.parseObject(lines.get(0), TraceModel.class);
        Assertions.assertEquals("trace-1", readTrace.getId());
    }
}
