#!/usr/bin/env bash

gulp prod-build
mv node_modules node_modules_tmp
npm ci --production
docker build -t backoffice_frontend .
docker save backoffice_frontend > backoffice_frontend
rm node_modules -rf
mv node_modules_tmp node_modules