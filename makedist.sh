#!/bin/sh

javac src/*.java -d bin
jar -cfm shinkoban.jar src/manifest bin
javadoc src/*.java -d doc
tar --exclude=distr --exclude=*~ -czf distr/shinkoban.tar.gz *
