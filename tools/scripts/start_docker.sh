#!/usr/bin/env bash

echo "[RUNNER] --> Running docker tools"
docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml pull
docker-compose -f ${TEST_SOURCES}/tools/docker/docker-compose.yml up -d