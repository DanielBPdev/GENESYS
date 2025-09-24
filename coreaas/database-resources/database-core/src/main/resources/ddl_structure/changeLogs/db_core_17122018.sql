--liquibase formatted sql

--changeset squintero:01
--comment: Actualización de plantillas comunicados
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación empresas web tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso afiliación empresas web tiempo de proceso solicitud','Pie','COM_AVI_AEW_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación empresas web tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso afiliación empresas web tiempo de gestión de la solicitud back','Pie','COM_AVI_AEW_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación persona presencial tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso afiliación persona presencial tiempo de proceso solicitud','Pie','COM_AVI_APP_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación persona presencial tiempo recepción documentos físicos','Cuerpo','Encabezado','Mensaje','Aviso afiliación persona presencial tiempo recepción documentos físicos','Pie','COM_AVI_APP_TIM_ASE') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación persona presencial tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso afiliación persona presencial tiempo de gestión de la solicitud back','Pie','COM_AVI_APP_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación dependiente web tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso afiliación dependiente web tiempo de proceso solicitud','Pie','COM_AVI_ADW_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación dependiente web tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso afiliación dependiente web tiempo de gestión de la solicitud back','Pie','COM_AVI_ADW_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación independiente pensionado web tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso afiliación independiente pensionado web tiempo de proceso solicitud','Pie','COM_AVI_AIPW_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso afiliación independiente pensionado web tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso afiliación independiente pensionado web tiempo de gestión de la solicitud back','Pie','COM_AVI_AIPW_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad empresa presencial tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso novedad empresa presencial tiempo de proceso solicitud','Pie','COM_AVI_NEP_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad empresa presencial tiempo recepción documentos físicos','Cuerpo','Encabezado','Mensaje','Aviso novedad empresa presencial tiempo recepción documentos físicos','Pie','COM_AVI_NEP_TIM_ASE') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad empresa presencial tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso novedad empresa presencial tiempo de gestión de la solicitud back','Pie','COM_AVI_NEP_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad empresa web tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso novedad empresa web tiempo de proceso solicitud','Pie','COM_AVI_NEW_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad empresa web tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso novedad empresa web tiempo de gestión de la solicitud back','Pie','COM_AVI_NEW_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad persona presencial tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso novedad persona presencial tiempo de proceso solicitud','Pie','COM_AVI_NPP_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad persona presencial tiempo recepción documentos físicos','Cuerpo','Encabezado','Mensaje','Aviso novedad persona presencial tiempo recepción documentos físicos','Pie','COM_AVI_NPP_TIM_ASE') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad persona presencial tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso novedad persona presencial tiempo de gestión de la solicitud back','Pie','COM_AVI_NPP_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad dependiente web tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso novedad dependiente web tiempo de proceso solicitud','Pie','COM_AVI_NDW_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad dependiente web tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso novedad dependiente web tiempo de gestión de la solicitud back','Pie','COM_AVI_NDW_TIM_GSB') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad persona web tiempo de proceso solicitud','Cuerpo','Encabezado','Mensaje','Aviso novedad persona web tiempo de proceso solicitud','Pie','COM_AVI_NPW_TIM_PS') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso novedad persona web tiempo de gestión de la solicitud back','Cuerpo','Encabezado','Mensaje','Aviso novedad persona web tiempo de gestión de la solicitud back','Pie','COM_AVI_NPW_TIM_GSB') ;

--changeset squintero:02
--comment: Adición de variables plantillas
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEW_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEW_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_APP_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_APP_TIM_ASE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_APP_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_ADW_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_ADW_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AIPW_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AIPW_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEP_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEP_TIM_ASE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEP_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEW_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEW_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPP_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPP_TIM_ASE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPP_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NDW_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NDW_TIM_GSB') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPW_TIM_PS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${solicitudDeNovedad}','0','Solicitud de Novedad','Número de radicado de la solicitud de Novedad','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPW_TIM_GSB') ) ;

--changeset squintero:03
--comment: Adición de constantes plantillas
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEW_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AEW_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_APP_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_APP_TIM_ASE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_APP_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_ADW_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_ADW_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AIPW_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_AIPW_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEP_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEP_TIM_ASE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEP_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEW_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NEW_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPP_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPP_TIM_ASE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPP_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NDW_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NDW_TIM_GSB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPW_TIM_PS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_AVI_NPW_TIM_GSB')) ;

--changeset squintero:04
--comment: Actualización de cuerpo de plantillas
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEW_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_AEW_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_APP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_APP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_APP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_ADW_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_ADW_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_AIPW_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_AIPW_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NEP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NEP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NEP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NEW_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NEW_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NPP_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NPP_TIM_ASE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NPP_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NDW_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NDW_TIM_GSB';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NPW_TIM_PS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'COM_AVI_NPW_TIM_GSB';

--changeset squintero:05
--comment: Adición de parámetros para timers de bpm
insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_AEW_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación empleador web - tiempo parametrizado para la gestión de la solicitud');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_AEW_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación empleador web - tiempo parametrizado para la gestión de la solicitud en el back');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_APP_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación persona presencial - tiempo parametrizado para la gestión de la solicitud');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_APP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación persona presencial - tiempo parametrizado para la recepción de documentos físicos');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_APP_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación persona presencial - tiempo parametrizado para la gestión de la solicitud en el back');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_ADW_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación dependiente web - tiempo parametrizado para la gestión de la solicitud');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_ADW_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación dependiente web - tiempo parametrizado para la gestión de la solicitud en el back');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_AIPW_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación independiente o pensionado web - tiempo parametrizado para la gestión de la solicitud');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_AIPW_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM afiliación independiente o pensionado web - tiempo parametrizado para la gestión de la solicitud en el back');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NEP_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad empresa presencial - tiempo parametrizado proceso solicitud.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NEP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad empresa presencial - tiempo parametrizado para la recepción de documentos físicos.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NEP_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad empresa presencial - tiempo parametrizado para la gestión de la solicitud en el back.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NEW_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad empresa web - tiempo parametrizado proceso solicitud.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NEW_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad empresa web - tiempo parametrizado para la gestión de la solicitud en el back.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NPP_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad persona presencial - tiempo parametrizado proceso solicitud.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NPP_TIEMPO_SOL_PENDIENTE_DOCUMENTOS', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad persona presencial - tiempo parametrizado para la recepción de documentos físicos.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NPP_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad persona presencial - tiempo parametrizado para la gestión de la solicitud en el back.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NDW_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad dependiente web - tiempo parametrizado proceso solicitud.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NDW_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad dependiente web - tiempo parametrizado para la gestión de la solicitud en el back.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NPW_TIEMPO_PROCESO_SOLICITUD', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad persona web - tiempo parametrizado proceso solicitud.');

insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion)
VALUES ('BPM_NPW_TIEMPO_ASIGNACION_BACK', '10m', 0, 'EJECUCION_TIMER', 'BPM novedad persona web - tiempo parametrizado para la gestión de la solicitud en el back.');

