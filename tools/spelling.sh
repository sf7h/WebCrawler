#!/bin/bash

sh ./install-misspell.sh &> /dev/null
wait
#mv ./bin/misspell /usr/local/bin
git ls-files | xargs ./bin/misspell
rm -rf ./bin
