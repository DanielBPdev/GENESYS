--liquibase formatted sql

--changeset jvelandia:01
--comment: Se agregan elementos a la tabla DestinatarioComunicado y PrioridadDestinatario
INSERT INTO DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('AFILIACION_INDEPENDIENTE_WEB', 'RCHZ_AFL_IDPE_PROD_NSUBLE');
INSERT INTO DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)VALUES('AFILIACION_INDEPENDIENTE_WEB', 'RCHZ_AFL_PNS_PROD_NSUBLE');
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_INDEPENDIENTE_WEB' AND des.dcoEtiquetaPlantilla='RCHZ_AFL_IDPE_PROD_NSUBLE'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_INDEPENDIENTE_WEB' AND des.dcoEtiquetaPlantilla='RCHZ_AFL_PNS_PROD_NSUBLE'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);