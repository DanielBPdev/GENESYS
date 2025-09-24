--liquibase formatted sql

--changeset squintero:01
--comment: creación de tabla para planillas candidatas a reproceso
CREATE TABLE pila.staging.PlanillasCandidatasReproceso(pcrId bigint IDENTITY(1,1) NOT NULL,
pcrIdRegistroGeneral bigint, 
pcrMotivoBloqueo varchar(100), 
pcrFechaCreacionRegistro datetime);
