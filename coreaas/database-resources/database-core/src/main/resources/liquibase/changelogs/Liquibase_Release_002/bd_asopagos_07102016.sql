--liquibase formatted sql

--changeset mgiraldo:01 stripComments:false 
ALTER TABLE ARL ALTER COLUMN arlNombre VARCHAR(25) ;
ALTER TABLE CajaCompensacion ALTER COLUMN ccfNombre VARCHAR(100) ;
ALTER TABLE Comunicado ALTER COLUMN comTextoAdicionar VARCHAR(500) ; 
ALTER TABLE Empleador ALTER COLUMN empValorTotalUltimaNomina NUMERIC(19) ; 
ALTER TABLE EscalaSoliciAfiliEmpleador ALTER COLUMN eaeAsunto VARCHAR(100) ; 
ALTER TABLE EscalaSoliciAfiliEmpleador ALTER COLUMN eaeDescripcion VARCHAR(500) ; 
ALTER TABLE SedeCajaCompensacion ALTER COLUMN sccfNombre VARCHAR(100) ; 
ALTER TABLE SucursalEmpleador ALTER COLUMN sueNombre VARCHAR(40) ; 
ALTER TABLE TipoSolicitante ALTER COLUMN tsoDescripcion VARCHAR(100) ; 
ALTER TABLE VariableComunicado ALTER COLUMN vcoClave VARCHAR(55) ; 
ALTER TABLE VariableComunicado ALTER COLUMN vcoNombre VARCHAR(50) ; 

--changeset mgiraldo:02 stripComments:false  

DROP TABLE HistoriaResultadoValidacion;

CREATE TABLE HistoriaResultadoValidacion
(
   hrvId bigint IDENTITY(1,1) NOT NULL,
   hrvDetalle varchar(20),
   hrvResultado varchar(20),
   hrvValidacion varchar(30),
   hrvIdDatosRegistro bigint
   CONSTRAINT PK_HistoriaResultadoValidacion_hrvId PRIMARY KEY (hrvId) 
   
)
GO
ALTER TABLE HistoriaResultadoValidacion ADD CONSTRAINT FK_HistoriaResultadoValidacion_hrvIdDatosRegistro FOREIGN KEY (hrvIdDatosRegistro) REFERENCES DatosRegistroValidacion(drvId)
GO


--changeset jcamargo:03 stripComments:false 
/*07/10/2016-jcamargo-HU-110*/
INSERT INTO Parametro (prmNombre, prmValor) values ('112_ABRIR_LINK_TIME', '1d');
INSERT INTO Parametro (prmNombre, prmValor) values ('112_DILIGENCIAR_FORMULARIO_TIMER ', '1d');
INSERT INTO Parametro (prmNombre, prmValor) values ('112_IMPRIMIR_FORMULARIO_TIMER', '1d');
INSERT INTO Parametro (prmNombre, prmValor) values ('112_CORREGIR_INFORMACION_TIMER', '1d');
INSERT INTO Parametro (prmNombre, prmValor) values ('BPMS_PROCESS_AFIL_EMP_WEB_DEPLOYMENTID', 'com.asopagos.coreaas.bpm.afiliacion_empresas_web:afiliacion_empresas_web:0.0.2-SNAPSHOT');

--changeset jcamargo:04 stripComments:false 
/*07/10/2016-jcamargo-HU-TRA*/

ALTER TABLE HistoriaResultadoValidacion ADD  hrvTipoExepcion VARCHAR(30);