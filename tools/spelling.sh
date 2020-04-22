#!/bin/bash

sh ./install-misspell.sh &> /dev/null
wait
#mv ./bin/misspell /usr/local/bin
FILES= git ls-files | xargs ./bin/misspell
rm -rf ./bin
if ${FILES}; then
  echo "ERROR: you have misspelled files above, please tun locally the script in 'tools/spelling correct'"
  exit 1
fi

