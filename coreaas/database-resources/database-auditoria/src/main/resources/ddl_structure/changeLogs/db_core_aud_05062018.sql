--liquibase formatted sql

--changeset flopez:01
--comment: Se elemina columna mpcCajaCompensacion
if exists (select 1 from INFORMATION_SCHEMA.COLUMNS where COLUMN_NAME = 'mpcCajaCompensacion' and TABLE_NAME = 'MediosPagoCCF_aud')
begin
	alter table dbo.MediosPagoCCF_aud drop column mpcCajaCompensacion
end

--changeset rlopez:02
--comment: Ajuste campos CuentaAdministradorSubsidio
if not exists (select 1 from INFORMATION_SCHEMA.COLUMNS where COLUMN_NAME = 'casSolicitudLiquidacionSubsidio' and TABLE_NAME = 'CuentaAdministradorSubsidio_aud')
begin
	alter table dbo.CuentaAdministradorSubsidio_aud add casSolicitudLiquidacionSubsidio bigInt
end
ALTER TABLE CuentaAdministradorSubsidio_aud ALTER COLUMN casCodigoBanco varchar(7) NULL;
ALTER TABLE CuentaAdministradorSubsidio_aud ALTER COLUMN casNombreBanco varchar(255) NULL;