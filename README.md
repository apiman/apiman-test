# Apiman Integration Test
Integration tests for Apiman live in this repository.

# Required tools
At minimum these tools must be installed on your system in order to run this test-suite:
- JDK 8
- Apache Maven 3.2 or higher
- Docker 1.8 or higher
- Docker Compose 1.4.2 or higher
- Mozila Firefox and/or Google Chrome

Additionally, pre-configured docker images are available in Docker Hub for:
- LDAP server
- JBoss KeyCloak server
- PostgreSQL database
- ElasticSearch (optional)


# Test execution
This suite contains two testing profiles. However, prior to running any tests the above mentioned tools should be running. Easiest way to do so is using provided docker-compose file.

```bash
docker-compose -f tools/docker/docker-compose.yml up
```
Alternatively, you can configure and run these tools however you see fit -- in that case, additional configuration will be required (see the next section).


### REST tests
```bash
mvn clean install -Prest-tests -Dapiman.test.proxy.address=${proxyAddress}
```

Where ${proxyAddress} is your current IP address (anything different from ```localhost``` will do).

### UI tests
```bash
mvn clean install -Pui-tests -Pbrowser=${browser}
```
Where ```${browser}``` is either ```firefox``` or ```chrome```


# Suite configuration
Various system properties can be used to configure the suit's execution. Default values are pre-configured in [default.properties](/apiman-it-commons/src/resources/default.properties).

Custom configuration can be provided. Furthermore property values specified in configuration file can be overriden directly on command line.

```bash
mvn clean install -Prest-tests  -Dsuite.properties="custom.properties" -Dapiman.host="10.10.10.42"
```


# Suite structure
The testsuite is structured into several (maven) modules. For detailed information see the ```README.md``` files in each module. The following is a brief overview:

### apiman-it-commons
Common base containing shared resources.

### apiman-it-coverage
This module provides code-coverage analysis of Apiman's Gateway and Manager components. As of yet, this module requires tests to be executed against Apiman deployed to a WildFly server. 

### apiman-it-deployments
This modules contains various utility services used by the following modules in tests. Artifacts provided by this test suite needs to be built and deployed prior to running any tests.

This command can be used for WildFly deployment
```
mvn clean install wildfly:deploy.
```

By default these services are expected to run on ```localhost:8080```. However the configuration can be changed using these properties.

```properties
apiman.test.deploy.host
apiman.test.deploy.port
apiman.test.deploy.protocol
```

### apiman-it-rest
This module contains integration tests for Management API and Gateway.

### apiman-it-ui
This module contains integration tests for the Manager UI. The default browser for execution of these tests is Firefox.
