#!/usr/bin/env bash

#=====================================================================
#
#   Usage: ./chrome_driver.sh </path/to/driver/bin>
#
#   Install latest chrome driver as "/path/to/driver/bin"
#   $CHROME_DRIVER variable is used as default binary name
#=====================================================================

if [ ! -z "$1" ]; then
    CHROME_DRIVER=$1
fi

: ${WORK_DIR="/tmp"}
: ${CHROME_DRIVER="$PWD/chromedriver"}


if [[ `uname` == 'Darwin' ]]; then
   ZIP="chromedriver_mac32.zip"
elif [[ `uname` == 'Linux' ]]; then
   ZIP="chromedriver_linux64.zip"
fi

BASE_URL="http://chromedriver.storage.googleapis.com"
: ${CHROMEDRIVER_VERSION=`wget -O- -q ${BASE_URL}/LATEST_RELEASE`}
DOWNLOAD_URL=${BASE_URL}/${CHROMEDRIVER_VERSION}/${ZIP}

# Download into /tmp
wget -O $WORK_DIR/${ZIP} -q ${DOWNLOAD_URL}

# Extract and install driver binar
BINARY_NAME=`unzip -qql $WORK_DIR/${ZIP}  | head -n1 | tr -s ' ' | cut -d' ' -f5-`
unzip -qo $WORK_DIR/${ZIP} -d $WORK_DIR && mv $WORK_DIR/${BINARY_NAME} ${CHROME_DRIVER}
