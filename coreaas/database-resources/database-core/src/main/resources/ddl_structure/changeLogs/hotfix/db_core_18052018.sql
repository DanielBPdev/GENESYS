--liquibase formatted sql

--changeset mfuquene:01
--comment: Acutalizacion PlantillaComunicado
 UPDATE PlantillaComunicado SET pcocuerpo = '<p style="text-align: justify;">${contenido}</p>
<p style="text-align: justify;">El suscrito&nbsp;<strong>${nombres}</strong>&nbsp;con documento de identificaci&oacute;n&nbsp;<strong>${tipoIdentificacion}</strong>&nbsp;No.&nbsp;<strong>${numeroIdentificacion}</strong>&nbsp;en mi nombre propio, solicito a&nbsp;<strong>${nombreCcf}</strong>,la afiliaci&oacute;n y si fuera aceptado me comprometo a cumplir y respetar todas las normas, as&iacute; como las disposiciones legales que se refieren al subsidio familiar.</p>
<p style="text-align: justify;">Acepto de antemano que la violaci&oacute;n por mi parte de cualquiera de estas normas, dar&aacute; derecho a<strong>&nbsp;${nombreCcf}</strong>&nbsp;para ordenar mi expulsi&oacute;n.&nbsp;&nbsp;</p>
<p><strong>Declaraci&oacute;n:</strong></p>
<p>Declaro que la informaci&oacute;n registrada en este formulario es cierta y tiene por objeto solicitar la afiliaci&oacute;n a ${nombreCcf}. En caso de ser aceptado como afiliado me comprometo a cumplir y a respetar la legislaci&oacute;n del Subsidio Familiar, al igual que los estatutos y reglamentos de ${nombreCcf}.</p>
<p style="text-align: justify;">&nbsp;</p>
<p style="text-align: justify;">Cualquier falsedad u omisi&oacute;n voluntaria conlleva a la anulaci&oacute;n de esta solicitud.</p>
<p style="text-align: justify;">&nbsp;</p>
<p style="text-align: justify;"><input id="aceptacionDeclaracion" name="aceptacionDeclaracion" type="checkbox" />&nbsp;Acepto los t&eacute;rminos y condiciones de afiliaci&oacute;n descritos en la anterior declaraci&oacute;n.</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p style="text-align: center;">________________________________________________<br />Firma de solicitante</p>
<p style="text-align: center;">&nbsp;</p>
<p style="text-align: right;"><strong>Fecha de impresi&oacute;n: ${fechaSistema}</strong></p>' WHERE pcoetiqueta = 'ACRDS_TRMS_CDNES_PERS';
