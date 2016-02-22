#!/usr/bin/env bash

# fix npm version
sed -i "s/<npmVersion>.*<\/npmVersion>/<npmVersion>2.0.2<\/npmVersion>/g" ${APIMAN_SOURCES}/manager/ui/war/pom.xml

# fix apiman version property
${TEST_SOURCES}/tools/fix_apiman_version.sh

# install apiman
${WORKSPACE}/apiman-qe-tests/tools/install_wildfly.sh ${APIMAN_HOME}

# remove embedded elastic
#rm ${APIMAN_HOME}/standalone/deployments/apiman-es.war

# configure es port
sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" $APIMAN_HOME/standalone/configuration/apiman.properties

# run docker environment
docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml up -d

# run apiman
$APIMAN_HOME/bin/standalone.sh -b 0.0.0.0 -c standalone-apiman.xml > server_stdout.log 2>&1 &
sleep 120
