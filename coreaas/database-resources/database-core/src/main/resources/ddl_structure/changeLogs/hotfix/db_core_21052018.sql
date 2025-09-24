--liquibase formatted sql

--changeset rarboleda:01
--comment: Insert PrioridadDestinatario
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' 
	AND des.dcoEtiquetaPlantilla='NTF_RAD_AFL_PER'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 4);

--changeset rarboleda:02
--comment: UPDATE PrioridadDestinatario
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 1
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_RAD_AFL_PER')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'AFILIADO_PRINCIPAL')

-- Poner ENVIO_DE_CORRESPONDENCIA en Prioridad 2
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 2
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_RAD_AFL_PER')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'ENVIO_DE_CORRESPONDENCIA')

-- Poner RESPONSABLE_DE_LAS_AFILIACIONES en Prioridad 3
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 3
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_RAD_AFL_PER')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'RESPONSABLE_DE_LAS_AFILIACIONES')
	
-- Poner REPRESENTANTE_LEGAL principal en Prioridad 4
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 4
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_RAD_AFL_PER')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'REPRESENTANTE_LEGAL')

--changeset rarboleda:03
--comment: Insert PrioridadDestinatario
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' 
	AND des.dcoEtiquetaPlantilla='NTF_INT_AFL_IDPE'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 4);
	
--changeset rarboleda:04
--comment: UPDATE PrioridadDestinatario
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 1
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_INT_AFL_IDPE')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'AFILIADO_PRINCIPAL')

-- Poner ENVIO_DE_CORRESPONDENCIA en Prioridad 2
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 2
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_INT_AFL_IDPE')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'ENVIO_DE_CORRESPONDENCIA')

-- Poner RESPONSABLE_DE_LAS_AFILIACIONES en Prioridad 3
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 3
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_INT_AFL_IDPE')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'RESPONSABLE_DE_LAS_AFILIACIONES')
	
-- Poner REPRESENTANTE_LEGAL principal en Prioridad 4
UPDATE dbo.PrioridadDestinatario SET prdPrioridad = 4
WHERE prdDestinatarioComunicado IN (SELECT des.dcoId FROM DestinatarioComunicado des 
	WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='NTF_INT_AFL_IDPE')
AND prdGrupoPrioridad IN (SELECT gpr.gprId FROM dbo.GrupoPrioridad gpr 
	WHERE gpr.gprNombre = 'REPRESENTANTE_LEGAL')