--liquibase formatted sql

--changeset dsuesca:01
--comment: Se eliminan datos de liquibase para ejectur nuevamente scripts de comments
DELETE FROM DATABASECHANGELOG WHERE FILENAME = 'ddl_structure/core_comments.sql';