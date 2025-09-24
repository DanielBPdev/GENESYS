--liquibase formatted sql

--changeset jocampo:01
--comment: 
CREATE TABLE PilaEstadoTransitorio (
    petId bigint IDENTITY(1,1) NOT NULL,
    petPilaIndicePlanilla bigint,
    petAccion VARCHAR(30),
    petEstado VARCHAR(30),
    pedFecha DATETIME,
    pedFechaReanudado DATETIME
)
CREATE CLUSTERED INDEX ix_Pila_Estado_Transitorio ON PilaEstadoTransitorio (petPilaIndicePlanilla);
