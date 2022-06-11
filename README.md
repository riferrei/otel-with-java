# OpenTelemetry with Java

This project showcases how to instrument a microservice written in Java using the OpenTelemetry SDK to produce telemetry data to [compatible observability backends](https://opentelemetry.io/vendors).

## Running locally with Prometheus, Tempo, and Grafana.

The simplest way to play with this code is running everything local:

```bash
docker compose up -d
```

## Running the microservice with AWS X-Ray and CloudWatch

Alternatively, you can have the microservice sending telemetry data to AWS. Traces will be sent to [X-Ray](https://aws.amazon.com/xray) and the metrics will be sent to [CloudWatch](https://aws.amazon.com/cloudwatch). This is possible thanks to the [AWS Distro for OpenTelemetry](https://aws.amazon.com/otel) that provides out-of-the-box integration with AWS services. Before running the code; make sure to [configure your AWS credentials](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-quickstart.html) in your machine, as the code will use them to connect with the target services.

1. Execute the OTel collector

```bash
docker compose -f collector-for-aws.yaml up -d
```

2. Execute the microservice

```bash
sh run-locally.sh
```

3. Invoke the microservice API

```bash
curl -X GET http://localhost:8888/hello
```

# License

This project is licensed under the [Apache 2.0 License](./LICENSE).