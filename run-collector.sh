#!/bin/bash

docker compose -f collector-for-aws.yaml up
docker compose -f collector-for-aws.yaml down