--liquibase formatted sql

--changeset fvasquez:01
--comment: 
ALTER TABLE datotemporalcartera drop CONSTRAINT FK_DatoTemporalCartera_dtaCarteraAgrupadora;
ALTER TABLE datotemporalcartera drop column dtaCarteraAgrupadora;
ALTER TABLE datotemporalcartera add dtaNumeroOperacion bigint NULL;

ALTER TABLE bitacoracartera ALTER column bcaActividad VARCHAR(36) NULL;
ALTER TABLE bitacoracartera ALTER column bcaResultado VARCHAR(33) NULL;