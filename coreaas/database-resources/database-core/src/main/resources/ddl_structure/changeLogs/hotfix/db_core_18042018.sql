--liquibase formatted sql

--changeset jvelandia:01
--comment: Inserts en tablas PlantillaComunicado y VariableComunicado
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Encabezado_159 Resumen datos de la solicitud','Cuerpo','Encabezado','Mensaje','Encabezado_159 Resumen datos de la solicitud','Pie','HU_PROCESO_13_417_PERSONA') ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacion}','1','Tipo identificación','Tipo identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacion}','2','Número Identificación','Número Identificación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${dv}','2,1','Dv','Cuando el valor del campo "Tipo identificación" es igual a "NIT"','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','3','Razón social / Nombre','Nombre completo compuesto por los campos Primer nombre, Segundo nombre, Primer apellido, Segundo apellido.Presentados en pantalla concatenados. Si es un empresa "Razón social" de ser persona','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoDeTransaccion}','10','Tipo de transacción','Tipo de transacción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoSolicitante}','11','Tipo solicitante','Presenta el valor del campo "Tipo de solicitante" asociado a ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${clasificacion}','12','Clasificación','Presenta el valor del campo "Clasificación" asociado a la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeSolicitud}','4','Número de solicitud','Campo corresponde al número asignado automáticamente ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeSolicitud}','5','Fecha y hora de solicitud','Corresponde al campo "Fecha y hora de radicación"','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${usuarioVerificador}','6','Usuario verificador','Campo que indica el nombre de usuario (back) que verificó la solicitud y la devolvió al front para gestionar el producto no conforme subsanable)','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaYHoraDeAsignacion}','7','Fecha y hora de asignación','Fecha y hora de asignación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoDeLaSolicitud}','8','Estado de la Solicitud','Estado de la Solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${canalDeRecepcion}','9','Canal de recepción','Canal de recepción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'HU_PROCESO_13_417_PERSONA') ) ;

--changeset jzambrano:02
--comment: Update PlantillaComunicado
UPDATE PlantillaComunicado SET pcoCuerpo = '<p style="text-align: justify;">${contenido}</p>
<p style="text-align: justify;">El suscrito <strong>${nombres}</strong> con <strong>${tipoIdentificacion}</strong> No. <strong>${numeroIdentificacion}</strong> &nbsp;en mi nombre propio como persona natural o en mi car&aacute;cter como representante legal de este empleador,solicito a <strong>${nombreCcf}</strong>,la afiliaci&oacute;n del empleador que represento y si fuera aceptado me comprometo a cumplir y respetar todas las normas, as&iacute; como las disposiciones legales que se refieren al subsidio familiar.</p>
<p style="text-align: justify;">Acepto &nbsp;de antemano que la violaci&oacute;n por parte del empleador de cualquiera de estas normas, dar&aacute; derecho a <strong>${nombreCcf}</strong> para ordenar la expulsi&oacute;n del empleador afiliado. La responsabilidad en cuanto al subsidio familiar,queda limitada para la caja desde el momento de la afiliaci&oacute;n y pago de aportes por parte del empleador,hasta que el empleador sea desafiliado por cualquier motivo. &nbsp;</p>
<p><strong>Declaraci&oacute;n:</strong></p>
<p style="text-align: justify;">En calidad de representante legal del empleador declaro que la Informaci&oacute;n registrada en este formulario es cierta y tiene por objeto solicitar la afiliaci&oacute;n a <strong>${nombreCcf}</strong>. En caso de ser aceptados como afiliados nos comprometemos a cumplir y a respetar la legislaci&oacute;n del Subsidio Familiar, al igual que los estatutos y reglamentos de <strong>${nombreCcf}</strong>.</p>
<p style="text-align: justify;">Cualquier falsedad u omisi&oacute;n voluntaria conlleva a la anulaci&oacute;n de esta solicitud.&nbsp;&nbsp;</p>
<p style="text-align: justify;"><input id="aceptacionDeclaracion" name="aceptacionDeclaracion" type="checkbox" /><strong>${aceptcion}</strong></p>
<p>&nbsp;</p>
<p>___________________________________</p>
<p><strong>${nombres}</strong><br />${tipoIdentificacion} ${numeroIdentificacion}</p>
<p>&nbsp;</p>' 
WHERE pcoetiqueta = 'ACRDS_TRMS_CDNES_WEB'

--changeset rarboleda:03
--comment: Insert ConjuntoValidacionSubsidio
INSERT ConjuntoValidacionSubsidio (cvsId, cvsTipoValidacion) VALUES (26,'PERMITIR_EVALUAR_LIQUIDACION_EMPRESA');
INSERT ConjuntoValidacionSubsidio (cvsId, cvsTipoValidacion) VALUES (27,'PERMITIR_EVALUAR_LIQUIDACION_TRABAJADOR');
INSERT ConjuntoValidacionSubsidio (cvsId, cvsTipoValidacion) VALUES (28,'PERMITIR_EVALUAR_LIQUIDACION_BENEFICIARIOS');
