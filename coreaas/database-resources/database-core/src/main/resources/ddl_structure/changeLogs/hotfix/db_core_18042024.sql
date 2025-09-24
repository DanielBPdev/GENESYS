
--Glpi 78741 CDC-Se solicita agregar validación para novedad Reporte fallecimiento persona. ( > GENESYS)
--Se agrega validación: VALIDACION_PERSONA_FALLECIDA_PRESENCIAL al bloque: NOVEDAD_REPORTE_FALLECIMIENTO_PERSONAS para que pueda ser tomada en cuenta desde el método ValidacionesAPIBusiness.validadorNovedadesHabilitadas
--Esto para el objeto validación TRABAJADOR_DEPENDIENTE
INSERT INTO ValidacionProceso VALUES ('NOVEDAD_REPORTE_FALLECIMIENTO_PERSONAS', 'VALIDACION_PERSONA_FALLECIDA_PRESENCIAL', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', 1, 'TRABAJADOR_DEPENDIENTE', 0)
