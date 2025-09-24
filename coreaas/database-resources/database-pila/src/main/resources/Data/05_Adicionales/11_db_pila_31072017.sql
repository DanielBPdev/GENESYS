--liquibase formatted sql

--changeset abaquero:01
--comment: Se alteran campos de las tablas PilaArchivoARegistro1,PilaArchivoAPRegistro1, PilaArchivoFRegistro5, PilaArchivoIPRegistro1
ALTER TABLE dbo.PilaArchivoARegistro1 ALTER COLUMN pa1IdRepresentante varchar(16) NULL;
ALTER TABLE dbo.PilaArchivoARegistro1 ALTER COLUMN pa1TipoIdRepresentante varchar(2) NULL;
ALTER TABLE dbo.PilaArchivoAPRegistro1 ALTER COLUMN ap1IdRepresentante varchar(16) NULL;
ALTER TABLE dbo.PilaArchivoAPRegistro1 ALTER COLUMN ap1TipoIdRepresentante varchar(2) NULL;
ALTER TABLE dbo.PilaArchivoFRegistro5 ALTER COLUMN pf5SistemaPago varchar(2) NULL;
ALTER TABLE dbo.PilaArchivoIRegistro1 ALTER COLUMN pi1FechaActualizacion date NOT NULL;
ALTER TABLE dbo.PilaArchivoIPRegistro1 ALTER COLUMN ip1FechaActualizacion date NOT NULL;

--changeset arocha:02
--comment: Se agrega campo TarifaBaseEmpleador y SMMV en la tabla staging.Aportante y RegistroGeneral. Se agrega tabla RegistroLog
CREATE TABLE staging.RegistroLog
(
	relId BIGINT IDENTITY,
	relFecha DATETIME NOT NULL,
	relParametrosEjecucion VARCHAR(255) NULL,
	relErrorMessage VARCHAR(MAX) NOT NULL,
	CONSTRAINT PK_RegistroLog PRIMARY KEY (relId)
);

ALTER TABLE staging.RegistroGeneral ADD regOUTTarifaBaseEmpleador NUMERIC(5,5);
ALTER TABLE staging.RegistroGeneral ADD regOUTSMMLV NUMERIC(19,5);

INSERT INTO staging.StagingParametros VALUES ('TARIFA_BASE_EMPLEADOR','0.04');
INSERT INTO staging.StagingParametros VALUES ('SMMLV','737717');

UPDATE staging.RegistroGeneral SET regOUTTarifaBaseEmpleador = '0.04', regOUTSMMLV = '737717';
 
--changeset abaquero:03
--comment: Se agregan campos en la tabla PilaEstadoBloque
ALTER TABLE dbo.PilaEstadoBloque ADD pebEstadoBloque10 varchar(75);
ALTER TABLE dbo.PilaEstadoBloque ADD pebAccionBloque10 varchar(75);
