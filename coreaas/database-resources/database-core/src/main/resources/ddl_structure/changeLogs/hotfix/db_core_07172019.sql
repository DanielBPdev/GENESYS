--liquibase formatted sql

--changeset jocampo:01
--comment: 
IF NOT EXISTS (select 1 from sys.types where name = 'TablaBigintIdType')
CREATE TYPE TablaBigintIdType AS TABLE (id BIGINT);
