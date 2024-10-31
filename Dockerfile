FROM --platform=$BUILDPLATFORM maven:3.9.3-eclipse-temurin-17 as builder
LABEL maintainer="raviranjanpandey75@gmail.com"
RUN apt-get upgrade -y
WORKDIR /app
ARG TARGETPLATFORM
COPY src src
COPY pom.xml pom.xml
COPY settings.xml settings.xml
COPY entrypoint.sh entrypoint.sh
RUN --mount=type=cache,target=/root/.m2 mvn -X -DskipTests -s /app/settings.xml clean package -P artifactory

FROM --platform=$BUILDPLATFORM eclipse-temurin:17-jre-alpine as release
RUN apk -U upgrade
WORKDIR /app
RUN wget -O dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'
COPY --from=builder /app/target/kosha-*.jar kosha-service.jar
COPY --from=builder /app/entrypoint.sh entrypoint.sh
ENTRYPOINT /app/entrypoint.sh