#!/usr/bin/env bash

SCRIPT_PATH=$(dirname ${BASH_SOURCE[0]})
DEPLOYMENTS_LOCATION=${SCRIPT_PATH}/../../../apiman-it-deployments/

mvn -f $DEPLOYMENTS_LOCATION/pom.xml package
cp $DEPLOYMENTS_LOCATION/target/apiman-it-deployments-1.1.0-SNAPSHOT.war $SCRIPT_PATH