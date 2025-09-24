--liquibase formatted sql

--changeset mamonroy:01
--comment: Se modifica campo en la tabla HistoriaResultadoValidacion
ALTER TABLE HistoriaResultadoValidacion ALTER COLUMN hrvDetalle VARCHAR(400) NULL;

--changeset mamonroy:02
--comment:Actualizacion e Insercion de registros en la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapBloque = 'NOVEDAD_MOVILIZACION_AHORRO_PREVIO_PAGO_OFERENTE' WHERE vapProceso = 'NOVEDADES_FOVIS_REGULAR' AND vapBloque = 'NOVEDAD_ MOVILIZACION_AHORRO_PREVIO_PAGO_OFERENTE' AND vapValidacion= 'VALIDACION_ESTADO_HOGAR_ASIGNADO_62_FOVIS' AND vapObjetoValidacion = 'HOGAR'; 
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('NOVEDAD_RESTITUCION_SUBSIDIO_INCUMPLIMIENTO','VALIDACION_ESTADO_HOGAR_ASIG_PEND_SUBSI_69_FOVIS','NOVEDADES_FOVIS_REGULAR','ACTIVO',1,'HOGAR',0);
