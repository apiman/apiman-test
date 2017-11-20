#!/usr/bin/env bash

# configure and run es
if ${CONFIGURE_ES}; then
    echo "[RUNNER] --> Setting ES configuration for Apiman"
    sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" ${APIMAN_HOME}/standalone/configuration/apiman.properties
fi

# Start apiman
echo "[RUNNER] --> Starting Apiman"
${APIMAN_HOME}/bin/standalone.sh > ${WORKSPACE}/server_stdout.log 2>&1 &

export MANAGER_PID=$!
echo "MANAGER_PID=$MANAGER_PID"

sleep 120