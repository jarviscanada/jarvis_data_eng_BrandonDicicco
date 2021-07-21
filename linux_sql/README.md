# Introduction
The purpose of this project was to develop a minimum viable product (MVP) that would automatically collect and store hardware specifications and usage data. 
The data comes from a cluster of internally connected Linux machines. The potential users include the Jarvis Linux Cluster Administration (LCA) team,
along with anyone that would need to collect this data, such as network administrators. The core of the project is the set of bash scripts that
collect the hardware data continuously using the `crontab` command. All the data is stored in a PostgreSQL Docker container. This allows the 
database system to be portable and fit to any Linux machine. Git was used for version control purposes throughout develop, along with this GitHub repository for storage.

# Quick Start
Start a PSQL instance using **psql_docker.sh**:

`bash ./scripts/psql_docker.sh create | start | stop  [db_username] [db_password]`

Create the PSQL database tables using **ddl.sql**:

`psql -h psql_host -U psql_user -d database_name -f sql/ddl.sql`

Insert the host hardware specifications into the database using **host_info.sh**:

`bash ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password`:

Insert the host hardware usage information into the database using **host_usage.sh**:

`bash ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password`

Setup **crontab** to run the usage gathering script every minute:

```
crontab -e
* * * * * bash /full/path/to/linux_sql/scripts/host_usage.sh 
psql_host port db_name psql_user psql_password &> /tmp/host_usage.log
```

# Implementation
Discuss how you implement the project.
## Architecture
Draw a cluster diagram with three Linux hosts, a DB, and agents (use draw.io website). Image must be saved to the `assets` directory.

## Scripts
Shell script description and usage (use markdown code block for script usage)
- psql_docker.sh
- host_info.sh
- host_usage.sh
- crontab
- queries.sql (describe what business problem you are trying to resolve)

## Database Modeling
Schema for 'host_info'

Attribute | Type | Description
--------- | -----| -----------
id | serial | Unique automatically incremented number to identify each host. Primary key.
hostname | varchar |  Unique name for every host.
cpu_number | smallint | The number of CPU cores.
cpu_architecture | varchar | The type of architecture of the processor.
cpu_model | varchar | Name of CPU model.
cpu_mhz | decimal | CPU clock speed in MHz.
2_cache | integer | L2 memory cache size in kB.
total_mem | integer | Size of total memory (RAM) in kB
timestamp | timestamp | The time when this record was collected.

Schema for 'host_usage'

Attribute | Type | Description
--------- | -----| -----------
timestamp | timestamp | The time when this record was collected.
host_id | integer | ID corresponding to the same host in the 'host_info' table. Foreign key.
memory_free | integer | The amount of available memory (RAM).
cpu_idle | smallint | Percentage of time that the CPU spends idle.
cpu_kernal | smallint | Percentage of time that the CPU spends running in kernal mode.
disk_io | integer | The number of disk input/out processes.
disk_available | integer | Root directory disk space available in MB.

# Testing
Due to being an MVP, I only tested a single machine instance. The assumption of the scripts working in a Linux
cluster that is connected properly was made. 

### Bash Scripts Testing
- The scripts (`psql_docker.sh`, `host_info.sh`, and `host_usage.sh`) were manually tested on a single machine. These tests used 
the Linux shell.
- Bash options such as `bash -x` were used. This one allowed the execution of the script to be seen and was useful for debugging.
- The scripts that collected data (`host_info.sh` and `host_usage.sh`) were verified by checking the 
database tables after execution.
- `psql_docker.sh` was tested using Docker CLI. After creating the PSQL container, it was verified to exist and run by accessing it.
### SQL Testing
- `ddl.sql` was verified by viewing the database tables using the database view of IntelliJ. The PostgreSQL terminal
was also used to list the tables the database contained.
- `queries.sql` was tested by inserting test data into the databases and verifying the queries results manually by calculating
the expected results, and the ones that were returned.
# Deployment
Due to the project scope and nature, deployment isn't necessary. Git and GitHub manages the source-control management, 
and Docker containers manage the PSQL instance. 

# Improvements
As an MVP, many improvements can be made. Some of these include:

- Implement a single bash script that automates the entire process. The script would 
  retrieve and create the Docker PSQL container, start it, and construct the tables using the DDL schemas. 
- Create an alert to notify the users immediately when the SQL queries detect system failure.  
- Create a backup storage mechanism. At the moment, a single machine contains the database.  