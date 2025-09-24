--liquibase formatted sql

--changeset jocorrea:01
--comment:Se actualiza registro en la tabla ParametrizacionNovedad
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarRetiroNovedadPersona' WHERE novTipoTransaccion = 'RETIRO_PENSIONADO_25ANIOS';

--changeset jzambrano:02
--comment:Insercion de registro en la tabla ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapInversa) VALUES ('112-110-1','VALIDACION_TIEMPO_REINTEGRO','AFILIACION_EMPRESAS_WEB','ACTIVO',1,0);
