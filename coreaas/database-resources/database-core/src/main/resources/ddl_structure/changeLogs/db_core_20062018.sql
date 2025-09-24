--liquibase formatted sql

--changeset fvasquez:01
--comment: Ajuste tamanio columna bcaActividad
ALTER TABLE BitacoraCartera ALTER COLUMN bcaActividad varchar(27);
ALTER TABLE aud.BitacoraCartera_aud ALTER COLUMN bcaActividad varchar(27);

--changeset jocorrea:02
--comment: Actualizacion tabla ParametrizacionNovedad
UPDATE ParametrizacionNovedad
SET novRutaCualificada = 'com.asopagos.novedades.convertidores.persona.ActualizarPersonaNovedadPersona'
WHERE novTipoTransaccion IN ('CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_PRESENCIAL','CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_WEB','CAMBIO_FECHA_EXPEDICION_DOCUMENTO_IDENTIDAD_PERSONA_DEPWEB','CAMBIO_FECHA_NACIMIENTO_PERSONA_PRESENCIAL','CAMBIO_FECHA_NACIMIENTO_PERSONA_WEB','CAMBIO_FECHA_NACIMIENTO_PERSONA_DEPWEB');