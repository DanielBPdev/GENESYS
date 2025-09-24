if exists(select * from ValidacionProceso  
where vapBloque like '%CAMBIO_TIPO_PENSIONADO_PENSIONADO%'
and vapValidacion = 'VALIDACION_PERSONA_CONYUGE_ACTIVO'
and vapestadoproceso = 'ACTIVO')
begin 
update ValidacionProceso set vapestadoproceso = 'INACTIVO'
where vapBloque like '%CAMBIO_TIPO_PENSIONADO_PENSIONADO%'
and vapValidacion = 'VALIDACION_PERSONA_CONYUGE_ACTIVO'
end