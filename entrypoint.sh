#!/bin/sh

exec java \
-javaagent:dd-java-agent.jar \
-Ddd.profiling.enabled=true \
-jar kosha-service.jar

#fi
