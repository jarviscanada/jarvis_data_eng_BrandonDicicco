--Query #1: Group hosts by CPU Number and sort by descending memory size
SELECT
    cpu_number,
    id AS "host_id",
    total_mem
FROM
    host_info
ORDER BY
    cpu_number,
    total_mem DESC;

--Function to return a timestamp rounded to the nearest 5 minute interval
CREATE OR REPLACE FUNCTION round5(ts timestamp) RETURNS timestamp AS
    $$
    BEGIN
        RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
    END;
    $$ LANGUAGE PLPGSQL;

--Query #2: Average used memory % over 5 minutes for each host
SELECT
    usage.host_id,
    info.hostname,
    round5(usage.timestamp) AS "ts",
    AVG(
            100 * (
                info.total_mem - usage.memory_free
            ) / info.total_mem
        ) AS "avg_used_mem_percentage"
FROM
    host_usage AS "usage"
        JOIN host_info AS "info" on usage.host_id = info.id
GROUP BY
    usage.host_id,
    info.hostname,
    ts
ORDER BY
    usage.host_id,
    ts;