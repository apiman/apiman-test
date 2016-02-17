#!/usr/bin/env bash

#=====================================================================
#
#   Usage: ./use_elastic_manager.sh </path/to/apiman/home>
#
#   Switch API manager storage from JPA to ElasticSearch
#=====================================================================

if [ ! -z "$1" ]; then
    APIMAN_HOME=$1
fi

: ${APIMAN_HOME="$PWD/apiman-$APIMAN_VERSION"}
: ${APIMAN_CFG="${APIMAN_HOME}/standalone/configuration/apiman.properties"}

# remove embedded elastic
rm ${APIMAN_HOME}/standalone/deployments/apiman-es.war

# client should listen on ES default port 9200
sed -i "s/apiman.es.port=19200/apiman.es.port=9200/g" ${APIMAN_CFG}

# switch storage to ES
sed -i "s/apiman-manager.storage.type=jpa/apiman-manager.storage.type=es/g" ${APIMAN_CFG}
# remove coments from ES configuration
sed -i '/apiman-manager.storage.es/s/#//g' ${APIMAN_CFG}


