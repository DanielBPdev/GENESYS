--liquibase formatted sql

--changeset borozco:01
--comment: Creacion tabla SolicitudPreventivaAgrupadora
 CREATE TABLE SolicitudPreventivaAgrupadora_aud (
 spaId bigint NOT NULL,
 REV bigint NOT NULL,
 REVTYPE smallint,
 spaEstadoSolicitudPreventivaAgrupadora varchar(255) NULL,
 spaSolicitudGlobal bigint NULL,
 CONSTRAINT FK_SolicitudPreventivaAgrupadora_aud_REV FOREIGN KEY (REV) REFERENCES Revision(revId)
)

--changeset borozco:02
--comment: Se agregan campos a tabla SolicitudPreventiva
ALTER TABLE SolicitudPreventiva_aud ADD sprCantidadVecesMoroso SMALLINT;
ALTER TABLE SolicitudPreventiva_aud ADD sprEstadoActualCartera VARCHAR(6);
ALTER TABLE SolicitudPreventiva_aud ADD sprFechaLimitePago DATE  ;
ALTER TABLE SolicitudPreventiva_aud ADD sprSolicitudPreventivaAgrupadora BIGINT;
ALTER TABLE SolicitudPreventiva_aud ADD sprTrabajadoresActivos SMALLINT;
ALTER TABLE SolicitudPreventiva_aud ADD sprValorPromedioAportes NUMERIC(19,2);
