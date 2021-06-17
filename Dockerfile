FROM maven:3.6.3-jdk-11

VOLUME /tmp
ADD . /usr/src/app
WORKDIR /usr/src/app

RUN mvn clean package -DskipTests
RUN curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent-all.jar --output opentelemetry-javaagent-all.jar
ENTRYPOINT [ "java", "-javaagent:opentelemetry-javaagent-all.jar", "-jar", "target/hello-app-1.0.jar" ]
