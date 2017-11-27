#!/bin/bash

echo "[RUNNER] --> Building Plugin distribution"
mvn -f ${PLUGIN_SOURCES}/pom.xml clean install -DskipTests 
