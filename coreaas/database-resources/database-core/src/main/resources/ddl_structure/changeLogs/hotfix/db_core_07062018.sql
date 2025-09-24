--liquibase formatted sql

--changeset rarboleda:01
--comment: Insertar en tabla DestinatarioComunicado
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('AFILIACION_DEPENDIENTE_WEB', 'NTF_INT_AFL');

--changeset rarboleda:02
--comment: Insertar en Tabla PrioridadDestinatario
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_DEPENDIENTE_WEB' 
	AND des.dcoEtiquetaPlantilla='NTF_INT_AFL'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 1);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_DEPENDIENTE_WEB' 
	AND des.dcoEtiquetaPlantilla='NTF_INT_AFL'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'), 2);
	
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_DEPENDIENTE_WEB' 
	AND des.dcoEtiquetaPlantilla='NTF_INT_AFL'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='OFICINA_PRINCIPAL'), 3);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_DEPENDIENTE_WEB' 
	AND des.dcoEtiquetaPlantilla='NTF_INT_AFL'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='ENVIO_DE_CORRESPONDENCIA'), 4);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_DEPENDIENTE_WEB' 
	AND des.dcoEtiquetaPlantilla='NTF_INT_AFL'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='NOTIFICACION_JUDICIAL'), 5);

--changeset jvelandia:03
--comment: Ajustes tabla VariableComunicado
UPDATE VariableComunicado SET vcoClave='${estadoDeLaSolicitud}' WHERE vcoClave='${fechaYHoraDeAsignacion}' AND vcoPlantillaComunicado =(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_418');
DELETE VariableComunicado WHERE vcoClave='${tipoSolicitante}' AND vcoPlantillaComunicado=(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_419');