#!/bin/bash

#Setup command line arguments
psql_host=$1
port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Validate correct number of arguments
if [ "$#" -ne 5 ]; then
  echo "Illegal number of arguments. Please provide host, port, db, user, and password."
  exit 1
fi

#Variables to help with parsing
lscpu_out=$(lscpu)
meminfo_out=$(cat /proc/meminfo)

#Parse hardware specifications and save to variables
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "^Model\sname:" | awk '{print $3, $4, $5, $6, $7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "^L2 cache:" | awk '{print $3}' | head --bytes -2 | xargs)
total_mem=$(echo "$meminfo_out"  | egrep "^MemTotal:" | awk '{print $2}' | xargs)
timestamp=$(date "+%Y-%m-%d %H:%M:%S")

#Insert statement to put hardware specs into db
insert_stmt="
INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp)
VALUES ('${hostname}', '${cpu_number}', '${cpu_architecture}', '${cpu_model}', '${cpu_mhz}', '${l2_cache}', '${total_mem}', '${timestamp}');"

#Insert into PSQL db
export PGPASSWORD="${psql_password}"
psql -h "${psql_host}" -p "${port}" -d "${db_name}" -U "${psql_user}" -c "${insert_stmt}"

exit $?
