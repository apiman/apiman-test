#!/usr/bin/env bash

#=======================================================================
#
#   Usage: ./fix_apiman_version.sh 1.1.7
#
#   This script is used to fix the value of ${version.apiman} property
#   in ${TEST_SOURCES}/pom.xml. If no version is provided, then then
#   value is extracted from ${APIMAN_SOURCES}/pom.xml
#=======================================================================

if [ ! -z "$1" ]; then
    APIMAN_VERSION=$1
fi

: ${TEST_SOURCES=$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )}
: ${APIMAN_SOURCES=$( cd "$TEST_SOURCES/../apiman" && pwd )}

: ${APIMAN_VERSION=$( grep -oP -m 1 '<version>\K[^<]+' ${APIMAN_SOURCES}/pom.xml )}

sed -i "s/<version.apiman>.*<\/version.apiman>/<version.apiman>${APIMAN_VERSION}<\/version.apiman>/g" ${TEST_SOURCES}/pom.xml
