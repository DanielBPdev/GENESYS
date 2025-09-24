--liquibase formatted sql

--changeset jzambrano:01
--comment:se elimina columna benEstadoBeneficiarioCaja de tabla Beneficiario
ALTER TABLE Beneficiario DROP COLUMN benEstadoBeneficiarioCaja;

--changeset jocorrea:02
--comment:Se actualiza registro en la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapBloque = 'NOVEDAD_INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_DEPWEB' WHERE vapBloque = 'NOVEDAD_INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_WEB' AND vapProceso = 'NOVEDADES_DEPENDIENTE_WEB';
