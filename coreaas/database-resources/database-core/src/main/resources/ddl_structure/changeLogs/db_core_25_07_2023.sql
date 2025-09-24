update PlantillaComunicado
set pcoEncabezado = 
'<table style="width: 680px; height: 100px;">  <tbody>  <tr style="width: 100%;">  <th style="width: 25%; font-weight: normal;">${logoDeLaCcf}</th>  <th style="width: 50%; text-align: center;">SOLICITUD DE AFILIACI&Oacute;N DE ${tipoAfiliado} A LA CAJA DE COMPENSACI&Oacute;N FAMILIAR</th>  <th style="width: 25%; font-weight: normal;">  <p><strong>&nbsp;N&uacute;mero de solicitud</strong></p>  <p><strong>${numeroRadicacion}&nbsp;&nbsp;</strong></p><p><strong>${fechaSistema}&nbsp;&nbsp;</strong></p>  <p><strong>&nbsp;</strong></p>  </th>  </tr>  </tbody>  </table>'
where pcoEtiqueta in ('ACRDS_TRMS_CDNES_PERS','ACRDS_TRMS_CDNES_PERS_PENS_IDPTE','ACRDS_TRMS_CDNES_PERS_WEB');

update PlantillaComunicado
set pcoEncabezado = 
'<table style="width: 680px; height: 100px;">  <tbody>  <tr style="width: 100%;">  <th style="width: 25%; font-weight: normal;">${logoDeLaCcf}</th>  <th style="width: 50%; text-align: center;">SOLICITUD DE AFILIACI&Oacute;N DE EMPLEADOR A LA CAJA DE COMPENSACI&Oacute;N FAMILIAR</th>  <th style="width: 25%; font-weight: normal;">  <p><strong>&nbsp;N&uacute;mero de solicitud</strong></p>  <p><strong>${numeroRadicacion}&nbsp;&nbsp;</strong></p><p><strong>${fechaRadicacion}&nbsp;&nbsp;</strong></p>  <p><strong>&nbsp;</strong></p>  </th>  </tr>  </tbody>  </table>'
where pcoEtiqueta in ('ACRDS_TRMS_CDNES_WEB');



INSERT INTO DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES('AFILIACION_INDEPENDIENTE_WEB','ACRDS_TRMS_CDNES_PERS_PENS_IDPTE');