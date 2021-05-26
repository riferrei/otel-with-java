package com.riferrei.otel.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.GlobalMeterProvider;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.LongValueObserver;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;
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
        GlobalOpenTelemetry.getTracer("io.opentelemetry.traces.hello", "1.0.0");

    private static final Meter meter =
        GlobalMeterProvider.getMeter("io.opentelemetry.metrics.hello", "1.0.0");

    private static final LongCounter numberOfExecutions =
        meter
            .longCounterBuilder(NUMBER_OF_EXEC_NAME)
            .setDescription(NUMBER_OF_EXEC_DESCRIPTION)
            .setUnit("int")
            .build();

    @SuppressWarnings("unused")
    private static final LongValueObserver heapMemory =
        meter
            .longValueObserverBuilder(HEAP_MEMORY_NAME)
            .setDescription(HEAP_MEMORY_DESCRIPTION)
            .setUnit("byte")
            .setUpdater(
                result -> result.observe(
                    getRuntime().totalMemory() - getRuntime().freeMemory(),
                    Labels.of(HEAP_MEMORY_NAME, HEAP_MEMORY_DESCRIPTION))
            )
            .build();

    @RequestMapping(method= RequestMethod.GET, value="/hello")
    public Response hello() {
        Response response = buildResponse();
        // Creating a custom span just for fun...
        Span span = tracer.spanBuilder("mySpan").startSpan();
        try (Scope scope = span.makeCurrent()) {
            if (response.isValid()) {
                log.info("The response is valid.");
            }
        } finally {
            span.end();
        }
        // Updating the number of executions metric...
        numberOfExecutions.add(1,
            Labels.of(NUMBER_OF_EXEC_NAME,
            NUMBER_OF_EXEC_DESCRIPTION));
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
