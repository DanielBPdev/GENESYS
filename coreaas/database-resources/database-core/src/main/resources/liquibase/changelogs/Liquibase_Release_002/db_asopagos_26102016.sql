--liquibase formatted sql

--changeset  sbrinez:01 
DROP INDEX IDX_Persona_perRazonSocial ON Persona;

--changeset  mgiraldo:03
--Eliminación de datos duplicados
delete from RequisitoTipoSolicitante where rtsTipoTransaccion='AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO';

--changeset  mgiraldo:04

/*"'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO'"*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro'),'OBLIGATORIO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'INHABILITADO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'INHABILITADO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');

--changeset  mgiraldo:05

ALTER TABLE RequisitoTipoSolicitante ADD CONSTRAINT UK_RequisitoTipoSolicitante UNIQUE (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion);

--changeset  edelgado:06

INSERT INTO variablecomunicado(vcoClave,vcoDescripcion,vcoNombre,vcoNombreConstante,vcoPlantillaComunicado)
VALUES ('${logoDeLaCcf}','Logotipo de la Caja de Compensación','Logo de la CCF','LOGO_DE_LA_CCF',(select pcoId from plantillacomunicado where pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_WEB'));

UPDATE PlantillaComunicado SET pcoAsunto='Acuerdo términos y condiciones web',
pcoCuerpo='<p style="text-align: justify;">${contenido}</p>
<p style="text-align: center;"><strong>Declaraci&oacute;n</strong></p>
<p style="text-align: justify;">El suscrito <strong>${nombres}</strong> con <strong>${tipoIdentificacion}</strong> No. <strong>${numeroIdentificacion}</strong> &nbsp;en mi nombre propio como persona natural o en mi car&aacute;cter como representante legal de este empleador, solicito a <strong>${nombreCcf}</strong>, la afiliaci&oacute;n del empleador que represento y si fuera aceptado me comprometo a cumplir y respetar todas las normas, as&iacute; como las disposiciones legales que se refieren al subsidio familiar.</p>
<p style="text-align: justify;">Acepto &nbsp;de antemano que la violaci&oacute;n por parte del empleador de cualquiera de estas normas, dar&aacute; derecho a <strong>${nombreCcf}</strong> para ordenar la expulsi&oacute;n del empleador afiliado. La responsabilidad en cuanto al subsidio familiar, queda limitada para la caja desde el momento de la afiliaci&oacute;n y pago de aportes por parte del empleador, hasta que el empleador sea desafiliado por cualquier motivo. &nbsp;</p>
<p><strong>Declaraci&oacute;n:</strong></p>
<p style="text-align: justify;">En calidad de representante legal del empleador declaro que la informaci&oacute;n registrada en este formulario es cierta y tiene por objeto solicitar la afiliaci&oacute;n a <strong>${nombreCcf}</strong>. En caso de ser aceptados como afiliados nos comprometemos a cumplir y a respetar la legislaci&oacute;n del Subsidio Familiar, al igual que los estatutos y reglamentos de <strong>${nombreCcf}</strong>.</p>
<p style="text-align: justify;">Cualquier falsedad u omisi&oacute;n voluntaria conlleva a la anulaci&oacute;n de esta solicitud.<input id="aceptacionDeclaracion" name="aceptacionDeclaracion" type="checkbox" /></p>
<p>&nbsp;</p>
<p>___________________________________</p>
<p><strong>${nombres}</strong><br />${tipoIdentificacion} ${numeroIdentificacion}</p>
<p>&nbsp;</p>',
pcoEncabezado='<table style="width: 100%;">
<tbody>
<tr style="width: 100%;">
<th style="width: 20%; text-align: left;">&nbsp;LOGO</th>
<th style="width: 60%;"><strong>SOLICITUD DE AFILIACI&Oacute;N DE EMPLEADORES</strong>&nbsp;</th>
<th style="width: 20%; text-align: right;">N&uacute;mero de solicitud<br />${numeroRadicacion}&nbsp;</th>
</tr>
</tbody>
</table>',
pcoMensaje='Mensaje',
pcoPie='<p style="text-align: center;">&nbsp;</p>
<p style="text-align: center;">________________________________________________<br />Firma del representante legal y sello empleador</p>'

WHERE pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_WEB';
