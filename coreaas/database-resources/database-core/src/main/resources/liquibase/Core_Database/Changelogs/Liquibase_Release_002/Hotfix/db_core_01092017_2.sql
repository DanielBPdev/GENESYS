--liquibase formatted sql

--changeset jusanchez:01 context:asopagos_confa
--comment: Se actualiza registro en la tabla parametro
UPDATE Parametro SET prmValor='9552242c-640c-4c73-bf81-29a9cda1f38e_1.0' WHERE prmNombre='ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122';

--changeset jusanchez:01 context:asopagos_funcional
--comment: Se actualiza registro en la tabla parametro
UPDATE Parametro SET prmValor='9552242c-640c-4c73-bf81-29a9cda1f38e_1.0' WHERE prmNombre='ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122';