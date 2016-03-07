#!/usr/bin/env bash
# fix apiman version property
${TEST_SOURCES}/tools/fix_apiman_version.sh

# install apiman
${WORKSPACE}/apiman-qe-tests/tools/install_wildfly.sh ${APIMAN_HOME}

# switch to es
${TEST_SOURCES}/tools/use_elastic_manager.sh

# configure and run es
sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" $APIMAN_HOME/standalone/configuration/apiman.properties

# run docker environment
docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml up -d

# run apiman
$APIMAN_HOME/bin/standalone.sh -c standalone-apiman.xml > server_stdout.log 2>&1 &
sleep 30
