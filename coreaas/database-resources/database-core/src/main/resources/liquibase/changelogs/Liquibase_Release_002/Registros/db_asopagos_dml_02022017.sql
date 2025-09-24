--liquibase formatted sql

--changeset jzambrano:01 
--comment: se agrega plantilla comunicado personas
INSERT INTO PlantillaComunicado (pcoNombre, pcoEtiqueta,pcoCuerpo) VALUES ('Acuerdo de términos y condiciones personas','ACUERDOS_TERMINOS_Y_CONDICIONES_PERSONAS','<p style="text-align:justify;">El suscrito <strong>${nombres}</strong> con <strong>${tipoIdentificacion}</strong> No. <strong>${numeroIdentificacion}</strong> &nbsp;en mi nombre propio como persona natural o en mi car&aacute;cter como representante legal de este empleador, solicito a <strong>${nombreCcf}</strong>, la afiliaci&oacute;n del empleador que represento y si fuera aceptado me comprometo a cumplir y respetar todas las normas, as&iacute; como las disposiciones legales que se refieren al subsidio familiar.</p>
<p style="text-align:justify;">Acepto &nbsp;de antemano que la violaci&oacute;n por parte del empleador de cualquiera de estas normas, dar&aacute; derecho a <strong>${nombreCcf}</strong> para ordenar la expulsi&oacute;n del empleador afiliado. La responsabilidad en cuanto al subsidio familiar, queda limitada para la caja desde el momento de la afiliaci&oacute;n y pago de aportes por parte del empleador, hasta que el empleador sea desafiliado por cualquier motivo. &nbsp;</p>
<p><strong>Declaraci&oacute;n:</strong></p>
<p style="text-align:justify;">En calidad de representante legal del empleador declaro que la informaci&oacute;n registrada en este formulario es cierta y tiene por objeto solicitar la afiliaci&oacute;n a <strong>${nombreCcf}</strong>. En caso de ser aceptados como afiliados nos comprometemos a cumplir y a respetar la legislaci&oacute;n del Subsidio Familiar, al igual que los estatutos y reglamentos de <strong>${nombreCcf}</strong>.</p>
<p style="text-align:justify;">Cualquier falsedad u omisi&oacute;n voluntaria conlleva a la anulaci&oacute;n de esta solicitud.<input id="aceptacionDeclaracion" name="aceptacionDeclaracion" type="checkbox"></p>
<p>&nbsp;</p>
<p>___________________________________</p>
<p><strong>${nombres}</strong><br/>${tipoIdentificacion} ${numeroIdentificacion}</p>
<p>&nbsp;</p>');


--changeset jzambrano:02 
--comment: se agrega plantilla comunicado personas
INSERT INTO variablecomunicado(vcoClave,vcoDescripcion,vcoNombre,vcoNombreConstante,vcoPlantillaComunicado)
VALUES ('${nombreCcf}','Nombre de la caja de Compensación','Nombre CCF','NOMBRE_CCF',(select pcoId from plantillacomunicado where pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_WEB'));

INSERT INTO variablecomunicado(vcoClave,vcoDescripcion,vcoNombre,vcoNombreConstante,vcoPlantillaComunicado)
VALUES ('${fechaDelSistema}','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','Fecha del sistema',NULL,(select pcoId from plantillacomunicado where pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_WEB'));


--changeset juagonzalez:03 
--comment: correccion plantillaComunicado
delete from VariableComunicado where vcoClave='${nombreCcf}' and vcoDescripcion='Nombre de la caja de Compensación' and vcoNombre='Nombre CCF' and vcoPlantillaComunicado=47;
delete from VariableComunicado where vcoClave='${fechaDelSistema}' and vcoDescripcion='dd/mm/aaaa proporcionado por el sistema al generar el comunicado' and vcoNombre='Fecha del sistema' and vcoPlantillaComunicado=47; 

INSERT INTO variablecomunicado(vcoClave,vcoDescripcion,vcoNombre,vcoNombreConstante,vcoPlantillaComunicado)
VALUES ('${nombreCcf}','Nombre de la caja de Compensación','Nombre CCF','NOMBRE_CCF',(select pcoId from plantillacomunicado where pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_WEB'));

INSERT INTO variablecomunicado(vcoClave,vcoDescripcion,vcoNombre,vcoNombreConstante,vcoPlantillaComunicado)
VALUES ('${fechaDelSistema}','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','Fecha del sistema',NULL,(select pcoId from plantillacomunicado where pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_WEB'));

INSERT INTO variablecomunicado(vcoClave,vcoDescripcion,vcoNombre,vcoNombreConstante,vcoPlantillaComunicado)
VALUES ('${nombreCcf}','Nombre de la caja de Compensación','Nombre CCF','NOMBRE_CCF',(select pcoId from plantillacomunicado where pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_PERSONAS'));

INSERT INTO variablecomunicado(vcoClave,vcoDescripcion,vcoNombre,vcoNombreConstante,vcoPlantillaComunicado)
VALUES ('${fechaDelSistema}','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','Fecha del sistema',NULL,(select pcoId from plantillacomunicado where pcoEtiqueta='ACUERDOS_TERMINOS_Y_CONDICIONES_PERSONAS'));

--changeset lzarate:04 
--comment: Actualización e Ingreso Bloque Validación 123-374-1
update ValidacionProceso set vapProceso = 'AFILIACION_INDEPENDIENTE_WEB' where vapBloque = '123-374-1';
INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion) VALUES ('123-374-1','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'PENSIONADO');

INSERT INTO ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion) VALUES ('123-374-1','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_INDEPENDIENTE_WEB','ACTIVO',1,'TRABAJADOR_INDEPENDIENTE');