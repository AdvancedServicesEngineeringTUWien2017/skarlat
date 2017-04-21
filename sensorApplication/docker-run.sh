#!/bin/bash

function die() {
	echo "$@" >&2
	exit 1
}

export IMAGE_NAME=lenaskarlat/sensor-app
export CONTAINER_NAME=lenaskarlat_sensorapp
export PORT=34006

docker images | grep "$IMAGE_NAME" >/dev/null 2>&1 || die "docker image '$IMAGE_NAME' not found -- run docker-build.sh first"
docker rm -f "$CONTAINER_NAME" || echo "(nothing killed)"

DOCKER_CMD="docker run"
DOCKER_CMD="$DOCKER_CMD -dit"
DOCKER_CMD="$DOCKER_CMD -p $PORT:8080"
DOCKER_CMD="$DOCKER_CMD --name=$CONTAINER_NAME"
#DOCKER_CMD="$DOCKER_CMD --rm"
DOCKER_CMD="$DOCKER_CMD $IMAGE_NAME"

echo
echo "Running docker with default settings: $DOCKER_CMD"
echo

$DOCKER_CMD

echo
echo "docker exited with status: $?"
echo


