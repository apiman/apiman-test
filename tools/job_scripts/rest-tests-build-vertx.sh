#!/usr/bin/env bash
# fix apiman version property
${TEST_SOURCES}/tools/fix_apiman_version.sh

# remove embedded elastic and gateway
rm ${APIMAN_HOME}/standalone/deployments/apiman-es.war
rm ${APIMAN_HOME}/standalone/deployments/apiman-gateway*

# configure and run es
sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" $APIMAN_HOME/standalone/configuration/apiman.properties
docker run -p 9200:9200 -d elasticsearch

# run apiman
$APIMAN_HOME/bin/standalone.sh -c standalone-apiman.xml  > /dev/null 2>&1  &
sleep 30

# Configure vertx gateway
${TEST_SOURCES}/tools/use_vertx.sh
envsubst < $VERTX_CFG_TEMPLATE > $VERTX_CFG

# Start the gateway
java -version
java -jar $APIMAN_SOURCES/gateway/platforms/vertx3/vertx3/target/apiman-gateway-platforms-vertx3-*-fat.jar -conf $VERTX_CFG > gateway.log 2>&1  &
sleep 10