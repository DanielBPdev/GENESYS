IF NOT EXISTS (SELECT * FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'DESISTIR_SOLICITUD_AFILIACION')
BEGIN
    INSERT INTO ParametrizacionEjecucionProgramada (
        pepProceso, pepHoras, pepMinutos, pepSegundos,
        pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio,
        pepFechaFin, pepFrecuencia, pepEstado) VALUES (
        'DESISTIR_SOLICITUD_AFILIACION',
        '06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'DIARIO', 'INACTIVO'
    );
END