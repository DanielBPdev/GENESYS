IF NOT EXISTS (SELECT * FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'ESTADISTICAS_GENESYS')
BEGIN
    INSERT INTO ParametrizacionEjecucionProgramada (
        pepProceso, pepHoras, pepMinutos, pepSegundos,
        pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio,
        pepFechaFin, pepFrecuencia, pepEstado) VALUES (
        'ESTADISTICAS_GENESYS',
        '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'DIARIO', 'INACTIVO'
    );
END