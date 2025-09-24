--liquibase formatted sql

--changeset jocampo:01
--comment: Actualizacion tabla ParametrizacionEjecucionProgramada
UPDATE ParametrizacionEjecucionProgramada SET pepHoras = '00,12' WHERE pepProceso = 'EJECUTAR_ORQUESTADOR_STAGING_SUBSIDIO';