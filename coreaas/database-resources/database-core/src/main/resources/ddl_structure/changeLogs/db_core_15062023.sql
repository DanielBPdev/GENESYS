IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado WHERE dcoProceso = 'AFILIACION_DEPENDIENTE_WEB' and dcoEtiquetaPlantilla = 'ACRDS_TRMS_CDNES_PERS_WEB')
 insert into DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)
values ('AFILIACION_DEPENDIENTE_WEB', 'ACRDS_TRMS_CDNES_PERS_WEB');


IF NOT EXISTS (SELECT 1 FROM DestinatarioComunicado WHERE dcoProceso = 'AFILIACION_EMPRESAS_WEB' and dcoEtiquetaPlantilla = 'ACRDS_TRMS_CDNES_WEB')
 insert into DestinatarioComunicado(dcoProceso, dcoEtiquetaPlantilla)
values ('AFILIACION_EMPRESAS_WEB', 'ACRDS_TRMS_CDNES_WEB');


update PlantillaComunicado set pcoEncabezado = '<table style="width: 100%;"><tbody><tr style="width: 100%;"><th style="width: 50%; text-align: left;">&nbsp;${logoDeLaCcf}</th><th style="width: 40%;text-align:center"><strong>SOLICITUD DE AFILIACIÓN DE EMPLEADOR  A LA CAJA DE COMPENSACIÓN FAMILIAR</strong>&nbsp;</th><th style="width: 20%;text-align: right;display: flex;">Número de solicitud<br></br>${numeroRadicacion}&nbsp;<br></br>${fechaRadicacion}&nbsp;</th></tr></tbody></table>'
where pcoEtiqueta = 'ACRDS_TRMS_CDNES_WEB'


update PlantillaComunicado set pcoEncabezado = '<table style="width: 100%;"><tbody><tr style="width: 100%;"><th style="width: 50%; text-align: left;">&nbsp;${logoDeLaCcf}</th><th style="width: 40%;text-align:center"><strong>SOLICITUD DE AFILIACI&Oacute;N DE ${tipoAfiliado} A LA CAJA DE COMPENSACI&Oacute;N FAMILIAR</strong>&nbsp;</th><th style="width: 20%;text-align: right;display: flex;">Número de solicitud<br></br>${numeroRadicacion}&nbsp;<br></br>${fechaRadicacion}&nbsp;</th></tr></tbody></table>'
where pcoEtiqueta = 'ACRDS_TRMS_CDNES_PERS_WEB'
