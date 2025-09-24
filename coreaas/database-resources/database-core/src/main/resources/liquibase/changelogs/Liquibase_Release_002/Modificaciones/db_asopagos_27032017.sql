--liquibase formatted sql

--changeset abaquero:01
--comment: Modificacion de registro de inconsistencias de PILA PT-INGE-035-211-392 Gestionar bandeja de inconsistencias.
 
drop table dbo.PilaPasoValores;
drop table dbo.PilaErrorValidacionLog;
 
CREATE TABLE dbo.PilaPasoValores (
                ppvId bigint NOT NULL IDENTITY,
                ppvIdPlanilla bigint NOT NULL,
                ppvTipoPlanilla varchar(75) NOT NULL,
                ppvBloque varchar(11) NOT NULL,
                ppvNombreVariable varchar(75) NOT NULL,
                ppvValorVariable varchar(200),
                ppvCodigoCampo varchar(10),
                CONSTRAINT PK_PilaPasoValores_ppvId PRIMARY KEY (ppvId)
);
 
create table dbo.PilaErrorValidacionLog (
                pevId bigint NOT NULL IDENTITY,
                pevIdIndicePlanilla bigint,
                pevTipoArchivo varchar(20),
                pevTipoError varchar(20),
                pevNumeroLinea smallint,
                pevBloqueValidacion varchar(11),
                pevNombreCampo varchar(150),
                pevPosicionInicial smallint,
                pevPosicionFinal smallint,
                pevValorCampo varchar(200),
                pevCodigoError varchar(10),
                pevMensajeError varchar(255),
                CONSTRAINT PK_PilaErrorValidacionLog_pevId PRIMARY KEY (pevId)
);


delete from ValidatorParamValue;
delete from ValidatorParameter;
delete from ValidatorDefinition where id < 1000;
delete from ValidatorCatalog where id < 1000;
delete from FileLoadedLog;
delete from FileLoaded;
delete from FIELDDEFINITIONLOAD where id < 1000;
delete from FIELDLOADCATALOG where id < 1000;
delete from LINEDEFINITIONLOAD where id < 1000;
delete from FILEDEFINITIONLOAD where id < 1000;
delete from LINELOADCATALOG where id < 1000;
delete from FILEDEFINITIONLOADTYPE where id < 1000;

--changeset atoro:02
--comment: Eliminacion en  VALIDACIONPROCESO
DELETE FROM VALIDACIONPROCESO WHERE vapBloque ='NOVEDAD_ANULACION_AFILIACION';


