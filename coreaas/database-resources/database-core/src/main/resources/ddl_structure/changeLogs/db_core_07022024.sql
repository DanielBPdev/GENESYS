
if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL')
begin
    insert into ParametrizacionEjecucionProgramada (
        pepProceso,pepHoras,pepMinutos,pepSegundos,
        pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,
        pepFechaFin,pepFrecuencia,pepEstado) values (
            'CAMBIO_CLASE_TRABAJADOR_TRABAJADOR_DEPENDIENTE_PRESENCIAL',
            '09','00',null,null,null,null,null,null,null,'DIARIO','INACTIVO')
end