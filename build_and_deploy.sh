#!/usr/bin/env bash

set -e
set -x

echo "Stopping containers...."

docker-compose down

echo "Building and Packaging"
mvn clean install

mvn clean package

echo "Building docker image"
docker-compose build kafka-connect

echo "Starting....."
docker-compose up -d

