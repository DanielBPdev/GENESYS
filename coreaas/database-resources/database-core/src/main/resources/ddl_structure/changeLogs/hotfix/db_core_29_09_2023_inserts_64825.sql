
-----ParametrizacionNovedad
BEGIN
   IF NOT EXISTS (SELECT novTipoTransaccion FROM ParametrizacionNovedad WHERE novTipoTransaccion = 'CAMBIO_TRASLADO_EMPRESAS_CCF')
   BEGIN
	INSERT INTO ParametrizacionNovedad (novTipoTransaccion, novPuntoResolucion, novRutaCualificada, novTipoNovedad, novProceso, novAplicaTodosRoles)
	VALUES('CAMBIO_TRASLADO_EMPRESAS_CCF', 'BACK', 'com.asopagos.novedades.convertidores.empleador.CambioMarcaTrasladoEmpresaCCF', 'GENERAL', 'NOVEDADES_EMPRESAS_PRESENCIAL', NULL)
   END
END

-----ValidacionProceso
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_EMPLEADOR_IS_ACTIVO' and vapObjetoValidacion='PERSONA_JURIDICA')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_EMPLEADOR_IS_ACTIVO', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'PERSONA_JURIDICA', 0)
 END
END
---------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_EMPLEADOR_IS_ACTIVO' and vapObjetoValidacion='PERSONA_NATURAL')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_EMPLEADOR_IS_ACTIVO', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'PERSONA_NATURAL', 0)
   END
END
-----------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_EMPLEADOR_IS_ACTIVO' and vapObjetoValidacion='EMPLEADOR_DE_SERVICIO_DOMESTICO')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_EMPLEADOR_IS_ACTIVO', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'EMPLEADOR_DE_SERVICIO_DOMESTICO', 0)
   END
END
-----------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_EMPLEADOR_IS_ACTIVO' and vapObjetoValidacion='PROPIEDAD_HORIZONTAL')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_EMPLEADOR_IS_ACTIVO', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'PROPIEDAD_HORIZONTAL', 0)
   END
END
----------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_EMPLEADOR_IS_ACTIVO' and vapObjetoValidacion='COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_EMPLEADOR_IS_ACTIVO', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO', 0)
   END
END
-----------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_EMPLEADOR_IS_ACTIVO' and vapObjetoValidacion='ENTIDAD_SIN_ANIMO_DE_LUCRO')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_EMPLEADOR_IS_ACTIVO', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'ENTIDAD_SIN_ANIMO_DE_LUCRO', 0)
   END
END
------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_EMPLEADOR_IS_ACTIVO' and vapObjetoValidacion='ORGANIZACION_RELIGIOSA_O_PARROQUIA')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_EMPLEADOR_IS_ACTIVO', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'ORGANIZACION_RELIGIOSA_O_PARROQUIA', 0)
   END
END
---------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_CERO_TRABAJADORES_ACTIVOS' and vapObjetoValidacion='PERSONA_JURIDICA')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_CERO_TRABAJADORES_ACTIVOS', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'PERSONA_JURIDICA', 0)
   END
END
-------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_CERO_TRABAJADORES_ACTIVOS' and vapObjetoValidacion='PERSONA_NATURAL')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_CERO_TRABAJADORES_ACTIVOS', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'PERSONA_NATURAL', 0)
   END
END
--------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_CERO_TRABAJADORES_ACTIVOS' and vapObjetoValidacion='EMPLEADOR_DE_SERVICIO_DOMESTICO')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_CERO_TRABAJADORES_ACTIVOS', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'EMPLEADOR_DE_SERVICIO_DOMESTICO', 0)
   END
END
------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_CERO_TRABAJADORES_ACTIVOS' and vapObjetoValidacion='PROPIEDAD_HORIZONTAL')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_CERO_TRABAJADORES_ACTIVOS', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'PROPIEDAD_HORIZONTAL', 0)
   END
END
--------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_CERO_TRABAJADORES_ACTIVOS' and vapObjetoValidacion='COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_CERO_TRABAJADORES_ACTIVOS', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO', 0)
   END
END
----------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_CERO_TRABAJADORES_ACTIVOS' and vapObjetoValidacion='ENTIDAD_SIN_ANIMO_DE_LUCRO')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_CERO_TRABAJADORES_ACTIVOS', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'ENTIDAD_SIN_ANIMO_DE_LUCRO', 0)
   END
END
--------------
BEGIN
   IF NOT EXISTS (SELECT vapId FROM ValidacionProceso WHERE vapBloque = 'NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF' and vapValidacion = 'VALIDACION_CERO_TRABAJADORES_ACTIVOS' and vapObjetoValidacion='ORGANIZACION_RELIGIOSA_O_PARROQUIA')
   BEGIN
	INSERT INTO ValidacionProceso ( vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
	VALUES ('NOVEDAD_CAMBIO_TRASLADO_EMPRESAS_CCF', 'VALIDACION_CERO_TRABAJADORES_ACTIVOS', 'NOVEDADES_EMPRESAS_PRESENCIAL', 'ACTIVO', 1, 'ORGANIZACION_RELIGIOSA_O_PARROQUIA', 0)
   END
END