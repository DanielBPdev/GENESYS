--liquibase formatted sql

--changeset jvelandia:01
--comment: 

DELETE  DestinatarioComunicado WHERE dcoProceso='NOVEDADES_PERSONAS_PRESENCIAL' AND dcoEtiquetaPlantilla='NTF_PARA_SBC_NVD_EMP'
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_INDEPENDIENTE_WEB' AND des.dcoEtiquetaPlantilla='RCHZ_AFL_IDPE_PROD_NSUBLE'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='CRT_ENT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'), 1);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='CRT_ENT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 2);
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)VALUES((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_PERSONAS_PRESENCIAL' AND des.dcoEtiquetaPlantilla='CRT_ENT_PAG'), (SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'), 3);