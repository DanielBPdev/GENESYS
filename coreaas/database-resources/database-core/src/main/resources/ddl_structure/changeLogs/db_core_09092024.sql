IF EXISTS(SELECT * FROM  ValidacionProceso
where vapBloque like 'CAMBIO_CLASE_DE_INDEPENDIENTE%'
and vapValidacion = 'VALIDACION_PERSONA_EMPLEADOR_ACTIVO'
AND vapEstadoProceso = 'ACTIVO'
)

	update ValidacionProceso
	set vapEstadoProceso = 'INACTIVO'
	where vapBloque like 'CAMBIO_CLASE_DE_INDEPENDIENTE%'
	and vapValidacion = 'VALIDACION_PERSONA_EMPLEADOR_ACTIVO'