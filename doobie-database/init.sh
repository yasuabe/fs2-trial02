#! /bin/ash

apk add --no-cache curl
curl -O https://raw.githubusercontent.com/tpolecat/doobie/series/0.7.x/world.sql
psql -c 'create user postgres createdb'
psql -c 'create database world;' -U postgres
psql -c '\i world.sql' -d world -U postgres

