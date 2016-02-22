#!/usr/bin/env bash

PERFCAKE_WORKSPACE=${WORKSPACE}/apiman-qe-tests/tools/perf:/opt/perfcake-workspace
ECHO_ENDPOINT="http://${HELPER_IP}:8080/apiman-echo"

# run helper and server environment
docker -H ${HELPER_IP}:${DOCKER_REMOTE_PORT} run -d -p 8080:8080 -p 8443:8443 $HELPER_IMAGE
docker -H ${SERVER_IP}:${DOCKER_REMOTE_PORT} run -d -p 8080:8080 -p 8443:8443 -e "APIMAN_SERVICE_ENDPOINT_URL=${ECHO_ENDPOINT}" $SERVER_IMAGE
sleep 1m

# run perfcake tests
docker run -v ${PERFCAKE_WORKSPACE} ${CLIENT_IMAGE} -s "apiman-https-simple.xml" -Dtest.url="https://${HELPER_IP}:8443/apiman-echo" -Dtest.group=raw || true
docker run -v ${PERFCAKE_WORKSPACE} ${CLIENT_IMAGE} -s "apiman-https-simple.xml" -Dtest.url="https://${SERVER_IP}:8443/apiman-gateway/qe/echo/1.0" -Dtest.group=gw || true

zip -r results.zip ${WORKSPACE}/apiman-qe-tests/tools/perf

