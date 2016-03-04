#!/usr/bin/env bash

# Configure vertx gateway
${TEST_SOURCES}/tools/use_vertx.sh
envsubst < $VERTX_CFG_TEMPLATE > $VERTX_CFG

# Start the gateway
java -version
java -jar $APIMAN_SOURCES/gateway/platforms/vertx3/vertx3/target/apiman-gateway-platforms-vertx3-*-fat.jar -conf $VERTX_CFG > gateway.log 2>&1  &
sleep 10
