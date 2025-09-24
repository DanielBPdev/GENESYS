--liquibase formatted sql

--changeset atoro:01 
--comment: Actualizacion en la tabla Novedad
UPDATE Novedad SET novRutaCualificada='com.asopagos.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novtipotransaccion='DESAFILIACION';

--changeset atoro:02
--comment: Actualizacion en la tabla Novedad
UPDATE Novedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.empleador.ActualizarBeneficioEmpleadorNovedad' WHERE novTipoTransaccion IN('ACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL','ACTIVAR_BENEFICIOS_LEY_1429_2010_WEB','INACTIVAR_BENEFICIOS_LEY_1429_2010_PRESENCIAL','INACTIVAR_BENEFICIOS_LEY_1429_2010_WEB','ACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL','ACTIVAR_BENEFICIOS_LEY_590_2000_WEB','INACTIVAR_BENEFICIOS_LEY_590_2000_PRESENCIAL','INACTIVAR_BENEFICIOS_LEY_590_2000_WEB');

--changeset flopez:03
--comment: Eliminacion de la tabla SolicitudNovedadPersona
DROP TABLE SolicitudNovedadPersona
DROP SEQUENCE SEC_consecutivoSna

--changeset flopez:04
--comment: Creacion de la tabla SolicitudNovedadPersona y la secuencia
CREATE TABLE SolicitudNovedadPersona(
	snpId bigint NOT NULL,
	snpPersona bigint NOT NULL,
	snpSolicitudNovedad bigint NOT NULL
CONSTRAINT PK_SolicitudNovedadPersona_snpId PRIMARY KEY (snpId)
);
CREATE SEQUENCE SEC_consecutivoSnp START WITH 1 INCREMENT BY 1 ;

ALTER TABLE SolicitudNovedadPersona WITH CHECK ADD CONSTRAINT FK_SolicitudNovedadPersona_snpPersona FOREIGN KEY (snpPersona) REFERENCES Persona (perId);
ALTER TABLE SolicitudNovedadPersona WITH CHECK ADD CONSTRAINT FK_SolicitudNovedadPersona_snpSolicitudNovedad FOREIGN KEY (snpSolicitudNovedad) REFERENCES SolicitudNovedad (snoId);

--changeset lzarate:05
--comment: Actualizacion de ParametrizacionEjecucionProgramada 
UPDATE ParametrizacionEjecucionProgramada SET pepProceso = 'INACTIVAR_ACTIVAR_USUARIOS_TEMPORALES' WHERE pepProceso = 'INACTIVAR_USUARIOS_TEMPORALES';

