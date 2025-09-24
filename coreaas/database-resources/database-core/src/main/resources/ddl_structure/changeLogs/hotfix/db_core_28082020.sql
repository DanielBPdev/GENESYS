--liquibase formatted sql

--changeset flopez:01
--comment: Crea tabla CuentaAdministradorSubsidioProgramada y elimina campo dprCuentaAdministradorSubsidio y agrega dprCuentaAdministradorSubsidioProgramada
IF NOT EXISTS (SELECT 1 FROM sysobjects WHERE name='CuentaAdministradorSubsidioProgramada')
CREATE TABLE dbo.CuentaAdministradorSubsidioProgramada (
	capId bigint IDENTITY(1,1) NOT NULL,
	capFechaHoraCreacionRegistro datetime NOT NULL,
	capUsuarioCreacionRegistro varchar(200) NOT NULL,
	capTipoTransaccionSubsidio varchar(40) NOT NULL,
	capEstadoTransaccionSubsidio varchar(25) NULL,
	capEstadoLiquidacionSubsidio varchar(25) NULL,
	capOrigenTransaccion varchar(30) NOT NULL,
	capMedioDePagoTransaccion varchar(13) NOT NULL,
	capNumeroTarjetaAdmonSubsidio varchar(50) NULL,
	capCodigoBanco varchar(6) NULL,
	capNombreBanco varchar(255) NULL,
	capTipoCuentaAdmonSubsidio varchar(30) NULL,
	capNumeroCuentaAdmonSubsidio varchar(30) NULL,
	capTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	capNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	capNombreTitularCuentaAdmonSubsidio varchar(200) NULL,
	capFechaHoraTransaccion datetime NOT NULL,
	capUsuarioTransaccion varchar(200) NOT NULL,
	capValorOriginalTransaccion numeric(19,5) NOT NULL,
	capValorRealTransaccion numeric(19,5) NOT NULL,
	capIdTransaccionOriginal bigint NULL,
	capIdRemisionDatosTerceroPagador varchar(200) NULL,
	capIdTransaccionTerceroPagador varchar(200) NULL,
	capNombreTerceroPagado varchar(200) NULL,
	capIdCuentaAdmonSubsidioRelacionado bigint NULL,
	capFechaHoraUltimaModificacion datetime NULL,
	capUsuarioUltimaModificacion varchar(200) NULL,
	capAdministradorSubsidio bigint NOT NULL,
	capSitioDePago bigint NULL,
	capSitioDeCobro bigint NULL,
	capMedioDePago bigint NOT NULL,
	capSolicitudLiquidacionSubsidio bigint NULL,
	capCondicionPersonaAdmin bigint NULL,
	capEmpleador bigint NULL,
	capAfiliadoPrincipal bigint NULL,
	capBeneficiarioDetalle bigint NULL,
	capGrupoFamiliar bigint NULL,
	capIdCuentaOriginal bigint NULL,
	capIdPuntoDeCobro varchar(200) NULL,
	CONSTRAINT PK_CuentaAdministradorSubsidioProgramada_capId PRIMARY KEY (capId)
);
IF NOT EXISTS (  SELECT 1 FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_NAME='FK_CuentaAdministradorSubsidioProgramada_casAdministradorSubsidio')
ALTER TABLE dbo.CuentaAdministradorSubsidioProgramada ADD CONSTRAINT FK_CuentaAdministradorSubsidioProgramada_casAdministradorSubsidio FOREIGN KEY (capAdministradorSubsidio) REFERENCES dbo.AdministradorSubsidio(asuId);
IF NOT EXISTS (  SELECT 1 FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_NAME='FK_CuentaAdministradorSubsidioProgramada_casMedioDePago')
ALTER TABLE dbo.CuentaAdministradorSubsidioProgramada ADD CONSTRAINT FK_CuentaAdministradorSubsidioProgramada_casMedioDePago FOREIGN KEY (capMedioDePago) REFERENCES dbo.MedioDePago(mdpId);
IF NOT EXISTS (  SELECT 1 FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_NAME='FK_CuentaAdministradorSubsidioProgramada_casSitioDeCobro')
ALTER TABLE dbo.CuentaAdministradorSubsidioProgramada ADD CONSTRAINT FK_CuentaAdministradorSubsidioProgramada_casSitioDeCobro FOREIGN KEY (capSitioDeCobro) REFERENCES dbo.SitioPago(sipId);
IF NOT EXISTS (  SELECT 1 FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_NAME='FK_CuentaAdministradorSubsidioProgramada_casSitioDePago')
ALTER TABLE dbo.CuentaAdministradorSubsidioProgramada ADD CONSTRAINT FK_CuentaAdministradorSubsidioProgramada_casSitioDePago FOREIGN KEY (capSitioDePago) REFERENCES dbo.SitioPago(sipId);

IF EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='DetalleSubsidioAsignadoProgramado' AND column_name='dprCuentaAdministradorSubsidio')
ALTER TABLE  DetalleSubsidioAsignadoProgramado 
DROP COLUMN dprCuentaAdministradorSubsidio;

IF NOT EXISTS(SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name='DetalleSubsidioAsignadoProgramado' AND column_name='dprCuentaAdministradorSubsidioProgramada')
ALTER TABLE DetalleSubsidioAsignadoProgramado 
ADD dprCuentaAdministradorSubsidioProgramada BIGINT NULL;

IF NOT EXISTS (SELECT 1 FROM sysobjects WHERE name='CuentaAdministradorSubsidioProgramada_aud')
CREATE TABLE aud.CuentaAdministradorSubsidioProgramada_aud(
	capId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	capFechaHoraCreacionRegistro datetime NOT NULL,
	capUsuarioCreacionRegistro varchar(200) NOT NULL,
	capTipoTransaccionSubsidio varchar(40) NOT NULL,
	capEstadoTransaccionSubsidio varchar(25) NULL,
	capEstadoLiquidacionSubsidio varchar(25) NULL,
	capOrigenTransaccion varchar(30) NOT NULL,
	capMedioDePagoTransaccion varchar(13) NOT NULL,
	capNumeroTarjetaAdmonSubsidio varchar(50) NULL,
	capCodigoBanco varchar(6) NULL,
	capNombreBanco varchar(255) NULL,
	capTipoCuentaAdmonSubsidio varchar(30) NULL,
	capNumeroCuentaAdmonSubsidio varchar(30) NULL,
	capTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	capNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	capNombreTitularCuentaAdmonSubsidio varchar(200) NULL,
	capFechaHoraTransaccion datetime NOT NULL,
	capUsuarioTransaccion varchar(200) NOT NULL,
	capValorOriginalTransaccion numeric(19,5) NOT NULL,
	capValorRealTransaccion numeric(19,5) NOT NULL,
	capIdTransaccionOriginal bigint NULL,
	capIdRemisionDatosTerceroPagador varchar(200) NULL,
	capIdTransaccionTerceroPagador varchar(200) NULL,
	capNombreTerceroPagado varchar(200) NULL,
	capIdCuentaAdmonSubsidioRelacionado bigint NULL,
	capFechaHoraUltimaModificacion datetime NULL,
	capUsuarioUltimaModificacion varchar(200) NULL,
	capAdministradorSubsidio bigint NOT NULL,
	capSitioDePago bigint NULL,
	capSitioDeCobro bigint NULL,
	capMedioDePago bigint NOT NULL,
	capSolicitudLiquidacionSubsidio bigint NULL,
	capCondicionPersonaAdmin bigint NULL,
	capEmpleador bigint NULL,
	capAfiliadoPrincipal bigint NULL,
	capBeneficiarioDetalle bigint NULL,
	capGrupoFamiliar bigint NULL,
	capIdCuentaOriginal bigint NULL,
	capIdPuntoDeCobro varchar(200) NULL
);

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS WHERE CONSTRAINT_NAME='FK_CuentaAdministradorSubsidioProgramada_aud_REV')
ALTER TABLE aud.CuentaAdministradorSubsidioProgramada_aud ADD CONSTRAINT FK_CuentaAdministradorSubsidioProgramada_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId);

IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='DetalleSubsidioAsignadoProgramado_aud' AND COLUMN_NAME = 'dprCuentaAdministradorSubsidio')
ALTER TABLE  aud.DetalleSubsidioAsignadoProgramado_aud DROP COLUMN dprCuentaAdministradorSubsidio;

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='DetalleSubsidioAsignadoProgramado_aud' AND COLUMN_NAME='dprCuentaAdministradorSubsidioProgramada')
ALTER TABLE  aud.DetalleSubsidioAsignadoProgramado_aud ADD dprCuentaAdministradorSubsidioProgramada BIGINT NULL;
