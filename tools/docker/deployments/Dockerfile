FROM apimanqe/wildfly:latest

ENV WAR apiman-it-deployments-1.1.0-SNAPSHOT.war

ADD ./$WAR $JBOSS_HOME/standalone/deployments
