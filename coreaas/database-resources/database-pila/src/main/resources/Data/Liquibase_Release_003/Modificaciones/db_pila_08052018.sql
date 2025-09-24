--liquibase formatted sql

--changeset abaquero:01
--comment:Adición de campo pipPresentaRegistro4 y pipMotivoProcesoManual tabla PilaIndicePlanilla 
ALTER TABLE PilaIndicePlanilla ADD pipPresentaRegistro4 bit;
ALTER TABLE PilaIndicePlanilla ADD pipMotivoProcesoManual varchar(20);

--changeset abaquero:02
--comment:Creacion tabla PilaArchivoIRegistro4
CREATE TABLE PilaArchivoIRegistro4 (
	pi4Id bigint NOT NULL IDENTITY(1,1),
	pi4IndicePlanilla bigint NOT NULL,
	pi4IndicadorUGPP smallint NOT NULL,
	pi4ActoAdministrativo varchar(14) NOT NULL,
	pi4FechaAperturaLiquidacionForzosa date,
	pi4NombreEntidadLiquidacionForzosa varchar(20),
	pi4ValorSancion numeric(19,5) NOT NULL,
	CONSTRAINT PK_PilaArchivoIRegistro4_pi4Id PRIMARY KEY (pi4Id)
);

--changeset abaquero:03
--comment: Se agrega relacion FK_PilaArchivoIRegistro4_pi4IndicePlanilla
ALTER TABLE PilaArchivoIRegistro4 ADD CONSTRAINT FK_PilaArchivoIRegistro4_pi4IndicePlanilla FOREIGN KEY(pi4IndicePlanilla) REFERENCES PilaIndicePlanilla (pipId);


--changeset abaquero:04
--comment: Creacion campos tabla TemAportante
ALTER TABLE TemAportante ADD tapPrimerNombreAportante varchar(20);
ALTER TABLE TemAportante ADD tapSegundoNombreAportante varchar(30);
ALTER TABLE TemAportante ADD tapPrimerApellidoAportante varchar(20);
ALTER TABLE TemAportante ADD tapSegundoApellidoAportante varchar(30);

--changeset abaquero:05
--comment: Creacion tabla LogErrorPilaM1
CREATE TABLE LogErrorPilaM1 (
	lp1Id bigint NOT NULL IDENTITY(1,1), 
	lp1IndicePlanilla bigint NOT NULL, 
	lp1FechaHoraError datetime NOT NULL, 
	lp1NombreArchivo varchar(80) NOT NULL, 
	lp1Mensaje varchar(2000) NOT NULL, 
	CONSTRAINT PK_LogErrorPilaM1_lp1Id PRIMARY KEY (lp1Id)
);

--changeset abaquero:06
--comment: adicion campos tabla staging.RegistroGeneral
ALTER TABLE staging.RegistroGeneral ADD regOUTMotivoProcesoManual varchar(20);
ALTER TABLE staging.RegistroGeneral ADD regNumPlanillaAsociada varchar(10);
ALTER TABLE staging.RegistroGeneral ADD regOUTPrimerNombreAportante varchar(20);
ALTER TABLE staging.RegistroGeneral ADD regOUTSegundoNombreAportante varchar(30);
ALTER TABLE staging.RegistroGeneral ADD regOUTPrimerApellidoAportante varchar(20);
ALTER TABLE staging.RegistroGeneral ADD regOUTSegundoApellidoAportante varchar(30);
ALTER TABLE staging.RegistroDetallado ADD redOUTValorIBCMod numeric(19, 5);

--changeset abaquero:07
--comment: actualizacion campo stpValorParametro
UPDATE staging.StagingParametros SET stpValorParametro = '3,4,16,34,35,36,53,57,60,61' WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE';
