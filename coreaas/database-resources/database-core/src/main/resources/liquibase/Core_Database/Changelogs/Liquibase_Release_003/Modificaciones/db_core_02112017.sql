--liquibase formatted sql

--changeset anvalbuena:01
--comment: Se adiciona campo en la tabla ParametrizacionEjecucionProgramada
ALTER TABLE ParametrizacionEjecucionProgramada ADD pepEstado VARCHAR(8);
UPDATE ParametrizacionEjecucionProgramada SET pepEstado = 'ACTIVO';

--changeset jocorrea:02
--comment: Se elimina registro en las tablas ParametrizacionNovedad y ParametrizacionEjecucionProgramada
DELETE FROM ParametrizacionNovedad WHERE novTipoTransaccion = 'ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE' AND novPuntoResolucion = 'SISTEMA_AUTOMATICO';
DELETE FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'ACTUALIZACION_VACACIONES_TRABAJADOR_DEPENDIENTE';

--changeset flopez:03
--comment: Se actualiza registros en la tabla Solicitud
UPDATE Solicitud SET solClasificacion='TRABAJADOR_DEPENDIENTE' WHERE solClasificacion='ADMINISTRADOR_SUBSIDIO';