--liquibase formatted sql

--changeset dsuesca:01
--comment: 
IF NOT EXISTS (SELECT * FROM sys.types WHERE is_table_type = 1 AND name = 'TY_GrupoFamiliar')
		CREATE TYPE dbo.TY_GrupoFamiliar AS table(grfId BIGINT);
