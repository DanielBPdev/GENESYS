update ValidacionProceso set vapEstadoProceso = 'INACTIVO'
where vapValidacion = 'VALIDACION_PERSONA_DEPENDIENTE_ACTIVO' and vapBloque = 'INACTIVAR_BENEFICIARIO_HIJO_BIOLOGICO_PRESENCIAL'
--db_core_02_11_2023_GLPI_72669