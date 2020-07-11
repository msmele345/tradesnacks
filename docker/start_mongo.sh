#!/bin/bash

docker container prune -f
docker-compose up -d
echo "Started MongoDb Container"