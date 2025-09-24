
if not exists (select * from ParametrizacionEjecucionProgramada where pepProceso = 'PRESCRIBIR_PERIODOS_APORTES_MORA_EN_CARTERA_X_TIEMPO')
begin
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
    VALUES ('PRESCRIBIR_PERIODOS_APORTES_MORA_EN_CARTERA_X_TIEMPO', 17, 53, 00, null, null, null, null, null, null, 'DIARIO', 'INACTIVO');

end

if not exists (select * from Constante where cnsNombre = 'CARTERA_PRESCRIBIR_DIAS_CONTEO_GENERAR_LIQUIDACION')
begin
    INSERT INTO Constante
    ( cnsNombre
    , cnsValor
    , cnsDescripcion
    , cnsTipoDato)
    VALUES
    ('CARTERA_PRESCRIBIR_DIAS_CONTEO_GENERAR_LIQUIDACION', 1800, 'Identifica el numero de dias en el cual se debe realizar la prescripción de la cartera por ese periodo. ', 'NUMBER');
end