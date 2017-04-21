#!/bin/bash

function die() {
	echo "$@" >&2
	exit 1
}

function check() {
	hash "$1" >/dev/null 2>&1 || die "command '$1' not found"
}

export IMAGE_NAME=lenaskarlat/ase/sensor-app
export CONTAINER_NAME=lenaskarlat_ase_sensorapp

echo "Building docker image $IMAGE_NAME..."
docker build -t "$IMAGE_NAME" . || die "docker build failed"

