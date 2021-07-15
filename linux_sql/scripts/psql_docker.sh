#!/bin/bash

cmd=$1
db_username=$2
db_password=$3

#Start docker if it is not already running
systemctl status docker || systemctl start docker

#Check command
case $cmd in

  #Create a container
  create)
    word_count=$(docker container ls -a -f name=jrvs-psql | wc -l)

    #If word_count equals 2, then the container has already been created
    if [ "$word_count" -eq "2" ]; then
      echo "The container has already been created"
      exit 1
    fi

    #If 3 arguments haven't been given, then user and/or password hasn't been supplied
    if [ $# -ne 3 ]; then
      echo "A username and password must be supplied"
      exit 1
    fi

    #Create volume
    docker volume create pgdata

    #Create the container
    docker run --name jrvs-psql -e POSTGRES_PASSWORD="${db_password}" -e POSTGRES_USER="${db_username}" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
    exit $?

    created=$(docker container ls -a -f name=jrvs-psql | wc -l)

    #If created word count doesn't equal 2, then the container did not get created
    if [ "$created" -ne "2" ]; then
      echo "There was an error and the container has not been created."
      exit 1
    fi

    ;;

  #Start the container
  start)
    docker container start jrvs-psql
    exit $?
    ;;

  #Stop the container
  stop)
    docker container stop jrvs-psql
    exit $?
    ;;

  #Invalid argument given or not provided
  *)
    echo "You must provide a valid command from: create | start | stop"
    exit 1
    ;;

esac

exit 0