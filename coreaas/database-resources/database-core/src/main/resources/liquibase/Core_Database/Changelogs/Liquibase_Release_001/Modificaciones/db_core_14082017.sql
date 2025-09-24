--liquibase formatted sql

--changeset flopez:01
--comment: Se actualizan registros en la tabla ParametrizacionNovedad
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.convertidores.empleador.EjecutarSustitucionPatronalNovedad' WHERE novTipotransaccion = 'SUSTITUCION_PATRONAL';