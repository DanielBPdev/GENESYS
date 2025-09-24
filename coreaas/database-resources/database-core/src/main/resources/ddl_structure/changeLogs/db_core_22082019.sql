--liquibase formatted sql

--changeset mamonroy:01
--comment:
UPDATE prd
SET prd.prdPrioridad = 0
FROM PrioridadDestinatario prd
JOIN GrupoPrioridad gpr ON gpr.gprId = prd.prdGrupoPrioridad
JOIN DestinatarioComunicado dco on dco.dcoId = prd.prdDestinatarioComunicado
WHERE dco.dcoProceso = 'GESTION_COBRO_MANUAL'
AND dco.dcoEtiquetaPlantilla = 'LIQ_APO_MAN';

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'RESPONSABLE_DE_APORTES'),
1);

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'REPRESENTANTE_LEGAL'),
2);

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'RESPONSABLE_DE_SUBSIDIOS'),
3);

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'REPRESENTANTE_LEGAL_SUPLENTE'),
4);

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'RESPONSABLE_DE_LAS_AFILIACIONES'),
5);

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'OFICINA_PRINCIPAL'),
0);

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'ENVIO_DE_CORRESPONDENCIA'),
0);

INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad)
VALUES ((SELECT dcoId FROM DestinatarioComunicado
WHERE dcoProceso = 'GESTION_COBRO_MANUAL'
AND dcoEtiquetaPlantilla = 'LIQ_APO_MAN'),
(SELECT gprId FROM GrupoPrioridad
WHERE gprNombre = 'NOTIFICACION_JUDICIAL'),
0);