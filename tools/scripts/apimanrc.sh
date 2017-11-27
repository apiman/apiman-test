# Location of sources
#
export TEST_SOURCES=${WORKSPACE}/apiman-integration-tests
export APIMAN_SOURCES=${WORKSPACE}/apiman
export PLUGIN_SOURCES=${WORKSPACE}/apiman-plugins
export WILDFLY_VERSION=10.1.0.Final

# Install directories
#
export APIMAN_HOME=${WORKSPACE}/apiman-wf-server
export APIMAN_HOME_GW=${WORKSPACE}/apiman-gw-vertx

# Config files
#
export APIMAN_CONFIG_FILE=standalone-apiman.xml
export APIMAN_GW_CONFIG=${TEST_SOURCES}/apiman-it-commons/src/main/resources/io/apiman/test/integration/vertx/config-es-basic-auth.json
export APIMAN_KEYSTORE=${APIMAN_HOME}/standalone/configuration/apiman.jks


# WebDriver UI tests
#
export CHROME_DRIVER=$WORKSPACE/chromedriver
export BROWSER=firefox


# Community tests
#
export API_ENDPOINT=http://localhost:8080/apiman
export API_USERNAME=admin
export API_PASSWORD=admin123!

export GATEWAY_ENDPOINT=http://localhost:8080/apiman-gateway
export GATEWAY_CONFIG_ENDPOINT=http://localhost:8080/apiman-gateway-api
export GATEWAY_CONFIG_USERNAME=admin
export GATEWAY_CONFIG_PASSWORD=admin123!

export ECHO_ENDPOINT=http://localhost:8180/services/echo

export NO_SERVER=true
