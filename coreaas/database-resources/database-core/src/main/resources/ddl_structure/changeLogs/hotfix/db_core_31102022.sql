INSERT INTO ParametrizacionEjecucionProgramada
( pepProceso
, pepHoras
, pepMinutos
, pepSegundos
, pepDiaSemana
, pepDiaMes
, pepMes
, pepAnio
, pepFechaInicio
, pepFechaFin
, pepFrecuencia
, pepEstado)
VALUES ('ACTA_LIQUIDACION_APORTES', 17, 53, 00, null, null, null, null, null, null, 'DIARIO', 'ACTIVO');


ALTER TABLE AccionCobro2C
ADD aocDiasGeneracionActa NUMBER(255) NULL;