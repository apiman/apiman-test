FROM jboss/keycloak:1.9.0.Final

ENV JAVA_OPTS "-Djava.security.egd=file:///dev/urandom"
ENV REALM_DIR $JBOSS_HOME/realms
ENV KC_USER_NAME "admin"
ENV KC_USER_PASSWORD "admin123!"

# Add wrapping docker entrypoint
ADD scripts/docker-entrypoint.sh ${JBOSS_HOME}/bin/docker-entrypoint.sh

# Add realm definition file
ADD realms $REALM_DIR

ENTRYPOINT ["sh", "/opt/jboss/keycloak/bin/docker-entrypoint.sh"]
