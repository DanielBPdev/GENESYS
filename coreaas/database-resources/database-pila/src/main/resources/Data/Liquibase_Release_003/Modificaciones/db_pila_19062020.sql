--liquibase formatted sql

--changeset silopez:01
--comment: 
INSERT INTO pila.[dbo].PilaEjecucionGestionInconsistencia (pegProceso, pegEjecucionActiva) VALUES ('21_392', 0);