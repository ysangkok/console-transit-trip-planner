#!/usr/bin/env bash
set -x
set -o nounset
set -o pipefail
set -e
cd cmdlet
gradle runScript "-PscriptArgs=$1,$2,$3"
