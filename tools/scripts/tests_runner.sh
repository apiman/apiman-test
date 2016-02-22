#!/usr/bin/env bash

OPTS=`getopt -o medsfurv --long move,elastic,docker,del_datasource,fix,ui,vertx -- $@`

MOVE_APIMAN=false
DELETE_ELASTIC=false
RUN_DOCKER=false
GET_CHROME_DRIVER=false
RELEASE=false
FIX_VERSION=false
INSTALL_WF=false
VERTX=false

eval set -- "$OPTS"

while true; do
    case "$1" in
    -m | --move) MOVE_APIMAN=true; shift ;;
    -e | --elastic) DELETE_ELASTIC=true; shift ;;
    -d | --docker) RUN_DOCKER=true; shift ;;
    -r | --release) FIX_VERSION=true; INSTALL_WF=true shift ;;
    -u | --ui) echo GET_CHROME_DRIVER=true shift ;;
    *) break ;;
    esac
done


if [ $MOVE_APIMAN ]; then
    # move build apiman server to proper location
    mv ${WORKSPACE}/apiman/tools/server-all/target/wildfly-9.0.2.Final ${APIMAN_HOME}
fi

if [ $GET_CHROME_DRIVER ]; then
    # Install chrome driver
    ${WORKSPACE}/apiman-qe-tests/tools/chrome_driver.sh   ${CHROME_DRIVER}
fi

if [ $FIX_VERSION ]; then
    # fix apiman version property
    ${TEST_SOURCES}/tools/fix_apiman_version.sh
fi

if [ $INSTALL_WF ]; then
    # install apiman
    ${WORKSPACE}/apiman-qe-tests/tools/install_wildfly.sh ${APIMAN_HOME}
fi

# Set api catalog properties
${TEST_SOURCES}/tools/api-catalog/set_api_catalog.sh

# Deploy datasource
${TEST_SOURCES}/tools/deploy_apimanqe_ds.sh

if [ $DELETE_ELASTIC ]; then
    # remove embedded elastic
    rm ${APIMAN_HOME}/standalone/deployments/apiman-es.war

    # configure and run es
    sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" $APIMAN_HOME/standalone/configuration/apiman.properties

fi

if [ $RUN_DOCKER ]; then
    # run docker environment
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml pull
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml up -d
fi

# run apiman
$APIMAN_HOME/bin/standalone.sh -c standalone-apiman.xml > server_stdout.log 2>&1 &
sleep 30

if [ $VERTX ]; then
    ${TEST_SOURCES}/tools/scripts/vertx.sh