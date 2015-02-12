SET SQL_SAFE_UPDATES = 0;

UPDATE noltodo.task SET priority = '400' WHERE priority = 'primary';

UPDATE noltodo.task SET priority = '300' WHERE priority = 'high';

UPDATE noltodo.task SET priority = '200' WHERE priority = 'mid';

UPDATE noltodo.task SET priority = '100' WHERE priority = 'low';