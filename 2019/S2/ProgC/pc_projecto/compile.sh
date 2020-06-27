#! /bin/bash

. $(dirname $0)/env.sh
mkdir -p classes
javac -cp $CLASSPATH -d classes $(find src -name '*.java')
