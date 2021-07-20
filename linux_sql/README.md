# Introduction
The purpose of this project was to develop a minimum viable product (MVP) that would automatically collect and store hardware specifications and usage data. 
The data comes from a cluster of internally connected Linux machines. The potential users include the Jarvis Linux Cluster Administration (LCA) team,
along with anyone that would need to collect this data, such as network administrators. The core of the project is the set of bash scripts that
collect the hardware data continuously using the `crontab` command. All the data is stored in a PostgreSQL Docker container. This allows the 
database system to be portable and fit to any Linux machine. Git was used for version control purposes throughout develop, along with this GitHub repository for storage.

# Quick Start
Start a PSQL instance using psql_docker.sh:

`bash ./scripts/psql_docker.sh [create | start | stop ] [db_username] [db_password]`

Create the PSQL database tables using ddl.sql:

`psql -h psql_host -U psql_user -d database_name -f sql/ddl.sql`

Insert the host hardware specifications into the database using host_info.sh:

`bash ./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password`:

Insert the host hardware usage information into the database using host_usage.sh:

`bash ./scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password`

Setup Crontab to run the usage gathering script every minute:

```
crontab -e
* * * * * bash /path/to/linux_sql/scripts/host_usage.sh 
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
Describe the schema of each table using markdown table syntax (do not put any sql code)
- `host_info`
- `host_usage`

# Test
How did you test your bash scripts and SQL queries? What was the result?

# Deployment
How did you deploy your app? (e.g. Github for SCM and docker for PSQL)

# Improvements
Write at least three things you want to improve
e.g.
- handle hardware update
- blah
- blah