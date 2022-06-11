# OpenTelemetry in Java

This project showcases how to instrument a microservice written in Java using OpenTelemetry to produce telemetry data to a [compatible observability backend](https://opentelemetry.io/vendors).

## Running locally with Prometheus, Grafana, and Grafana Tempo

The simplest way to play with this code is running everything local using docker compose:

```bash
docker compose up -d
```

## Running the microservice with AWS X-Ray and CloudWatch

Alternatively, you can execute the microservice to send the telemetry data to AWS. Traces will be sent to [X-Ray](https://aws.amazon.com/xray) and metrics will be sent to [CloudWatch](https://aws.amazon.com/cloudwatch). This is possible thanks to the [AWS Distro for OpenTelemetry](https://aws.amazon.com/otel) that provides out-of-the-box integration with AWS services. Before running the code, configure your AWS credentials in your machine, as the code will try to use them to connect with the target services.

### 1. Execute the collector

```bash
docker compose -f collector-for-aws.yaml up -d
```

### 2. Execute the microservice

```bash
sh run-locally.sh
```

## Invoking the microservice API

Once everything is running, you can send requests to the microservice API using:

```bash
curl -X GET http://localhost:8888/hello
```

# License

This project is licensed under the [Apache 2.0 License](./LICENSE).