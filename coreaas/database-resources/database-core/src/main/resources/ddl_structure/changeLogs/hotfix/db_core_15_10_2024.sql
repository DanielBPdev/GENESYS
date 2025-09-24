IF NOT EXISTS (SELECT * FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'EJECUCION_PRE_CALIFICACION_PARA_ASIGNACION_FOVIS')
BEGIN
    INSERT INTO ParametrizacionEjecucionProgramada (pepProceso, pepHoras, pepMinutos, pepSegundos, pepFrecuencia)
	VALUES('EJECUCION_PRE_CALIFICACION_PARA_ASIGNACION_FOVIS', 00, 00, 59, 'DIARIO')
END