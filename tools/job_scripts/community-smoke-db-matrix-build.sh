#!/usr/bin/env bash
# delete default data source
rm $APIMAN_HOME/standalone/deployments/apiman-ds.xml

# remove embedded elastic
rm ${APIMAN_HOME}/standalone/deployments/apiman-es.war

# configure es port
sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" $APIMAN_HOME/standalone/configuration/apiman.properties

# run docker environment
/usr/local/bin/docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml up -d

# start wildfly
$APIMAN_HOME/bin/standalone.sh -c standalone-apiman.xml &

# dummy start wait start
sleep 2m