--Query #1: Group hosts by CPU Number and sort by descending memory size
SELECT
    cpu_number,
    id AS "host_id",
    total_mem
FROM
    host_agent.public.host_info
ORDER BY
    cpu_number,
    total_mem DESC;