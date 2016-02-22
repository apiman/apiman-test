#!/usr/bin/env bash

# move build apiman server to proper location
mv ${WORKSPACE}/apiman/tools/server-all/target/wildfly-9.0.2.Final ${APIMAN_HOME}

# Install chrome driver
${TEST_SOURCES}/tools/chrome_driver.sh ${CHROME_DRIVER}

# Fix apiman version property
${TEST_SOURCES}/tools/fix_apiman_version.sh

# Set api catalog properties
${TEST_SOURCES}/tools/api-catalog/set_api_catalog.sh

# Deploy datasource
${TEST_SOURCES}/tools/deploy_apimanqe_ds.sh

# remove embedded elastic
rm ${APIMAN_HOME}/standalone/deployments/apiman-es.war

# configure and run es
sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" $APIMAN_HOME/standalone/configuration/apiman.properties

# run docker environment
docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml pull
docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml up -d

# run apiman
$APIMAN_HOME/bin/standalone.sh -c standalone-apiman.xml 2>&1  &
sleep 30