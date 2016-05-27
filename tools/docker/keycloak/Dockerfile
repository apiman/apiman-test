FROM jboss/keycloak:1.9.5.Final

ENV JAVA_OPTS "-Djava.security.egd=file:///dev/urandom"
ENV REALM_DIR $JBOSS_HOME/realms
ENV REALM_FILE $REALM_DIR/apiman-realm.json
ENV REALM_FILE_TMPL $REALM_DIR/apiman-realm.json.tmpl
ENV KC_USER_NAME "admin"
ENV KC_USER_PASSWORD "admin123!"
ENV MANAGER_HOST "localhost"
ENV GATEWAY_HOST "localhost"
ENV MANAGER_PORT "8080"
ENV GATEWAY_PORT "8080"
ENV MANAGER_HTTPS_PORT "8443"
ENV GATEWAY_HTTPS_PORT "8443"

# Add wrapping docker entrypoint
ADD scripts/docker-entrypoint.sh ${JBOSS_HOME}/bin/docker-entrypoint.sh

# Add realm definition file
ADD realms $REALM_DIR

# change owner of newely created directory and install gettext package for envsubst
USER root
RUN chown -R jboss:jboss ${REALM_DIR} &&\
    yum update -y &&\
    yum install -y gettext
USER jboss

ENTRYPOINT ["sh", "/opt/jboss/keycloak/bin/docker-entrypoint.sh"]
