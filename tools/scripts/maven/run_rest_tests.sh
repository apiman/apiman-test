#!/bin/bash

echo "[RUNNER] --> Running tests: REST"


PROPERTIES=(
    "-Dapiman.keystore=$APIMAN_KEYSTORE"
    "-Dapiman.home=$APIMAN_HOME"
    "-Dapiman.test.proxy.address.server=$MACHINE_IP"
    )

mvn -f ${TEST_SOURCES}/pom.xml clean install -Prest-tests $PROPERTIES -Dtest.exclude="**/ipblacklist/*,**/ipwhitelist/*"
