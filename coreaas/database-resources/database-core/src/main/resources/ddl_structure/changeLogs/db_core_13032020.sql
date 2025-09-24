--liquibase formatted sql

--changeset flopez:01
--comment: CC Novedades

INSERT INTO ParametrizacionNovedad (novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles) VALUES('CAMBIO_FACTOR_VULNERABILIDAD_PERSONAS','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona','GENERAL','NOVEDADES_PERSONAS_PRESENCIAL',NULL);
INSERT INTO ParametrizacionNovedad (novTipoTransaccion,novPuntoResolucion,novRutaCualificada,novTipoNovedad,novProceso,novAplicaTodosRoles) VALUES('CAMBIO_PERTENENCIA_ETNICA_PERSONAS','FRONT','com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona','GENERAL','NOVEDADES_PERSONAS_PRESENCIAL',NULL);

UPDATE ParametrizacionNovedad set novtiponovedad = 'GRUPO_FAMILIAR' WHERE novTipoTransaccion LIKE 'ACTUALIZACION_SECTOR_UBICACION_GRUPOFAMILIAR%';
UPDATE ParametrizacionNovedad set novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona' where novTipoTransaccion like 'ACTUALIZACION_PAIS_RESIDENCIA_PERSONAS%';
UPDATE ParametrizacionNovedad set novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona' where novTipoTransaccion like 'CAMBIO_PERTENENCIA_ETNICA_PERSONAS%';
UPDATE ParametrizacionNovedad set novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona' where novTipoTransaccion like 'CAMBIO_FACTOR_VULNERABILIDAD_PERSONAS%';
UPDATE ParametrizacionNovedad set novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona' where novTipoTransaccion like 'CAMBIO_ORIENTACION_SEXUAL_PERSONAS%';