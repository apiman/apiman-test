#!/bin/bash

PROPERTIES=(
"-Dbrowser=$BROWSER"
"-Dapiman.keystore=${APIMAN_KEYSTORE}"
"-Dwebdriver.chrome.driver=${CHROME_DRIVER}"
)

mvn clean install -Pui-tests $PROPERTIES -f ${TEST_SOURCES}/pom.xml -fae
