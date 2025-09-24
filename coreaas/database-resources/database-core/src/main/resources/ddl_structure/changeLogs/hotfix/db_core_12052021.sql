--liquibase formatted sql

--changeset dsuesca:01
--comment: mantis 0266571

UPDATE FieldLoadCatalog
SET dataType = 'STRING'
WHERE id = 2110064