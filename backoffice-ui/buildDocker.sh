#!/usr/bin/env bash

gulp prod-build
mv node_modules node_modules_tmp
npm ci --production
docker build -t seyranmamutov/backoffice_frontend:latest .
rm node_modules -rf
mv node_modules_tmp node_modules