#!/bin/sh

javac src/*.java -d bin
jar -cfm shinkoban.jar src/manifest bin
