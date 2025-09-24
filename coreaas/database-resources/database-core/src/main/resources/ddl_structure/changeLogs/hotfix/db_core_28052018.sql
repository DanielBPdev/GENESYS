--liquibase formatted sql

--changeset jocorrea:01
--comment: Insercion tabla PrioridadDestinatario
INSERT PrioridadDestinatario (prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad) VALUES
((SELECT dcoId FROM DestinatarioComunicado WHERE dcoEtiquetaPlantilla = 'NTF_PARA_SBC_NVD_WEB_TRB_EMP' AND dcoProceso = 'NOVEDADES_DEPENDIENTE_WEB'), (SELECT gprId FROM GrupoPrioridad WHERE gprNombre = 'RESPONSABLE_DE_LAS_AFILIACIONES'), 1),
((SELECT dcoId FROM DestinatarioComunicado WHERE dcoEtiquetaPlantilla = 'NTF_PARA_SBC_NVD_WEB_TRB_EMP' AND dcoProceso = 'NOVEDADES_DEPENDIENTE_WEB'), (SELECT gprId FROM GrupoPrioridad WHERE gprNombre = 'REPRESENTANTE_LEGAL'), 2),
((SELECT dcoId FROM DestinatarioComunicado WHERE dcoEtiquetaPlantilla = 'NTF_PARA_SBC_NVD_WEB_TRB_EMP' AND dcoProceso = 'NOVEDADES_DEPENDIENTE_WEB'), (SELECT gprId FROM GrupoPrioridad WHERE gprNombre = 'OFICINA_PRINCIPAL'), 3);