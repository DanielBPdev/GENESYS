--liquibase formatted sql

--changeset atoro:01
--comment: Se elimina la tabla RegistroPilaNovedad y se crea la tabla RegistroNovedadFutura
--Eliminacion de la tabla RegistroPilaNovedad
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnTipoNovedadPila')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnTipoNovedadPila;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnTipoTransaccionNovedad')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnTipoTransaccionNovedad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnMarcaNovedad')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnMarcaNovedad;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnCanalRecepcion')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnCanalRecepcion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_RegistroPilaNovedad_rpnEstadoNovedad')) ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT CK_RegistroPilaNovedad_rpnEstadoNovedad;
ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT FK_RegistroPilaNovedad_rpnGlosaNovedad;
ALTER TABLE RegistroPilaNovedad DROP CONSTRAINT FK_RegistroPilaNovedad_rpnRegistroAporteDetallado;
DROP TABLE RegistroPilaNovedad;

--Creacion de la tabla RegistroNovedadFutura
CREATE TABLE RegistroNovedadFutura(
	rnfId bigint IDENTITY(1,1) NOT NULL,
	rnfFechaInicio date NULL,
	rnfFechaFin date NULL,
	rnfTipoTransaccion varchar (90) NULL,
	rnfCanalRecepcion varchar(30) NULL,
	rnfComentarios varchar(250) NULL,
	rnfPersona bigint NULL,
CONSTRAINT PK_RegistroNovedadFutura_coiId PRIMARY KEY (rnfId)
);
ALTER TABLE RegistroNovedadFutura WITH CHECK ADD CONSTRAINT FK_RegistroNovedadFutura_rnfPersona FOREIGN KEY (rnfPersona) REFERENCES Persona (perId);

--changeset criparra:02
--comment: Se agrega el campo soaObservacionesSupervisor en la tabla SolicitudAporte
ALTER TABLE SolicitudAporte ADD soaObservacionesSupervisor varchar(150) NULL;

--changeset clmarin:03
--comment: Se actualiza registro de la tabla PrioridadDestinatario
UPDATE PrioridadDestinatario SET prdGrupoPrioridad=9 WHERE prdDestinatarioComunicado=54;

--changeset jusanchez:04
--comment: Se actualiza registros de las tablas LINELOADCATALOG,VALIDATORCATALOG, VALIDATORCATALOG y FIELDDEFINITIONLOAD
UPDATE LINELOADCATALOG set className='com.asopagos.aportes.load.PagoManualAportePersistLine' WHERE ID= 1212; 
UPDATE VALIDATORCATALOG set className='com.asopagos.aportes.validator.PagoManualAporteLineValidator' where id = 1212;
UPDATE LINEDEFINITIONLOAD set identifier=0 where id=1212;
UPDATE FIELDDEFINITIONLOAD set identifierLine=0 where LINEDEFINITION_ID=100000;
