--liquibase formatted sql

--changeset clmarin:01
--comment:mantis 
CREATE TABLE HistoricoAsignacionCartera (
hacId bigint NOT NULL IDENTITY(1,1),
hacCartera bigint NOT NULL, 
hacTipoAccionCobro varchar(4),
hacFechaAsignacionAccion datetime
CONSTRAINT PK_HistoricoAsignacionCartera_hacId PRIMARY KEY (hacId),
);

alter table HistoricoAsignacionCartera ADD CONSTRAINT FK_HistoricoAsignacionCartera_hacCartera FOREIGN KEY (hacCartera) REFERENCES  Cartera (carId)

--changeset mamonroy:02
--comment:mantis
ALTER TABLE IntentoAfiliacion ADD iafComunicado BIGINT;
ALTER TABLE aud.IntentoAfiliacion_aud ADD iafComunicado BIGINT;