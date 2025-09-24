UPDATE ParametrizacionNovedad
SET novRutaCualificada = 'com.asopagos.novedades.fovis.convertidores.HabilitarPostulacionSuspendidaXCambioAnio', novProceso = 'NOVEDADES_FOVIS_REGULAR'
WHERE novTipoTransaccion = 'HABILITACION_POSTULACION_SUSPENDIDA_CAMBIO_ANIO'

UPDATE ValidacionProceso
SET vapProceso = 'NOVEDADES_FOVIS_REGULAR'
WHERE vapBloque = 'NOVEDAD_HABILITACION_POSTULACION_SUSPENDIDA_CAMBIO_ANIO'