#!/usr/bin/env bash
: ${SLEEP_PERIOD:=30}
: ${CONFIGURED_FILE:="/opt/configured"}
if [ ! -f $CONFIGURED_FILE ]
then
	echo 'Setting up new image'
	/usr/bin/wrapper &
	: ${WRAPPER:=$!}
	echo "Sleeping for $SLEEP_PERIOD seconds"
	sleep $SLEEP_PERIOD
	echo 'Wrapper is running'
	ldapadd -a -x -w secret -D 'uid=admin,ou=system' -h 127.0.0.1 -p 10389 -f /opt/add_content.ldif
	touch ${CONFIGURED_FILE}
	echo 'Wrapper is configured'

else
	echo 'Starting wrapper'
	/usr/bin/wrapper &
	: ${WRAPPER:=$!}
fi

trap "kill -TERM $WRAPPER && wait $WRAPPER" TERM INT

wait $WRAPPER

