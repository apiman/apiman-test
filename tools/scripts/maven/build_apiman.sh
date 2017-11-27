#!/bin/bash

echo "[RUNNER] --> Building Apiman distribution"
mvn -f ${APIMAN_SOURCES}/pom.xml install -Ptools -Pinstall-all-wildfly10 -DskipTests

export APIMAN_VERSION=$( grep -oP -m 1 '<version>\K[^<]+' ${APIMAN_SOURCES}/pom.xml )

if ${MOVE_APIMAN}; then
    echo "[RUNNER] --> Moving Apiman distribution to proper location"
    cp -r ${APIMAN_SOURCES}/tools/server-all/target/wildfly-*.Final ${APIMAN_HOME}
fi

if ${DELETE_ELASTIC}; then
    echo "[RUNNER] --> Removing embedded ES deployment"
    rm ${APIMAN_HOME}/standalone/deployments/apiman-es.*
fi

if ${RUN_VERTX}; then
    echo "[RUNNER] --> Moving Apiman gateway distribution to proper location"
    unzip -q ${APIMAN_SOURCES}/distro/vertx/target/apiman-distro-vertx-*-SNAPSHOT.zip -d ${WORKSPACE}
    mv ${WORKSPACE}/apiman-distro-vertx-*-SNAPSHOT ${APIMAN_HOME_GW}
    rm ${APIMAN_HOME}/standalone/deployments/apiman-gateway*
fi


