FROM jboss/base
MAINTAINER Miroslav Jaros <mjaros@redhat.com>

USER root
ADD apacheds-2.0.0-M20-x86_64.rpm  /tmp/apacheds.rpm
COPY wrapper/* /opt/wrapper/
ADD add_content.ldif /opt/add_content.ldif
 
RUN	yum update -y \
    && yum install -y java-1.7.0-openjdk-devel gcc make openldap-clients \
    && yum localinstall -y /tmp/apacheds.rpm \
    && sh /opt/wrapper/install.sh

COPY scripts/* /opt/scripts/
EXPOSE 10389

CMD ["sh", "/opt/scripts/docker-entrypoint.sh"]
