--liquibase formatted sql

--changeset flopez:01
--comment: Crea tabla CuentaAdministradorSubsidioProgramada_aud y elimina campo dprCuentaAdministradorSubsidio y agrega dprCuentaAdministradorSubsidioProgramada

IF NOT EXISTS (SELECT 1 FROM sysobjects WHERE name='CuentaAdministradorSubsidioProgramada_aud')
CREATE TABLE CuentaAdministradorSubsidioProgramada_aud (
	capId bigint IDENTITY(1,1) NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	capFechaHoraCreacionRegistro datetime NOT NULL,
	capUsuarioCreacionRegistro varchar(200) COLLATE Latin1_General_CI_AI NOT NULL,
	capTipoTransaccionSubsidio varchar(40) COLLATE Latin1_General_CI_AI NOT NULL,
	capEstadoTransaccionSubsidio varchar(25) COLLATE Latin1_General_CI_AI NULL,
	capEstadoLiquidacionSubsidio varchar(25) COLLATE Latin1_General_CI_AI NULL,
	capOrigenTransaccion varchar(30) COLLATE Latin1_General_CI_AI NOT NULL,
	capMedioDePagoTransaccion varchar(13) COLLATE Latin1_General_CI_AI NOT NULL,
	capNumeroTarjetaAdmonSubsidio varchar(50) COLLATE Latin1_General_CI_AI NULL,
	capCodigoBanco varchar(6) COLLATE Latin1_General_CI_AI NULL,
	capNombreBanco varchar(255) COLLATE Latin1_General_CI_AI NULL,
	capTipoCuentaAdmonSubsidio varchar(30) COLLATE Latin1_General_CI_AI NULL,
	capNumeroCuentaAdmonSubsidio varchar(30) COLLATE Latin1_General_CI_AI NULL,
	capTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20) COLLATE Latin1_General_CI_AI NULL,
	capNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20) COLLATE Latin1_General_CI_AI NULL,
	capNombreTitularCuentaAdmonSubsidio varchar(200) COLLATE Latin1_General_CI_AI NULL,
	capFechaHoraTransaccion datetime NOT NULL,
	capUsuarioTransaccion varchar(200) COLLATE Latin1_General_CI_AI NOT NULL,
	capValorOriginalTransaccion numeric(19,5) NOT NULL,
	capValorRealTransaccion numeric(19,5) NOT NULL,
	capIdTransaccionOriginal bigint NULL,
	capIdRemisionDatosTerceroPagador varchar(200) COLLATE Latin1_General_CI_AI NULL,
	capIdTransaccionTerceroPagador varchar(200) COLLATE Latin1_General_CI_AI NULL,
	capNombreTerceroPagado varchar(200) COLLATE Latin1_General_CI_AI NULL,
	capIdCuentaAdmonSubsidioRelacionado bigint NULL,
	capFechaHoraUltimaModificacion datetime NULL,
	capUsuarioUltimaModificacion varchar(200) COLLATE Latin1_General_CI_AI NULL,
	capAdministradorSubsidio bigint NOT NULL,
	capSitioDePago bigint NULL,
	capSitioDeCobro bigint NULL,
	capMedioDePago bigint NOT NULL,
	capCondicionPersonaAdmin bigint NULL,
	capSolicitudLiquidacionSubsidio bigint NULL,
	capEmpleador bigint NULL,
	capAfiliadoPrincipal bigint NULL,
	capBeneficiarioDetalle bigint NULL,
	capGrupoFamiliar bigint NULL,
	capIdCuentaOriginal bigint NULL,
	capIdPuntoDeCobro varchar(200) COLLATE Latin1_General_CI_AI NULL
);
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name='IX_CuentaAdministradorSubsidioProgramada_REV' AND object_id = OBJECT_ID('dbo.CuentaAdministradorSubsidioProgramada_aud'))
 CREATE CLUSTERED INDEX IX_CuentaAdministradorSubsidioProgramada_REV ON dbo.CuentaAdministradorSubsidioProgramada_aud (  REV ASC  )
 
IF EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'DetalleSubsidioAsignadoProgramado_aud' AND column_name = 'dprCuentaAdministradorSubsidio')
ALTER TABLE  DetalleSubsidioAsignadoProgramado_aud DROP COLUMN dprCuentaAdministradorSubsidio;

IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'DetalleSubsidioAsignadoProgramado_aud' AND column_name = 'dprCuentaAdministradorSubsidioProgramada')
ALTER TABLE  DetalleSubsidioAsignadoProgramado_aud ADD dprCuentaAdministradorSubsidioProgramada BIGINT NULL;