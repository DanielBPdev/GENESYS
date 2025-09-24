--liquibase formatted sql

--changeset abaquero:01
--comment: Inserts tabla ConjuntoValidacionSubsidio
IF NOT EXISTS (SELECT 1 FROM ConjuntoValidacionSubsidio WHERE cvsId BETWEEN 1 AND 25)
BEGIN
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (1,'CONDICION_AGRICOLA','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (2,'RETROACTIVO_NOVEDAD','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (3,'RETROACTIVO_APORTE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (4,'ESTADO_EMPLEADOR','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (5,'ESTADO_TRABAJADOR','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (6,'TIENE_BENEFICARIOS','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (7,'CAUSACION_SALARIOS','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (8,'TRABAJADOR_ES_EMPLEADOR','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (9,'ESTADO_APORTE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (10,'DIAS_COTIZADOS_NOVEDADES','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (11,'SALARIO','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (12,'ESTADO_BENEFICIARIO_PADRE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (13,'ESTADO_BENEFICIARIO_HIJO','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (14,'BENEFICIARIO_AFILIADO_PRINCIPAL_PADRE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (15,'BENEFICIARIO_EMPLEADOR_PADRE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (16,'BENEFICIARIO_OTROS_APORTES_PADRE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (17,'BENEFICIARIO_AFILIADO_PRINCIPAL_HIJO','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (18,'BENEFICIARIO_EMPLEADOR_HIJO','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (19,'BENEFICIARIO_OTROS_APORTES_HIJO','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (20,'BENEFICIARIO_OTRAS_PERSONAS_PADRE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (21,'BENEFICIARIO_OTRAS_PERSONAS_HIJO','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (22,'CONDICION_INVALIDEZ_PADRE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (23,'CONDICION_INVALIDEZ_HIJO','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (24,'EDAD_BENEFICIARIO_PADRE','RECONOCIMIENTO')
	INSERT ConjuntoValidacionSubsidio (cvsId,cvsTipoValidacion,cvsTipoProceso) VALUES (25,'EDAD_BENEFICIARIO_HIJO','RECONOCIMIENTO')
END;