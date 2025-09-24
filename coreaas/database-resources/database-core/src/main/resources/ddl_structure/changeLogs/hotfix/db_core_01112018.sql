--liquibase formatted sql

--changeset mamonroy:01
--comment: Actualizaciones tabla PlantillaComunicado
UPDATE PlantillaComunicado
SET pcoCuerpo = '<p style="text-align: justify;">${contenido}</p><p style="text-align: justify;"><p><strong>Declaraci&oacute;n:</strong></p><p style="text-align: justify;">En calidad de jefe de hogar postulante declaro que la informaci&oacute;n registrada en este formulario es cierta y tiene por objeto solicitar la postulaci&oacute;n al subsidio de vivienda FOVIS de <strong>${nombreCcf}</strong>. En caso de ser aceptados como postulantes nos comprometemos a cumplir y a respetar la legislaci&oacute;n vigente, al igual que los estatutos y reglamentos de <strong>${nombreCcf}</strong>. Cualquier falsedad u omisi&oacute;n voluntaria conlleva a la anulaci&oacute;n de esta solicitud.</p><p style="text-align: justify;">Si requiere m&aacute;s informaci&oacute;n sobre los resultados de validaci&oacute;n, por favor comun&iacute;quese con la caja de compensaci&oacute;n.</p><p style="text-align: justify;">Nota: La solicitud ser&aacute; radicada, lo cual no implica aceptaci&oacute;n debido a que quedar&aacute; pendiente la revisi&oacute;n de los requisitos documentales, por parte de la caja de compensaci&oacute;n familiar. En caso de hallar inconsistencias la postulaci&oacute;n puede ser rechazada.</p><p style="text-align: justify;">Acepto los t&eacute;rminos y condiciones de postulaci&oacute;n descritos en la anterior declaraci&oacute;n.<span class="glyphicon glyphicon-info-sign" aria-hidden="true" role="button" data-toggle="tooltip" title="" data-original-title="Haga click aquí, si usted está de acuerdo con la información registrada"></span><input id="aceptacionDeclaracion" name="aceptacionDeclaracion" type="checkbox" /></p>'
WHERE pcoEtiqueta = 'ACRDS_TRMS_CDNES_WEB_FOVIS'

--changeSET abaquero:01
--comment: Actualizacion parametrización para validación Lion de campos de fecha de inicio de concordato - Archivo A-AR PILA
UPDATE dbo.ValidatorParamValue SET value='9' WHERE id=2110760

--changeSET abaquero:02
--comment: Actualizacion parametrización para validación Lion de campos Indicador UGPP y # de acto
UPDATE dbo.ValidatorParamValue SET value='9' WHERE id=2111669

--changeSET abaquero:03
--comment: Actualizacion parametrización para validación Lion de cotizante por subsistema al que aporta (Incidencia 0244019)
UPDATE dbo.ValidatorDefinition SET state=1 WHERE id=2110054
