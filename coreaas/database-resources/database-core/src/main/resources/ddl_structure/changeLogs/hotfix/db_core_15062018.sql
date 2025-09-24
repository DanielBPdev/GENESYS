--liquibase formatted sql

--changeset jocampo:01
--comment: Inserts DestinatarioComunicado
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES ('SUBSIDIO_MONETARIO_MASIVO', 'COM_SUB_DIS_PAG_EMP');
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES ('SUBSIDIO_MONETARIO_MASIVO', 'COM_SUB_DIS_PAG_TRA');
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES ('SUBSIDIO_MONETARIO_MASIVO', 'COM_SUB_DIS_PAG_ADM_SUB');
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES ('PAGOS_SUBSIDIO_MONETARIO', 'COM_PAG_SUB_RET_VENT');
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES ('PAGOS_SUBSIDIO_MONETARIO', 'COM_PAG_SUB_INC_ARC_CON');

--changeset jocorrea:02
--comment: Insert DestinatarioComunicado
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES
('NOVEDADES_EMPRESAS_PRESENCIAL', 'RCHZ_NVD_EMP_PROD_NSUBLE'),
('NOVEDADES_EMPRESAS_WEB', 'RCHZ_NVD_EMP_PROD_NSUBLE'),
('NOVEDADES_EMPRESAS_WEB', 'NTF_PARA_SBC_NVD_EMP');