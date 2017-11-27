#!/bin/bash

echo "[RUNNER] --> Running tests: COMMUNITY"

declare -a PROPERTIES=(
    "-Dapiman.suite.api-endpoint=$API_ENDPOINT"
    "-Dapiman.suite.api-username=$API_USERNAME"
    "-Dapiman.suite.api-password=$API_PASSWORD"
    "-Dapiman.suite.gateway-config-endpoint=$GATEWAY_CONFIG_ENDPOINT"
    "-Dapiman.suite.gateway-config-username=$GATEWAY_CONFIG_USERNAME"
    "-Dapiman.suite.gateway-config-password=$GATEWAY_CONFIG_PASSWORD"
    "-Dapiman.suite.gateway-endpoint=$GATEWAY_ENDPOINT"
    "-Dapiman.suite.echo-endpoint=$ECHO_ENDPOINT"
    "-Dapiman.junit.no-server=$NO_SERVER"
    )

mvn -f ${APIMAN_SOURCES}/test/suite/pom.xml test -DskipTests=false -fae $PROPERTIES
