#!/usr/bin/env bash

IMAGE_LOCAL=apimanqe/ldap

docker build -t ${IMAGE_LOCAL} $( dirname "${BASH_SOURCE[0]}" )
docker push ${IMAGE_LOCAL}
