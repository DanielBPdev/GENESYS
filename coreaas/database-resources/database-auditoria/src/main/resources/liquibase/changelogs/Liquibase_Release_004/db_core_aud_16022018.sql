--liquibase formatted sql

--changeset hhernandez:01
--comment:Creacion de tablas CuentaAdministradorSubsidio_aud y DetalleSubsidioAsignado_aud
CREATE TABLE CuentaAdministradorSubsidio_aud(
	casId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	casFechaHoraCreacionRegistro datetime NOT NULL,
	casUsuarioCreacionRegistro varchar(200) NOT NULL,
	casTipoTransaccionSubsidio varchar(40) NOT NULL,
	casEstadoTransaccionSubsidio varchar(25) NULL,
	casEstadoLiquidacionSubsidio varchar(25) NULL,
	casOrigenTransaccion varchar(30) NOT NULL,
	casMedioDePagoTransaccion varchar(13) NOT NULL,
	casNumeroTarjetaAdmonSubsidio varchar(50) NULL,
	casCodigoBanco varchar(4) NULL,
	casNombreBanco varchar(200) NULL,
	casTipoCuentaAdmonSubsidio varchar(30) NULL,
	casNumeroCuentaAdmonSubsidio varchar(30) NULL,
	casTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	casNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	casNombreTitularCuentaAdmonSubsidio varchar(200) NULL,
	casFechaHoraTransaccion datetime NOT NULL,
	casUsuarioTransaccion varchar(200) NOT NULL,
	casValorOriginalTransaccion numeric(19, 5) NOT NULL,
	casValorRealTransaccion numeric(19, 5) NOT NULL,
	casIdTransaccionOriginal bigint NULL,
	casIdRemisionDatosTerceroPagador varchar(200) NULL,
	casIdTransaccionTerceroPagador varchar(200) NULL,
	casNombreTerceroPagado varchar(200) NULL,
	casIdCuentaAdmonSubsidioRelacionado bigint NULL,
	casFechaHoraUltimaModificacion datetime NULL,
	casUsuarioUltimaModificacion varchar(200) NULL,
	casAdministradorSubsidio bigint NOT NULL,
	casSitioDePago bigint NULL,
	casSitioDeCobro bigint NULL,
	casMedioDePago bigint NOT NULL,
);
ALTER TABLE CuentaAdministradorSubsidio_aud ADD CONSTRAINT FK_CuentaAdministradorSubsidio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DetalleSubsidioAsignado_aud(
	dsaId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dsaUsuarioCreador varchar(200) NOT NULL,
	dsaFechaHoraCreacion datetime NOT NULL,
	dsaEstado varchar(20) NOT NULL,
	dsaMotivoAnulacion varchar(40) NULL,
	dsaDetalleAnulacion varchar(250) NULL,
	dsaOrigenRegistroSubsidio varchar(30) NOT NULL,
	dsaTipoliquidacionSubsidio varchar(60) NOT NULL,
	dsaTipoCuotaSubsidio varchar(80) NOT NULL,
	dsaValorSubsidioMonetario numeric(19, 5) NOT NULL,
	dsaValorDescuento numeric(19, 5) NOT NULL,
	dsaValorOriginalAbonado numeric(19, 5) NOT NULL,
	dsaValorTotal numeric(19, 5) NOT NULL,
	dsaFechaTransaccionRetiro date NULL,
	dsaUsuarioTransaccionRetiro varchar(200) NULL,
	dsaFechaTransaccionAnulacion date NULL,
	dsaUsuarioTransaccionAnulacion varchar(200) NULL,
	dsaFechaHoraUltimaModificacion datetime NULL,
	dsaUsuarioUltimaModificacion varchar(200) NULL,
	dsaSolicitudLiquidacionSubsidio bigint NOT NULL,
	dsaEmpleador bigint NOT NULL,
	dsaAfiliadoPrincipal bigint NOT NULL,
	dsaGrupoFamiliar bigint NOT NULL,
	dsaAdministradorSubsidio bigint NOT NULL,
	dsaIdRegistroOriginalRelacionado bigint NULL,
	dsaCuentaAdministradorSubsidio bigint NOT NULL,
	dsaBeneficiarioDetalle bigint NOT NULL,
	dsaPeriodoLiquidado date NOT NULL,
	dsaResultadoValidacionLiquidacion bigint NOT NULL,
);
ALTER TABLE DetalleSubsidioAsignado_aud ADD CONSTRAINT FK_DetalleSubsidioAsignado_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset flopez:02
--comment:Se modifica tamaño campo de la tabla SolicitudLegalizacionDesembolso_aud
ALTER TABLE SolicitudLegalizacionDesembolso_aud ALTER COLUMN sldEstadoSolicitud VARCHAR(48) NULL;

--changeset borozco:03
--comment:Creacion de la tabla ConvenioPagoDependiente_aud
CREATE TABLE ConvenioPagoDependiente_aud(
	cpdId bigint NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	cpdPagoPeriodoConvenio bigint NULL,
	cpdPersona bigint NULL,
);
ALTER TABLE ConvenioPagoDependiente_aud ADD CONSTRAINT FK_ConvenioPagoDependiente_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset ecastano:04
--comment:Modificacion de tamaño de campo de la tabla Licencia_aud
ALTER TABLE Licencia_aud ALTER COLUMN licTipoLicencia VARCHAR(21) NULL;
