#!/usr/bin/env bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
MAVEN_SCRIPTS="$DIR/maven/"
TEST_SOURCES="$DIR/../.."

: ${RUN_APIMAN:=true}
: ${CLEANUP:=true}
: ${BUILD_APIMAN:=false}
: ${MOVE_APIMAN:=false}
: ${DELETE_ELASTIC:=true}
: ${RUN_DOCKER:=false}
: ${GET_CHROME_DRIVER:=false}
: ${RELEASE:=false}
: ${FIX_VERSION:=false}
: ${RUN_VERTX:=false}
: ${API_CATALOG:=false}
: ${CONFIGURE_ES:=false}
: ${UI_TESTS:=false}
: ${REST_TESTS:=false}
: ${BUILD_PLUGINS:=false}
: ${TEST_DEPLOYMENTS:=false}
: ${COMMUNITY_TESTS:=false}
: ${SMOKE_TESTS:=false}
: ${MANAGER_ONLY:=false}
: ${RC_FILE="$DIR/custom.rc"}


OPTS=`getopt -o urcsx --long ui,rest,community,smoke,vertx -- $@`

source "${DIR}/apimanrc.sh"
if [ -f ${RC_FILE} ]; then
    source "${RC_FILE}"
fi

eval set -- "$OPTS"

function set_ui() {
    echo "[RUNNER] --> Setting UI Variables"
    BUILD_APIMAN=true
    MOVE_APIMAN=true
    RUN_DOCKER=true
    GET_CHROME_DRIVER=true
    FIX_VERSION=true
    CONFIGURE_ES=true
    API_CATALOG=true
    BUILD_PLUGINS=true
    UI_TESTS=true
}

function set_rest() {
    echo "[RUNNER] --> Enabling REST test build"
    MOVE_APIMAN=true
    BUILD_APIMAN=true
    RUN_DOCKER=true
    API_CATALOG=true
    CONFIGURE_ES=true
    BUILD_PLUGINS=true
    FIX_VERSION=true
    REST_TESTS=true
}

function set_vertx() {
    echo "[RUNNER] --> Enabling Vert.X test build"
    RUN_VERTX=true
    MANAGER_ONLY=true
}

function set_community() {
    echo "[RUNNER] --> Enabling Community test build"
    BUILD_APIMAN=true
    MOVE_APIMAN=true
    COMMUNITY_TESTS=true
}

function set_smoke() {
    echo "[RUNNER] --> Enabling Smoke test build"
    BUILD_APIMAN=true
    MOVE_APIMAN=true
    SMOKE_TESTS=true
    RUN_DOCKER=true
    API_CATALOG=true
    CONFIGURE_ES=true
    FIX_VERSION=true
}

while true; do
    case "$1" in
    -u | --ui) set_ui; shift ;;
    -r | --rest) set_rest; shift ;;
    -c | --community) set_community; shift;;
    -s | --smoke) set_smoke; shift;;
    -x | --vertx) set_vertx; shift;;
    *) break ;;
    esac
done

echo "[RUNNER] --> Changing directory to WORKSPACE: ${WORKSPACE}"
cd ${WORKSPACE}

#
# Build & Install environment
#
if ${BUILD_APIMAN}; then
    ${MAVEN_SCRIPTS}/build_apiman.sh
fi

if ${BUILD_PLUGINS}; then
    ${MAVEN_SCRIPTS}/build_plugins.sh
fi

# Set api catalog properties
if ${API_CATALOG}; then
    ${TEST_SOURCES}/tools/api-catalog/set_api_catalog.sh
fi


#
# Run environment
#
# Run docker environment
if ${RUN_DOCKER}; then
    ${TEST_SOURCES}/tools/scripts/start_docker.sh
fi

# Run Apiman on WF
if ${RUN_APIMAN}; then
    ${TEST_SOURCES}/tools/scripts/start_wildfly.sh
fi

# Run Vert.X gateway
if ${RUN_VERTX}; then
    ${TEST_SOURCES}/tools/scripts/start_vertx.sh
fi


#
# Prepare tests
#
# Install chrome driver
if ${GET_CHROME_DRIVER}; then
    ${TEST_SOURCES}/tools/chrome_driver.sh   ${CHROME_DRIVER}
fi

# fix apiman version property
if ${FIX_VERSION}; then
    ${TEST_SOURCES}/tools/fix_apiman_version.sh
fi


#
# Run Tests
#
if ${COMMUNITY_TESTS}; then
    ${MAVEN_SCRIPTS}/run_community_tests.sh
fi

if ${REST_TESTS}; then
    ${MAVEN_SCRIPTS}/run_rest_tests.sh
fi

if ${UI_TESTS}; then
    ${MAVEN_SCRIPTS}/run_ui_tests.sh
fi

if ${SMOKE_TESTS}; then
    ${MAVEN_SCRIPTS}/run_smoke_tests.sh
fi


#
# Cleanup
#
if ${RUN_DOCKER} && ${CLEANUP}; then
    echo "[RUNNER] --> Shutting down docker containers"
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml stop
    docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml rm -f
fi

if ${RUN_APIMAN} && ${CLEANUP}; then
    echo "[RUNNER] --> Shutting down Apiman"
    kill -TERM ${MANAGER_PID}
fi

if ${RUN_VERTX} && ${CLEANUP}; then
    echo "[RUNNER] --> Shutting down Apiman Vert.X gateway"
    kill -TERM ${GW_PID}
fi