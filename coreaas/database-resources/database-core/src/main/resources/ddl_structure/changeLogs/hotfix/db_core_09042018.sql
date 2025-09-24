--liquibase formatted sql

--changeset mamonroy:01
--comment: Eliminación y Adicion de campo en la tabla VALIDACIONPROCESO
DELETE FROM ValidacionProceso WHERE vapProceso = 'NOVEDADES_FOVIS_REGULAR' AND vapBloque = 'PRORROGA_FOVIS' AND vapObjetoValidacion = 'HOGAR' AND vapValidacion IN ('VALIDACION_ESTADO_HOGAR_ASIGNADO_PRIMERA_PRORROGA_54_FOVIS','VALIDACION_ESTADO_HOGAR_ASIGNADO_SIN_PRORROGA_53_FOVIS');
INSERT INTO ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoproceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES('PRORROGA_FOVIS','VALIDACION_ESTADO_HOGAR_NOVEDAD_FOVIS_135','NOVEDADES_FOVIS_REGULAR','ACTIVO',1,'HOGAR',0);