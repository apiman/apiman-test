#!/usr/bin/env bash

echo "[RUNNER] --> Running tests: SMOKE"

PROPERTIES=(
    "-Dapiman.keystore=$APIMAN_KEYSTORE"
    "-Dapiman.home=$APIMAN_HOME"
    "-Dapiman.test.proxy.address.server=$MACHINE_IP"
    "-Dwebdriver.chrome.driver=${CHROME_DRIVER}"
    "-Dapiman.keystore=${APIMAN_KEYSTORE}"
    )

mvn -f ${TEST_SOURCES}/pom.xml clean install -Psmoke-tests $PROPERTIES -fae