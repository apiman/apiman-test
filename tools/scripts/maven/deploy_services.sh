#!/bin/bash

mvn package wildfly:deploy -DskipTests -f ${TEST_SOURCES}/apiman-it-deployments/pom.xml
