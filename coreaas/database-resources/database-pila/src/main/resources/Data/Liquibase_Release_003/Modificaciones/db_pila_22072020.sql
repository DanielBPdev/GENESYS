--liquibase formatted sql

--changeset squintero:01
--comment: creación de flag para la ejecución de la HU 410 automática
INSERT INTO pila.dbo.PilaEjecucionGestionInconsistencia (pegProceso,pegEjecucionActiva,pegEstado) VALUES ('410',0,'FINALIZADO_EXITOSO');