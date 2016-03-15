#!/bin/bash

mvn -f ${TEST_SOURCES}/pom.xml install -DskipTests
mvn -f ${TEST_SOURCES}/apiman-it-deployments/pom.xml package wildfly:deploy -DskipTests
