
IF NOT EXISTS (SELECT * FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'RETOMA_TRASLADO_SALDOS')
BEGIN
    INSERT INTO ParametrizacionEjecucionProgramada (pepProceso,  pepSegundos, pepFrecuencia,pepEstado)
	VALUES('RETOMA_TRASLADO_SALDOS', 600, 'INTERVALO','INACTIVO')
END