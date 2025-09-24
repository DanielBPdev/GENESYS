DELETE FROM ValidacionProceso WHERE vapid = (select vapid from ValidacionProceso
where vapValidacion = 'VALIDACION_PERSONA_CONYUGE_ACTIVO'
and vapProceso = 'NOVEDADES_PERSONAS_PRESENCIAL'
and vapBloque = 'CAMBIO_TIPO_PENSIONADO_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL'
)