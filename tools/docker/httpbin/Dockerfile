FROM jboss/base
MAINTAINER Peter Stanko <pstanko@redhat.com>

USER root

COPY scripts/* /opt/scripts/

RUN yum -y update
RUN yum -y install python
RUN yum -y install git

# Install pip

RUN curl "https://bootstrap.pypa.io/get-pip.py" -o "get-pip.py"
RUN python "./get-pip.py"

RUN pip install gunicorn

EXPOSE 5000

CMD ["sh", "/opt/scripts/docker-entrypoint.sh"]
