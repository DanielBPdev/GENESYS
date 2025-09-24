IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='empleadoresFaltantesRetiroMoraAportes' AND TABLE_SCHEMA = 'dbo')
create table dbo.empleadoresFaltantesRetiroMoraAportes (
	efrmId INT IDENTITY(1,1) PRIMARY KEY,
	efrmNumeroRadicado varchar(25),
	efrmIdEmpleador varchar(13),
	efrmEstadoProceso varchar(12),
	efrmIntentosDiarios int
);


if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'CONTINUACION_EXPULSION_MORA_EMPLEADORES')
begin
    insert into ParametrizacionEjecucionProgramada (
        pepProceso,pepHoras,pepMinutos,pepSegundos,
        pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,
        pepFechaFin,pepFrecuencia,pepEstado) values (
            'CONTINUACION_EXPULSION_MORA_EMPLEADORES',
            null,null,'60',null,null,null,null,null,null,'INTERVALO','INACTIVO')
end