if not exists (select * from ValidacionProceso where vapBloque = 'NOVEDAD_CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB' 
and vapValidacion = 'VALIDACION_ESTADO_AFILIACION' 
AND vapObjetoValidacion = 'PROPIEDAD_HORIZONTAL' 
AND vapProceso = 'NOVEDADES_EMPRESAS_WEB')
begin		
	    insert validacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
    	values ('NOVEDAD_CAMBIO_ACTIVIDAD_ECONOMICA_PRINCIPAL_WEB', 'VALIDACION_ESTADO_AFILIACION', 'NOVEDADES_EMPRESAS_WEB', 'ACTIVO', 1, 'PROPIEDAD_HORIZONTAL', 0)
		PRINT 'Se realízó cambio'
END