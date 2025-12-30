package com.antigravity.langfuse.reporter;

import com.antigravity.langfuse.domain.ObservationModel;
import com.antigravity.langfuse.domain.TraceModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class FileReporter implements Reporter {

    private final PrintWriter writer;

    public FileReporter(String filePath) throws IOException {
        this.writer = new PrintWriter(new FileWriter(filePath, true));
    }

    @Override
    public void report(TraceModel trace) {
        trace.setCreatedAt(Instant.now());
        writeJson(trace);
    }

    @Override
    public void report(ObservationModel observation) {
        observation.setCreatedAt(Instant.now());
        writeJson(observation);
    }

    private void writeJson(Object object) {
        String json = JSON.toJSONString(object, SerializerFeature.UseISO8601DateFormat);
        synchronized (this) {
            writer.println(json);
            writer.flush(); // autoflush for now
        }
    }

    @Override
    public void flush() {
        writer.flush();
    }

    @Override
    public void close() throws Exception {
        writer.close();
    }
}
