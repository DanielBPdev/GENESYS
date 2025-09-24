--liquibase formatted sql

--changeset rcastillo:01
--comment:Ajuste solicitudNovedadPila

if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'dbo' and TABLE_NAME = 'SolicitudNovedadPila' and COLUMN_NAME = 'spiIdRegistroDetalladoNovedad') 
alter table dbo.SolicitudNovedadPila add spiIdRegistroDetalladoNovedad bigInt


if not exists (select * from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA = 'aud' and TABLE_NAME = 'SolicitudNovedadPila_aud' and COLUMN_NAME = 'spiIdRegistroDetalladoNovedad') 
alter table aud.SolicitudNovedadPila_aud add spiIdRegistroDetalladoNovedad bigInt