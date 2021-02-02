#!/usr/bin/env bash

echo loading backend docker image...
docker load < docker/backoffice_backend 

echo loading frontend docker image...
docker load < docker/backoffice_frontend 

docker-compose up