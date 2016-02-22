#!/usr/bin/env bash
. ${NPM_VERSION:="2.0.2"}
# fix npm version
sed -i "s/<npmVersion>.*<\/npmVersion>/<npmVersion>${NPM_VERSION}<\/npmVersion>/g" ${WORKSPACE}/apiman/manager/ui/war/pom.xml