--liquibase formatted sql

--changeset silopez:01
--comment: eliminar validacion bloque 121-107-1
DELETE FROM ValidacionProceso
WHERE vapBloque = '121-107-1'
AND vapValidacion = 'VALIDACION_PERSONA_HIJO_AFILIADO_PRINCIPAL'
AND vapProceso = 'AFILIACION_PERSONAS_PRESENCIAL'
AND vapObjetoValidacion = 'HIJO';

--changeset dsuesca:02
--comment: mantis 261421
INSERT dbo.ConjuntoValidacionSubsidio (cvsId, cvsTipoValidacion, cvsTipoProceso) 
VALUES ((SELECT MAX(cvsId) + 1 FROM ConjuntoValidacionSubsidio), 'SUBSIDIO_ASIGNADO_POR_OTRA_CCF', 'FALLECIMIENTO_BENEFICIARIO');
INSERT dbo.ConjuntoValidacionSubsidio (cvsId, cvsTipoValidacion, cvsTipoProceso) 
VALUES ((SELECT MAX(cvsId) + 1 FROM ConjuntoValidacionSubsidio), 'BENEFICIARIO_COMO_AFILIADO_POR_OTRA_CCF', 'FALLECIMIENTO_BENEFICIARIO');
