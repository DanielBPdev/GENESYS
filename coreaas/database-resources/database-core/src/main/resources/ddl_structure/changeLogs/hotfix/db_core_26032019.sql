--liquibase formatted sql

--changeset mamonroy:01
--comment:  mantis 247172
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 302 AND prdGrupoPrioridad IN (58,56); 
UPDATE PrioridadDestinatario SET prdPrioridad = 3 WHERE prdDestinatarioComunicado = 302 AND prdGrupoPrioridad IN (57);

--77 / 36
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 328 AND prdGrupoPrioridad IN (70,64);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (328,70,1);

--78 / 37
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 329 AND prdGrupoPrioridad IN (64);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (329,70,1);

--88 / 47
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado = 519 AND prdGrupoPrioridad IN (70);
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (519,70,1);

--changeset mamonroy:02
--comment:  mantis 248846
UPDATE PlantillaComunicado
SET pcoMensaje = '<p>Señor(a)<br />${nombresYApellidosDelAfiliadoPrincipal} <br /> Su contraseña de usuario se vencerá en ${diasVencimiento} días, por favor actualizarla.</p>'
WHERE pcoEtiqueta = 'NTF_VEN_CON_USR'

--changeset mamonroy:03
--comment:  mantis 248846
INSERT INTO VARIABLECOMUNICADO (vcoClave,vcoDescripcion,vcoNombre,vcoPlantillaComunicado,vcoNombreConstante,vcoTipoVariableComunicado,vcoOrden) VALUES ('${anio}','Año en que se realizaron los aportes','Ano certificado',(select TOP 1 pcoId from plantillaComunicado join variableComunicado on pcoId = vcoPlantillaComunicado where pcoEtiqueta = 'COM_GEN_CER_APO'),NULL,'VARIABLE',0);

--changeset mamonroy:04
--comment:  mantis 247172
DELETE FROM DestinatarioComunicado WHERE dcoProceso = 'LEGALIZACION_DESEMBOLSO_FOVIS' AND dcoEtiquetaPlantilla = 'NTF_SBC_POS_FOVIS_EXT';
--149
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (378,65,2);
--150
INSERT INTO PrioridadDestinatario (prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES (379,65,2);




