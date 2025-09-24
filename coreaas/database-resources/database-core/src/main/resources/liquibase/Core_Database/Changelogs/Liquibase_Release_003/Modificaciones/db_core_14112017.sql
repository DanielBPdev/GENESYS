--liquibase formatted sql

--changeset jocorrea:01
--comment:Se actualiza registro en la tabla ValidacionProceso
UPDATE ParametrizacionNovedad SET novTipoNovedad = 'GENERAL', novRutaCualificada = 'com.asopags.novedades.convertidores.empleador.ActualizarEmpleadorNovedad' WHERE novTipoTransaccion = 'ACTIVAR_INACTIVAR_CODIGO_SUCURSAL_DEBE_COINCIDIR_CON_PILA';
