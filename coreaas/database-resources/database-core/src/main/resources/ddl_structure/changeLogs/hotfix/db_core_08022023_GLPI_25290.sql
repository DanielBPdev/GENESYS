
if not exists (select * from ValidacionProceso where vapBloque = '321-020-6' and vapValidacion = 'VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA' AND vapObjetoValidacion = 'JEFE_HOGAR' AND vapProceso = 'POSTULACION_FOVIS_PRESENCIAL')
	begin
	INSERT ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) 
VALUES (N'321-020-6',N'VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA',N'POSTULACION_FOVIS_PRESENCIAL',N'ACTIVO',N'1',N'JEFE_HOGAR',0)
	end
else
	begin
		print 'el parametro VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA - POSTULACION_FOVIS_PRESENCIAL - JEFE_HOGAR ya existe'
	end
	
---------------------------
--Se elimina la validacion en el bloque 321-020-4
	delete from ValidacionProceso
	where vapValidacion = 'VALIDACION_PERSONA_HOGAR_HA_SIDO_BENEFICIARIA' and vapbloque = '321-020-4' and vapObjetoValidacion = 'JEFE_HOGAR' and vapProceso = 'POSTULACION_FOVIS_PRESENCIAL'


