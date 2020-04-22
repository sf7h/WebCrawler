#!/bin/bash

sh ./install-misspell.sh &> /dev/null
wait

FILES= git ls-files | xargs ./bin/misspell
rm -rf ./bin
if test -z "$FILES" ; then
  echo "ERROR: you have misspelled files above, please run locally the script in 'tools/spelling correct'"
  exit 1
fi

