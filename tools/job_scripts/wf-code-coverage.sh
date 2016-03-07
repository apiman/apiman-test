#!/usr/bin/env bash
# install apiman
${WORKSPACE}/apiman-qe-tests/tools/install_wildfly.sh ${APIMAN_HOME}

# creati jacoco home
mkdir -p ${JACOCO_HOME}

## Retrieve latest coverage reports
JOB="https://api-qe-jenkins.rhev-ci-vms.eng.rdu2.redhat.com/job"

# Smoke comunity data
wget -O "smoke.zip" -q --no-check-certificate "$JOB/apiman-community-tests-smoke-release/jdk=java-1.8,label=rhel7_x86_64/lastSuccessfulBuild/artifact/coverage/data/*zip*/data.zip"
unzip -qo "smoke.zip"

# REST coverage data
wget -O "rest.zip" -q --no-check-certificate "$JOB/apiman-rest-tests-release/jdk=java-1.8,label=rhel7/lastSuccessfulBuild/artifact/coverage/*zip*/coverage.zip"
unzip -qo "rest.zip"

# REST ES coverage data
wget -O "rest-es.zip" -q --no-check-certificate "$JOB/apiman-rest-tests-es-release/jdk=java-1.8,label=rhel7/lastSuccessfulBuild/artifact/coverage/*zip*/coverage.zip"
unzip -qo "rest-es.zip"

# UI coverage data
wget -O "ui.zip" -q --no-check-certificate "$JOB/apiman-ui-rhel-release/BROWSER=firefox,jdk=java-1.8,label_exp=rhel7/lastSuccessfulBuild/artifact/coverage/*zip*/coverage.zip"
unzip -qo "ui.zip"
