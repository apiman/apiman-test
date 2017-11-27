#!/usr/bin/env bash

remotes=()
remove_images=false

for ARG in $@
do
    case $ARG in
    -l)
    remotes+=$ARG
    ;;
    -r)
    remove_images=true
    ;;
    *)
    remotes+=$ARG
    ;;
    esac
done

if [ ${#remotes[@]} -eq 0 ]
then
    echo "USAGE: ./docker-kill.sh [-r] [REMOTES] [-l]"
    echo -e "\t-l run command on local machine"
    echo -e "\t-r delete all images on machine"
    exit 1
fi

for ARG in $remotes
do
    if [ "${ARG}" = "-l" ];
    then
        HOST=""
    else 
        HOST="-H ${ARG}"
    fi 
    CONTAINERS=$(docker ${HOST} ps -q)
    docker ${HOST} stop ${CONTAINERS} 2> /dev/null || true
    CONTAINERS=$(docker ${HOST} ps -a -q)
    docker ${HOST} rm -f ${CONTAINERS} 2> /dev/null || true

    if [ $remove_images = true ]
    then
        IMAGES=$(docker ${HOST}  images -a -q)
        docker ${HOST} rmi -f ${IMAGES} 2> /dev/null || true
    fi
done
