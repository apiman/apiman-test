FROM jboss/keycloak:latest

ENV JAVA_OPTS "-Djava.security.egd=file:///dev/urandom"
ENV REALM_FILE $JBOSS_HOME/qerealm.json

# Add wrapping docker entrypoint
ADD scripts/docker-entrypoint.sh ${JBOSS_HOME}/bin/docker-entrypoint.sh

# Add realm definition file
ADD qerealm.json $REALM_FILE

ENTRYPOINT ["sh", "/opt/jboss/keycloak/bin/docker-entrypoint.sh"]

