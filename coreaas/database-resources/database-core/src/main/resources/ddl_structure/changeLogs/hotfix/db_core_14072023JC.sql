--liquibase formatted sql
--changeset Desarrollo:01
--comment: GLPI ***
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'DocumentosSeguimientoGestion' and COLUMN_NAME = 'dsgObservaciones') 
BEGIN
    ALTER TABLE DocumentosSeguimientoGestion ADD dsgObservaciones varchar(500)
END
if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'aud' and TABLE_NAME = 'DocumentosSeguimientoGestion_aud' and COLUMN_NAME = 'dsgObservaciones') 
BEGIN
    ALTER TABLE aud.DocumentosSeguimientoGestion_aud ADD dsgObservaciones VARCHAR(500)
END
