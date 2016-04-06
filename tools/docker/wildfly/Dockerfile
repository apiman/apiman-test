FROM jboss/wildfly:10.0.0.Final
MAINTAINER Jakub Cechacek <jcechace@redhat.com>

ENV INIT_DIRECTORY  $JBOSS_HOME/init.d
ENV JBOSS_CONFIG_FILE "standalone.xml"

# attach volume with keystores
VOLUME ["/opt/keys"]

# Add wrapping docker entrypoint
ADD scripts/docker-entrypoint.sh ${JBOSS_HOME}/bin/docker-entrypoint.sh

# Fix the access rights
# This requires temporary switch to root user
USER root
RUN chown jboss:jboss /opt/keys

# Use jboss user
USER jboss

# Create directory for init cli scripts
RUN mkdir $INIT_DIRECTORY

ENTRYPOINT ["sh", "/opt/jboss/wildfly/bin/docker-entrypoint.sh"]
