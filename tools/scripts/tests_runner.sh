#!/usr/bin/env bash

DIR=`dirname ${BASH_SOURCE[0]}`
MAVEN_SCRIPTS="$DIR/maven/"

: ${RUN_APIMAN:=true}
: ${CLEANUP:=true}
: ${BUILD_APIMAN:=false}
: ${MOVE_APIMAN:=false}
: ${DELETE_ELASTIC:=false}
: ${RUN_DOCKER:=false}
: ${GET_CHROME_DRIVER:=false}
: ${RELEASE:=false}
: ${FIX_VERSION:=false}
: ${INSTALL_WF:=false}
: ${VERTX:=false}
: ${API_CATALOG:=false}
: ${DEPLOY_DS:=false}
: ${CONFIGURE_ES:=false}
: ${UI_TESTS:=false}
: ${REST_TESTS:=false}
: ${INSTALL_PLUGINS:=false}
: ${TEST_DEPLOYMENTS:=false}
: ${COMMUNITY_TESTS:=false}
: ${SMOKE_TESTS:=false}
: ${RC_FILE="$DIR/custom.rc"}

OPTS=`getopt -o urcs --long ui,rest,community,smoke -- $@`

source "$DIR/apimanrc.sh"
if [ -f $RC_FILE ]; then
    source "$RC_FILE"
fi

eval set -- "$OPTS"

function set_ui() {
    BUILD_APIMAN=true
    MOVE_APIMAN=true
    RUN_DOCKER=true
    GET_CHROME_DRIVER=true
    FIX_VERSION=true
    DELETE_ELASTIC=true
    CONFIGURE_ES=true
    API_CATALOG=true
    INSTALL_PLUGINS=true
    TEST_DEPLOYMENTS=true
    UI_TESTS=true
}

function set_rest() {
    MOVE_APIMAN=true
    BUILD_APIMAN=true
    RUN_DOCKER=true
    API_CATALOG =true
    DEPLOY_DS=true
    DELETE_ELASTIC=true
    CONFIGURE_ES=true
    INSTALL_PLUGINS=true
    REST_TESTS=true
    TEST_DEPLOYMENTS=true
}

function set_community() {
    BUILD_APIMAN=true
    MOVE_APIMAN=true
    COMMUNITY_TESTS=true

}

function set_smoke() {
    BUILD_APIMAN=true
    MOVE_APIMAN=true
    SMOKE_TESTS=true
    RUN_DOCKER=true
    API_CATALOG =true
    DEPLOY_DS=true
    DELETE_ELASTIC=true
    CONFIGURE_ES=true
    TEST_DEPLOYMENTS=true
}
while true; do
    case "$1" in
    -u | --ui) set_ui; shift ;;
    -r | --rest) set_rest; shift ;;
    -c | --community) set_community; shift;;
    -s | --smoke) set_smoke; shift;;
    *) break ;;
    esac
done

if $BUILD_APIMAN; then
    sh $MAVEN_SCRIPTS/build_apiman.sh
    sh $MAVEN_SCRIPTS/build_plugins.sh
fi

if $MOVE_APIMAN; then
    # move build apiman server to proper location
    mv ${WORKSPACE}/apiman/tools/server-all/target/wildfly-10.0.0.Final ${APIMAN_HOME}
fi

if $GET_CHROME_DRIVER; then
    # Install chrome driver
    ${TEST_SOURCES}/tools/chrome_driver.sh   ${CHROME_DRIVER}
fi

if $FIX_VERSION; then
    # fix apiman version property
    ${TEST_SOURCES}/tools/fix_apiman_version.sh
fi

if $INSTALL_WF; then
    # install apiman
    ${TEST_SOURCES}/tools/install_wildfly.sh ${APIMAN_HOME}
fi

if $API_CATALOG; then
    # Set api catalog properties
    ${TEST_SOURCES}/tools/api-catalog/set_api_catalog.sh
fi

if $DEPLOY_DS; then
    # Deploy datasource
    ${TEST_SOURCES}/tools/deploy_apimanqe_ds.sh
fi

if $DELETE_ELASTIC; then
    # remove embedded elastic
    rm ${APIMAN_HOME}/standalone/deployments/apiman-es.war
fi

if $CONFIGURE_ES; then
    # configure and run es
    sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" $APIMAN_HOME/standalone/configuration/apiman.properties

fi

if $RUN_DOCKER; then
    # run docker environment
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml pull
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml up -d
fi

if $RUN_APIMAN; then
    # run apiman
    $APIMAN_HOME/bin/standalone.sh > server_stdout.log 2>&1 &
    PID=$!
    echo "PID=$PID"
    sleep 30
fi

if $VERTX; then
    ${TEST_SOURCES}/tools/scripts/vertx.sh
fi

if $INSTALL_PLUGINS; then
    sh $MAVEN_SCRIPTS/build_plugins.sh
fi

if $TEST_DEPLOYMENTS; then
    sh $MAVEN_SCRIPTS/deploy_services.sh
fi

if $COMMUNITY_TESTS; then
    sh $MAVEN_SCRIPTS/run_community_tests.sh
fi

if $REST_TESTS; then
    sh $MAVEN_SCRIPTS/build_rest_tests.sh
fi

if $UI_TESTS; then
    sh $MAVEN_SCRIPTS/run_ui_tests.sh
fi

if $SMOKE_TESTS; then
    sh $MAVEN_SCRIPTS/run_smoke_tests.sh
fi

if $RUN_DOCKER && $CLEANUP; then
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml stop
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml rm -f
fi

if $RUN_APIMAN && $CLEANUP; then
    kill -TERM $PID
    wait $PID
fi
