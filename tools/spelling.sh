#!/bin/bash

sh ./install-misspell.sh &>/dev/null
wait

FILES= git ls-files | xargs ./bin/misspell -error
rm -rf ./bin
