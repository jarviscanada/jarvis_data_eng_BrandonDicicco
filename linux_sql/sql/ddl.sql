-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

--Create table to store hardware specifications
CREATE TABLE IF NOT EXISTS PUBLIC.host_info (
    id SERIAL PRIMARY KEY,
    hostname VARCHAR UNIQUE NOT NULL,
    cpu_number SMALLINT NOT NULL,
    cpu_architecture VARCHAR NOT NULL,
    cpu_model VARCHAR NOT NULL,
    cpu_mhz DECIMAL NOT NULL,
    L2_cache INTEGER NOT NULL,
    total_mem INTEGER NOT NULL,
    "timestamp" TIMESTAMP NOT NULL
);

--Create table to store resource usage data
CREATE TABLE IF NOT EXISTS PUBLIC.host_usage (
    "timestamp" TIMESTAMP NOT NULL,
    host_id INTEGER NOT NULL,
    memory_free INTEGER NOT NULL,
    cpu_idle SMALLINT NOT NULL CHECK (cpu_idle <= 100 AND cpu_idle >= 0),
    cpu_kernel SMALLINT NOT NULL CHECK (cpu_kernel <= 100 AND cpu_kernel >= 0),
    disk_io INTEGER NOT NULL,
    disk_available INTEGER NOT NULL,
    FOREIGN KEY (host_id)
        REFERENCES host_info (id)
);