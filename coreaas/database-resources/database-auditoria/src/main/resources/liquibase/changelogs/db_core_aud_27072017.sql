--liquibase formatted sql

--changeset flopez:01
--comment: Creacion de las tablas de medio de pago, AdministradorSubsidio_aud, MedioDePago_aud, MedioEfectivo_aud, MedioTarjeta_aud, MedioTransferencia_aud, AdminSubsidioGrupo_aud, MediosPagoCCF_aud y MedioPagoPersona_aud
CREATE TABLE AdministradorSubsidio_aud(
	asuId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	asuPersona BIGINT NOT NULL,
	
);

CREATE TABLE MedioDePago_aud(
	mdpId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mdpTipo VARCHAR(30) NOT NULL,
);

CREATE TABLE MedioEfectivo_aud(
	mdpId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mefEfectivo BIT NOT NULL,
);


CREATE TABLE MedioTarjeta_aud(
	mdpId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mtrNumeroTarjeta VARCHAR(50) NOT NULL,
	mtrDisponeTarjeta BIT NOT NULL,
	mtrEstadoTarjetaMultiservicios VARCHAR(30) NOT NULL,
	mtrSolicitudTarjeta VARCHAR(30) NOT NULL,
);
 
CREATE TABLE MedioTransferencia_aud(
	mdpId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	metBanco BIGINT NOT NULL,
	metTipoCuenta VARCHAR(30) NOT NULL,
	metNumeroCuenta VARCHAR(30) NOT NULL,
	metTipoIdentificacionTitular VARCHAR(20) NULL,
	metNumeroIdentificacionTitular VARCHAR(16) NULL,
	metDigitoVerificacionTitular SMALLINT NULL,
	metNombreTitularCuenta VARCHAR (200) NULL,	
);

CREATE TABLE AdminSubsidioGrupo_aud(
	asgId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	asgGrupoFamiliar BIGINT NOT NULL,
	asgAdministradorSubsidio BIGINT NULL,
	asgMedioDePago BIGINT NULL,
	asgAfiliadoEsAdminSubsidio bit NOT NULL,
	asgMedioPagoActivo bit NOT NULL,
	asgRelacionGrupoFamiliar smallint null,
);

CREATE TABLE MediosPagoCCF_aud(
	mpcId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mpcCajaCompensacion INT NOT NULL,
	mpcMedioPago VARCHAR(30) NOT NULL,
	mpcMedioPreferente BIT NOT NULL,
);

CREATE TABLE MedioPagoPersona_aud(
	mppId BIGINT NOT NULL IDENTITY(1,1),
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mppMedioPago BIGINT NOT NULL,
	mppPersona BIGINT NOT NULL,
	mppMedioActivo BIT NOT NULL,
);

ALTER TABLE AdministradorSubsidio_aud ADD CONSTRAINT FK_AdministradorSubsidio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE MedioDePago_aud ADD CONSTRAINT FK_MedioDePago_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE MedioEfectivo_aud ADD CONSTRAINT FK_MedioEfectivo_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE MedioTarjeta_aud ADD CONSTRAINT FK_MedioTarjeta_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE MedioTransferencia_aud ADD CONSTRAINT FK_MedioTransferencia_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE AdminSubsidioGrupo_aud ADD CONSTRAINT FK_AdminSubsidioGrupo_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE MediosPagoCCF_aud ADD CONSTRAINT FK_MediosPagoCCF_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
ALTER TABLE MedioPagoPersona_aud ADD CONSTRAINT FK_MedioPagoPersona_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--Modificación PersonaDetalle
ALTER TABLE PersonaDetalle_aud DROP COLUMN pedMedioPago;

--Modificación Empleador
ALTER TABLE Empleador_aud DROP COLUMN empMedioPagoSubsidioMonetario;
ALTER TABLE Empleador_aud ADD empMedioPagoSubsidioMonetario VARCHAR(30) NULL;

-- Modificación SucursalEmpresa
ALTER TABLE SucursalEmpresa_aud DROP COLUMN sueMedioPagoSubsidioMonetario;
ALTER TABLE SucursalEmpresa_aud ADD sueMedioPagoSubsidioMonetario VARCHAR(30) NULL;

-- Modificación Grupo Familiar
ALTER TABLE GrupoFamiliar_aud DROP COLUMN grfParametrizacionMedioPago;
ALTER TABLE GrupoFamiliar_aud DROP COLUMN grfAdministradorSubsidio;
ALTER TABLE GrupoFamiliar_aud DROP COLUMN grfRelacionGrupoFamiliar;

-- Eliminación de Tabla ParametrizacionMedioDePago
ALTER TABLE ParametrizacionMedioDePago_aud DROP CONSTRAINT FK_ParametrizacionMedioDePago_aud_REV;
DROP TABLE ParametrizacionMedioDePago_aud;
