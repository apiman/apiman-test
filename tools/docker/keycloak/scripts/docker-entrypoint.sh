#!/usr/bin/env bash


function wait_for_server() {
    while ! grep -e 'Keycloak\s.*\sstarted\s.*in\s[0-9]*ms' ${JBOSS_HOME}/standalone/log/server.log; do
        sleep 5
    done
    echo ">>>>> SERVER STARTED <<<<<"
}

# builds string representation of array of redirect uris and prints it in variable format
function build_array() {
    host=$1
    port=$2
    ssl_port=$3
    url=$4
    var_name=$5
    echo "export ${var_name}=\"[\\\"http://${host}:${port}/${url}\\\", \\\"https://${host}:${ssl_port}/${url}\\\"]\""
}
# make sure we get fresh logfile logfile
rm ${JBOSS_HOME}/standalone/log/server.log 2> /dev/null

# configure and start server
: ${CONFIGURED_FILE:="${JBOSS_HOME}/configured"}

if [ ! -f $CONFIGURED_FILE ]
then
    # Create variables containing arrays for realm file
    eval `build_array $MANAGER_HOST $MANAGER_PORT $MANAGER_HTTPS_PORT "apiman/*" "APIMAN_URLS"`
    eval `build_array $MANAGER_HOST $MANAGER_PORT $MANAGER_HTTPS_PORT "apimanui/*" "APIMANUI_URLS"`
    eval `build_array $GATEWAY_HOST $GATEWAY_PORT $GATEWAY_HTTPS_PORT "apiman-gateway-api/*" "APIMAN_GATEWAY_URLS"`

    # create realm file substituting variables in template file
    envsubst '$APIMAN_URLS:$APIMANUI_URLS:$APIMAN_GATEWAY_URLS' < ${REALM_FILE_TMPL} > ${REALM_FILE}

    ${JBOSS_HOME}/bin/add-user-keycloak.sh -r master -u ${KC_USER_NAME} -p ${KC_USER_PASSWORD}
    ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0      \
        -Dkeycloak.migration.action=import          \
        -Dkeycloak.migration.provider=singleFile    \
        -Dkeycloak.migration.file=${REALM_FILE}     &

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
