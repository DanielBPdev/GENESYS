--liquibase formatted sql

--changeset hhernandez:01
--comment: Eliminacion de las tablas OperadorInformacionCcf, OperadorInformacion, ConexionOperadorInformacion, PilaTasasInteresMora, PilaIndiceCorreccionPlanilla, PilaEjecucionProgramada, PilaClasificacionAportante
ALTER TABLE dbo.OperadorInformacionCcf DROP CONSTRAINT FK_OperadorInformacionCcf_oicCajaCompensacion;
ALTER TABLE dbo.OperadorInformacionCcf DROP CONSTRAINT FK_OperadorInformacionCcf_oicOperadorInformacion;
DROP TABLE OperadorInformacionCcf;
DROP TABLE dbo.OperadorInformacion;
DROP TABLE dbo.ConexionOperadorInformacion;  
DROP TABLE dbo.PilaTasasInteresMora;
ALTER TABLE dbo.PilaIndiceCorreccionPlanilla DROP CONSTRAINT FK_PilaIndiceCorreccionPlanilla_picPilaIndicePlanilla;
DROP TABLE dbo.PilaIndiceCorreccionPlanilla;
ALTER TABLE dbo.PilaEjecucionProgramada DROP CONSTRAINT FK_PilaEjecucionProgramada_pepCajaCompensacion;
DROP TABLE dbo.PilaEjecucionProgramada;
DROP TABLE dbo.CajaCompensacion;
ALTER TABLE dbo.PilaNormatividadFechaVencimiento DROP CONSTRAINT FK_PilaNormatividadFechaVencimiento_pfvClasificacionAportante;
TRUNCATE TABLE dbo.PilaNormatividadFechaVencimiento;
DROP TABLE dbo.PilaClasificacionAportante;

--changeset hhernandez:02
--comment:-Creacion de la tabla dbo.PilaCondicionAportanteVencimiento    
CREATE TABLE dbo.PilaCondicionAportanteVencimiento(
	pcaId bigint IDENTITY(1,1) NOT NULL,
	pcaTipoArchivo varchar(20) NOT NULL,
	pcaCampo smallint NOT NULL,
	pcaValor varchar(30) NOT NULL,
	pcaComparacion varchar(20) NOT NULL,
 CONSTRAINT PK_PilaCondicionAportanteVencimiento_pcaId PRIMARY KEY (pcaId)
);
ALTER TABLE dbo.PilaNormatividadFechaVencimiento ADD CONSTRAINT FK_PilaNormatividadFechaVencimiento_pfvClasificacionAportante FOREIGN KEY(pfvClasificacionAportante) REFERENCES dbo.PilaCondicionAportanteVencimiento (pcaId);

--changeset hhernandez:03
--comment: Creacion de la tabla dbo.PilaSolicitudCambioNumIdentAportante    
CREATE TABLE dbo.PilaSolicitudCambioNumIdentAportante(
	pscId bigint IDENTITY(1,1) NOT NULL,
	pscAccion varchar(75) NULL,
	pscEstadoArchivoAfectado varchar(75) NULL,
	pscFechaSolicitud date NOT NULL,
	pscUsuario varchar(25) NOT NULL,
	pscNumeroIdentificacion bigint NOT NULL,
	pscArchivosCorreccion varchar(225) NULL,
	pscUsuarioAprobador varchar(25) NULL,
	pscFechaRespuesta date NULL,
	pscRazonRechazo varchar(25) NULL,
	pscIdPlanillaInformacion bigint NOT NULL,
	pscIdPlanillaFinanciera bigint NOT NULL,
	pscPilaIndicePlanilla bigint NOT NULL,
	pscComentarios varchar(500) NULL,
 CONSTRAINT PK_PilaSolicitudCambioNumIdentAportante_pscId PRIMARY KEY (pscId) 
);
ALTER TABLE dbo.PilaSolicitudCambioNumIdentAportante ADD CONSTRAINT FK_PilaSolicitudCambioNumIdentAportante_pscPilaIndicePlanilla FOREIGN KEY(pscPilaIndicePlanilla) REFERENCES dbo.PilaIndicePlanilla (pipId);