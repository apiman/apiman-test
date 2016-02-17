#!/usr/bin/env bash


function wait_for_server() {
    while ! grep 'WildFly 8.2.0.Final "Tweek" started' ${JBOSS_HOME}/standalone/log/server.log; do
        sleep 5
    done
    echo ">>>>> SERVER STARTED <<<<<"
}

if [ -z ${REALM_SET+x} ]
then
    ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0      \
        -Dkeycloak.migration.action=import          \
        -Dkeycloak.migration.provider=singleFile    \
        -Dkeycloak.migration.file=${REALM_FILE}     &

    WILDFLY_PID=$!
    echo "export REALM_SET=true" >> ~/.profile

else
    ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0 &
    WILDFLY_PID=$!
fi

trap "kill -TERM ${WILDFLY_PID} && wait ${WILDFLY_PID}" TERM INT

wait ${WILDFLY_PID}
