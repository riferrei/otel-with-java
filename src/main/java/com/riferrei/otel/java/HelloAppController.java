package com.riferrei.otel.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.GlobalMeterProvider;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.extension.annotations.WithSpan;

import static com.riferrei.otel.java.Constants.*;
import static java.lang.Runtime.*;

@RestController
public class HelloAppController {

    private static final Logger log =
        LoggerFactory.getLogger(HelloAppController.class);

    private static final Tracer tracer =
        GlobalOpenTelemetry.getTracer("io.opentelemetry.traces.hello");

    private static final Meter meter =
        GlobalMeterProvider.get().get("io.opentelemetry.metrics.hello");

    private static final LongCounter numberOfExecutions =
        meter
            .counterBuilder(NUMBER_OF_EXEC_NAME)
            .setDescription(NUMBER_OF_EXEC_DESCRIPTION)
            .setUnit("int")
            .build();

    static {
        meter
            .counterBuilder(HEAP_MEMORY_NAME)
            .setDescription(HEAP_MEMORY_DESCRIPTION)
            .setUnit("bytes")
            .buildWithCallback(
                r -> {
                    r.observe(getRuntime().totalMemory() - getRuntime().freeMemory());
                });
    }

    @RequestMapping(method= RequestMethod.GET, value="/hello")
    public Response hello() {
        Response response = buildResponse();
        // Creating a custom span
        Span span = tracer.spanBuilder("mySpan").startSpan();
        try (Scope scope = span.makeCurrent()) {
            if (response.isValid()) {
                log.info("The response is valid.");
            }
        } finally {
            span.end();
        }
        // Updating the metric
        numberOfExecutions.add(1);
        return response;
    }

    @WithSpan
    private Response buildResponse() {
        return new Response("Hello World");
    }

    private class Response {

        private String message;

        public Response(String message) {
            setMessage(message);
        }

        @SuppressWarnings("unused")
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isValid() {
            return true;
        }

    }

}
