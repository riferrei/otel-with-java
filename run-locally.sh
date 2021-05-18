#!/bin/bash

mvn clean package -Dmaven.test.skip=true

OTEL_AGENT=opentelemetry-javaagent-all.jar
if [ ! -f "${OTEL_AGENT}" ]; then
  wget -O opentelemetry-javaagent-all.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.2.0/opentelemetry-javaagent-1.2.0-all.jar
fi

java -javaagent:./${OTEL_AGENT} \
-Dotel.traces.exporter=otlp \
-Dotel.metrics.exporter=otlp \
-Dotel.exporter.otlp.endpoint=http://localhost:8200 \
-Dotel.resource.attributes=service.name=hello-app,service.version=1.0 \
-jar target/hello-app-1.0.jar
