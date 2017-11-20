#!/usr/bin/env bash

# prepare configuration
echo "[RUNNER] --> Copying gateway configuration "
cp ${APIMAN_GW_CONFIG} ${APIMAN_HOME_GW}/config.json

# Start the gateway
echo "[RUNNER] --> Starting Apiman Vert.X gateway"
${APIMAN_HOME_GW}/apiman-gateway.sh -conf ${APIMAN_HOME_GW}/config.json > ${WORKSPACE}/gateway_stdout.log 2>&1 &

export GW_PID=$!
echo "GW_PID=${GW_PID}"

sleep 10

# Configure vertx gateway
${TEST_SOURCES}/tools/use_vertx.sh
