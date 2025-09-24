
if not exists (select * from ValidacionProceso where vapBloque = '321-020-4' and vapValidacion = 'VALIDACION_PERSONA_SOLICITUD_EN_PROCESO' AND vapObjetoValidacion = 'JEFE_HOGAR' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL')
	begin
	    insert validacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
    	values ('321-020-4', 'VALIDACION_PERSONA_SOLICITUD_EN_PROCESO', 'POSTULACION_FOVIS_PRESENCIAL', 'ACTIVO', 1, 'JEFE_HOGAR', 0)
	end
else
	begin
		print 'el parametro VALIDACION_PERSONA_SOLICITUD_EN_PROCESO - POSTULACION_FOVIS_PRESENCIAL - JEFE_HOGAR ya existe'
	end
	
---------------------------
