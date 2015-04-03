#!/bin/bash

if [ "$1" != "" ]; then
	java -cp java_cup-runtime.jar:. parser "$1"
else
	echo "Usage: ./run_parser.sh %PATH_TO_TESTFILE"
fi
