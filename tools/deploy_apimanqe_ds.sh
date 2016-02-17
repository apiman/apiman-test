#!/usr/bin/env bash

#=======================================================================
#
#   Usage: ./deploy_apimanqe_ds.sh </path/to/apiman/home>
#
#   This script is used to deploy apimanqe datasource xml into wildfly
#=======================================================================


if [ ! -z "$1" ]; then
    APIMAN_HOME=$1
fi

# ensure variables are set
: ${APIMAN_HOME="$PWD/apiman-${APIMAN_VERSION}"}
: ${TEST_SOURCES=$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )}

set -a
: ${POSTGRES_HOST="127.0.0.1"}
: ${POSTGRES_PORT="5432"}
: ${POSTGRES_DATABASE="apimanqe"}
: ${POSTGRES_USER="apimanqe"}
: ${POSTGRES_PASSWORD="apimanqe"}
: ${POSTGRES_DRIVER="postgresql-9.3-1102.jdbc41.jar"}
set +a

# deploy jdbc driver
cp "${TEST_SOURCES}/tools/docker/postgres/${POSTGRES_DRIVER}" "${APIMAN_HOME}/standalone/deployments"

# deploy datasource XML
TARGET_FILE="${APIMAN_HOME}/standalone/deployments/apimanqe-ds.xml"


envsubst < "${TEST_SOURCES}/tools/docker/postgres/ds.tpl.xml" > "${TARGET_FILE}"
