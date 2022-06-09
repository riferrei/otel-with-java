#!/bin/bash

mvn clean package -Dmaven.test.skip=true

AGENT_FILE=opentelemetry-javaagent-all.jar
if [ ! -f "${AGENT_FILE}" ]; then
  curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.14.0/opentelemetry-javaagent.jar --output ${AGENT_FILE}
fi

export OTEL_TRACES_EXPORTER=otlp
export OTEL_METRICS_EXPORTER=otlp
export OTEL_EXPORTER_OTLP_ENDPOINT=http://localhost:8200

#################################### Run with Elastic Cloud ####################################

# export OTEL_EXPORTER_OTLP_ENDPOINT=https://clusterid.apm.region.provider.elastic-cloud.com:443
# export OTEL_EXPORTER_OTLP_HEADERS="Authorization=Bearer APM_SECRET_TOKEN"

################################################################################################

export OTEL_RESOURCE_ATTRIBUTES=service.name=hello-app,service.version=1.0

java -javaagent:./${AGENT_FILE} -jar target/hello-app-1.0.jar
