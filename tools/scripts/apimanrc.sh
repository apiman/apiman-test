: ${DEFAULT_INTERFACE:=eth0}

# Location of sources
export TEST_SOURCES=${WORKSPACE}/apiman-test
export APIMAN_SOURCES=${WORKSPACE}/apiman
export PLUGIN_SOURCES=${WORKSPACE}/apiman-plugins

export WILDFLY_VERSION=10.0.0.Final
export APIMAN_CONFIG_FILE=standalone-apiman.xml
export APIMAN_HOME=${WORKSPACE}/apiman-wf-server
export APIMAN_KEYSTORE=${APIMAN_HOME}/standalone/configuration/apiman.jks



# WebDriver UI tests
#
export CHROME_DRIVER=$WORKSPACE/chromedriver
export MACHINE_IP=`/usr/sbin/ip addr show ${DEFAULT_INTERFACE} | grep -o "\([0-9]\{1,3\}\.\)\{3\}[0-9]\{1,3\}" | head -n 1`
export BROWSER=firefox

export API_ENDPOINT=http://localhost:8080/apiman
export API_USERNAME=admin
export API_PASSWORD=admin123!

export GATEWAY_ENDPOINT=http://localhost:8080/apiman-gateway
export GATEWAY_CONFIG_ENDPOINT=http://localhost:8080/apiman-gateway-api
export GATEWAY_CONFIG_USERNAME=admin
export GATEWAY_CONFIG_PASSWORD=admin123!

export ECHO_ENDPOINT=http://localhost:8080/services/echo

export NO_SERVER=true
