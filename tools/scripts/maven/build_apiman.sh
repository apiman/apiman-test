#!/bin/bash

mvn clean install -Ptools -Pinstall-all-wildfly10 -DskipTests\
 -f ${APIMAN_SOURCES}/pom.xml
