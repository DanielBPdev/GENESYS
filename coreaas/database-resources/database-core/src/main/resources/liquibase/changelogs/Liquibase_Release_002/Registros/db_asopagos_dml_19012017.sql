--liquibase formatted sql

--changeset   jusanchez:01

--comment: Actualización FIELDLOADCATALOG  SET DATATYPE='STRING'
UPDATE FIELDLOADCATALOG SET DATATYPE='STRING' WHERE ID IN (1231,1232)