--liquibase formatted sql

--changeset jocorrea:01
--comment: Actualizacion tabla ParametrizacionNovedad
UPDATE ParametrizacionNovedad SET novRutaCualificada = 'com.asopagos.novedades.fovis.convertidores.LevantarInhabilidadSancionNovedadFovis' WHERE novTipoTransaccion = 'LEVANTAR_INHABILIDAD_SANCION_HOGAR';