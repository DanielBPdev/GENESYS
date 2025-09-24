if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'DISPERSAR_ANULACIONES_PRESCRIPCION')
begin
    insert into ParametrizacionEjecucionProgramada (
        pepProceso,pepHoras,pepMinutos,pepSegundos,
        pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,
        pepFechaFin,pepFrecuencia,pepEstado) values (
            'DISPERSAR_ANULACIONES_PRESCRIPCION',
            '09','00','00',null,null,null,null,null,null,'DIARIO','INACTIVO')
end