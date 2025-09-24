--liquibase formatted sql

--changeset rcastillo:01
--comment:Ajuste planillasN
if not exists (select * from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'RegistroDetalladoPlanillaN' and TABLE_SCHEMA = 'pila')
begin
	CREATE EXTERNAL TABLE [pila].[RegistroDetalladoPlanillaN]
	(
		[redId] [bigint] NOT NULL,
		[redRegistroDetalladoAnterior] [bigint] NULL
	)
	WITH (DATA_SOURCE = [PilaReferenceData],SCHEMA_NAME = N'staging',OBJECT_NAME = N'RegistroDetalladoPlanillaN')
end