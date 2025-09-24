IF NOT EXISTS (
    SELECT * FROM INFORMATION_SCHEMA.TABLES 
    WHERE TABLE_SCHEMA = 'SAT' 
      AND TABLE_NAME = 'AUD_AFILIACION_EMPLEADORES_PRIMER_VEZ'
)
BEGIN
    CREATE TABLE SAT.AUD_AFILIACION_EMPLEADORES_PRIMER_VEZ(
        id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
        fecha date NOT NULL,
        numero_transaccion varchar(100) NOT NULL,
        status varchar(10) NOT NULL,
        URL varchar(500) NULL,
        REQUEST varchar(4000) NULL,
        RESPONSE varchar(4000) NULL
    );
END
if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'EJECUCION_SAT_REPORTE_MINISTERIO_AFILIACION_PRIMER_VEZ')
begin
    insert into ParametrizacionEjecucionProgramada (
        pepProceso,pepHoras,pepMinutos,pepSegundos,
        pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,
        pepFechaFin,pepFrecuencia,pepEstado) values (
            'EJECUCION_SAT_REPORTE_MINISTERIO_AFILIACION_PRIMER_VEZ',
            '09','00','00',null,null,null,null,null,null,'DIARIO','INACTIVO')
end
if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'CORRECCION_CATEGORIAS_AFILIADOS_BENEFICIARIOS')
begin
    insert into ParametrizacionEjecucionProgramada (
        pepProceso,pepHoras,pepMinutos,pepSegundos,
        pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,
        pepFechaFin,pepFrecuencia,pepEstado) values (
            'CORRECCION_CATEGORIAS_AFILIADOS_BENEFICIARIOS',
            '09','00','00',null,null,null,null,null,null,'DIARIO','INACTIVO')
end
IF NOT EXISTS (
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = 'SAT' 
      AND TABLE_NAME = 'WS_TRANSACCION' 
      AND COLUMN_NAME = 'ESTADO_AFILIACION'
)
BEGIN
    ALTER TABLE SAT.WS_TRANSACCION
    ADD ESTADO_AFILIACION VARCHAR(80) NULL;
END
