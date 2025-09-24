--liquibase formatted sql

--changeset clmarin:01
--comment: Modificaciones tabla PrioridadDestinatario
UPDATE PrioridadDestinatario SET prdGrupoPrioridad=(SELECT gprid FROM GrupoPrioridad where gprnombre='APORTANTE') WHERE prdDestinatarioComunicado = (select dcoId from DestinatarioComunicado where dcoEtiquetaPlantilla='NTF_GST_INF_PAG_APT' and dcoProceso='PAGO_APORTES_MANUAL') ;
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='PAGO_APORTES_MANUAL' AND des.dcoEtiquetaPlantilla='NTF_GST_INF_PAG_APT'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_APORTES'), 2);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='PAGO_APORTES_MANUAL' AND des.dcoEtiquetaPlantilla='NTF_GST_INF_PAG_APT'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 3);

