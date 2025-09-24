--liquibase formatted sql

--changeset mosanchez:01
--comment: Se actualiza registros en la tabla TemNovedad
UPDATE TemNovedad SET tenTipoTransaccion = 'INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL' WHERE tenTipoTransaccion = 'INCAPACIDAD_POR_ACCIDENTE_TRABAJO';