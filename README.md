# OpenTelemetry in Java with Elastic APM

This project showcase how to use [Elastic APM](https://www.elastic.co/apm) with a microservice written in [Java](https://openjdk.java.net/) and instrumented using [OpenTelemetry](https://opentelemetry.io/). Everything is based on [Docker Compose](https://docs.docker.com/compose/) and you can test it with Elastic APM running locally or running on [Elasticsearch Service](https://www.elastic.co/elasticsearch/service).

## Run with the collector

Using this model, the Java application sends the traces and metrics to a collector that forwards them to Elastic APM.

```bash
docker-compose -f run-with-collector.yaml up -d
```

## Run without the collector

Using this model, the Java application sends the traces and metrics directly to Elastic APM.

```bash
docker-compose -f run-without-collector.yaml up -d
```

Once everything is running there will periodic requests being sent to the microservice so you don't need to issue any requests by yourself. However, if you want to do it anyway just execute:

```bash
curl -X GET http://localhost:8888/hello
```

# License

This project is licensed under the [Apache 2.0 License](./LICENSE).
