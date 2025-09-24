
update PlantillaComunicado
set pcoCuerpo = '<p style="text-align: justify;">${contenido}</p><p style="text-align: justify;"></p><p><strong>Declaración:</strong></p><p style="text-align: justify;">En calidad de jefe de hogar postulante declaro que la información registrada en este formulario es cierta y tiene por objeto solicitar la postulación al subsidio de vivienda FOVIS de <strong>${nombreCcf}</strong>. En caso de ser aceptados como postulantes nos comprometemos a cumplir y a respetar la legislación vigente, al igual que los estatutos y reglamentos de <strong>${nombreCcf}</strong>. Cualquier falsedad u omisión voluntaria conlleva a la anulación de esta solicitud.</p><p style="text-align: justify;">Si requiere más información sobre los resultados de validación, por favor comuníquese con la caja de compensación.</p><p style="text-align: justify;">Nota: La solicitud será radicada, lo cual no implica aceptación debido a que quedará pendiente la revisión de los requisitos documentales, por parte de la caja de compensación familiar. En caso de hallar inconsistencias la postulación puede ser rechazada.</p><p style="text-align: justify;">Acepto los términos y condiciones de postulación descritos en la anterior declaración.<span class="glyphicon glyphicon-info-sign" aria-hidden="true" role="button" data-toggle="tooltip" title="" data-original-title="Haga click aquí, si usted está de acuerdo con la información registrada"></span><input id="aceptacionDeclaracion" name="aceptacionDeclaracion" type="checkbox"></p>
<p><strong>Firmas</strong></p><p></p>
<p style="margin-bottom:0px;">Firma del afiliado: </p>
<sup style="margin-top:0px;font-family: serif;">___________________________________________________________________</sup>
<p style="margin-bottom:0px;">Nombre del afiliado: </p>
<sup style="margin-top:0px;font-family: serif;">___________________________________________________________________</sup>
<p style="margin-bottom:0px;">Tipo de identificación</p>
<sup style="margin-top:0px;font-family: serif;">___________________________________________________________________</sup>
<p style="margin-bottom:0px;">Número de identificación </p>
<sup style="margin-top:0px;font-family: serif;">___________________________________________________________________</sup>',
pcoEncabezado = '<table style="width: 100%;">   <tbody> <tr style="width: 100%;"> <th style="width: 20%; text-align: center;">${logoDeLaCcf}</th> <th style="width: 50%;text-align: right;padding: 0 4em;"><strong>Solicitud de postulación FOVIS  </strong><br></br>Fecha de postulación<br></br>${fechaPostulacion}</th>  </tr> </tbody> </table>'
where pcoEtiqueta = 'ACRDS_TRMS_CDNES_WEB_FOVIS'

update PlantillaComunicado
set pcoEncabezado = '<table style="width: 100%;"><tbody><tr style="width: 100%;"><th style="width: 20%; text-align: left;">&nbsp;${logoDeLaCcf}</th><th style="width: 60%;text-align:center"><strong>SOLICITUD DE AFILIACIÓN DE EMPLEADOR
A LA CAJA DE COMPENSACIÓN</strong>&nbsp;</th><th style="width: 30%;text-align: right;display: flex;">Número de solicitud<br></br>${numeroRadicacion}&nbsp;<br></br>${fechaRadicacion}&nbsp;</th></tr></tbody></table>'
where pcoEtiqueta =  'ACRDS_TRMS_CDNES_WEB'

