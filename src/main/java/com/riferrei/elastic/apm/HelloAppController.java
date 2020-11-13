package com.riferrei.elastic.apm;

import org.springframework.web.bind.annotation.*;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.context.Scope;

@RestController
public class HelloAppController {

    private final TracerProvider tracerProvider =
        OpenTelemetry.getGlobalTracerProvider();

    @RequestMapping(method= RequestMethod.GET, value="/hello")
    public Response Car() {

        Tracer tracer = tracerProvider.get("hello-app", "1.0");
        Span customSpan = tracer.spanBuilder("custom-span").startSpan();
        try (Scope scope = customSpan.makeCurrent()) {
            customSpan.setAttribute("custom-label", "Duke");
        } finally {
            customSpan.end();
        }

        return new Response("Hello World");

    }

    private class Response {

        private String message;

        public Response(String message) {
            setMessage(message);
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}
