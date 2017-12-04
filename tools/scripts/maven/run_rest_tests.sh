#!/bin/bash

echo "[RUNNER] --> Running tests: REST"


PROPERTIES=(
    "-Dapiman.keystore=$APIMAN_KEYSTORE"
    "-Dapiman.home=$APIMAN_HOME"
    "-Dapiman.test.proxy.address.server=$MACHINE_IP"
    "-Dtest.exclude='${EXCLUDE_TESTS}'"
    "-Dtest.include='${INCLUDE_TESTS:-*}'"
    )

set +x

mvn -f ${TEST_SOURCES}/pom.xml clean install -Prest-tests ${PROPERTIES} -fae

set -x