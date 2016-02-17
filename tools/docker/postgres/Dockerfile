FROM postgres:9.3
MAINTAINER Jakub Cechacek <jcechace@redhat.com>

ENV POSTGRES_USER apimanqe
ENV POSTGRES_PASSWORD apimanqe

ADD db.sql /docker-entrypoint-initdb.d/db.sql

