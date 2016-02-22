#!/usr/bin/env bash


function wait_for_server() {
    while ! grep -e 'Keycloak\s.*\sstarted\s.*in\s[0-9]*ms' ${JBOSS_HOME}/standalone/log/server.log; do
        sleep 5
    done
    echo ">>>>> SERVER STARTED <<<<<"
}

# make sure we get fresh logfile logfile
rm ${JBOSS_HOME}/standalone/log/server.log 2> /dev/null

# configure and start server
: ${CONFIGURED_FILE:="${JBOSS_HOME}/configured"}

if [ ! -f $CONFIGURED_FILE ]
then
    ${JBOSS_HOME}/bin/add-user.sh -r master -u ${KC_USER_NAME} -p ${KC_USER_PASSWORD}
    ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0      \
        -Dkeycloak.migration.action=import          \
        -Dkeycloak.migration.provider=dir           \
        -Dkeycloak.migration.dir=${REALM_DIR}       &

    WILDFLY_PID=$!
    touch ${CONFIGURED_FILE}

else
    ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0 &
    WILDFLY_PID=$!
fi

#wait for server to start
wait_for_server

trap "kill -TERM ${WILDFLY_PID} && wait ${WILDFLY_PID}" TERM INT

wait ${WILDFLY_PID}
