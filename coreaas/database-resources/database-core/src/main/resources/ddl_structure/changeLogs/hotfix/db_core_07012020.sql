--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE prd
SET prdPrioridad = 1
FROM PrioridadDestinatario prd
WHERE prd.prdDestinatarioComunicado IN
	(SELECT dcoId FROM DestinatarioComunicado dco
	WHERE dco.dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL'
	AND dco.dcoEtiquetaPlantilla = 'CNFR_RET_APRT' )
AND prdGrupoPrioridad = 73;

UPDATE prd
SET prdPrioridad = 2
FROM PrioridadDestinatario prd
WHERE prd.prdDestinatarioComunicado IN
	(SELECT dcoId FROM DestinatarioComunicado dco
	WHERE dco.dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL'
	AND dco.dcoEtiquetaPlantilla = 'CNFR_RET_APRT' )
AND prdGrupoPrioridad = 74;

UPDATE prd
SET prdPrioridad = 3
FROM PrioridadDestinatario prd
WHERE prd.prdDestinatarioComunicado IN
	(SELECT dcoId FROM DestinatarioComunicado dco
	WHERE dco.dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL'
	AND dco.dcoEtiquetaPlantilla = 'CNFR_RET_APRT' )
AND prdGrupoPrioridad = 54;

UPDATE prd
SET prdPrioridad = 4
FROM PrioridadDestinatario prd
WHERE prd.prdDestinatarioComunicado IN
	(SELECT dcoId FROM DestinatarioComunicado dco
	WHERE dco.dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL'
	AND dco.dcoEtiquetaPlantilla = 'CNFR_RET_APRT' )
AND prdGrupoPrioridad = 55;

UPDATE prd
SET prdPrioridad = 5
FROM PrioridadDestinatario prd
WHERE prd.prdDestinatarioComunicado IN
	(SELECT dcoId FROM DestinatarioComunicado dco
	WHERE dco.dcoProceso = 'NOVEDADES_EMPRESAS_PRESENCIAL'
	AND dco.dcoEtiquetaPlantilla = 'CNFR_RET_APRT' )
AND prdGrupoPrioridad = 72;

--changeset squintero:02
--comment:

INSERT INTO VariableComunicado (vcoClave, vcoDescripcion, vcoNombre, vcoPlantillaComunicado, vcoNombreConstante, vcoTipoVariableComunicado, vcoOrden)
VALUES ('${ciudadCcf}', 'Ciudad de Ubicación de la Caja de Compensación', 'Ciudad CCF', 350, 'CIUDAD_CCF', 'CONSTANTE', NULL);