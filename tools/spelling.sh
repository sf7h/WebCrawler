#!/bin/bash

sh ./install-misspell.sh &> /dev/null

#mv ./bin/misspell /usr/local/bin
git ls-files | xargs ./bin/misspell
rm -rf ./bin
