#!/usr/bin/env bash

#==============================================================================
#
#   Usage: ./set_api_catalog.sh </path/to/apiman/home>
#
#   Set Apiman api-catalog properties to json catalog located on localhost.
#==============================================================================


if [ ! -z "$1" ]; then
    APIMAN_HOME=$1
fi

# ensure variables are set
: ${APIMAN_HOME="$PWD/apiman-${APIMAN_VERSION}"}
: ${TEST_SOURCES=$( cd "$( dirname "${BASH_SOURCE[0]}" )/.." && pwd )}

: ${APIMAN_PROPERTIES="$APIMAN_HOME/standalone/configuration/apiman.properties"}
: ${CATALOG_TYPE="io.apiman.manager.api.core.catalog.JsonApiCatalog"}
: ${CATALOG_URL="file://$(dirname `realpath $0`)/api-catalog.json"}

# Set catalog type
sed -i "s|apiman-manager.api-catalog.type=.*$|apiman-manager.api-catalog.type=${CATALOG_TYPE}|" ${APIMAN_PROPERTIES}

# Set catalog url
sed -i "s|apiman-manager.api-catalog.catalog-url=.*$|apiman-manager.api-catalog.catalog-url=${CATALOG_URL}|" ${APIMAN_PROPERTIES}
