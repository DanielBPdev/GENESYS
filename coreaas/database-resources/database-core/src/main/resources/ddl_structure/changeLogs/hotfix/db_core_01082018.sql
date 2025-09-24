--liquibase formatted sql

--changeset jvelandia:01
--comment: Correcion Plantillas
DELETE FROM PrioridadDestinatario WHERE prdDestinatarioComunicado in (SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='RECAUDO_APORTES_PILA')
DELETE FROM DestinatarioComunicado WHERE dcoProceso = 'RECAUDO_APORTES_PILA'


INSERT INTO DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('RECAUDO_APORTES_PILA','NTF_REC_APT_PLA_DEP1');
INSERT INTO DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('RECAUDO_APORTES_PILA','NTF_REC_APT_PLA_IDPE1');
INSERT INTO DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('RECAUDO_APORTES_PILA','NTF_REC_APT_PLA_PNS1');

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='RECAUDO_APORTES_PILA' AND des.dcoEtiquetaPlantilla='NTF_REC_APT_PLA_IDPE1'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='RECAUDO_APORTES_PILA' AND des.dcoEtiquetaPlantilla='NTF_REC_APT_PLA_PNS1'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='ENTIDAD_PAGADORA_ROL'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='RECAUDO_APORTES_PILA' AND des.dcoEtiquetaPlantilla='NTF_REC_APT_PLA_DEP1'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_APORTES'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='RECAUDO_APORTES_PILA' AND des.dcoEtiquetaPlantilla='NTF_REC_APT_PLA_DEP1'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 2);

--changeset abaquero:02
--comment: Se agrega el campo spiOriginadoEnAporteManual
ALTER TABLE SolicitudNovedadPila ADD spiOriginadoEnAporteManual bit;
ALTER TABLE aud.SolicitudNovedadPila_aud ADD spiOriginadoEnAporteManual bit;