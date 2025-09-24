UPDATE validacionproceso
SET vapEstadoProceso = 'INACTIVO'
WHERE VAPBLOQUE IN ('NOVEDAD_CAMBIO_ORIENTACION_SEXUAL_PERSONAS','NOVEDAD_CAMBIO_FACTOR_VULNERABILIDAD_PERSONAS','NOVEDAD_CAMBIO_PERTENENCIA_ETNICA_PERSONAS')

if not exists(select * from ParametrizacionEjecucionProgramada where pepProceso = 'EJECUCIÓN_SAT_POR_PARTE_DE_LA_CCF')
begin
insert ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia,pepEstado)
values ('EJECUCIÓN_SAT_POR_PARTE_DE_LA_CCF','18','00', '00',null, null,null,null,null,null,'DIARIO','ACTIVO')
end