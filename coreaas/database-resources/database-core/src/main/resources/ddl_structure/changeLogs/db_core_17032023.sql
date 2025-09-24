DELETE FROM  ValidacionProceso
where vapValidacion = 'VALIDACION_ESTADO_MADRE'
AND vapBloque IN ('INACTIVAR_BENEFICIOS_MADRE_PRESENCIAL',
'INACTIVAR_BENEFICIOS_MADRE_WEB',
'INACTIVAR_BENEFICIOS_MADRE_DEPWEB')


update ParametrizacionEjecucionProgramada
set pephoras = '08', pepMinutos = '00'
where pepProceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA'

update ParametrizacionEjecucionProgramada
set pephoras = '14', pepMinutos = '00'
where pepProceso = 'CAMBIO_AUTOMATICO_CATEGORIA_BENEFICIARIO_CIRCULAR_UNICA_II'