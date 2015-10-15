#!/usr/bin/env bash
set -x
set -o nounset
set -o pipefail
set -e
cd cmdlet
gradle runScript '-PscriptArgs=Bahn,DA Taunusplatz,Vladivostok'
