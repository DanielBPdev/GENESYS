--liquibase formatted sql

--changeset jocorrea:01
--comment: Insercion tabla DestinatarioComunicado
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES ('NOVEDADES_DEPENDIENTE_WEB', 'NTF_PARA_SBC_NVD_WEB_TRB_EMP');