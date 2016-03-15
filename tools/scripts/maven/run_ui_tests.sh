#!/bin/bash

PROPERTIES=(
    "-Dbrowser=$BROWSER"
    "-Dapiman.keystore=${APIMAN_KEYSTORE}"
    "-Dwebdriver.chrome.driver=${CHROME_DRIVER}"
    )

mvn -f ${TEST_SOURCES}/pom.xml clean install -Pui-tests $PROPERTIES -fae 
