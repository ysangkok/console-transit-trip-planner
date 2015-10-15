#!/usr/bin/env zsh
set -x
set -o nounset
set -o pipefail
set -e
if [ ! -e enabler/build/libs/enabler.jar ]; then
  if [ ! -e build.gradle ]; then
    wget https://github.com/schildbach/public-transport-enabler/archive/master.tar.gz
    tar --strip-components=1 -zxvf master.tar.gz
  fi
  gradle jar
fi
groovy -cp enabler/build/libs/enabler.jar:$(echo $HOME/.gradle/{caches/{artifacts-23/filestore/net.sf.kxml/kxml2/2.3.0/jar/**/kxml2-2.3.0.jar,jars-1/**/guava-jdk5-17.0.jar,modules-2/files-2.1/{org.json/json/20090211/**/json-20090211.jar,org.slf4j/slf4j-api/1.7.12/**/slf4j-api-1.7.12.jar,com.google.guava/guava/18.0/**/guava-18.0.jar}},wrapper/dists/gradle-2.1-all/**/gradle-2.1/lib/{logback-classic-1.0.13.jar,logback-core-1.0.13.jar}} | tr ' ' ':') opnv.groovy "$@"
