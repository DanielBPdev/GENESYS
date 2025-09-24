--liquibase formatted sql

--changeset fvasquez:01
--comment: Se elimina campo de la tabla Cartera
ALTER TABLE Cartera_aud DROP COLUMN carNumeroOperacion

--changeset clmarin:02
--comment:Se modifican y se agregan campos en la tabla ConvenioPago
EXEC sp_rename 'dbo.ConvenioPago_aud.copNombreUsuario', 'copUsuarioAnulacion', 'COLUMN';
ALTER TABLE ConvenioPago_aud ADD copUsuarioCreacion VARCHAR(255) NULL;

--changeset fvasquez:03
--comment:Creacion de las tablas DocumentoCartera_aud,SolicitudGestionCobroFisico_aud,DetalleSolicitudGestionCruce_aud,SolicitudGestionCobroElectronico_aud,MotivoNoGestionCobro_aud y DetalleComunicadoEnviado_aud
CREATE TABLE DocumentoCartera_aud(
	dcaId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dcaCartera BIGINT NOT NULL,	
	dcaDocumentoSoporte BIGINT NOT NULL,
);
ALTER TABLE DocumentoCartera_aud ADD CONSTRAINT FK_DocumentoCartera_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SolicitudGestionCobroFisico_aud(
	sgfId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sgfDocumentoCartera BIGINT NULL,
	sgfEstado VARCHAR(33) NOT NULL,
	sgfFechaRemision DATETIME NULL,
	sgfObservacionRemision VARCHAR(255) NULL,
	sgfTipoAccionCobro VARCHAR(4) NOT NULL,
	sgfSolicitud BIGINT NOT NULL,
);
ALTER TABLE SolicitudGestionCobroFisico_aud ADD CONSTRAINT FK_SolicitudGestionCobroFisico_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DetalleSolicitudGestionCobro_aud(
	dsgId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dsgEnviarPrimeraRemision BIT NULL,
	dsgEnviarSegundaRemision BIT NULL,
	dsgEstado VARCHAR(37) NOT NULL,
	dsgFechaPrimeraRemision DATETIME NULL,
	dsgFechaSegundaRemision DATETIME NULL,
	dsgCartera BIGINT NOT NULL,
	dsgObservacionPrimeraEntrega VARCHAR(255) NULL,
	dsgObservacionPrimeraRemision VARCHAR(255) NULL,
	dsgObservacionSegundaEntrega VARCHAR(255) NULL,
	dsgObservacionSegundaRemision VARCHAR(255) NULL,
	dsgSolicitudPrimeraRemision BIGINT NULL,
	dsgSolicitudSegundaRemision BIGINT NULL,
);
ALTER TABLE DetalleSolicitudGestionCobro_aud ADD CONSTRAINT FK_DetalleSolicitudGestionCobro_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE SolicitudGestionCobroElectronico_aud(
	sgeId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	sgeEstado VARCHAR(33) NOT NULL,
	sgeCartera BIGINT NOT NULL,
	sgeTipoAccionCobro VARCHAR(4) NOT NULL,
	sgeSolicitud BIGINT NOT NULL,
);
ALTER TABLE SolicitudGestionCobroElectronico_aud ADD CONSTRAINT FK_SolicitudGestionCobroElectronico_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE MotivoNoGestionCobro_aud(
	mgcId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	mgcCartera BIGINT NOT NULL,
	mgcTipo VARCHAR(36) NULL,
);
ALTER TABLE MotivoNoGestionCobro_aud ADD CONSTRAINT FK_MotivoNoGestionCobro_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE DetalleComunicadoEnviado_aud(
	dceId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	dceComunicado BIGINT NOT NULL,
	dceIdentificador BIGINT NULL,
	dceTipoTransaccion VARCHAR(100) NOT NULL,
);
ALTER TABLE DetalleComunicadoEnviado_aud ADD CONSTRAINT FK_DetalleComunicadoEnviado_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset atoro:04
--comment:Creacion de la tabla ParametrizacionCriteriosGestionCobro_aud
CREATE TABLE ParametrizacionCriteriosGestionCobro_aud(
	pacId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pcrLineaCobro VARCHAR(3) NULL,
	pcrActiva BIT NULL,
	pcrMetodo VARCHAR(10) NULL,
	pcrAutomatica VARCHAR(10) NULL,
	pcrCorteEntidades BIGINT NULL,
);
ALTER TABLE ParametrizacionCriteriosGestionCobro_aud ADD CONSTRAINT FK_ParametrizacionCriteriosGestionCobro_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset jusanchez:05
--comment:Creacion de la tabla ParametrizacionCriteriosGestionCobro_aud
ALTER TABLE ParametrizacionCriteriosGestionCobro_aud DROP CONSTRAINT FK_ParametrizacionCriteriosGestionCobro_aud_REV;
DROP TABLE ParametrizacionCriteriosGestionCobro_aud;
CREATE TABLE ParametrizacionCriterioGestionCobro_aud(
	pacId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	pcrLineaCobro VARCHAR(3) NULL,
	pcrActiva BIT NULL,
	pcrMetodo VARCHAR(10) NULL,
	pcrAutomatica VARCHAR(10) NULL,
	pcrCorteEntidad BIGINT NULL,
);
ALTER TABLE ParametrizacionCriterioGestionCobro_aud ADD CONSTRAINT FK_ParametrizacionCriterioGestionCobro_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

--changeset jusanchez:06
--comment:Se modifica nombre de campo en la tabla ParametrizacionCriterioGestionCobro_aud y se modifica tamaño a campo en la tabla ParametrizacionCartera_aud 
EXEC SP_RENAME 'ParametrizacionCriterioGestionCobro_aud.pcrAutomatica', 'pcrAccion', 'COLUMN';
ALTER TABLE ParametrizacionCartera_aud ALTER COLUMN pacTipoParametrizacion VARCHAR(74);

--changeset atoro:07
--comment:Se adicionan y modifican campos en las tablas DetalleSolicitudGestionCobro_aud y SolicitudGestionCobroFisico_aud
ALTER TABLE DetalleSolicitudGestionCobro_aud ADD dsgResultadoPrimeraEntrega VARCHAR(18) NULL;
ALTER TABLE DetalleSolicitudGestionCobro_aud ADD dsgResultadoSegundaEntrega VARCHAR(18) NULL;
ALTER TABLE DetalleSolicitudGestionCobro_aud ADD dsgDocumentoPrimeraRemision BIGINT NULL;
ALTER TABLE DetalleSolicitudGestionCobro_aud ADD dsgDocumentoSegundaRemision BIGINT NULL;       
ALTER TABLE DetalleSolicitudGestionCobro_aud ALTER COLUMN dsgEstado VARCHAR(52) NULL;
ALTER TABLE SolicitudGestionCobroFisico_aud ALTER COLUMN sgfEstado VARCHAR(52) NULL;
EXEC sp_rename 'SolicitudGestionCobroFisico_aud.sgfDocumentoCartera', 'sgfDocumentoSoporte', 'COLUMN';
