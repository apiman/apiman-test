#!/usr/bin/env bash

DIR=$(dirname ${BASH_SOURCE[0]})

source $DIR/installrc.sh

<<<<<<< HEAD
bash -x $DIR/../tests_runner.sh > $WORKSPACE/out.log 2>&1 || (cat $WORKSPACE/out.log && false)
=======
bash $DIR/../tests_runner.sh > $WORKSPACE/out.log 2>&1 || (cat $WORKSPACE/out.log && false)
>>>>>>> master
