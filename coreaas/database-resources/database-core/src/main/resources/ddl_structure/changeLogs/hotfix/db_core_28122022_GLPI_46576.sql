
if not exists (select * from ValidacionProceso where vapBloque = '322-039-2' and vapValidacion like '%VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_USADA%' AND vapObjetoValidacion = 'JEFE_HOGAR' AND vapProceso = 'POSTULACION_FOVIS_WEB')
	begin
	INSERT ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) 
	VALUES (N'322-039-2',N'VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_USADA',N'POSTULACION_FOVIS_WEB',N'ACTIVO',N'1',N'JEFE_HOGAR',0)
	end
else
	begin
		print 'el parametro VALIDACION_MODALIDAD_ADQUISICION_VIVIENDA_USADA - POSTULACION_FOVIS_WEB - JEFE_HOGAR ya existe'
	end

