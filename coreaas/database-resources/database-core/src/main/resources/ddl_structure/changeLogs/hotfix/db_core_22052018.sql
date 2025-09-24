--liquibase formatted sql

--changeset mfuquene:01
--comment: Ajuste PlantillaComunicado
UPDATE PlantillaComunicado SET pcoEncabezado = '<table style="width: 680px; height: 72px;"><tbody><tr style="width: 100%;"><th style="width: 25%; font-weight: normal;"><strong>&nbsp; LOGO &nbsp;</strong></th><th style="width: 50%; text-align: center;"><strong>SOLICITUD DE AFILIACI&Oacute;N DE ${tipoAfiliado}</strong></th><th style="width: 25%; font-weight: normal;"><p><strong>&nbsp;N&uacute;mero de solicitud</strong></p><p><strong>${numeroRadicacion}&nbsp;&nbsp;</strong></p><p><strong>&nbsp;</strong></p></th></tr></tbody></table>' WHERE pcoetiqueta = 'ACRDS_TRMS_CDNES_PERS';

