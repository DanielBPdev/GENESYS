--liquibase formatted sql

--changeset slopez:01
--comment: 
CREATE TABLE pila.dbo.PilaEjecucionGestionInconsistencia
   (
    pegId int IDENTITY(1,1),
	pegProceso varchar(25),
    pegEjecucionActiva BIT,
    pegFechaUltimaEjecucion datetime,
    pegEstado varchar(25)
)

INSERT INTO pila.dbo.PilaEjecucionGestionInconsistencia (pegProceso, pegEjecucionActiva)
VALUES ('21_399', 0);