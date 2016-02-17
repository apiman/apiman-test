#!/usr/bin/env bash

#=====================================================================
#
#   Usage: ./install_wildfly.sh </path/to/apiman/home>
#
#   Install WildFly and Apiman overlay to "/path/to/apiman/home".
#   $APIMAN_HOME variable is used as default installation directory
#=====================================================================

if [ ! -z "$1" ]; then
    APIMAN_HOME=$1
fi

# ensure variables are set
: ${WORK_DIR="/tmp"}
: ${APIMAN_VERSION="1.2.0.Beta2"}
: ${APIMAN_HOME="$PWD/apiman-${APIMAN_VERSION}"}
: ${WILDFLY_VERSION="8.2.0.Final"}
: ${TEST_SOURCES=$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )}
: ${TOOLS=$( dirname "${BASH_SOURCE[0]}" )}

BASE_URL="http://downloads.jboss.org"

WILDFLY_ZIP="wildfly-${WILDFLY_VERSION}.zip"
WILDFLY_URL="${BASE_URL}/wildfly/${WILDFLY_VERSION}/${WILDFLY_ZIP}"

APIMAN_ZIP="apiman-distro-wildfly8-${APIMAN_VERSION}-overlay.zip"
APIMAN_URL="${BASE_URL}/apiman/${APIMAN_VERSION}/${APIMAN_ZIP}"

# Download into /tmp
wget -O ${WORK_DIR}/${WILDFLY_ZIP} -q ${WILDFLY_URL}
wget -O ${WORK_DIR}/${APIMAN_ZIP}  -q ${APIMAN_URL}

# Determine WF dir name
WILDFLY_DIR=`unzip -qql ${WORK_DIR}/${WILDFLY_ZIP} | head -n1 | tr -s ' ' | cut -d' ' -f5-`

# Extract and install
unzip -qo ${WORK_DIR}/${WILDFLY_ZIP} -d ${WORK_DIR} && mv ${WORK_DIR}/${WILDFLY_DIR} ${APIMAN_HOME}
unzip -qo ${WORK_DIR}/${APIMAN_ZIP} -d ${APIMAN_HOME}

# Set api catalog properties
${TOOLS}/api-catalog/set_api_catalog.sh ${APIMAN_HOME}

# Deploy datasource
${TOOLS}/deploy_apimanqe_ds.sh ${APIMAN_HOME}

