--liquibase formatted sql

--changeset jusanchez:01 context:asopagos_confa
--comment: Se actualiza registro en la tabla parametro
UPDATE Parametro SET prmValor='48886d02-b8ea-46b0-a5c9-281c80029158_1.0' WHERE prmNombre='ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122';

--changeset jusanchez:01 context:asopagos_funcional
--comment: Se actualiza registro en la tabla parametro
UPDATE Parametro SET prmValor='48886d02-b8ea-46b0-a5c9-281c80029158_1.0' WHERE prmNombre='ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122';

--changeset flopez:02
--comment: Se elimina registro de la tabla ParametrizacionEjecucionProgramada
DELETE FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'DESAFILIAR_AUTOMATICAMENTE_EMPLEADORES_SOLICITUD_RECHAZADA';