FROM jboss/base
MAINTAINER Miroslav Jaros <mjaros@redhat.com>

USER root

RUN yum update -y \
    && yum install -y epel-release \
    && yum install -y tinyproxy

EXPOSE 8888
ENTRYPOINT ["/usr/sbin/tinyproxy", "-d"]
