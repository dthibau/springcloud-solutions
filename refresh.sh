#!/bin/sh

# Refreshing configuration properties
curl -XPOST -H "Content-Type: application/json"  http://localhost:1111/actuator/refresh

curl -XPOST -H "Content-Type: application/json"  http://localhost:3333/actuator/refresh

