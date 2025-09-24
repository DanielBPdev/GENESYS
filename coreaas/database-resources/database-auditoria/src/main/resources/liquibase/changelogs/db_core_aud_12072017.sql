--liquibase formatted sql

--changeset jocorrea:01
--comment:Modificacion al nombre de las tablas Novedad_aud y NovedadPila_aud
EXEC sp_rename 'dbo.Novedad_aud', 'ParametrizacionNovedad_aud';
EXEC sp_rename 'dbo.NovedadPila_aud', 'NovedadDetalle_aud';
ALTER TABLE dbo.NovedadDetalle_aud DROP COLUMN nopRolAfiliado;
ALTER TABLE dbo.NovedadDetalle_aud DROP COLUMN nopTipoNovedadPila;
ALTER TABLE dbo.NovedadDetalle_aud ADD nopSolicitudNovedad bigint NULL;