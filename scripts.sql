
WITH RECURSIVE subgroups AS (
    SELECT
        group_id,
        parent_id
    FROM
        groups g1
    WHERE
        group_id = 'Washing Machines'
    UNION
    SELECT
        g.group_id,
        g.parent_id
    FROM
        groups g
            INNER JOIN subgroups s ON g.group_id = s.parent_id
    WHERE (NOT (SELECT COUNT(*) FROM position_cards WHERE position_cards.group_id = s.group_id) = 1)
) SELECT
    subgroups.group_id, subgroups.parent_id, pc.name
FROM
    subgroups
INNER JOIN position_cards pc ON pc.group_id = subgroups.group_id;