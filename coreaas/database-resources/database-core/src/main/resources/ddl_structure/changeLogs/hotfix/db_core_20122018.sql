--liquibase formatted sql

--changeset clmarin:01
--comment: Adiciones en tabla DestinatarioComunicado (0246712)
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla)
VALUES('LEGALIZACION_DESEMBOLSO_FOVIS', 'RCHZ_SOL_LEG_DES_FOVIS');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla)
VALUES('LEGALIZACION_DESEMBOLSO_FOVIS', 'NTF_SBC_POS_FOVIS_EXT');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla)
VALUES('VISTAS_360', 'COM_GEN_CER_AFI');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla)
VALUES('VISTAS_360', 'COM_GEN_CER_APO');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla)
VALUES('VISTAS_360', 'COM_GEN_CER_HIS_AFI');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla)
VALUES('VISTAS_360', 'COM_GEN_CER_PYS');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla)
VALUES('VISTAS_360', 'COM_GEN_CER_PYS_EMP');

