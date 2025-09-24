--liquibase formatted sql

--changeset jocorrea:01
--comment: Se agregan campos a la tabla Empleador_aud
ALTER TABLE Empleador_aud ADD empFechaSubsanacionExpulsion date NULL;
ALTER TABLE Empleador_aud ADD empMotivoSubsanacionExpulsion varchar(200) NULL;

--changeset atoro:02
--comment: Se modifican los tamaños de los campo ifaUsuario y traUsuario
ALTER TABLE dbo.InformacionFaltanteAportante_aud ALTER COLUMN ifaUsuario varchar(255) NULL;
ALTER TABLE dbo.Trazabilidad_aud ALTER COLUMN traUsuario varchar(255) NULL;
