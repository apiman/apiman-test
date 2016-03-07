#!/usr/bin/env bash

# start wildfly
$APIMAN_HOME/bin/standalone.sh -c standalone-apiman.xml > server_stdout.log &

# dummy start wait start
sleep 2m

#wget $ECHO_ENDPOINT