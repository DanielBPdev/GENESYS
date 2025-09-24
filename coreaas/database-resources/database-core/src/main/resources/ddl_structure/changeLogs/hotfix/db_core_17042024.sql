
--Glpi 78741 CDC-Se solicita agregar validación para novedad Reporte fallecimiento persona. ( > GENESYS)
--Se agrega validación: VALIDACION_PERSONA_FALLECIDA_PRESENCIAL al bloque: NOVEDAD_REPORTE_FALLECIMIENTO_PERSONAS para que pueda ser tomada en cuenta desde el método ValidacionesAPIBusiness.validadorNovedadesHabilitadas
INSERT INTO ValidacionProceso VALUES ('NOVEDAD_REPORTE_FALLECIMIENTO_PERSONAS', 'VALIDACION_PERSONA_FALLECIDA_PRESENCIAL', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', 1, 'HIJO_BIOLOGICO', 0);
