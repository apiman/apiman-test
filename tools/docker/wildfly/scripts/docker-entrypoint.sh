#!/usr/bin/env bash

JBOSS_CLI=${JBOSS_HOME}/bin/jboss-cli.sh

function wait_for_server() {
    # TODO: pattern match the message
    while ! grep 'WildFly 8.2.0.Final "Tweek" started' ${JBOSS_HOME}/standalone/log/server.log; do
        sleep 5
    done
    echo ">>>>> SERVER STARTED <<<<<"
}

if [ -z ${WILDFLY_SET+x} ]
then
    ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0 -c ${JBOSS_CONFIG_FILE} &
    WILDFLY_PID=$!
    echo "export WILDFLY_SET=true" >> ~/.profile
    
    wait_for_server
    echo 'Setting up Wildfly'
   
    for FILE in `ls $INIT_DIRECTORY/*.cli`
    do
        echo "Running cli script: $FILE"
        ${JBOSS_CLI} -c --file=${FILE}
    done

    for FILE in `ls $INIT_DIRECTORY/*.sh`
    do
        echo "Running shell script: $FILE"
        . ${FILE}
    done

else
    ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0 -c ${JBOSS_CONFIG_FILE} &
    WILDFLY_PID=$!
fi

trap "kill -TERM ${WILDFLY_PID} && wait ${WILDFLY_PID}" TERM INT

wait ${WILDFLY_PID}


if [ ${KEEP_ALIVE:+x} ]
then
    echo "[KEEP_ALIVE=${KEEP_ALIVE}]"
    echo "Keeping container running"
    tail -f /dev/null
fi
