FROM postgres:12-alpine

COPY init.sh /docker-entrypoint-initdb.d/

ENTRYPOINT ["docker-entrypoint.sh"]