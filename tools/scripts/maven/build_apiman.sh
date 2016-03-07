#!/bin/bash

mvn -f ${APIMAN_SOURCES}/pom.xml clean install -Ptools -Pinstall-all-wildfly10 -DskipTests
