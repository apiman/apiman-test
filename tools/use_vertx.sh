#!/usr/bin/env bash

ENDPOINT="http://localhost:8080/apiman/gateways"
CONTENT_TYPE="Content-Type:application/json"
CREDENTIALS="admin:admin123!"

curl -X DELETE -u ${CREDENTIALS} "${ENDPOINT}/TheGateway"
curl -X POST -u ${CREDENTIALS} -H ${CONTENT_TYPE} ${ENDPOINT} -d '{
 "name" : "vertx",
 "description" : "Vert.X gateway",
 "type": "REST",
 "configuration": "{\"endpoint\":\"http://localhost:8081/\",\"username\":\"admin\",\"password\":\"admin\"}"
}'

