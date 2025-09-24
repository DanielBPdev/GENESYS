--liquibase formatted sql

--changeset clmarin:01
--comment: Insercion de tabla PlantillaComunicado para etiquetas APR_ANL_CON, RCHZ_SUP_APO, RCHZ_ANL_CON - PlantillaComunicado
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aprobación Cierre aportes - Analista contable','Cuerpo','Encabezado','Mensaje','Aprobación Cierre aportes - Analista contable','Pie','APR_ANL_CON') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Rechazo Cierre aportes - Supervisor Aportes','Cuerpo','Encabezado','Mensaje','Rechazo Cierre aportes - Supervisor Aportes','Pie','RCHZ_SUP_APO') ;
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Rechazo Cierre aportes - Analista contable','Cuerpo','Encabezado','Mensaje','Rechazo Cierre aportes - Analista contable','Pie','RCHZ_ANL_CON') ;

--changeset clmarin:02
--comment: Insercion y actualiazcion de tablas VariableComunicado y PlantillaComunicado - Plantillas
-- APR_ANL_CON, RCHZ_SUP_APO, RCHZ_ANL_CON
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreUsuarioNotificacion}','0','Nombre Usuario Notificación','Nombre de usuario que recibira el correo ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoUsuario}','0','Cargo Usuario','Cargo del usuario que recibira el correo','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroCierre}','0','Número cierre','Número unico del proceso de cierre','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreUsuarioRemitente}','0','Nombre Usuario Remitente','Nombre de usuario el cual ejecuto la acción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoUsuarioRemitente}','0','Cargo Usuario Remitente','Cargo del usuario que ejecuto la acción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreUsuarioNotificacion}','0','Nombre Usuario Notificación','Nombre de usuario que recibira el correo ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoUsuario}','0','Cargo Usuario','Cargo del usuario que recibira el correo','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroCierre}','0','Número cierre','Número unico del proceso de cierre','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreUsuarioRemitente}','0','Nombre Usuario Remitente','Nombre de usuario el cual ejecuto la acción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoUsuarioRemitente}','0','Cargo Usuario Remitente','Cargo del usuario que ejecuto la acción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreUsuarioNotificacion}','0','Nombre Usuario Notificación','Nombre de usuario que recibira el correo ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoUsuario}','0','Cargo Usuario','Cargo del usuario que recibira el correo','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroCierre}','0','Número cierre','Número unico del proceso de cierre','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreUsuarioRemitente}','0','Nombre Usuario Remitente','Nombre de usuario el cual ejecuto la acción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoUsuarioRemitente}','0','Cargo Usuario Remitente','Cargo del usuario que ejecuto la acción','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON') ) ;

UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreUsuarioNotificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreUsuarioNotificacion}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoUsuario}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoUsuario}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroCierre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroCierre}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreUsuarioRemitente}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreUsuarioRemitente}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoUsuarioRemitente}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoUsuarioRemitente}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreUsuarioNotificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreUsuarioNotificacion}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoUsuario}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoUsuario}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroCierre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroCierre}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreUsuarioRemitente}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreUsuarioRemitente}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoUsuarioRemitente}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoUsuarioRemitente}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreUsuarioNotificacion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreUsuarioNotificacion}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoUsuario}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoUsuario}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroCierre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroCierre}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreUsuarioRemitente}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreUsuarioRemitente}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoUsuarioRemitente}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoUsuarioRemitente}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';

--changeset clmarin:03
--comment: Insercion y actualiazcion de tablas VariableComunicado y PlantillaComunicado - Constantes
-- APR_ANL_CON, RCHZ_SUP_APO, RCHZ_ANL_CON
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'APR_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_SUP_APO')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_ANL_CON')) ;

UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'APR_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'RCHZ_SUP_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'RCHZ_ANL_CON';

--changeset clmarin:04
--comment: Insercion y actualiazcion de tablas VariableComunicado y PlantillaComunicado - CONSTANTES
-- APR_ANL_CON, RCHZ_SUP_APO, RCHZ_ANL_CON
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('CIERRE_RECAUDO', 'APR_ANL_CON');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('CIERRE_RECAUDO', 'RCHZ_SUP_APO');
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('CIERRE_RECAUDO', 'RCHZ_ANL_CON');

--changeset jocampo:05
--comment: Insercion de tabla PlantillaComunicado VariableComunicado para etiqueta COM_SUB_DIS_PAG_EMP
INSERT INTO PlantillaComunicado (pcoNombre, pcoAsunto, pcoEncabezado, pcoPie, pcoCuerpo, pcoMensaje, pcoEtiqueta) VALUES('Notificación de dispersión de pagos al empleador', 'Notificación de dispersión de pagos al empleador', 'Encabezado', 'Pie', 'Cuerpo<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${razonSocial/</p><br /> <p>${tipoIdentificacionEmpleador}</p><br /> <p>${numeroIdentificacionEmpleador}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br />  <p>${numeroDeTrabajadores}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br />', 'Mensaje<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${razonSocial/</p><br /> <p>${tipoIdentificacionEmpleador}</p><br /> <p>${numeroIdentificacionEmpleador}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br />  <p>${numeroDeTrabajadores}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br />', 'COM_SUB_DIS_PAG_EMP');
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razón social / Nombre','Nombre del empleador por el cual liquidó el subsidio','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionEmpleador}','Tipo identificación empleador','Tipo de identificación del empleador que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacionEmpleador}','Número identificación empleador','Número de identificación del empleador que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','Dirección','Dirección capturada en Información de ubicación y correspondencia del empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','Municipio','Municipio capturada en Información de ubicación y correspondencia del empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','Departamento','Departamento capturada en Información de ubicación y correspondencia del empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodosLiquidados}','Periodos liquidados','Periodos presentes en la liquidación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${montoLiquidado}','Monto liquidado','Monto liquidado por el empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeTrabajadores}','Número de trabajadores','Número de trabajadores en los periodos liquidados','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Firma de la caja de Compensación','FIRMA_RESPONSABLE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP')) ;

--changeset jocampo:06
--comment: Insercion de tabla PlantillaComunicado y VariableComunicado para etiqueta COM_SUB_DIS_PAG_TRA
INSERT INTO PlantillaComunicado (pcoNombre, pcoAsunto, pcoEncabezado, pcoPie, pcoCuerpo, pcoMensaje, pcoEtiqueta) VALUES('Notificación de dispersión de pagos al trabajador', 'Notificación de dispersión de pagos al trabajador', 'Encabezado', 'Pie', 'Cuerpo<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${nombreDelTrabajador}</p><br /> <p>${tipoIdentificacionEmpleador}</p><br /> <p>${numeroIdentificacionEmpleador}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br /> <p>${numeroDeBeneficiarios}</p><br /> <p>${reporteDeBeneficiarios}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br /> ', 'Mensaje<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${nombreDelTrabajador}</p><br /> <p>${tipoIdentificacionEmpleador}</p><br /> <p>${numeroIdentificacionEmpleador}</p><br /> <p>${direccion}</p><br /> <p>${municipio}</p><br /> <p>${departamento}</p><br /> <p>${telefono}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br /> <p>${numeroDeBeneficiarios}</p><br /> <p>${reporteDeBeneficiarios}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br /> ', 'COM_SUB_DIS_PAG_TRA');
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreDelTrabajador}','Nombre del trabajador','Nombre del trabajador por el cual liquidó el subsidio','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionEmpleador}','Tipo identificación empleador','Tipo de identificación del empleador que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacionEmpleador}','Número identificación empleador','Número de identificación del empleador que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','Dirección','Dirección capturada en Información de ubicación y correspondencia del empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${municipio}','Municipio','Municipio capturada en Información de ubicación y correspondencia del empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamento}','Departamento','Departamento capturada en Información de ubicación y correspondencia del empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodosLiquidados}','Periodos liquidados','Periodos presentes en la liquidación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${montoLiquidado}','Monto liquidado','Monto liquidado por el trabajador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeBeneficiarios}','Número de beneficiarios','Número de beneficiarios en los periodos liquidados','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${reporteDeBeneficiarios}','Reporte de beneficiarios','Lista de beneficiarios en los periodos liquidados','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Firma de la caja de Compensación','FIRMA_RESPONSABLE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_TRA')) ;

--changeset jocampo:07
--comment: Insercion de tabla PlantillaComunicado y VariableComunicado para etiqueta COM_SUB_DIS_PAG_TRA
INSERT INTO PlantillaComunicado (pcoNombre, pcoAsunto, pcoEncabezado, pcoPie, pcoCuerpo, pcoMensaje, pcoEtiqueta) VALUES('Notificación de dispersión de pagos al admin subsidio', 'Notificación de dispersión de pagos al admin subsidio', 'Encabezado', 'Pie', 'Cuerpo<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${nombreDelAdministradorDelSubsidio}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br /> <p>${numeroDeEmpleadores}</p><br /> <p>${numeroDeTrabajadores}</p><br /> <p>${numeroDeBeneficiarios}</p><br /> <p>${reporteDeBeneficiarios}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br />', 'Mensaje<br /> <p>${ciudadSolicitud}</p><br /> <p>${fechaDelSistema}</p><br /> <p>${nombreDelAdministradorDelSubsidio}</p><br /> <p>${periodosLiquidados}</p><br /> <p>${montoLiquidado}</p><br /> <p>${numeroDeEmpleadores}</p><br /> <p>${numeroDeTrabajadores}</p><br /> <p>${numeroDeBeneficiarios}</p><br /> <p>${reporteDeBeneficiarios}</p><br /> <p>${nombreCcf}</p><br /> <p>${logoDeLaCcf}</p><br /> <p>${departamentoCcf}</p><br /> <p>${ciudadCcf}</p><br /> <p>${direccionCcf}</p><br /> <p>${telefonoCcf}</p><br /> <p>${webCcf}</p><br /> <p>${logoSuperservicios}</p><br /> <p>${firmaResponsableCcf}</p><br /> <p>${responsableCcf}</p><br /> <p>${cargoResponsableCcf}</p><br />', 'COM_SUB_DIS_PAG_ADM_SUB');
-- variables
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreDelAdministradorDelSubsidio}','Nombre del administrador del subsidio','Nombre del administrador de subsidio','USUARIO_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodosLiquidados}','Periodos liquidados','Periodos presentes en la liquidación','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${montoLiquidado}','Monto liquidado','Monto liquidado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeEmpleadores}','Número de empleadores','Número de empleadores en los periodos liquidados','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeTrabajadores}','Número de trabajadores','Número de trabajadores en los periodos liquidados','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroDeBeneficiarios}','Número de beneficiarios','Número de beneficiarios en los periodos liquidados','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${reporteDeBeneficiarios}','Reporte de beneficiarios','Lista de beneficiarios en los periodos liquidados','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_EMP') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Firma de la caja de Compensación','FIRMA_RESPONSABLE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'COM_SUB_DIS_PAG_ADM_SUB')) ;

--changeset alquintero:08
--comment: Creacion tabla SolicitudVerificacionFovis
CREATE TABLE SolicitudVerificacionFovis(
	svfId bigint IDENTITY(1,1) NOT NULL,
	svfSolicitudGlobal bigint NOT NULL,
	svfPostulacionFOVIS bigint NULL,
	svfEstadoSolicitud varchar(42) NULL,
	svfTipo varchar(15) NULL,
	svfObservaciones varchar(500) NULL,
 CONSTRAINT PK_SolicitudVerificacionFovis_svfId PRIMARY KEY (svfId)
);

--changeset alquintero:09
--comment: Relaciones tabla SolicitudVerificacionFovis
ALTER TABLE SolicitudVerificacionFovis WITH CHECK ADD CONSTRAINT FK_SolicitudVerificacionFovis_svfPostulacionFOVIS FOREIGN KEY(svfPostulacionFOVIS) REFERENCES PostulacionFOVIS (pofId);
ALTER TABLE SolicitudVerificacionFovis CHECK CONSTRAINT FK_SolicitudVerificacionFovis_svfPostulacionFOVIS;
ALTER TABLE SolicitudVerificacionFovis WITH CHECK ADD CONSTRAINT FK_SolicitudVerificacionFovis_svfSolicitudGlobal FOREIGN KEY(svfSolicitudGlobal) REFERENCES Solicitud (solId);
ALTER TABLE SolicitudVerificacionFovis CHECK CONSTRAINT FK_SolicitudVerificacionFovis_svfSolicitudGlobal;

--changeset alquintero:10
--comment: Inserciones tablas ParametrizacionMetodoAsignacion y Constante
INSERT INTO ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion, pmaProceso, pmaMetodoAsignacion, pmaUsuario, pmaGrupo, pmaSedeCajaDestino) VALUES (1, 'VERIFICACION_POSTULACION_FOVIS', 'NUMERO_SOLICITUDES', null, 'CooConInFov', null);
INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion) VALUES ('BPMS_PROCESS_VERIFICACION_FOVIS_DEPLOYMENTID','com.asopagos.coreaas.bpm.postulacion_fovis_verificacion:postulacion_fovis_verificacion:0.0.2-SNAPSHOT', 'Identificador versión proceso BPM para la gestión de verficaición de la Postulación Fovis');

--changeset alquintero:11
--comment: ajuste de datos datotemporalsolicitud porque se eliminaron los campos idDocumentoControlInterno y salarioMensualIntegrante del objeto que guarda los datos de la postulación 
UPDATE datotemporalsolicitud 
SET dtsJsonPayload = REPLACE ( CAST(dtsJsonPayload AS nvarchar(max)) , '"idDocumentoControlInterno":null,"observacionesWeb"' , '"observacionesWeb"' ) 
WHERE dtsJsonPayload LIKE '%"idDocumentoControlInterno":null,"observacionesWeb"%';

UPDATE datotemporalsolicitud 
SET dtsJsonPayload = REPLACE ( CAST(dtsJsonPayload AS nvarchar(max)) , ',"salarioMensualIntegrante":null' , '' ) 
WHERE dtsJsonPayload LIKE '%,"salarioMensualIntegrante":null%';

UPDATE solicitudlegalizacionDesembolso 
SET sldjsonPostulacion = REPLACE ( CAST(sldjsonPostulacion AS nvarchar(max)) , '"idDocumentoControlInterno":null,"observacionesWeb"' , '"observacionesWeb"' ) 
WHERE sldjsonPostulacion LIKE '%"idDocumentoControlInterno":null,"observacionesWeb"%';

UPDATE solicitudlegalizacionDesembolso 
SET sldjsonPostulacion = REPLACE ( CAST(sldjsonPostulacion AS nvarchar(max)) , ',"salarioMensualIntegrante":null' , '' ) 
WHERE sldjsonPostulacion LIKE '%,"salarioMensualIntegrante":null%';

--changeset rlopez:12
--comment: Se agrega campo banNit
ALTER TABLE Banco ADD banNit VARCHAR(18);

--changeset rlopez:13
--comment: Se agrega campo pcsCalendarioPagoFallecimiento
ALTER TABLE ParametrizacionCondicionesSubsidio ADD pcsCalendarioPagoFallecimiento SMALLINT;

--changeset arocha:14
--comment: Se crea nuevo esquema de base de datos para la creacion de copia de todas las tablas de auditoria para azure
CREATE SCHEMA aud;

--changeset arocha:15
--comment: Se crean todas las tablas de auditoria existentes en la base de datos core_aud
CREATE TABLE aud.AccionCobro1C_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	accDiasLiquidacion bigint NULL,
	accHoraEjecucion datetime NULL,
	accLimiteEnvioDocumento bigint NULL,
	accVariableCalculo varchar(16) NULL,
	accCantidadPeriodos bigint NULL
)

CREATE TABLE aud.AccionCobro1D1E_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	acdInicioDiasConteo varchar(13) NULL,
	acdDiasTranscurridos bigint NULL,
	acdHoraEjecucion datetime NULL,
	acdLimiteEnvio bigint NULL,
	acdTipoCobro varchar(12) NULL
)

CREATE TABLE aud.AccionCobro1F_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	abfAccionCobro1F bit NULL,
	abfDiasParametrizados smallint NULL,
	abfSiguienteAccion varchar(29) NULL
)

CREATE TABLE aud.AccionCobro2C_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aocAnexoLiquidacion bit NULL,
	aocDiasEjecucion bigint NULL,
	aocHoraEjecucion datetime NULL
)

CREATE TABLE aud.AccionCobro2D_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aodInicioDiasConteo varchar(13) NULL,
	aodDiasTranscurridos bigint NULL,
	aodHoraEjecucion datetime NULL
)

CREATE TABLE aud.AccionCobro2E_aud(
	aceId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aceInicioDiasConteo varchar(13) NULL,
	aceDiasTranscurridos bigint NULL,
	aceHoraEjecucion datetime NULL
)

CREATE TABLE aud.AccionCobro2F2G_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aofInicioDiasConteo varchar(13) NULL,
	aofDiasTranscurridos bigint NULL,
	aofHoraEjecucion datetime NULL,
	aofLimiteEnvio bigint NULL,
	aofTipoCobro varchar(12) NULL
)

CREATE TABLE aud.AccionCobro2H_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	achAccionCobro2H bit NULL,
	achDiasRegistro bigint NULL,
	achDiasParametrizados bigint NULL,
	achSiguienteAccion varchar(29) NULL
)

CREATE TABLE aud.AccionCobroA_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	acaSuspensionAutomatica bit NULL,
	acaDiasLimitePago bigint NULL,
	acaFechaHoraEjecucion datetime NULL,
	acaDiasLimiteEnvioComunicado bigint NULL,
	acaMetodo varchar(8) NULL
)

CREATE TABLE aud.AccionCobroB_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	acbDiasGeneracionAviso bigint NULL,
	acbHoraEjecucion datetime NULL,
	acbLimiteEnvioComunicado bigint NULL,
	acbMetodo varchar(8) NULL
)

CREATE TABLE aud.ActaAsignacionFovis_aud(
	aafId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aafSolicitudAsignacion bigint NOT NULL,
	aafIdentificadorDocumentoActa varchar(255) NULL,
	aafIdentificadorDocumentoConsolidado varchar(255) NULL,
	aafNumeroResolucion varchar(20) NULL,
	aafNumeroOficio varchar(20) NULL,
	aafAnoResolucion varchar(4) NULL,
	aafFechaResolucion datetime NULL,
	aafFechaOficio datetime NULL,
	aafNombreResponsable1 varchar(50) NULL,
	aafCargoResponsable1 varchar(50) NULL,
	aafNombreResponsable2 varchar(50) NULL,
	aafCargoResponsable2 varchar(50) NULL,
	aafNombreResponsable3 varchar(50) NULL,
	aafCargoResponsable3 varchar(50) NULL,
	aafFechaConfirmacion datetime NULL,
	aafFechaCorte datetime NULL,
	aafInicioVigencia datetime NULL,
	aafFinVigencia datetime NULL,
	aafFechaActaAsignacionFovis datetime NULL,
	aafFechaPublicacion datetime NULL
)

CREATE TABLE aud.ActividadCartera_aud(
	acrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	acrActividadCartera varchar(42) NOT NULL,
	acrResultadoCartera varchar(33) NULL,
	acrComentarios varchar(500) NULL,
	acrCicloAportante bigint NOT NULL,
	acrFecha datetime NOT NULL,
	acrFechaCompromiso date NULL
)

CREATE TABLE aud.ActividadDocumento_aud(
	adoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	adoIdentificadorDocumento varchar(255) NOT NULL,
	adoTipoDocumento varchar(12) NOT NULL,
	adoActividadCartera bigint NOT NULL,
	adoDocumentoSoporte bigint NULL
)

CREATE TABLE aud.ActoAceptacionProrrogaFovis_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aapSolicitudNovedadFovis bigint NOT NULL,
	aapNumeroActoAdministrativo varchar(50) NOT NULL,
	aapFechaAprobacionConsejo date NOT NULL,
	aapId bigint NOT NULL
)

CREATE TABLE aud.AdministradorSubsidio_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	asuPersona bigint NOT NULL,
	asuId bigint NOT NULL
)

CREATE TABLE aud.AdminSubsidioGrupo_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	asgGrupoFamiliar bigint NOT NULL,
	asgAdministradorSubsidio bigint NOT NULL,
	asgMedioDePago bigint NULL,
	asgAfiliadoEsAdminSubsidio bit NOT NULL,
	asgMedioPagoActivo bit NOT NULL,
	asgRelacionGrupoFamiliar smallint NULL,
	asgId bigint NOT NULL
)

CREATE TABLE aud.Afiliado_aud(
	afiId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	afiPersona bigint NULL,
	afiPignoracionSubsidio bit NULL,
	afiCesionSubsidio bit NULL,
	afiRetencionSubsidio bit NULL,
	afiServicioSinAfiliacion bit NULL,
	afiCausaSinAfiliacion varchar(20) NULL,
	afiFechaInicioServiciosSinAfiliacion date NULL,
	afifechaFinServicioSinAfiliacion date NULL
)

CREATE TABLE aud.AFP_aud(
	afpId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	afpNombre varchar(150) NOT NULL,
	afpCodigoPila varchar(10) NULL
)

CREATE TABLE aud.AgendaCartera_aud(
	agrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	agrVisitaAgenda varchar(13) NULL,
	agrFecha date NOT NULL,
	agrHorario datetime NOT NULL,
	agrContacto varchar(255) NOT NULL,
	agrTelefono varchar(255) NULL,
	agrCicloAportante bigint NOT NULL
)

CREATE TABLE aud.AhorroPrevio_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ahpNombreAhorro varchar(65) NULL,
	ahpEntidad varchar(50) NULL,
	ahpFechaInicial date NULL,
	ahpValor numeric(19, 5) NULL,
	ahpFechaInmovilizacion date NULL,
	ahpFechaAdquisicion date NULL,
	ahpPostulacionFOVIS bigint NOT NULL,
	ahpId bigint NOT NULL,
	ahpAhorroMovilizado bit NULL
)

CREATE TABLE aud.AporteDetallado_aud(
	apdId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	apdAporteGeneral bigint NULL,
	apdDiasCotizados smallint NULL,
	apdHorasLaboradas smallint NULL,
	apdSalarioBasico numeric(19, 5) NULL,
	apdValorIBC numeric(19, 5) NULL,
	apdValorIntMora numeric(19, 5) NULL,
	apdTarifa numeric(5, 5) NULL,
	apdAporteObligatorio numeric(19, 5) NULL,
	apdValorSaldoAporte numeric(19, 5) NULL,
	apdCorrecciones varchar(400) NULL,
	apdEstadoAporteRecaudo varchar(50) NULL,
	apdEstadoAporteAjuste varchar(50) NULL,
	apdEstadoRegistroAporte varchar(50) NULL,
	apdSalarioIntegral bit NULL,
	apdMunicipioLaboral smallint NULL,
	apdDepartamentoLaboral smallint NULL,
	apdRegistroDetallado bigint NOT NULL,
	apdTipoCotizante varchar(100) NULL,
	apdEstadoCotizante varchar(60) NULL,
	apdEstadoAporteCotizante varchar(50) NULL,
	apdEstadoRegistroAporteCotizante varchar(40) NULL,
	apdPersona bigint NULL,
	apdUsuarioAprobadorAporte varchar(50) NOT NULL,
	apdEstadoRegistroAporteArchivo varchar(60) NOT NULL,
	apdCodSucursal varchar(10) NULL,
	apdNomSucursal varchar(100) NULL,
	apdFechaMovimiento date NULL,
	apdFechaCreacion date NULL,
	apdFormaReconocimientoAporte varchar(75) NULL,
	apdMarcaPeriodo varchar(19) NULL
)

CREATE TABLE aud.AporteGeneral_aud(
	apgId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	apgPeriodoAporte varchar(7) NULL,
	apgValTotalApoObligatorio numeric(19, 5) NULL,
	apgValorIntMora numeric(19, 5) NULL,
	apgFechaRecaudo date NULL,
	apgFechaProcesamiento datetime NULL,
	apgCodEntidadFinanciera smallint NULL,
	apgOperadorInformacion bigint NULL,
	apgModalidadPlanilla varchar(40) NULL,
	apgModalidadRecaudoAporte varchar(40) NULL,
	apgApoConDetalle bit NULL,
	apgNumeroCuenta varchar(17) NULL,
	apgRegistroGeneral bigint NOT NULL,
	apgPersona bigint NULL,
	apgEmpresa bigint NULL,
	apgSucursalEmpresa bigint NULL,
	apgEstadoAportante varchar(50) NULL,
	apgEstadoAporteAportante varchar(40) NULL,
	apgEstadoRegistroAporteAportante varchar(30) NULL,
	apgPagadorPorTerceros bit NULL,
	apgOrigenAporte varchar(26) NULL,
	apgCajaCompensacion int NULL,
	apgTipoSolicitante varchar(13) NULL,
	apgEmailAportante varchar(255) NULL,
	apgEmpresaTramitadoraAporte bigint NULL,
	apgFechaReconocimiento datetime NULL,
	apgFormaReconocimientoAporte varchar(75) NULL,
	apgMarcaPeriodo varchar(19) NULL,
	apgMarcaActualizacionCartera bit NULL
)

CREATE TABLE aud.AreaCajaCompensacion_aud(
	arcId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	arcNombre varchar(30) NULL
)

CREATE TABLE aud.ARL_aud(
	arlId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	arlNombre varchar(25) NOT NULL
)

CREATE TABLE aud.AsesorResponsableEmpleador_aud(
	areId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	areNombreUsuario varchar(255) NULL,
	arePrimario bit NULL,
	areEmpleador bigint NULL
)

CREATE TABLE aud.Banco_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	banCodigoPILA varchar(4) NOT NULL,
	banNombre varchar(255) NOT NULL,
	banMedioPago bit NULL,
	banId bigint NOT NULL,
	banCodigo varchar(6) NULL,
	banNit VARCHAR(18)
)

CREATE TABLE aud.Beneficiario_aud(
	benId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	benCertificadoEscolaridad bit NULL,
	benEstadoBeneficiarioAfiliado varchar(20) NULL,
	benEstadoBeneficiarioCaja varchar(20) NULL,
	benEstudianteTrabajoDesarrolloHumano bit NULL,
	benFechaAfiliacion date NULL,
	benFechaRecepcionCertificadoEscolar date NULL,
	benFechaVencimientoCertificadoEscolar date NULL,
	benLabora bit NULL,
	benTipoBeneficiario varchar(30) NOT NULL,
	benGrupoFamiliar bigint NULL,
	benPersona bigint NOT NULL,
	benAfiliado bigint NOT NULL,
	benSalarioMensualBeneficiario numeric(19, 0) NULL,
	benGradoAcademico smallint NULL,
	benMotivoDesafiliacion varchar(70) NULL,
	benFechaRetiro date NULL,
	benFechaInicioSociedadConyugal date NULL,
	benFechaFinSociedadConyugal date NULL,
	benRolAfiliado bigint NULL,
	benBeneficiarioDetalle bigint NULL
)

CREATE TABLE aud.BeneficiarioDetalle_aud(
	bedId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	bedSalarioMensual numeric(19, 5) NULL,
	bedLabora bit NULL,
	bedPersonaDetalle bigint NOT NULL,
	bedCertificadoEscolaridad bit NULL,
	bedFechaRecepcionCertificadoEscolar date NULL,
	bedFechaVencimientoCertificadoEscolar date NULL
)

CREATE TABLE aud.Beneficio_aud(
	befTipoBeneficio varchar(16) NOT NULL,
	befVigenciaFiscal bit NOT NULL,
	befFechaVigenciaInicio date NULL,
	befFechaVigenciaFin date NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	befId bigint NOT NULL
)

CREATE TABLE aud.BeneficioEmpleador_aud(
	bemId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	bemBeneficioActivo bit NULL,
	bemFechaVinculacion date NULL,
	bemFechaDesvinculacion date NULL,
	bemEmpleador bigint NOT NULL,
	bemBeneficio bigint NOT NULL,
	bemMotivoInactivacion varchar(50) NULL
)

CREATE TABLE aud.BitacoraCartera_aud(
	bcaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	bcaFecha date NULL,
	bcaActividad varchar(22) NULL,
	bcaMedio varchar(16) NULL,
	bcaResultado varchar(33) NULL,
	bcaUsuario varchar(255) NULL,
	bcaPersona bigint NOT NULL,
	bcaTipoSolicitante varchar(13) NOT NULL
)

CREATE TABLE aud.CajaCompensacion_aud(
	ccfId int NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ccfHabilitado bit NOT NULL,
	ccfMetodoGeneracionEtiquetas varchar(150) NOT NULL,
	ccfNombre varchar(100) NOT NULL,
	ccfSocioAsopagos bit NOT NULL,
	ccfDepartamento smallint NOT NULL,
	ccfCodigo varchar(5) NOT NULL,
	ccfCodigoRedeban int NULL
)

CREATE TABLE aud.CajaCorrespondencia_aud(
	ccoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ccoCodigoEtiqueta varchar(12) NULL,
	ccoConsecutivo bigint NULL,
	ccoDestinatario varchar(255) NULL,
	ccoEstado varchar(20) NULL,
	ccoFechaFin datetime2(7) NULL,
	ccoFechaInicio datetime2(7) NULL,
	ccoFechaRecepcion datetime2(7) NULL,
	ccoRemitente varchar(255) NULL,
	ccoSedeDestinatario varchar(2) NULL,
	ccoSedeRemitente varchar(2) NULL,
	ccoUsuarioRecepcion varchar(255) NULL
)

CREATE TABLE aud.CargueArchivoActualizacion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	caaNombreArchivo varchar(50) NOT NULL,
	caaFechaProcesamiento datetime NULL,
	caaCodigoIdentificadorECM varchar(255) NOT NULL,
	caaEstado varchar(40) NOT NULL,
	caaFechaAceptacion datetime NULL,
	caaId bigint NOT NULL
)

CREATE TABLE aud.CargueArchivoCruceFovis_aud(
	cacId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cacCodigoIdentificadorECM varchar(255) NOT NULL,
	cacNombreArchivo varchar(50) NOT NULL,
	cacFechaCargue datetime NOT NULL,
	cacInfoArchivoJsonPayload text NULL
)

CREATE TABLE aud.CargueMultiple_aud(
	camId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	camCodigoCargueMultiple bigint NOT NULL,
	camIdSucursalEmpleador bigint NOT NULL,
	camIdEmpleador bigint NOT NULL,
	camTipoSolicitante varchar(30) NOT NULL,
	camClasificacion varchar(100) NOT NULL,
	camTipoTransaccion varchar(100) NULL,
	camProceso varchar(100) NOT NULL,
	camEstado varchar(20) NOT NULL,
	camFechaCarga date NOT NULL,
	camCodigoIdentificacionECM varchar(255) NOT NULL
)

CREATE TABLE aud.CargueMultipleSupervivencia_aud(
	cmsId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cmsFechaIngreso date NOT NULL,
	cmsUsuario varchar(255) NOT NULL,
	cmsPeriodo date NULL,
	cmsIdentificacionECM varchar(255) NOT NULL,
	cmsEstadoCargueSupervivencia varchar(255) NOT NULL,
	cmsNombreArchivo varchar(30) NOT NULL
)

CREATE TABLE aud.Cartera_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	carDeudaPresunta numeric(19, 5) NULL,
	carEstadoCartera varchar(6) NOT NULL,
	carEstadoOperacion varchar(10) NOT NULL,
	carFechaCreacion datetime NOT NULL,
	carPersona bigint NOT NULL,
	carMetodo varchar(8) NULL,
	carPeriodoDeuda date NOT NULL,
	carRiesgoIncobrabilidad varchar(48) NULL,
	carTipoAccionCobro varchar(4) NULL,
	carTipoDeuda varchar(11) NULL,
	carTipoLineaCobro varchar(3) NULL,
	carTipoSolicitante varchar(13) NULL,
	carFechaAsignacionAccion datetime NULL,
	carId bigint NOT NULL,
	carUsuarioTraspaso varchar(255) NULL
)

CREATE TABLE aud.CarteraDependiente_aud(
	cadId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cadDeudaPresunta numeric(19, 5) NULL,
	cadEstadoOperacion varchar(10) NOT NULL,
	cadCartera bigint NOT NULL,
	cadPersona bigint NOT NULL,
	cadDeudaReal numeric(19, 5) NULL
)

CREATE TABLE aud.Categoria_aud(
	catId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	catTipoAfiliado varchar(30) NOT NULL,
	catCategoriaPersona varchar(50) NOT NULL,
	catTipoBeneficiario varchar(30) NULL,
	catClasificacion varchar(48) NOT NULL,
	catTotalIngresoMesada numeric(19, 0) NOT NULL,
	catFechaCambioCategoria date NOT NULL,
	catMotivoCambioCategoria varchar(50) NOT NULL,
	catAfiliadoPrincipal bit NOT NULL,
	catIdAfiliado bigint NOT NULL,
	catIdBeneficiario bigint NULL
)

CREATE TABLE aud.CicloAportante_aud(
	capId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	capPersona bigint NULL,
	capTipoSolicitante varchar(14) NULL,
	capCicloCartera bigint NULL
)

CREATE TABLE aud.CicloAsignacion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ciaNombre varchar(50) NULL,
	ciaFechaInicio date NULL,
	ciaFechaFin date NULL,
	ciaCicloPredecesor bigint NULL,
	ciaEstadoCicloAsignacion varchar(30) NULL,
	ciaCicloActivo bit NULL,
	ciaId bigint NOT NULL,
	ciaValorDisponible numeric(19, 5) NULL
)

CREATE TABLE aud.CicloCartera_aud(
	ccrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ccrEstadoCiclo varchar(10) NOT NULL,
	ccrFechaInicio date NOT NULL,
	ccrFechaFin date NOT NULL,
	ccrFechaCreacion datetime NOT NULL,
	ccrTipoCiclo varchar(14) NULL
)

CREATE TABLE aud.CicloModalidad_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cmoCicloAsignacion bigint NOT NULL,
	cmoModalidad varchar(50) NOT NULL,
	cmoId bigint NOT NULL
)

CREATE TABLE aud.CodigoCIIU_aud(
	ciiId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ciiCodigo varchar(4) NOT NULL,
	ciiDescripcion varchar(255) NOT NULL,
	ciiCodigoSeccion varchar(1) NULL,
	ciiDescripcionSeccion varchar(200) NULL,
	ciiCodigoDivision varchar(2) NULL,
	ciiDescripcionDivision varchar(250) NULL,
	ciiCodigoGrupo varchar(3) NULL,
	ciiDescripcionGrupo varchar(200) NULL
)

CREATE TABLE aud.Comunicado_aud(
	comId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	comEmail varchar(255) NULL,
	comIdentificaArchivoComunicado varchar(255) NULL,
	comTextoAdicionar varchar(500) NULL,
	comPlantillaComunicado bigint NULL,
	comFechaComunicado datetime2(7) NULL,
	comRemitente varchar(255) NULL,
	comSedeCajaCompensacion varchar(2) NULL,
	comNumeroCorreoMasivo bigint NULL,
	comDestinatario varchar(255) NULL,
	comEstadoEnvio varchar(20) NULL,
	comMensajeEnvio varchar(max) NULL,
	comMedioComunicado varchar(10) NULL,
	comSolicitud bigint NULL
)

CREATE TABLE aud.CondicionEspecialPersona_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cepPersona bigint NOT NULL,
	cepId bigint NOT NULL,
	cepNombreCondicion varchar(28) NOT NULL,
	cepActiva bit NULL
)

CREATE TABLE aud.CondicionInvalidez_aud(
	coiId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	coiPersona bigint NOT NULL,
	coiInvalidez bit NULL,
	coiFechaReporteInvalidez date NULL,
	coiComentarioInvalidez varchar(500) NULL
)

CREATE TABLE aud.CondicionVisita_aud(
	covId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	covCondicion varchar(42) NOT NULL,
	covCumple bit NOT NULL,
	covObservacion varchar(250) NULL,
	covVisita bigint NOT NULL
)

CREATE TABLE aud.ConexionOperadorInformacion_aud(
	coiId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	coiOperadorInformacionCcf bigint NULL,
	coiProtocolo varchar(10) NULL,
	coiUrl varchar(255) NULL,
	coiPuerto smallint NULL,
	coiHost varchar(75) NULL,
	coiUsuario varchar(255) NULL,
	coiContrasena varchar(32) NULL
)

CREATE TABLE aud.ConsolaEstadoCargueMasivo_aud(
	cecId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cecCcf varchar(5) NULL,
	cecTipoProcesoMasivo varchar(40) NOT NULL,
	cecUsuario varchar(255) NULL,
	cecFechaInicio datetime2(7) NULL,
	cecFechaFin datetime2(7) NULL,
	cecNumRegistroObjetivo bigint NOT NULL,
	cecNumRegistroProcesado bigint NOT NULL,
	cecNumRegistroConErrores bigint NULL,
	cecNumRegistroValidos bigint NULL,
	cecEstadoCargueMasivo varchar(15) NOT NULL,
	cecCargueId bigint NULL,
	cecFileLoadedId bigint NULL,
	cecIdentificacionECM varchar(255) NOT NULL,
	cecGradoAvance numeric(6, 3) NOT NULL
)

CREATE TABLE aud.Constante_aud(
	cnsId int NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cnsNombre varchar(100) NULL,
	cnsValor varchar(150) NULL,
	cnsDescripcion varchar(250) NULL
)

CREATE TABLE aud.ConvenioPago_aud(
	copId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	copPersona bigint NOT NULL,
	copTipoSolicitante varchar(13) NOT NULL,
	copDeudaPresuntaRegistrada numeric(19, 5) NOT NULL,
	copDeudaRealRegistrada numeric(19, 5) NULL,
	copCuotasPorPagar smallint NOT NULL,
	copEstadoConvenioPago varchar(30) NOT NULL,
	copMotivoAnulacion varchar(30) NULL,
	copUsuarioAnulacion varchar(255) NULL,
	copFechaAnulacion datetime NULL,
	copFechaRegistro datetime NOT NULL,
	copUsuarioCreacion varchar(255) NULL
)

CREATE TABLE aud.ConvenioPagoDependiente_aud(
	cpdId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cpdPagoPeriodoConvenio bigint NULL,
	cpdPersona bigint NULL
)

CREATE TABLE aud.Correccion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	corAporteDetallado bigint NULL,
	corAporteGeneral bigint NULL,
	corSolicitudCorreccionAporte bigint NULL,
	corId bigint NOT NULL
)

CREATE TABLE aud.Cruce_aud(
	cruId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cruCargueArchivoCruceFovis bigint NULL,
	cruNumeroPostulacion varchar(12) NULL,
	cruPersona bigint NULL,
	cruEstadoCruce varchar(22) NOT NULL,
	cruSolicitudGestionCruce bigint NULL,
	cruResultadoCodigoIdentificadorECM varchar(255) NULL,
	cruObservacionResultado varchar(500) NULL,
	cruEjecucionProcesoAsincrono bigint NULL,
	cruFechaRegistro datetime NOT NULL
)

CREATE TABLE aud.CruceDetalle_aud(
	crdId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	crdCruce bigint NOT NULL,
	crdCausalCruce varchar(30) NULL,
	crdNitEntidad varchar(16) NULL,
	crdNombreEntidad varchar(100) NULL,
	crdNumeroIdentificacion varchar(16) NULL,
	crdApellidos varchar(100) NULL,
	crdNombres varchar(100) NULL,
	crdCedulaCatastral varchar(50) NULL,
	crdDireccionInmueble varchar(300) NULL,
	crdMatriculaInmobiliaria varchar(50) NULL,
	crdDepartamento varchar(100) NULL,
	crdMunicipio varchar(50) NULL,
	crdFechaActualizacionMinisterio date NULL,
	crdFechaCorteEntidad date NULL,
	crdApellidosNombres varchar(200) NULL,
	crdPuntaje varchar(10) NULL,
	crdSexo varchar(20) NULL,
	crdZona varchar(30) NULL,
	crdParentesco varchar(30) NULL,
	crdFolio varchar(30) NULL,
	crdTipodocumento varchar(30) NULL,
	crdFechaSolicitud date NULL,
	crdEntidadOtorgante varchar(30) NULL,
	crdCajaCompensacion varchar(30) NULL,
	crdAsignadoPosterior varchar(30) NULL,
	crdTipo varchar(15) NULL,
	crdResultadoValidacion varchar(255) NULL,
	crdClasificacion varchar(30) NULL
)

CREATE TABLE aud.CuentaAdministradorSubsidio_aud(
	casId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	casFechaHoraCreacionRegistro datetime NOT NULL,
	casUsuarioCreacionRegistro varchar(200) NOT NULL,
	casTipoTransaccionSubsidio varchar(40) NOT NULL,
	casEstadoTransaccionSubsidio varchar(25) NULL,
	casEstadoLiquidacionSubsidio varchar(25) NULL,
	casOrigenTransaccion varchar(30) NOT NULL,
	casMedioDePagoTransaccion varchar(13) NOT NULL,
	casNumeroTarjetaAdmonSubsidio varchar(50) NULL,
	casCodigoBanco varchar(4) NULL,
	casNombreBanco varchar(200) NULL,
	casTipoCuentaAdmonSubsidio varchar(30) NULL,
	casNumeroCuentaAdmonSubsidio varchar(30) NULL,
	casTipoIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	casNumeroIdentificacionTitularCuentaAdmonSubsidio varchar(20) NULL,
	casNombreTitularCuentaAdmonSubsidio varchar(200) NULL,
	casFechaHoraTransaccion datetime NOT NULL,
	casUsuarioTransaccion varchar(200) NOT NULL,
	casValorOriginalTransaccion numeric(19, 5) NOT NULL,
	casValorRealTransaccion numeric(19, 5) NOT NULL,
	casIdTransaccionOriginal bigint NULL,
	casIdRemisionDatosTerceroPagador varchar(200) NULL,
	casIdTransaccionTerceroPagador varchar(200) NULL,
	casNombreTerceroPagado varchar(200) NULL,
	casIdCuentaAdmonSubsidioRelacionado bigint NULL,
	casFechaHoraUltimaModificacion datetime NULL,
	casUsuarioUltimaModificacion varchar(200) NULL,
	casAdministradorSubsidio bigint NOT NULL,
	casSitioDePago bigint NULL,
	casSitioDeCobro bigint NULL,
	casMedioDePago bigint NOT NULL,
	casCondicionPersonaAdmin bigint NULL
)

CREATE TABLE aud.Departamento_aud(
	depId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	depCodigo varchar(2) NOT NULL,
	depIndicativoTelefoniaFija varchar(2) NOT NULL,
	depNombre varchar(100) NOT NULL,
	depExcepcionAplicaFOVIS bit NULL
)

CREATE TABLE aud.DesafiliacionAportante_aud(
	deaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	deaPersona bigint NULL,
	deaSolicitudDesafiliacion bigint NULL,
	deaTipoSolicitante varchar(13) NULL,
	deaTipoLineaCobro varchar(3) NOT NULL
)

CREATE TABLE aud.DescuentoInteresMora_aud(
	dimId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dimPerfilLecturaPila varchar(40) NOT NULL,
	dimIndicadorUGPP smallint NULL,
	dimFechaPagoInicial date NULL,
	dimFechaPagoFinal date NULL,
	dimPeriodoPagoInicial varchar(7) NULL,
	dimPeriodoPagoFinal varchar(7) NULL,
	dimPorcentajeDescuento numeric(19, 5) NOT NULL,
	dimExclusionTipoCotizante varchar(30) NULL
)

CREATE TABLE aud.DestinatarioComunicado_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dcoProceso varchar(150) NOT NULL,
	dcoEtiquetaPlantilla varchar(150) NOT NULL,
	dcoId bigint NOT NULL
)

CREATE TABLE aud.DestinatarioGrupo_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dgrGrupoPrioridad bigint NOT NULL,
	dgrRolContacto varchar(60) NOT NULL,
	dgrId bigint NOT NULL
)

CREATE TABLE aud.DetalleComunicadoEnviado_aud(
	dceId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dceComunicado bigint NOT NULL,
	dceIdentificador bigint NULL,
	dceTipoTransaccion varchar(100) NOT NULL
)

CREATE TABLE aud.DetalleSolicitudGestionCobro_aud(
	dsgId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dsgEnviarPrimeraRemision bit NULL,
	dsgEnviarSegundaRemision bit NULL,
	dsgEstado varchar(52) NULL,
	dsgFechaPrimeraRemision datetime NULL,
	dsgFechaSegundaRemision datetime NULL,
	dsgCartera bigint NOT NULL,
	dsgObservacionPrimeraEntrega varchar(255) NULL,
	dsgObservacionPrimeraRemision varchar(255) NULL,
	dsgObservacionSegundaEntrega varchar(255) NULL,
	dsgObservacionSegundaRemision varchar(255) NULL,
	dsgSolicitudPrimeraRemision bigint NULL,
	dsgSolicitudSegundaRemision bigint NULL,
	dsgResultadoPrimeraEntrega varchar(18) NULL,
	dsgResultadoSegundaEntrega varchar(18) NULL,
	dsgDocumentoPrimeraRemision bigint NULL,
	dsgDocumentoSegundaRemision bigint NULL
)

CREATE TABLE aud.DetalleSubsidioAsignado_aud(
	dsaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dsaUsuarioCreador varchar(200) NOT NULL,
	dsaFechaHoraCreacion datetime NOT NULL,
	dsaEstado varchar(20) NOT NULL,
	dsaMotivoAnulacion varchar(40) NULL,
	dsaDetalleAnulacion varchar(250) NULL,
	dsaOrigenRegistroSubsidio varchar(30) NOT NULL,
	dsaTipoliquidacionSubsidio varchar(60) NOT NULL,
	dsaTipoCuotaSubsidio varchar(80) NOT NULL,
	dsaValorSubsidioMonetario numeric(19, 5) NOT NULL,
	dsaValorDescuento numeric(19, 5) NOT NULL,
	dsaValorOriginalAbonado numeric(19, 5) NOT NULL,
	dsaValorTotal numeric(19, 5) NOT NULL,
	dsaFechaTransaccionRetiro date NULL,
	dsaUsuarioTransaccionRetiro varchar(200) NULL,
	dsaFechaTransaccionAnulacion date NULL,
	dsaUsuarioTransaccionAnulacion varchar(200) NULL,
	dsaFechaHoraUltimaModificacion datetime NULL,
	dsaUsuarioUltimaModificacion varchar(200) NULL,
	dsaSolicitudLiquidacionSubsidio bigint NOT NULL,
	dsaEmpleador bigint NOT NULL,
	dsaAfiliadoPrincipal bigint NOT NULL,
	dsaGrupoFamiliar bigint NOT NULL,
	dsaAdministradorSubsidio bigint NOT NULL,
	dsaIdRegistroOriginalRelacionado bigint NULL,
	dsaCuentaAdministradorSubsidio bigint NOT NULL,
	dsaBeneficiarioDetalle bigint NOT NULL,
	dsaPeriodoLiquidado date NOT NULL,
	dsaResultadoValidacionLiquidacion bigint NOT NULL,
	dsaCondicionPersonaBeneficiario bigint NULL,
	dsaCondicionPersonaAfiliado bigint NULL,
	dsaCondicionPersonaEmpleador bigint NULL
)

CREATE TABLE aud.DetalleSubsidioAsignadoProgramado_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dprUsuarioCreador varchar(200) NOT NULL,
	dprFechaHoraCreacion datetime NOT NULL,
	dprEstado varchar(20) NOT NULL,
	dprMotivoAnulacion varchar(40) NULL,
	dprDetalleAnulacion varchar(250) NULL,
	dprOrigenRegistroSubsidio varchar(30) NOT NULL,
	dprTipoliquidacionSubsidio varchar(60) NOT NULL,
	dprTipoCuotaSubsidio varchar(80) NOT NULL,
	dprValorSubsidioMonetario numeric(19, 5) NOT NULL,
	dprValorDescuento numeric(19, 5) NOT NULL,
	dprValorOriginalAbonado numeric(19, 5) NOT NULL,
	dprValorTotal numeric(19, 5) NOT NULL,
	dprFechaTransaccionRetiro date NULL,
	dprUsuarioTransaccionRetiro varchar(200) NULL,
	dprFechaTransaccionAnulacion date NULL,
	dprUsuarioTransaccionAnulacion varchar(200) NULL,
	dprFechaHoraUltimaModificacion datetime NULL,
	dprUsuarioUltimaModificacion varchar(200) NULL,
	dprSolicitudLiquidacionSubsidio bigint NOT NULL,
	dprEmpleador bigint NOT NULL,
	dprAfiliadoPrincipal bigint NOT NULL,
	dprGrupoFamiliar bigint NOT NULL,
	dprAdministradorSubsidio bigint NOT NULL,
	dprIdRegistroOriginalRelacionado bigint NULL,
	dprCuentaAdministradorSubsidio bigint NOT NULL,
	dprBeneficiarioDetalle bigint NOT NULL,
	dprPeriodoLiquidado date NOT NULL,
	dprResultadoValidacionLiquidacion bigint NOT NULL,
	dprCondicionPersonaBeneficiario bigint NULL,
	dprCondicionPersonaAfiliado bigint NULL,
	dprCondicionPersonaEmpleador bigint NULL,
	dprId bigint NOT NULL
)

CREATE TABLE aud.DevolucionAporte_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dapFechaRecepcion datetime NULL,
	dapMotivoPeticion varchar(28) NULL,
	dapDestinatarioDevolucion varchar(13) NULL,
	dapCajaCompensacion int NULL,
	dapOtroDestinatario varchar(255) NULL,
	dapMontoAportes numeric(19, 5) NULL,
	dapMontoIntereses numeric(19, 5) NULL,
	dapPeriodoReclamado varchar(255) NULL,
	dapMedioPago bigint NULL,
	dapSedeCajaCompensacion bigint NULL,
	dapDescuentoGestionPagoOI numeric(19, 5) NULL,
	dapDescuentoGestionFinanciera numeric(19, 5) NULL,
	dapDescuentoOtro numeric(19, 5) NULL,
	dapId bigint NOT NULL,
	dapOtraCaja varchar(255) NULL,
	dapOtroMotivo varchar(255) NULL
)

CREATE TABLE aud.DevolucionAporteDetalle_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dadIncluyeAporteObligatorio bit NULL,
	dadIncluyeMoraCotizante bit NULL,
	dadComentarioHistorico varchar(255) NULL,
	dadComentarioNovedades varchar(255) NULL,
	dadComentarioAportes varchar(255) NULL,
	dadUsuario varchar(255) NULL,
	dadFechaGestion datetime NULL,
	dadDevolucionAporte bigint NOT NULL,
	dadMovimientoAporte bigint NULL,
	dadId bigint NOT NULL
)

CREATE TABLE aud.DiasFestivos_aud(
	pifId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pifConcepto varchar(150) NOT NULL,
	pifFecha date NOT NULL
)

CREATE TABLE aud.DiferenciasCargueActualizacion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dicTipoTransaccion varchar(100) NULL,
	dicJsonPayload text NULL,
	dicCargueArchivoActualizacion bigint NOT NULL,
	dicId bigint NOT NULL
)

CREATE TABLE aud.DocumentoAdministracionEstadoSolicitud_aud(
	daeId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	daeSolicitud bigint NULL,
	daeDocumentoSoporteCambioEstado varchar(100) NULL,
	daeNombreDocumento varchar(100) NULL,
	daeTipoDocumentoAdjunto varchar(22) NULL,
	daeActividad varchar(29) NULL
)

CREATE TABLE aud.DocumentoBitacora_aud(
	dbiId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dbiBitacoraCartera bigint NOT NULL,
	dbiDocumentoSoporte bigint NOT NULL
)

CREATE TABLE aud.DocumentoCartera_aud(
	dcaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dcaCartera bigint NOT NULL,
	dcaDocumentoSoporte bigint NOT NULL,
	dcaAccionCobro varchar(4) NULL,
	dcaConsecutivoLiquidacion varchar(10) NULL
)

CREATE TABLE aud.DocumentoDesafiliacion_aud(
	dodId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dodDocumentoSoporte bigint NULL,
	dodSolicitudDesafiliacion bigint NULL
)

CREATE TABLE aud.DocumentoEntidadPagadora_aud(
	dpgId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dpgEntidadPagadora bigint NULL,
	dpgIdentificadorDocumento varchar(255) NULL,
	dpgTipoDocumento varchar(50) NULL,
	dpgNombreDocumento varchar(60) NULL,
	dpgVersionDocumento smallint NULL
)

CREATE TABLE aud.DocumentoSoporte_aud(
	dosId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dosNombreDocumento varchar(255) NOT NULL,
	dosDescripcionComentarios varchar(255) NOT NULL,
	dosIdentificacionDocumento varchar(255) NOT NULL,
	dosVersionDocumento varchar(6) NOT NULL,
	dosFechaHoraCargue datetime NOT NULL,
	dosTipoDocumento varchar(24) NULL
)

CREATE TABLE aud.DocumentoSoporteOferente_aud(
	dsoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dsoOferente bigint NOT NULL,
	dsoDocumentoSoporte bigint NOT NULL
)

CREATE TABLE aud.DocumentoSoporteProyectoVivienda_aud(
	dspId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dspProyectoSolucionVivienda bigint NOT NULL,
	dspDocumentoSoporte bigint NOT NULL
)

CREATE TABLE aud.EjecucionProcesoAsincrono_aud(
	epsId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	epsFechaInicio datetime NOT NULL,
	epsFechaFin datetime NULL,
	epsRevisado bit NOT NULL,
	epsTipoProceso varchar(20) NULL
)

CREATE TABLE aud.EjecucionProgramada_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ejpFechaDefinicion datetime NULL,
	ejpUsuario varchar(255) NULL,
	ejpFrecuencia varchar(50) NULL,
	ejpHoraInicio varchar(5) NULL,
	ejpFechaInicioVigencia datetime NULL,
	ejpFechaFinVigencia datetime NULL,
	ejpCajaCompensacion int NULL,
	ejpId bigint NOT NULL
)

CREATE TABLE aud.ElementoDireccion_aud(
	eldId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	eldNombre varchar(20) NOT NULL
)

CREATE TABLE aud.Empleador_aud(
	empId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	empEstadoEmpleador varchar(8) NULL,
	empExpulsionSubsanada bit NULL,
	empFechaCambioEstadoAfiliacion datetime2(7) NULL,
	empMotivoDesafiliacion varchar(100) NULL,
	empNumeroTotalTrabajadores int NULL,
	empPeriodoUltimaNomina date NULL,
	empValorTotalUltimaNomina numeric(19, 0) NULL,
	empEmpresa bigint NULL,
	empFechaRetiro datetime NULL,
	empFechaSubsanacionExpulsion date NULL,
	empMotivoSubsanacionExpulsion varchar(200) NULL,
	empCantIngresoBandejaCeroTrabajadores smallint NULL,
	empFechaRetiroTotalTrabajadores date NULL,
	empFechaGestionDesafiliacion date NULL,
	empMedioPagoSubsidioMonetario varchar(30) NULL,
	empValidarSucursalPila bit NULL,
	empDiaHabilVencimientoAporte smallint NULL,
	empMarcaExpulsion varchar(22) NULL,
	empRetencionSubsidioActiva bit NULL,
	empMotivoRetencionSubsidio varchar(24) NULL,
	empMotivoInactivaRetencionSubsidio varchar(26) NULL
)

CREATE TABLE aud.Empresa_aud(
	empId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	empPersona bigint NULL,
	empNombreComercial varchar(250) NULL,
	empFechaConstitucion date NULL,
	empNaturalezaJuridica varchar(100) NULL,
	empCodigoCIIU smallint NULL,
	empArl smallint NULL,
	empUltimaCajaCompensacion int NULL,
	empPaginaWeb varchar(256) NULL,
	empRepresentanteLegal bigint NULL,
	empRepresentanteLegalSuplente bigint NULL,
	empEspecialRevision bit NULL,
	empUbicacionRepresentanteLegal bigint NULL,
	empUbicacionRepresentanteLegalSuplente bigint NULL,
	empCreadoPorPila bit NULL,
	empEnviadoAFiscalizacion bit NULL,
	empMotivoFiscalizacion varchar(30) NULL,
	empFechaFiscalizacion date NULL
)

CREATE TABLE aud.EntidadDescuento_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	endCodigo bigint NOT NULL,
	endTipo varchar(10) NOT NULL,
	endEmpresa bigint NULL,
	endPrioridad int NOT NULL,
	endEstado varchar(10) NOT NULL,
	endNombreContacto varchar(250) NULL,
	endObservaciones varchar(250) NULL,
	endFechaCreacion date NOT NULL,
	endNombre varchar(250) NULL,
	endNumeroCelular varchar(10) NULL,
	endId bigint NOT NULL
)

CREATE TABLE aud.EntidadPagadora_aud(
	epaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	epaAportante bit NULL,
	epaCanalComunicacion varchar(20) NULL,
	epaEmailComunicacion varchar(255) NULL,
	epaEstadoEntidadPagadora varchar(20) NULL,
	epaMedioComunicacion varchar(50) NULL,
	epaNombreContacto varchar(50) NULL,
	epaEmpresa bigint NULL,
	epaSucursalPagadora bigint NULL,
	epaTipoAfiliacion varchar(50) NULL,
	epaCargoContacto varchar(20) NULL
)

CREATE TABLE aud.EscalamientoSolicitud_aud(
	esoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	esoSolicitud bigint NOT NULL,
	esoAsunto varchar(100) NOT NULL,
	esoDescripcion varchar(255) NOT NULL,
	esoDestinatario varchar(255) NULL,
	esoResultadoAnalista varchar(30) NULL,
	esoComentarioAnalista varchar(255) NULL,
	esoIdentificadorDocumentoSoporteAnalista varchar(255) NULL,
	esoUsuarioCreacion varchar(255) NULL,
	esoFechaCreacion datetime2(7) NULL,
	esoTipoAnalistaFOVIS varchar(22) NULL
)

CREATE TABLE aud.EtiquetaCorrespondenciaRadicado_aud(
	eprId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	eprAsignada bit NULL,
	eprCodigo varchar(12) NULL,
	eprTipoEtiqueta varchar(50) NULL,
	eprProcedenciaEtiqueta varchar(20) NULL
)

CREATE TABLE aud.ExclusionCartera_aud(
	excId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	excPersona bigint NOT NULL,
	excTipoSolicitante varchar(15) NOT NULL,
	excEstadoExclusionCartera varchar(10) NOT NULL,
	excFechaInicio date NOT NULL,
	excFechaFin date NULL,
	excFechaRegistro date NOT NULL,
	excFechaMovimiento date NULL,
	excObservacion varchar(400) NULL,
	excTipoExclusionCartera varchar(25) NOT NULL,
	excEstadoAntesExclusion varchar(45) NOT NULL,
	excNumeroOperacionMora bigint NULL,
	excUsuarioRegistro varchar(400) NULL,
	excResultado varchar(11) NULL,
	excObservacionCambioResultado varchar(400) NULL
)

CREATE TABLE aud.ExpulsionSubsanada_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	exsExpulsionSubsanada bit NULL,
	exsFechaSubsanacionExpulsion date NULL,
	exsMotivoSubsanacionExpulsion varchar(200) NOT NULL,
	exsEmpleador bigint NULL,
	exsRolAfiliado bigint NULL,
	exsId bigint NOT NULL
)

CREATE TABLE aud.FormaPagoModalidad_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	fpmFormaPago varchar(34) NOT NULL,
	fpmModalidad varchar(50) NOT NULL,
	fpmId bigint NOT NULL
)

CREATE TABLE aud.GestionNotiNoEnviadas_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	gneEmpresa bigint NOT NULL,
	gneTipoInconsistencia varchar(20) NOT NULL,
	gneCanalContacto varchar(20) NOT NULL,
	gneFechaIngreso datetime NOT NULL,
	gneEstadoGestion varchar(25) NULL,
	gneObservaciones varchar(60) NULL,
	gneFechaRespuesta datetime NULL,
	gneId bigint NOT NULL
)

CREATE TABLE aud.GlosaComentarioNovedad_aud(
	gcnId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	gcnNombreGlosaNovedad varchar(60) NULL,
	gcnDescripcionGlosaNovedad varchar(400) NULL,
	gcnEstadoGlosaNovedad bit NULL
)

CREATE TABLE aud.GradoAcademico_aud(
	graId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	graNombre varchar(20) NOT NULL,
	graNivelEducativo varchar(43) NOT NULL
)

CREATE TABLE aud.GrupoFamiliar_aud(
	grfId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	grfNumero smallint NOT NULL,
	grfObservaciones varchar(500) NULL,
	grfAfiliado bigint NOT NULL,
	grfUbicacion bigint NULL,
	grfInembargable bit NULL
)

CREATE TABLE aud.GrupoPrioridad_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	gprNombre varchar(150) NULL,
	gprId bigint NOT NULL
)

CREATE TABLE aud.GrupoRequisito_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	grqRequisitoCajaClasificacion bigint NULL,
	grqGrupoUsuario varchar(60) NULL,
	grqId bigint NOT NULL
)

CREATE TABLE aud.HistoriaResultadoValidacion_aud(
	hrvId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	hrvDetalle varchar(400) NULL,
	hrvResultado varchar(20) NULL,
	hrvValidacion varchar(100) NULL,
	hrvIdDatosRegistro bigint NULL,
	hrvTipoExepcion varchar(30) NULL
)

CREATE TABLE aud.InformacionFaltanteAportante_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ifaSolicitud bigint NOT NULL,
	ifaTipoGestion varchar(22) NULL,
	ifaResponsableInformacion varchar(18) NULL,
	ifaTipoDocumentoGestion varchar(10) NULL,
	ifaMedioComunicacion varchar(19) NULL,
	ifaObservaciones varchar(500) NULL,
	ifaFechaGestion date NULL,
	ifaFechaRegistro date NULL,
	ifaUsuario varchar(255) NULL,
	ifaId bigint NOT NULL
)

CREATE TABLE aud.Infraestructura_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	infCodigoParaSSF varchar(13) NOT NULL,
	infCodigoParaCCF varchar(12) NOT NULL,
	infConsecutivoInfraestructura smallint NOT NULL,
	infNombre varchar(255) NOT NULL,
	infTipoInfraestructura bigint NOT NULL,
	infZona varchar(255) NOT NULL,
	infDireccion varchar(300) NOT NULL,
	infAreaGeografica varchar(6) NOT NULL,
	infMunicipio bigint NOT NULL,
	infTipoTenencia bigint NOT NULL,
	infLatitud numeric(9, 6) NOT NULL,
	infLongitud numeric(9, 6) NOT NULL,
	infActivo bit NOT NULL,
	infCapacidadEstimada numeric(7, 2) NULL,
	infId bigint NOT NULL
)

CREATE TABLE aud.InhabilidadSubsidioFovis_aud(
	isfId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	isfJefeHogar bigint NULL,
	isfIntegranteHogar bigint NULL,
	isfFechaInicio datetime NULL,
	isfFechaFin datetime NULL,
	isfInhabilitadoParaSubsidio bit NULL
)

CREATE TABLE aud.IntegranteHogar_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	inhJefeHogar bigint NOT NULL,
	inhPersona bigint NOT NULL,
	inhIntegranteReemplazaJefeHogar bit NULL,
	inhTipoIntegrante varchar(32) NOT NULL,
	inhEstadoHogar varchar(10) NULL,
	inhIntegranteValido bit NULL,
	inhSalarioMensual numeric(19, 5) NULL,
	inhId bigint NOT NULL
)

CREATE TABLE aud.IntentoAfiliacion_aud(
	iafId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	iafCausaIntentoFallido varchar(50) NULL,
	iafFechaCreacion datetime2(7) NULL,
	iafFechaInicioProceso datetime2(7) NULL,
	iafSedeCajaCompensacion varchar(2) NULL,
	iafTipoTransaccion varchar(100) NULL,
	iafUsuarioCreacion varchar(255) NULL,
	iafSolicitud bigint NULL
)

CREATE TABLE aud.IntentoAfiliRequisito_aud(
	iarId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	iarIntentoAfiliacion bigint NULL,
	iarRequisito bigint NULL
)

CREATE TABLE aud.IntentoLegalizacionDesembolso_aud(
	ildId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ildCausaIntentoFallido varchar(50) NULL,
	ildFechaCreacion datetime NULL,
	ildSedeCajaCompensacion varchar(2) NULL,
	ildUsuarioCreacion varchar(255) NULL,
	ildSolicitud bigint NULL,
	ildProceso varchar(32) NULL,
	ildTipoSolicitante varchar(5) NULL,
	ildModalidad varchar(33) NULL
)

CREATE TABLE aud.IntentoLegalizacionDesembolsoRequisito_aud(
	ilrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ilrIntentoLegalizacionDesembolso bigint NULL,
	ilrRequisito bigint NULL
)

CREATE TABLE aud.IntentoNovedad_aud(
	inoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	inoCausaIntentoFallido varchar(255) NULL,
	inoFechaInicioProceso datetime2(7) NULL,
	inoSolicitud bigint NULL,
	inoTipoTransaccion varchar(100) NULL
)

CREATE TABLE aud.IntentoNoveRequisito_aud(
	inrId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	inrRequisito bigint NULL,
	inrIntentoNovedad bigint NULL
)

CREATE TABLE aud.IntentoPostulacion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ipoCausaIntentoFallido varchar(50) NULL,
	ipoFechaCreacion datetime NULL,
	ipoFechaInicioProceso datetime NULL,
	ipoSedeCajaCompensacion varchar(2) NULL,
	ipoTipoTransaccion varchar(100) NULL,
	ipoUsuarioCreacion varchar(255) NULL,
	ipoSolicitud bigint NULL,
	ipoProceso varchar(32) NULL,
	ipoTipoSolicitante varchar(5) NULL,
	ipoModalidad varchar(33) NULL,
	ipoId bigint NOT NULL
)

CREATE TABLE aud.IntentoPostulacionRequisito_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	iprIntentoPostulacion bigint NULL,
	iprRequisito bigint NULL,
	iprId bigint NOT NULL
)

CREATE TABLE aud.ItemChequeo_aud(
	ichId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ichSolicitud bigint NULL,
	ichRequisito bigint NULL,
	ichPersona bigint NULL,
	ichIdentificadorDocumento varchar(255) NULL,
	ichVersionDocumento smallint NULL,
	ichEstadoRequisito varchar(20) NULL,
	ichPrecargado bit NULL,
	ichCumpleRequisito bit NULL,
	ichFormatoEntregaDocumento varchar(20) NULL,
	ichComentarios varchar(255) NULL,
	ichCumpleRequisitoBack bit NULL,
	ichComentariosBack varchar(255) NULL,
	ichIdentificadorDocumentoPrevio varchar(255) NULL
)

CREATE TABLE aud.JefeHogar_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	jehAfiliado bigint NOT NULL,
	jehEstadoHogar varchar(10) NULL,
	jehId bigint NOT NULL
)

CREATE TABLE aud.LegalizacionDesembolso_aud(
	lgdId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	lgdFormaPago varchar(50) NULL,
	lgdValorDesembolsar numeric(19, 5) NULL,
	lgdFechaLimitePago datetime NULL,
	lgdVisita bigint NULL,
	lgdSubsidioDesembolsado bit NULL,
	lgdTipoMedioPago varchar(30) NULL,
	lgdDocumentoSoporte varchar(50) NULL,
	lgdFechaTrasnferencia datetime2(7) NULL,
	lgdObservaciones varchar(500) NULL,
	lgdMontoDesembolsado numeric(19, 5) NULL,
	lgdObservacionesBack varchar(500) NULL,
	lgdDocumentoSoporteBack varchar(50) NULL
)

CREATE TABLE aud.Licencia_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	licEntidadExpide varchar(21) NULL,
	licNumeroLicencia varchar(50) NULL,
	licMatriculaInmobiliaria varchar(50) NULL,
	licProyectoSolucionVivienda bigint NOT NULL,
	licId bigint NOT NULL,
	licTipoLicencia varchar(21) NULL
)

CREATE TABLE aud.LicenciaDetalle_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	lidNumeroResolucion varchar(50) NULL,
	lidFechaInicio date NULL,
	lidFechaFin date NULL,
	lidLicencia bigint NOT NULL,
	lidId bigint NOT NULL,
	lidClasificacionLicencia varchar(33) NULL,
	lidEstadoLicencia bit NULL
)

CREATE TABLE aud.LineaCobro_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	lcoHabilitarAccionCobroA bit NULL,
	lcoDiasLimitePago bigint NULL,
	lcoDiasParametrizados bigint NULL,
	lcoHabilitarAccionCobroB bit NULL,
	lcoTipoLineaCobro varchar(3) NULL
)

CREATE TABLE aud.ListaEspecialRevision_aud(
	lerId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	lerTipoIdentificacion varchar(20) NOT NULL,
	lerNumeroIdentificacion varchar(16) NOT NULL,
	lerDigitoVerificacion tinyint NULL,
	lerCajaCompensacion int NULL,
	lerNombreEmpleador varchar(200) NULL,
	lerFechaInicioInclusion date NULL,
	lerFechaFinInclusion date NULL,
	lerRazonInclusion varchar(20) NULL,
	lerEstado varchar(20) NULL,
	lerComentario varchar(255) NULL
)

CREATE TABLE aud.MedioCheque_aud(
	mdpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mecTipoIdentificacionTitular varchar(20) NULL,
	mecNumeroIdentificacionTitular varchar(16) NULL,
	mecDigitoVerificacionTitular smallint NULL,
	mecNombreTitularCuenta varchar(200) NULL
)

CREATE TABLE aud.MedioConsignacion_aud(
	mdpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mcoBanco bigint NOT NULL,
	mcoTipoCuenta varchar(30) NOT NULL,
	mcoNumeroCuenta varchar(30) NOT NULL,
	mcoTipoIdentificacionTitular varchar(20) NULL,
	mcoNumeroIdentificacionTitular varchar(16) NULL,
	mcoDigitoVerificacionTitular smallint NULL,
	mcoNombreTitularCuenta varchar(200) NULL
)

CREATE TABLE aud.MedioDePago_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mdpTipo varchar(100) NOT NULL,
	mdpId bigint NOT NULL
)

CREATE TABLE aud.MedioEfectivo_aud(
	mdpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mefEfectivo bit NOT NULL,
	mefSitioPago bigint NULL,
	mefSedeCajaCompensacion bigint NULL
)

CREATE TABLE aud.MedioPagoPersona_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mppMedioPago bigint NOT NULL,
	mppPersona bigint NOT NULL,
	mppMedioActivo bit NOT NULL,
	mppId bigint NOT NULL
)

CREATE TABLE aud.MedioPagoProyectoVivienda_aud(
	mprId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mprProyectoSolucionVivienda bigint NOT NULL,
	mprMedioDePago bigint NOT NULL,
	mprActivo bit NULL
)

CREATE TABLE aud.MediosPagoCCF_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mpcCajaCompensacion int NOT NULL,
	mpcMedioPago varchar(30) NOT NULL,
	mpcMedioPreferente bit NOT NULL,
	mpcAplicaFOVIS bit NULL,
	mpcId bigint NOT NULL
)

CREATE TABLE aud.MedioTarjeta_aud(
	mdpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mtrNumeroTarjeta varchar(50) NOT NULL,
	mtrDisponeTarjeta bit NOT NULL,
	mtrEstadoTarjetaMultiservicios varchar(30) NOT NULL,
	mtrSolicitudTarjeta varchar(30) NOT NULL
)

CREATE TABLE aud.MedioTransferencia_aud(
	mdpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	metBanco bigint NOT NULL,
	metTipoCuenta varchar(30) NOT NULL,
	metNumeroCuenta varchar(30) NOT NULL,
	metTipoIdentificacionTitular varchar(20) NULL,
	metNumeroIdentificacionTitular varchar(16) NULL,
	metDigitoVerificacionTitular smallint NULL,
	metNombreTitularCuenta varchar(200) NULL
)

CREATE TABLE aud.MotivoNoGestionCobro_aud(
	mgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	mgcCartera bigint NOT NULL,
	mgcTipo varchar(36) NULL
)

CREATE TABLE aud.MovimientoAjusteAporte_aud(
	maaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	maaTipoMovimientoRecaudoAporte varchar(40) NULL,
	maaAporteDetalladoOriginal bigint NULL,
	maaAporteDetalladoCorregido bigint NULL,
	maaPeriodoMovimientoAporte varchar(7) NULL,
	maaFechaRegisroMovimientoAporte datetime NULL,
	maaValorAporteRegOriginal numeric(38, 3) NULL,
	maaValorIntAporteRegOriginal numeric(38, 3) NULL,
	maaValorTotalAporteRegOriginal numeric(38, 3) NULL,
	maaTipoAjusteMovimientoAporte varchar(40) NULL,
	maaValorAjusteRegAporte numeric(38, 3) NULL,
	maaValorIntAjusteRegAporte numeric(38, 3) NULL,
	maaValorTotalAjusteRegAporte numeric(38, 3) NULL,
	maaValorFinalRegAporte numeric(38, 3) NULL,
	maaValorIntFinalRegAporte numeric(38, 3) NULL,
	maaValorTotalFinalRegAporte numeric(38, 3) NULL,
	maaEstadoAjusteRegAporte varchar(40) NULL
)

CREATE TABLE aud.MovimientoAporte_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	moaTipoAjuste varchar(20) NULL,
	moaTipoMovimiento varchar(23) NULL,
	moaEstadoAporte varchar(22) NULL,
	moaValorAporte numeric(19, 5) NULL,
	moaValorInteres numeric(19, 5) NULL,
	moaFechaActualizacionEstado datetime NULL,
	moaFechaCreacion datetime NULL,
	moaAporteDetallado bigint NULL,
	moaAporteGeneral bigint NOT NULL,
	moaId bigint NOT NULL
)

CREATE TABLE aud.Municipio_aud(
	munId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	munCodigo varchar(5) NOT NULL,
	munNombre varchar(50) NOT NULL,
	munDepartamento smallint NOT NULL
)

CREATE TABLE aud.NotificacionDestinatario_aud(
	nodId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	nodNotEnviada bigint NULL,
	nodDestintatario varchar(255) NULL,
	nodTipoDestintatario varchar(3) NULL
)

CREATE TABLE aud.NotificacionEnviada_aud(
	noeId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	noeFechaEnvio datetime2(7) NULL,
	noeRemitente varchar(255) NULL,
	noeSccfId bigint NULL,
	noeProcesoEvento varchar(255) NULL,
	noeEstadoEnvioNot varchar(20) NULL,
	noeError varchar(4000) NULL
)

CREATE TABLE aud.NotificacionPersonal_aud(
	ntpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ntpActividad varchar(41) NOT NULL,
	ntpPersona bigint NOT NULL,
	ntpTipoSolicitante varchar(13) NOT NULL,
	ntpComentario varchar(250) NULL,
	ntpCartera bigint NULL
)

CREATE TABLE aud.NotificacionPersonalDocumento_aud(
	ntdId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ntdDocumentoSoporte bigint NOT NULL,
	ntdNotificacionPersonal bigint NOT NULL
)

CREATE TABLE aud.NovedadDetalle_aud(
	nopId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	nopFechaInicio date NULL,
	nopFechaFin date NULL,
	nopVigente bit NULL,
	nopSolicitudNovedad bigint NULL
)

CREATE TABLE aud.OcupacionProfesion_aud(
	ocuId int NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ocuNombre varchar(100) NOT NULL
)

CREATE TABLE aud.Oferente_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ofePersona bigint NOT NULL,
	ofeEmpresa bigint NULL,
	ofeEstado varchar(30) NULL,
	ofeId bigint NOT NULL,
	ofeCuentaBancaria bit NULL,
	ofeBanco bigint NULL,
	ofeTipoCuenta varchar(30) NULL,
	ofeNumeroCuenta varchar(30) NULL,
	ofeTipoIdentificacionTitular varchar(20) NULL,
	ofeNumeroIdentificacionTitular varchar(16) NULL,
	ofeDigitoVerificacionTitular smallint NULL,
	ofeNombreTitularCuenta varchar(200) NULL
)

CREATE TABLE aud.OperadorInformacion_aud(
	oinId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	oinCodigo varchar(2) NOT NULL,
	oinNombre varchar(75) NOT NULL,
	oinOperadorActivo bit NOT NULL
)

CREATE TABLE aud.OperadorInformacionCcf_aud(
	oicId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	oicOperadorInformacion bigint NULL,
	oicCajaCompensacion int NULL,
	oicEstado bit NULL
)

CREATE TABLE aud.PagoPeriodoConvenio_aud(
	ppcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ppcConvenioPago bigint NOT NULL,
	ppcFechaPago date NOT NULL,
	ppcValorCuota numeric(19, 5) NOT NULL,
	ppcPeriodo date NOT NULL
)

CREATE TABLE aud.ParametrizacionCartera_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pacAplicar bit NULL,
	pacIncluirIndependientes bit NULL,
	pacIncluirPensionados bit NULL,
	pacEstadoCartera varchar(7) NULL,
	pacValorPromedioAportes varchar(16) NULL,
	pacCantidadPeriodos smallint NULL,
	pacTrabajadoresActivos varchar(18) NULL,
	pacPeriodosMorosos varchar(21) NULL,
	pacMayorValorPromedio smallint NULL,
	pacMayorTrabajadoresActivos smallint NULL,
	pacMayorVecesMoroso smallint NULL,
	pacTipoParametrizacion varchar(74) NULL,
	pacId bigint NOT NULL,
	pacFechaActualizacion datetime NULL
)

CREATE TABLE aud.ParametrizacionCondicionesSubsidio_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pcsAnioVigenciaParametrizacion int NOT NULL,
	pcsPeriodoInicio date NOT NULL,
	pcsPeriodoFin date NOT NULL,
	pcsValorCuotaAnualBase numeric(19, 5) NOT NULL,
	pcsValorCuotaAnualAgraria numeric(19, 5) NOT NULL,
	pcsProgramaDecreto4904 bit NOT NULL,
	pcsRetroactivoNovInvalidez bit NOT NULL,
	pcsRetroactivoReingresoEmpleadores bit NOT NULL,
	pcsId bigint NOT NULL,
	pcsCantidadSubsidiosLiquidados int NULL,
	pcsMontoSubsidiosLiquidados numeric(10, 0) NULL,
	pcsCantidadSubsidiosLiquidadosInvalidez int NULL,
	pcsCantidadPeriodosRetroactivosMes int NULL,
	pcsCodigoCajaCompensacion varchar(5) NULL,
	pcsCalendarioPagoFallecimiento SMALLINT
)

CREATE TABLE aud.ParametrizacionConveniosPago_aud(
	pcpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pcpCantidadPeriodos smallint NULL,
	pcpNumeroConveniosPermitido smallint NULL
)

CREATE TABLE aud.ParametrizacionCriterioGestionCobro_aud(
	pacId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pcrLineaCobro varchar(3) NULL,
	pcrActiva bit NULL,
	pcrMetodo varchar(10) NULL,
	pcrAccion varchar(10) NULL,
	pcrCorteEntidad bigint NULL
)

CREATE TABLE aud.ParametrizacionDesafiliacion_aud(
	pdeId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pdeLineaCobro varchar(3) NOT NULL,
	pdeProgramacionEjecucion varchar(8) NULL,
	pdeMontoMoraInexactitud numeric(19, 5) NULL,
	pdePeriodosMora bigint NULL,
	pdeMetodoEnvioComunicado varchar(11) NULL,
	pdeOficinaPrincipalFisico bit NULL,
	pdeCorrespondenciaFisico bit NULL,
	pdeNotificacionJudicialFisico bit NULL,
	pdeOficinaPrincipalElectronico bit NULL,
	pdeRepresentanteLegalElectronico bit NULL,
	pdeResponsableAportesElectronico bit NULL,
	pdeSiguienteAccion varchar(29) NULL,
	pdeHabilitado bit NOT NULL
)

CREATE TABLE aud.ParametrizacionEjecucionProgramada_aud(
	pepId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pepProceso varchar(100) NOT NULL,
	pepHoras varchar(20) NULL,
	pepMinutos varchar(20) NULL,
	pepSegundos varchar(20) NULL,
	pepDiaSemana varchar(50) NULL,
	pepDiaMes varchar(50) NULL,
	pepMes varchar(50) NULL,
	pepAnio varchar(50) NULL,
	pepFechaInicio date NULL,
	pepFechaFin date NULL,
	pepFrecuencia varchar(50) NOT NULL,
	pepEstado varchar(8) NULL
)

CREATE TABLE aud.ParametrizacionExclusiones_aud(
	pexId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pexExclusionNegocios bit NULL,
	pexImposicionRecurso bit NULL,
	pexConvenioPago bit NULL,
	pexAclaracionMora bit NULL,
	pexRiesgoIncobrabilidad bit NULL
)

CREATE TABLE aud.ParametrizacionFiscalizacion_aud(
	pacId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pfiAlertaValidacionPila bit NULL,
	pfiEstadoAporteNoOk bit NULL,
	pfiIbcMenorUltimo bit NULL,
	pfiNovedadRetiro bit NULL,
	pfiPeriodosRetroactivos smallint NULL,
	pfiSalarioMenorUltimo bit NULL,
	pfiCorteEntidades bigint NULL,
	pfiGestionPreventiva bit NULL
)

CREATE TABLE aud.ParametrizacionFOVIS_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pafNombre varchar(50) NOT NULL,
	pafValor bit NULL,
	pafValorNumerico numeric(4, 1) NULL,
	pafPlazoVencimiento varchar(15) NULL,
	pafPlazoAdicional varchar(15) NULL,
	pafValorAdicional numeric(4, 1) NULL,
	pafValorString varchar(30) NULL,
	pafId bigint NOT NULL
)

CREATE TABLE aud.ParametrizacionGestionCobro_aud(
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pgcOficinaPrincipalFisico bit NULL,
	pgcCorrespondenciaFisico bit NULL,
	pgcNotificacionJudicialFisico bit NULL,
	pgcOficinaPrincipalElectronico bit NULL,
	pgcRepresentanteLegalElectronico bit NULL,
	pgcResponsableAportesElectronico bit NULL,
	pgcMetodoEnvioComunicado varchar(11) NULL,
	pgcTipoParametrizacion varchar(55) NULL
)

CREATE TABLE aud.ParametrizacionLiquidacionSubsidio_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	plsAnioVigenciaParametrizacion int NOT NULL,
	plsPeriodoInicio date NOT NULL,
	plsPeriodoFin date NOT NULL,
	plsFactorCuotaInvalidez numeric(19, 5) NOT NULL,
	plsFactorPorDefuncion numeric(19, 5) NOT NULL,
	plsHorasTrabajadas int NOT NULL,
	plsSMLMV numeric(19, 5) NULL,
	plsId bigint NOT NULL
)

CREATE TABLE aud.ParametrizacionMetodoAsignacion_aud(
	pmaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pmaSedeCajaCompensacion bigint NOT NULL,
	pmaProceso varchar(100) NOT NULL,
	pmaMetodoAsignacion varchar(20) NULL,
	pmaUsuario varchar(255) NULL,
	pmaGrupo varchar(50) NULL,
	pmaSedeCajaDestino bigint NULL
)

CREATE TABLE aud.ParametrizacionModalidad_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pmoNombre varchar(50) NULL,
	pmoEstado bit NULL,
	pmoTopeSMLMV numeric(4, 1) NULL,
	pmoTopeAvaluoCatastral numeric(4, 1) NULL,
	pmoId bigint NOT NULL
)

CREATE TABLE aud.ParametrizacionNovedad_aud(
	novId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	novTipoTransaccion varchar(100) NULL,
	novPuntoResolucion varchar(255) NULL,
	novRutaCualificada varchar(255) NULL,
	novTipoNovedad varchar(255) NULL,
	novProceso varchar(50) NULL,
	novAplicaTodosRoles bit NULL
)

CREATE TABLE aud.ParametrizacionPreventiva_aud(
	pacId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pprDiasHabilesPrevios smallint NULL,
	pprHoraEjecucion datetime NULL,
	pprEjecucionAutomatica bit NULL
)

CREATE TABLE aud.ParametrizacionTablaAuditable(
	ptaId int IDENTITY(1,1) NOT NULL,
	ptaActualizar bit NULL,
	ptaConsultar bit NULL,
	ptaCrear bit NULL,
	ptaEntityClassName varchar(255) NULL,
	ptaNombreTabla varchar(255) NULL,
 CONSTRAINT PK_ParametrizacionTablaAuditable_ptaId PRIMARY KEY (ptaId)
)

CREATE TABLE aud.ParametrizaEnvioComunicado_aud(
	pecId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pecProceso varchar(100) NULL,
	pecTipoCorreo varchar(20) NULL
)

CREATE TABLE aud.Parametro_aud(
	prmId int NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	prmNombre varchar(100) NULL,
	prmValor varchar(150) NULL,
	prmCargaInicio bit NULL,
	prmSubCategoriaParametro varchar(23) NULL,
	prmDescripcion varchar(250) NULL
)

CREATE TABLE aud.Periodo_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	priPeriodo date NOT NULL,
	priId bigint NOT NULL
)

CREATE TABLE aud.PeriodoBeneficio_aud(
	pbePeriodo smallint NOT NULL,
	pbePorcentaje numeric(5, 5) NULL,
	pbeBeneficio bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pbeId bigint NOT NULL
)

CREATE TABLE aud.PeriodoExclusionMora_aud(
	pemId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pemPeriodo date NOT NULL,
	pemExclusionCartera bigint NOT NULL,
	pemEstadoPeriodo varchar(10) NOT NULL
)

CREATE TABLE aud.PeriodoLiquidacion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pelSolicitudLiquidacionSubsidio bigint NOT NULL,
	pelPeriodo bigint NOT NULL,
	pelTipoPeriodo varchar(10) NOT NULL,
	pelId bigint NOT NULL
)

CREATE TABLE aud.Persona_aud(
	perId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	perDigitoVerificacion smallint NULL,
	perNumeroIdentificacion varchar(16) NULL,
	perRazonSocial varchar(250) NULL,
	perTipoIdentificacion varchar(20) NULL,
	perUbicacionPrincipal bigint NULL,
	perPrimerNombre varchar(50) NULL,
	perSegundoNombre varchar(50) NULL,
	perPrimerApellido varchar(50) NULL,
	perSegundoApellido varchar(50) NULL,
	perCreadoPorPila bit NULL
)

CREATE TABLE aud.PersonaDetalle_aud(
	pedId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pedPersona bigint NOT NULL,
	pedFechaNacimiento date NULL,
	pedFechaExpedicionDocumento date NULL,
	pedGenero varchar(10) NULL,
	pedOcupacionProfesion int NULL,
	pedNivelEducativo varchar(100) NULL,
	pedGradoAcademico smallint NULL,
	pedCabezaHogar bit NULL,
	pedAutorizaUsoDatosPersonales bit NULL,
	pedResideSectorRural bit NULL,
	pedEstadoCivil varchar(20) NULL,
	pedHabitaCasaPropia bit NULL,
	pedFallecido bit NULL,
	pedFechaFallecido date NULL,
	pedBeneficiarioSubsidio bit NULL,
	pedEstudianteTrabajoDesarrolloHumano bit NULL,
	pedFechaDefuncion date NULL
)

CREATE TABLE aud.PlantillaComunicado_aud(
	pcoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pcoAsunto varchar(150) NULL,
	pcoCuerpo varchar(5000) NULL,
	pcoEncabezado varchar(500) NULL,
	pcoIdentificadorImagenPie varchar(255) NULL,
	pcoMensaje varchar(5000) NULL,
	pcoNombre varchar(150) NULL,
	pcoPie varchar(500) NULL,
	pcoEtiqueta varchar(150) NULL
)

CREATE TABLE aud.PostulacionFOVIS_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pofCicloAsignacion bigint NULL,
	pofJefeHogar bigint NULL,
	pofEstadoHogar varchar(58) NULL,
	pofCondicionHogar varchar(44) NULL,
	pofHogarPerdioSubsidioNoPago bit NULL,
	pofCantidadFolios smallint NULL,
	pofValorSFVSolicitado numeric(19, 5) NULL,
	pofProyectoSolucionVivienda bigint NULL,
	pofModalidad varchar(50) NULL,
	pofId bigint NOT NULL,
	pofSolicitudAsignacion bigint NULL,
	pofResultadoAsignacion varchar(50) NULL,
	pofValorAsignadoSFV numeric(19, 5) NULL,
	pofIdentificardorDocumentoActaAsignacion varchar(255) NULL,
	pofPuntaje numeric(5, 2) NULL,
	pofFechaCalificacion datetime NULL,
	pofPrioridadAsignacion varchar(11) NULL,
	pofValorCalculadoSFV numeric(19, 5) NULL,
	pofValorProyectoVivienda numeric(19, 5) NULL,
	pofMotivoDesistimiento varchar(29) NULL,
	pofMotivoRechazo varchar(51) NULL,
	pofMotivoHabilitacion varchar(38) NULL,
	pofRestituidoConSancion bit NULL,
	pofTiempoSancion varchar(10) NULL,
	pofMotivoRestitucion varchar(45) NULL,
	pofMotivoEnajenacion varchar(40) NULL,
	pofValorAjusteIPCSFV numeric(19, 5) NULL
)

CREATE TABLE aud.PrioridadDestinatario_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	prdDestinatarioComunicado bigint NOT NULL,
	prdGrupoPrioridad bigint NOT NULL,
	prdPrioridad smallint NULL,
	prdId bigint NOT NULL
)

CREATE TABLE aud.ProductoNoConforme_aud(
	pncId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	pncCampo varchar(150) NULL,
	pncCampoObligatorio bit NULL,
	pncComentariosBack varchar(255) NULL,
	pncComentariosBack2 varchar(255) NULL,
	pncComentariosFront varchar(255) NULL,
	pncDescripcion varchar(255) NULL,
	pncFechaCreacion datetime2(7) NULL,
	pncGrupoCampos varchar(150) NULL,
	pncDocumentoSoporteGestion varchar(255) NULL,
	pncNumeroConsecutivo smallint NULL,
	pncResultadoGestion varchar(20) NULL,
	pncResultadoRevisionBack varchar(20) NULL,
	pncResultadoRevisionBack2 varchar(20) NULL,
	pncSeccion varchar(150) NULL,
	pncSubsanable bit NULL,
	pncTipoError varchar(80) NULL,
	pncTipoProductoNoConforme varchar(20) NULL,
	pncUsuarioBack varchar(255) NULL,
	pncUsuarioFront varchar(255) NULL,
	pncValorCampoBack varchar(300) NULL,
	pncValorCampoFront varchar(300) NULL,
	pncSolicitud bigint NULL,
	pncBeneficiario bigint NULL,
	pncNombreInconsistencia varchar(50) NULL,
	pncDescripcionInconsistencia varchar(150) NULL,
	pncClasificacionTipoProducto varchar(15) NULL
)

CREATE TABLE aud.ProyectoSolucionVivienda_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	psvNombreProyecto varchar(250) NULL,
	psvMatriculaInmobiliariaInmueble varchar(50) NULL,
	psvLoteUrbanizado bit NULL,
	psvFechaRegistroEscritura date NULL,
	psvNumeroEscritura varchar(20) NULL,
	psvFechaEscritura date NULL,
	psvAvaluoCatastral bigint NULL,
	psvObservaciones varchar(500) NULL,
	psvOferente bigint NULL,
	psvUbicacionProyecto bigint NULL,
	psvUbicacionIgualProyecto bit NULL,
	psvUbicacionVivienda bigint NULL,
	psvNumeroDocumentoElegibilidad varchar(50) NULL,
	psvCodigoProyectoElegibilidad varchar(50) NULL,
	psvNombreEntidadElegibilidad varchar(50) NULL,
	psvFechaElegibilidad date NULL,
	psvNumeroViviendaElegibilidad int NULL,
	psvTipoInmuebleElegibilidad varchar(50) NULL,
	psvComentariosElegibilidad varchar(500) NULL,
	psvModalidad varchar(50) NULL,
	psvPoseedorOcupanteVivienda varchar(50) NULL,
	psvId bigint NOT NULL,
	psvRegistrado bit NULL,
	psvDisponeCuentaBancaria bit NULL,
	psvComparteCuentaOferente bit NULL
)

CREATE TABLE aud.RangoTopeValorSFV_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rtvNombre varchar(50) NOT NULL,
	rtvOperadorValorMinimo varchar(13) NOT NULL,
	rtvValorMinimo numeric(3, 1) NOT NULL,
	rtvOperadorValorMaximo varchar(13) NOT NULL,
	rtvValorMaximo numeric(3, 1) NOT NULL,
	rtvTopeSMLMV numeric(4, 1) NOT NULL,
	rtvModalidad varchar(50) NOT NULL,
	rtvId bigint NOT NULL
)

CREATE TABLE aud.RecursoComplementario_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	recNombre varchar(26) NULL,
	recEntidad varchar(50) NULL,
	recFecha date NULL,
	recOtroRecurso varchar(255) NULL,
	recValor numeric(19, 5) NULL,
	recPostulacionFOVIS bigint NOT NULL,
	recId bigint NOT NULL
)

CREATE TABLE aud.RegistroEstadoAporte_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	reaSolicitud bigint NOT NULL,
	reaActividad varchar(29) NULL,
	reaEstadoSolicitud varchar(25) NULL,
	reaFecha datetime NULL,
	reaComunicado bigint NULL,
	reaUsuario varchar(255) NULL,
	reaId bigint NOT NULL
)

CREATE TABLE aud.RegistroNovedadFutura_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rnfFechaInicio date NULL,
	rnfFechaFin date NULL,
	rnfTipoTransaccion varchar(90) NULL,
	rnfCanalRecepcion varchar(30) NULL,
	rnfComentarios varchar(250) NULL,
	rnfPersona bigint NULL,
	rnfRegistroDetallado bigint NULL,
	rnfId bigint NOT NULL,
	rnfClasificacion varchar(48) NULL,
	rnfEmpleador bigint NULL,
	rnfProcesada bit NULL
)

CREATE TABLE aud.RegistroPersonaInconsistente_aud(
	rpiId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rpiCargueMultipleSupervivencia bigint NULL,
	rpiPersona bigint NOT NULL,
	rpiCanalContacto varchar(15) NULL,
	rpiFechaIngreso date NULL,
	rpiEstadoGestion varchar(20) NULL,
	rpiObservaciones varchar(255) NULL,
	rpiTipoInconsistencia varchar(42) NULL
)

CREATE TABLE aud.RelacionGrupoFamiliar_aud(
	rgfId smallint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rgfNombre varchar(15) NULL
)

CREATE TABLE aud.Requisito_aud(
	reqId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	reqDescripcion varchar(200) NOT NULL,
	reqEstado varchar(20) NOT NULL
)

CREATE TABLE aud.RequisitoCajaClasificacion_aud(
	rtsId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rtsEstado varchar(20) NULL,
	rtsRequisito bigint NULL,
	rtsClasificacion varchar(100) NULL,
	rtsTipoTransaccion varchar(100) NULL,
	rtsCajaCompensacion int NULL,
	rtsTextoAyuda varchar(1500) NULL,
	rtsTipoRequisito varchar(30) NULL
)

CREATE TABLE aud.ResultadoEjecucionProgramada_aud(
	repId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	repProceso varchar(100) NOT NULL,
	repFechaEjecucion datetime NULL,
	repResultadoEjecucion varchar(50) NULL,
	repTipoResultadoProceso varchar(10) NULL
)

CREATE TABLE aud.Revision(
	revId bigint IDENTITY(1,1) NOT NULL,
	revIp varchar(255) NULL,
	revNombreUsuario varchar(255) NULL,
	revRequestId varchar(255) NULL,
	revTimeStamp bigint NULL,
 CONSTRAINT PK_Revision_revId PRIMARY KEY (revId)
)

CREATE TABLE aud.RevisionEntidad(
	reeId bigint IDENTITY(1,1) NOT NULL,
	reeEntityClassName varchar(255) NULL,
	reeRevisionType int NULL,
	reeTimeStamp bigint NULL,
	reeRevision bigint NOT NULL,
 CONSTRAINT PK_RevisionEntidad_reeId PRIMARY KEY (reeId)
)

CREATE TABLE aud.RolAfiliado_aud(
	roaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	roaCargo varchar(200) NULL,
	roaClaseIndependiente varchar(50) NULL,
	roaClaseTrabajador varchar(20) NULL,
	roaEstadoAfiliado varchar(8) NULL,
	roaEstadoEnEntidadPagadora varchar(20) NULL,
	roaFechaIngreso date NULL,
	roaFechaRetiro datetime NULL,
	roaHorasLaboradasMes smallint NULL,
	roaIdentificadorAnteEntidadPagadora varchar(15) NULL,
	roaPorcentajePagoAportes numeric(19, 2) NULL,
	roaTipoAfiliado varchar(30) NOT NULL,
	roaTipoContrato varchar(20) NULL,
	roaTipoSalario varchar(10) NULL,
	roaValorSalarioMesadaIngresos numeric(19, 2) NULL,
	roaAfiliado bigint NOT NULL,
	roaEmpleador bigint NULL,
	roaPagadorAportes bigint NULL,
	roaPagadorPension smallint NULL,
	roaSucursalEmpleador bigint NULL,
	roaFechaAfiliacion date NULL,
	roaMotivoDesafiliacion varchar(50) NULL,
	roaSustitucionPatronal bit NULL,
	roaFechaFinPagadorAportes date NULL,
	roaFechaFinPagadorPension date NULL,
	roaEstadoEnEntidadPagadoraPension varchar(20) NULL,
	roaDiaHabilVencimientoAporte smallint NULL,
	roaMarcaExpulsion varchar(22) NULL,
	roaEnviadoAFiscalizacion bit NULL,
	roaMotivoFiscalizacion varchar(30) NULL,
	roaFechaFiscalizacion date NULL,
	roaOportunidadPago varchar(11) NULL
)

CREATE TABLE aud.RolContactoEmpleador_aud(
	rceId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	rceTipoRolContactoEmpleador varchar(50) NULL,
	rceEmpleador bigint NULL,
	rcePersona bigint NULL,
	rceCargo varchar(100) NULL,
	rcetoken varchar(50) NULL,
	rceCorreoEnviado bit NULL,
	rceUbicacion bigint NOT NULL
)

CREATE TABLE aud.SedeCajaCompensacion_aud(
	sccfId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sccfNombre varchar(100) NULL,
	sccfVirtual bit NULL,
	sccCodigo varchar(2) NULL
)

CREATE TABLE aud.SitioPago_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sipCodigo varchar(3) NOT NULL,
	sipNombre varchar(255) NOT NULL,
	sipInfraestructura bigint NOT NULL,
	sipActivo bit NOT NULL,
	sipId bigint NOT NULL
)

CREATE TABLE aud.SocioEmpleador_aud(
	semId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	semExistenCapitulaciones bit NULL,
	semIdentifiDocumCapitulaciones varchar(255) NULL,
	semConyugue bigint NULL,
	semEmpleador bigint NULL,
	semPersona bigint NULL
)

CREATE TABLE aud.Solicitud_aud(
	solId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	solCanalRecepcion varchar(21) NULL,
	solFechaRadicacion datetime2(7) NULL,
	solInstanciaProceso varchar(255) NULL,
	solNumeroRadicacion varchar(12) NULL,
	solUsuarioRadicacion varchar(255) NULL,
	solCajaCorrespondencia bigint NULL,
	solTipoTransaccion varchar(100) NULL,
	solCiudadUsuarioRadicacion varchar(255) NULL,
	solEstadoDocumentacion varchar(50) NULL,
	solMetodoEnvio varchar(20) NULL,
	solClasificacion varchar(48) NULL,
	solTipoRadicacion varchar(20) NULL,
	solFechaCreacion datetime2(7) NULL,
	solDestinatario varchar(255) NULL,
	solSedeDestinatario varchar(2) NULL,
	solObservacion varchar(500) NULL,
	solCargaAfiliacionMultipleEmpleador bigint NULL,
	solResultadoProceso varchar(22) NULL,
	solDiferenciasCargueActualizacion bigint NULL,
	solAnulada bit NULL
)

CREATE TABLE aud.SolicitudAfiliaciEmpleador_aud(
	saeId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	saeCodigoEtiquetaPreimpresa varchar(12) NULL,
	saeEstadoSolicitud varchar(50) NULL,
	saeFechaAprobacionConsejo datetime2(7) NULL,
	saeNumeroActoAdministrativo varchar(50) NULL,
	saeNumeroCustodiaFisica varchar(12) NULL,
	saeEmpleador bigint NULL,
	saeSolicitudGlobal bigint NULL
)

CREATE TABLE aud.SolicitudAfiliacionPersona_aud(
	sapId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sapEstadoSolicitud varchar(50) NULL,
	sapRolAfiliado bigint NULL,
	sapSolicitudGlobal bigint NULL
)

CREATE TABLE aud.SolicitudAnalisisNovedadFovis_aud(
	sanId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sanSolicitudGlobal bigint NOT NULL,
	sanSolicitudNovedad bigint NOT NULL,
	sanPersona bigint NOT NULL,
	sanEstadoSolicitud varchar(9) NOT NULL,
	sanPostulacionFovis bigint NOT NULL,
	sanObservaciones varchar(500) NULL
)

CREATE TABLE aud.SolicitudAporte_aud(
	soaSolicitudGlobal bigint NOT NULL,
	soaEstadoSolicitud varchar(30) NULL,
	soaAporteGeneral bigint NULL,
	soaNumeroIdentificacion varchar(16) NULL,
	soaTipoIdentificacion varchar(20) NULL,
	soaNombreAportante varchar(200) NULL,
	soaPeriodoAporte varchar(7) NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	soaTipoSolicitante varchar(13) NULL,
	soaId bigint NOT NULL
)

CREATE TABLE aud.SolicitudAsignacion_aud(
	safId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	safSolicitudGlobal bigint NOT NULL,
	safFechaAceptacion datetime NULL,
	safUsuario varchar(50) NULL,
	safValorSFVAsignado numeric(19, 5) NULL,
	safEstadoSolicitudAsignacion varchar(50) NULL,
	safComentarios varchar(500) NULL,
	safCicloAsignacion bigint NOT NULL,
	safComentarioControlInterno varchar(500) NULL
)

CREATE TABLE aud.SolicitudAsociacionPersonaEntidadPagadora_aud(
	soaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	soaConsecutivo varchar(11) NULL,
	soaEstado varchar(50) NULL,
	soaFechaGestion datetime2(7) NULL,
	soaIdentificadorArchivoCarta varchar(255) NULL,
	soaIdentificadorArchivoPlano varchar(255) NULL,
	soaTipoGestion varchar(50) NULL,
	soaRolAfiliado bigint NOT NULL,
	soaSolicitudGlobal bigint NOT NULL,
	soaUsuarioGestion varchar(255) NULL,
	soaIdentificadorCartaResultadoGestion varchar(255) NULL
)

CREATE TABLE aud.SolicitudCierreRecaudo_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sciSolicitudGlobal bigint NOT NULL,
	sciEstadoSolicitud varchar(31) NOT NULL,
	sciFechaInicio datetime NOT NULL,
	sciFechaFin datetime NOT NULL,
	sciTipoCierre varchar(9) NOT NULL,
	sciObservacionesSupervisor varchar(255) NULL,
	sciObservacionesContabilidad varchar(255) NULL,
	sciUsuarioSupervisor varchar(50) NULL,
	sciUsuarioAnalistaContable varchar(50) NULL,
	sciCodigoIdentificacionECM varchar(255) NULL,
	sciResumen varchar(255) NULL,
	sciId bigint NOT NULL
)

CREATE TABLE aud.SolicitudCorreccionAporte_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	scaEstadoSolicitud varchar(25) NULL,
	scaTipoSolicitante varchar(25) NULL,
	scaObservacionSupervisor varchar(255) NULL,
	scaResultadoSupervisor varchar(10) NULL,
	scaSolicitudGlobal bigint NULL,
	scaPersona bigint NULL,
	scaAporteGeneral bigint NULL,
	scaId bigint NOT NULL
)

CREATE TABLE aud.SolicitudDesafiliacion_aud(
	sodId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sodComentarioCoordinador varchar(500) NULL,
	sodEstadoSolicitud varchar(9) NULL,
	sodSolicitudGlobal bigint NOT NULL
)

CREATE TABLE aud.SolicitudDevolucionAporte_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sdaEstadoSolicitud varchar(25) NULL,
	sdaTipoSolicitante varchar(13) NULL,
	sdaPersona bigint NULL,
	sdaObservacionAnalista varchar(255) NULL,
	sdaObservacionSupervisor varchar(255) NULL,
	sdaResultadoAnalista varchar(10) NULL,
	sdaResultadoSupervisor varchar(10) NULL,
	sdaDevolucionAporte bigint NULL,
	sdaSolicitudGlobal bigint NULL,
	sdaId bigint NOT NULL
)

CREATE TABLE aud.SolicitudFiscalizacion_aud(
	sfiId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sfiEstadoFiscalizacion varchar(11) NULL,
	sfiSolicitudGlobal bigint NOT NULL,
	sfiCicloAportante bigint NOT NULL
)

CREATE TABLE aud.SolicitudGestionCobroElectronico_aud(
	sgeId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sgeEstado varchar(33) NOT NULL,
	sgeCartera bigint NOT NULL,
	sgeTipoAccionCobro varchar(4) NOT NULL,
	sgeSolicitud bigint NOT NULL
)

CREATE TABLE aud.SolicitudGestionCobroFisico_aud(
	sgfId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sgfDocumentoSoporte bigint NULL,
	sgfEstado varchar(52) NULL,
	sgfFechaRemision datetime NULL,
	sgfObservacionRemision varchar(255) NULL,
	sgfTipoAccionCobro varchar(4) NOT NULL,
	sgfSolicitud bigint NOT NULL
)

CREATE TABLE aud.SolicitudGestionCobroManual_aud(
	scmId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	scmCicloAportante bigint NOT NULL,
	scmEstadoSolicitud varchar(25) NULL,
	scmSolicitudGlobal bigint NOT NULL,
	scmLineaCobro varchar(3) NOT NULL
)

CREATE TABLE aud.SolicitudGestionCruce_aud(
	sgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sgcSolicitudPostulacion bigint NOT NULL,
	sgcEstadoCruceHogar varchar(53) NULL,
	sgcTipoCruce varchar(8) NULL,
	sgcEstado varchar(32) NULL,
	sgcSolicitudGlobal bigint NULL
)

CREATE TABLE aud.SolicitudLegalizacionDesembolso_aud(
	sldId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sldSolicitudGlobal bigint NOT NULL,
	sldPostulacionFOVIS bigint NULL,
	sldEstadoSolicitud varchar(48) NULL,
	sldLegalizacionDesembolso bigint NULL,
	sldObservaciones varchar(500) NULL,
	sldFechaOperacion datetime NULL,
	sldJsonPostulacion text NULL,
	sldCantidadReintentos smallint NULL
)

CREATE TABLE aud.SolicitudLiquidacionSubsidio_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	slsSolicitudGlobal bigint NOT NULL,
	slsFechaCorteAporte datetime NULL,
	slsFechaInicio datetime NULL,
	slsFechaFin datetime NULL,
	slsTipoLiquidacion varchar(33) NOT NULL,
	slsTipoLiquidacionEspecifica varchar(32) NULL,
	slsEstadoLiquidacion varchar(25) NOT NULL,
	slsTipoEjecucionProceso varchar(10) NOT NULL,
	slsFechaEjecucionProgramada datetime NULL,
	slsUsuarioEvaluacionPrimerNivel varchar(50) NULL,
	slsObservacionesPrimerNivel varchar(250) NULL,
	slsUsuarioEvaluacionSegundoNivel varchar(50) NULL,
	slsObservacionesSegundoNivel varchar(250) NULL,
	slsRazonRechazoLiquidacion varchar(250) NULL,
	slsObservacionesProceso varchar(250) NULL,
	slsId bigint NOT NULL,
	slsFechaEvaluacionPrimerNivel datetime NULL,
	slsFechaEvaluacionSegundoNivel datetime NULL,
	slsCodigoReclamo varchar(50) NULL,
	slsComentarioReclamo varchar(250) NULL,
	slsFechaDispersion datetime NULL,
	slsConsideracionAporteDesembolso bit NULL,
	slsTipoDesembolso varchar(40) NULL
)

CREATE TABLE aud.SolicitudNovedad_aud(
	snoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	snoEstadoSolicitud varchar(50) NULL,
	snoNovedad bigint NULL,
	snoSolicitudGlobal bigint NULL,
	snoObservaciones varchar(200) NULL,
	snoCargaMultiple bit NULL
)

CREATE TABLE aud.SolicitudNovedadEmpleador_aud(
	sneId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sneIdEmpleador bigint NULL,
	sneIdSolicitudNovedad bigint NULL
)

CREATE TABLE aud.SolicitudNovedadFovis_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	snfSolicitudGlobal bigint NOT NULL,
	snfEstadoSolicitud varchar(38) NOT NULL,
	snfParametrizacionNovedad bigint NOT NULL,
	snfObservaciones varchar(200) NULL,
	snfId bigint NOT NULL
)

CREATE TABLE aud.SolicitudNovedadPersona_aud(
	snpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	snpPersona bigint NOT NULL,
	snpSolicitudNovedad bigint NOT NULL,
	snpRolAfiliado bigint NULL,
	snpBeneficiario bigint NULL
)

CREATE TABLE aud.SolicitudNovedadPersonaFovis_aud(
	spfId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	spfPersona bigint NULL,
	spfSolicitudNovedadFovis bigint NOT NULL,
	spfPostulacionFovis bigint NOT NULL
)

CREATE TABLE aud.SolicitudNovedadPila_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	spiSolicitudNovedad bigint NOT NULL,
	spiRegistroDetallado bigint NOT NULL,
	spiId bigint NOT NULL
)

CREATE TABLE aud.SolicitudPostulacion_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	spoSolicitudGlobal bigint NOT NULL,
	spoPostulacionFOVIS bigint NULL,
	spoEstadoSolicitud varchar(42) NULL,
	spoObservaciones varchar(500) NULL,
	spoId bigint NOT NULL,
	spoObservacionesWeb varchar(500) NULL
)

CREATE TABLE aud.SolicitudPreventiva_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sprActualizacionEfectiva bit NULL,
	sprBackActualizacion varchar(255) NULL,
	sprContactoEfectivo bit NULL,
	sprEstadoSolicitudPreventiva varchar(34) NULL,
	sprPersona bigint NOT NULL,
	sprRequiereFiscalizacion bit NULL,
	sprTipoSolicitanteMovimientoAporte varchar(14) NOT NULL,
	sprSolicitudGlobal bigint NOT NULL,
	sprTipoGestionCartera varchar(10) NULL,
	sprId bigint NOT NULL,
	sprFechaFiscalizacion date NULL,
	sprCantidadVecesMoroso smallint NULL,
	sprEstadoActualCartera varchar(6) NULL,
	sprFechaLimitePago date NULL,
	sprSolicitudPreventivaAgrupadora bigint NULL,
	sprTrabajadoresActivos smallint NULL,
	sprValorPromedioAportes numeric(19, 2) NULL
)

CREATE TABLE aud.SolicitudPreventivaAgrupadora_aud(
	spaId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	spaEstadoSolicitudPreventivaAgrupadora varchar(255) NULL,
	spaSolicitudGlobal bigint NULL
)

CREATE TABLE aud.SolicitudVerificacionFovis_aud(
	svfId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	svfSolicitudGlobal bigint NOT NULL,
	svfPostulacionFOVIS bigint NULL,
	svfEstadoSolicitud varchar(42) NULL,
	svfTipo varchar(15) NULL,
	svfObservaciones varchar(500) NULL
)

CREATE TABLE aud.SucursalEmpresa_aud(
	sueId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	sueCodigo varchar(10) NULL,
	sueNombre varchar(100) NULL,
	sueCodigoCIIU smallint NULL,
	sueEmpresa bigint NULL,
	sueUbicacion bigint NULL,
	sueEstadoSucursal varchar(25) NULL,
	sueCoindicirCodigoPila bit NULL,
	sueMedioPagoSubsidioMonetario varchar(30) NULL,
	sueSucursalPrincipal bit NULL,
	sueRetencionSubsidioActiva bit NULL,
	sueMotivoRetencionSubsidio varchar(24) NULL,
	sueMotivoInactivaRetencionSubsidio varchar(26) NULL
)

CREATE TABLE aud.SucursaRolContactEmpleador_aud(
	srcId bigint NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	srcRolContactoEmpleador bigint NULL,
	srcSucursalEmpleador bigint NULL
)

CREATE TABLE aud.Tarjeta_aud(
	tarId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	tarEstadoTarjeta varchar(20) NULL,
	tarNumeroTarjeta varchar(20) NULL,
	afiPersona bigint NULL
)

CREATE TABLE aud.TasasInteresMora_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	timFechaInicioTasa date NOT NULL,
	timFechaFinTasa date NOT NULL,
	timNumeroPeriodoTasa smallint NOT NULL,
	timPorcentajeTasa numeric(4, 4) NOT NULL,
	timNormativa varchar(100) NOT NULL,
	timTipoInteres varchar(20) NOT NULL,
	timId bigint NOT NULL
)

CREATE TABLE aud.TipoInfraestructura_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	tifCodigo varchar(3) NOT NULL,
	tifNombre varchar(255) NOT NULL,
	tifMedidaCapacidad varchar(40) NOT NULL,
	tifActivo bit NOT NULL,
	tifId bigint NOT NULL
)

CREATE TABLE aud.TipoTenencia_aud(
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	tenCodigo smallint NOT NULL,
	tenNombre varchar(255) NOT NULL,
	tenActivo bit NOT NULL,
	tenId bigint NOT NULL
)

CREATE TABLE aud.TipoVia_aud(
	tviId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	tviNombreVia varchar(20) NOT NULL
)

CREATE TABLE aud.Ubicacion_aud(
	ubiId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ubiAutorizacionEnvioEmail bit NULL,
	ubiCodigoPostal varchar(10) NULL,
	ubiDireccionFisica varchar(300) NULL,
	ubiEmail varchar(255) NULL,
	ubiIndicativoTelFijo varchar(2) NULL,
	ubiTelefonoCelular varchar(10) NULL,
	ubiTelefonoFijo varchar(7) NULL,
	ubiMunicipio smallint NULL,
	ubiDescripcionIndicacion varchar(100) NULL
)

CREATE TABLE aud.UbicacionEmpresa_aud(
	ubeId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	ubeEmpresa bigint NULL,
	ubeUbicacion bigint NULL,
	ubeTipoUbicacion varchar(30) NULL
)

CREATE TABLE aud.ValidacionProceso_aud(
	vapId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	vapBloque varchar(150) NULL,
	vapValidacion varchar(100) NULL,
	vapProceso varchar(100) NULL,
	vapEstadoProceso varchar(20) NULL,
	vapOrden int NULL,
	vapObjetoValidacion varchar(60) NULL,
	vapInversa bit NOT NULL
)

CREATE TABLE aud.VariableComunicado_aud(
	vcoId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	vcoClave varchar(55) NULL,
	vcoDescripcion varchar(200) NULL,
	vcoNombre varchar(50) NULL,
	vcoPlantillaComunicado bigint NOT NULL,
	vcoNombreConstante varchar(100) NULL,
	vcoTipoVariableComunicado varchar(20) NULL,
	vcoOrden varchar(3) NULL
)

CREATE TABLE aud.Visita_aud(
	visId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	visFecha date NOT NULL,
	visNombresEncargado varchar(50) NOT NULL,
	visCodigoIdentificadorECM varchar(255) NOT NULL
);

--changeset arocha:16
--comment: Laves foraneas de el esquema aud
ALTER TABLE aud.AccionCobro1C_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro1C_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro1C_aud CHECK CONSTRAINT FK_AccionCobro1C_aud_REV

ALTER TABLE aud.AccionCobro1D1E_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro1D1E_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro1D1E_aud CHECK CONSTRAINT FK_AccionCobro1D1E_aud_REV

ALTER TABLE aud.AccionCobro1F_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro1F_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro1F_aud CHECK CONSTRAINT FK_AccionCobro1F_aud_REV

ALTER TABLE aud.AccionCobro2C_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro2C_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro2C_aud CHECK CONSTRAINT FK_AccionCobro2C_aud_REV

ALTER TABLE aud.AccionCobro2D_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro2D_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro2D_aud CHECK CONSTRAINT FK_AccionCobro2D_aud_REV

ALTER TABLE aud.AccionCobro2E_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro2E_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro2E_aud CHECK CONSTRAINT FK_AccionCobro2E_aud_REV

ALTER TABLE aud.AccionCobro2F2G_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro2F2G_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro2F2G_aud CHECK CONSTRAINT FK_AccionCobro2F2G_aud_REV

ALTER TABLE aud.AccionCobro2H_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobro2H_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobro2H_aud CHECK CONSTRAINT FK_AccionCobro2H_aud_REV

ALTER TABLE aud.AccionCobroA_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobroA_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobroA_aud CHECK CONSTRAINT FK_AccionCobroA_aud_REV

ALTER TABLE aud.AccionCobroB_aud  WITH CHECK ADD  CONSTRAINT FK_AccionCobroB_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AccionCobroB_aud CHECK CONSTRAINT FK_AccionCobroB_aud_REV

ALTER TABLE aud.ActaAsignacionFovis_aud  WITH CHECK ADD  CONSTRAINT FK_ActaAsignacionFovis_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ActaAsignacionFovis_aud CHECK CONSTRAINT FK_ActaAsignacionFovis_aud_REV

ALTER TABLE aud.ActividadCartera_aud  WITH CHECK ADD  CONSTRAINT FK_ActividadFiscalizacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ActividadCartera_aud CHECK CONSTRAINT FK_ActividadFiscalizacion_aud_REV

ALTER TABLE aud.ActividadDocumento_aud  WITH CHECK ADD  CONSTRAINT FK_ActividadDocumento_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ActividadDocumento_aud CHECK CONSTRAINT FK_ActividadDocumento_aud_REV

ALTER TABLE aud.ActoAceptacionProrrogaFovis_aud  WITH CHECK ADD  CONSTRAINT FK_ActoAceptacionProrrogaFovis_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ActoAceptacionProrrogaFovis_aud CHECK CONSTRAINT FK_ActoAceptacionProrrogaFovis_aud_REV

ALTER TABLE aud.AdministradorSubsidio_aud  WITH CHECK ADD  CONSTRAINT FK_AdministradorSubsidio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AdministradorSubsidio_aud CHECK CONSTRAINT FK_AdministradorSubsidio_aud_REV

ALTER TABLE aud.AdminSubsidioGrupo_aud  WITH CHECK ADD  CONSTRAINT FK_AdminSubsidioGrupo_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AdminSubsidioGrupo_aud CHECK CONSTRAINT FK_AdminSubsidioGrupo_aud_REV

ALTER TABLE aud.Afiliado_aud  WITH CHECK ADD  CONSTRAINT FK_Afiliado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Afiliado_aud CHECK CONSTRAINT FK_Afiliado_aud_REV

ALTER TABLE aud.AFP_aud  WITH CHECK ADD  CONSTRAINT FK_AFP_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AFP_aud CHECK CONSTRAINT FK_AFP_aud_REV

ALTER TABLE aud.AgendaCartera_aud  WITH CHECK ADD  CONSTRAINT FK_AgendaFiscalizacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AgendaCartera_aud CHECK CONSTRAINT FK_AgendaFiscalizacion_aud_REV

ALTER TABLE aud.AhorroPrevio_aud  WITH CHECK ADD  CONSTRAINT FK_AhorroPrevio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AhorroPrevio_aud CHECK CONSTRAINT FK_AhorroPrevio_aud_REV

ALTER TABLE aud.AporteDetallado_aud  WITH CHECK ADD  CONSTRAINT FK_AporteDetallado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AporteDetallado_aud CHECK CONSTRAINT FK_AporteDetallado_aud_REV

ALTER TABLE aud.AporteGeneral_aud  WITH CHECK ADD  CONSTRAINT FK_AporteGeneral_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AporteGeneral_aud CHECK CONSTRAINT FK_AporteGeneral_aud_REV

ALTER TABLE aud.AreaCajaCompensacion_aud  WITH CHECK ADD  CONSTRAINT FK_AreaCajaCompensacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AreaCajaCompensacion_aud CHECK CONSTRAINT FK_AreaCajaCompensacion_aud_REV

ALTER TABLE aud.ARL_aud  WITH CHECK ADD  CONSTRAINT FK_ARL_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ARL_aud CHECK CONSTRAINT FK_ARL_aud_REV

ALTER TABLE aud.AsesorResponsableEmpleador_aud  WITH CHECK ADD  CONSTRAINT FK_AsesorResponsableEmpleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.AsesorResponsableEmpleador_aud CHECK CONSTRAINT FK_AsesorResponsableEmpleador_aud_REV

ALTER TABLE aud.Banco_aud  WITH CHECK ADD  CONSTRAINT FK_Banco_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Banco_aud CHECK CONSTRAINT FK_Banco_aud_REV

ALTER TABLE aud.Beneficiario_aud  WITH CHECK ADD  CONSTRAINT FK_Beneficiario_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Beneficiario_aud CHECK CONSTRAINT FK_Beneficiario_aud_REV

ALTER TABLE aud.BeneficiarioDetalle_aud  WITH CHECK ADD  CONSTRAINT FK_BeneficiarioDetalle_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.BeneficiarioDetalle_aud CHECK CONSTRAINT FK_BeneficiarioDetalle_aud_REV

ALTER TABLE aud.Beneficio_aud  WITH CHECK ADD  CONSTRAINT FK_Beneficio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Beneficio_aud CHECK CONSTRAINT FK_Beneficio_aud_REV

ALTER TABLE aud.BeneficioEmpleador_aud  WITH CHECK ADD  CONSTRAINT FK_BeneficioEmpleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.BeneficioEmpleador_aud CHECK CONSTRAINT FK_BeneficioEmpleador_aud_REV

ALTER TABLE aud.BitacoraCartera_aud  WITH CHECK ADD  CONSTRAINT FK_BitacoraCartera_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.BitacoraCartera_aud CHECK CONSTRAINT FK_BitacoraCartera_aud_REV

ALTER TABLE aud.CajaCompensacion_aud  WITH CHECK ADD  CONSTRAINT FK_CajaCompensacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CajaCompensacion_aud CHECK CONSTRAINT FK_CajaCompensacion_aud_REV

ALTER TABLE aud.CajaCorrespondencia_aud  WITH CHECK ADD  CONSTRAINT FK_CajaCorrespondencia_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CajaCorrespondencia_aud CHECK CONSTRAINT FK_CajaCorrespondencia_aud_REV

ALTER TABLE aud.CargueArchivoActualizacion_aud  WITH CHECK ADD  CONSTRAINT FK_CargueArchivoActualizacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CargueArchivoActualizacion_aud CHECK CONSTRAINT FK_CargueArchivoActualizacion_aud_REV

ALTER TABLE aud.CargueArchivoCruceFovis_aud  WITH CHECK ADD  CONSTRAINT FK_CargueArchivoCruceFovis_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CargueArchivoCruceFovis_aud CHECK CONSTRAINT FK_CargueArchivoCruceFovis_aud_REV

ALTER TABLE aud.CargueMultiple_aud  WITH CHECK ADD  CONSTRAINT FK_CargueMultiple_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CargueMultiple_aud CHECK CONSTRAINT FK_CargueMultiple_aud_REV

ALTER TABLE aud.CargueMultipleSupervivencia_aud  WITH CHECK ADD  CONSTRAINT FK_CargueMultipleSupervivencia_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CargueMultipleSupervivencia_aud CHECK CONSTRAINT FK_CargueMultipleSupervivencia_aud_REV

ALTER TABLE aud.Cartera_aud  WITH CHECK ADD  CONSTRAINT FK_Cartera_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Cartera_aud CHECK CONSTRAINT FK_Cartera_aud_REV

ALTER TABLE aud.CarteraDependiente_aud  WITH CHECK ADD  CONSTRAINT FK_CarteraDependiente_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CarteraDependiente_aud CHECK CONSTRAINT FK_CarteraDependiente_aud_REV

ALTER TABLE aud.Categoria_aud  WITH CHECK ADD  CONSTRAINT FK_Categoria_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Categoria_aud CHECK CONSTRAINT FK_Categoria_aud_REV

ALTER TABLE aud.CicloAportante_aud  WITH CHECK ADD  CONSTRAINT FK_CicloAportante_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CicloAportante_aud CHECK CONSTRAINT FK_CicloAportante_aud_REV

ALTER TABLE aud.CicloAsignacion_aud  WITH CHECK ADD  CONSTRAINT FK_CicloAsignacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CicloAsignacion_aud CHECK CONSTRAINT FK_CicloAsignacion_aud_REV

ALTER TABLE aud.CicloCartera_aud  WITH CHECK ADD  CONSTRAINT FK_CicloFiscalizacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CicloCartera_aud CHECK CONSTRAINT FK_CicloFiscalizacion_aud_REV

ALTER TABLE aud.CicloModalidad_aud  WITH CHECK ADD  CONSTRAINT FK_CicloModalidad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CicloModalidad_aud CHECK CONSTRAINT FK_CicloModalidad_aud_REV

ALTER TABLE aud.CodigoCIIU_aud  WITH CHECK ADD  CONSTRAINT FK_CodigoCIIU_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CodigoCIIU_aud CHECK CONSTRAINT FK_CodigoCIIU_aud_REV

ALTER TABLE aud.Comunicado_aud  WITH CHECK ADD  CONSTRAINT FK_Comunicado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Comunicado_aud CHECK CONSTRAINT FK_Comunicado_aud_REV

ALTER TABLE aud.CondicionInvalidez_aud  WITH CHECK ADD  CONSTRAINT FK_CondicionInvalidez_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CondicionInvalidez_aud CHECK CONSTRAINT FK_CondicionInvalidez_aud_REV

ALTER TABLE aud.CondicionVisita_aud  WITH CHECK ADD  CONSTRAINT FK_CondicionVisita_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CondicionVisita_aud CHECK CONSTRAINT FK_CondicionVisita_aud_REV

ALTER TABLE aud.ConexionOperadorInformacion_aud  WITH CHECK ADD  CONSTRAINT FK_ConexionOperadorInformacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ConexionOperadorInformacion_aud CHECK CONSTRAINT FK_ConexionOperadorInformacion_aud_REV

ALTER TABLE aud.ConsolaEstadoCargueMasivo_aud  WITH CHECK ADD  CONSTRAINT FK_ConsolaEstadoCargueMasivo_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ConsolaEstadoCargueMasivo_aud CHECK CONSTRAINT FK_ConsolaEstadoCargueMasivo_aud_REV

ALTER TABLE aud.Constante_aud  WITH CHECK ADD  CONSTRAINT FK_Constante_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Constante_aud CHECK CONSTRAINT FK_Constante_aud_REV

ALTER TABLE aud.ConvenioPago_aud  WITH CHECK ADD  CONSTRAINT FK_ConvenioPago_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ConvenioPago_aud CHECK CONSTRAINT FK_ConvenioPago_aud_REV

ALTER TABLE aud.ConvenioPagoDependiente_aud  WITH CHECK ADD  CONSTRAINT FK_ConvenioPagoDependiente_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ConvenioPagoDependiente_aud CHECK CONSTRAINT FK_ConvenioPagoDependiente_aud_REV

ALTER TABLE aud.Correccion_aud  WITH CHECK ADD  CONSTRAINT FK_Correccion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Correccion_aud CHECK CONSTRAINT FK_Correccion_aud_REV

ALTER TABLE aud.Cruce_aud  WITH CHECK ADD  CONSTRAINT FK_Cruce_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Cruce_aud CHECK CONSTRAINT FK_Cruce_aud_REV

ALTER TABLE aud.CruceDetalle_aud  WITH CHECK ADD  CONSTRAINT FK_CruceDetalle_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CruceDetalle_aud CHECK CONSTRAINT FK_CruceDetalle_aud_REV

ALTER TABLE aud.CuentaAdministradorSubsidio_aud  WITH CHECK ADD  CONSTRAINT FK_CuentaAdministradorSubsidio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.CuentaAdministradorSubsidio_aud CHECK CONSTRAINT FK_CuentaAdministradorSubsidio_aud_REV

ALTER TABLE aud.Departamento_aud  WITH CHECK ADD  CONSTRAINT FK_Departamento_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Departamento_aud CHECK CONSTRAINT FK_Departamento_aud_REV

ALTER TABLE aud.DesafiliacionAportante_aud  WITH CHECK ADD  CONSTRAINT FK_DesafiliacionAportante_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DesafiliacionAportante_aud CHECK CONSTRAINT FK_DesafiliacionAportante_aud_REV

ALTER TABLE aud.DescuentoInteresMora_aud  WITH CHECK ADD  CONSTRAINT FK_DescuentoInteresMora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DescuentoInteresMora_aud CHECK CONSTRAINT FK_DescuentoInteresMora_aud_REV

ALTER TABLE aud.DestinatarioComunicado_aud  WITH CHECK ADD  CONSTRAINT FK_DestinatarioComunicado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DestinatarioComunicado_aud CHECK CONSTRAINT FK_DestinatarioComunicado_aud_REV

ALTER TABLE aud.DestinatarioGrupo_aud  WITH CHECK ADD  CONSTRAINT FK_DestinatarioGrupo_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DestinatarioGrupo_aud CHECK CONSTRAINT FK_DestinatarioGrupo_aud_REV

ALTER TABLE aud.DetalleComunicadoEnviado_aud  WITH CHECK ADD  CONSTRAINT FK_DetalleComunicadoEnviado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DetalleComunicadoEnviado_aud CHECK CONSTRAINT FK_DetalleComunicadoEnviado_aud_REV

ALTER TABLE aud.DetalleSolicitudGestionCobro_aud  WITH CHECK ADD  CONSTRAINT FK_DetalleSolicitudGestionCobro_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DetalleSolicitudGestionCobro_aud CHECK CONSTRAINT FK_DetalleSolicitudGestionCobro_aud_REV

ALTER TABLE aud.DetalleSubsidioAsignado_aud  WITH CHECK ADD  CONSTRAINT FK_DetalleSubsidioAsignado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DetalleSubsidioAsignado_aud CHECK CONSTRAINT FK_DetalleSubsidioAsignado_aud_REV

ALTER TABLE aud.DetalleSubsidioAsignadoProgramado_aud  WITH CHECK ADD  CONSTRAINT FK_DetalleSubsidioAsignadoProgramado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DetalleSubsidioAsignadoProgramado_aud CHECK CONSTRAINT FK_DetalleSubsidioAsignadoProgramado_aud_REV

ALTER TABLE aud.DevolucionAporte_aud  WITH CHECK ADD  CONSTRAINT FK_DevolucionAporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DevolucionAporte_aud CHECK CONSTRAINT FK_DevolucionAporte_aud_REV

ALTER TABLE aud.DevolucionAporteDetalle_aud  WITH CHECK ADD  CONSTRAINT FK_DevolucionAporteDetalle_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DevolucionAporteDetalle_aud CHECK CONSTRAINT FK_DevolucionAporteDetalle_aud_REV

ALTER TABLE aud.DiasFestivos_aud  WITH CHECK ADD  CONSTRAINT FK_DiasFestivos_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DiasFestivos_aud CHECK CONSTRAINT FK_DiasFestivos_aud_REV

ALTER TABLE aud.DiferenciasCargueActualizacion_aud  WITH CHECK ADD  CONSTRAINT FK_DiferenciasCargueActualizacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DiferenciasCargueActualizacion_aud CHECK CONSTRAINT FK_DiferenciasCargueActualizacion_aud_REV

ALTER TABLE aud.DocumentoAdministracionEstadoSolicitud_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoAdministracionEstadoSolicitud_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoAdministracionEstadoSolicitud_aud CHECK CONSTRAINT FK_DocumentoAdministracionEstadoSolicitud_aud_REV

ALTER TABLE aud.DocumentoBitacora_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoBitacora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoBitacora_aud CHECK CONSTRAINT FK_DocumentoBitacora_aud_REV

ALTER TABLE aud.DocumentoCartera_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoCartera_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoCartera_aud CHECK CONSTRAINT FK_DocumentoCartera_aud_REV

ALTER TABLE aud.DocumentoDesafiliacion_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoDesafiliacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoDesafiliacion_aud CHECK CONSTRAINT FK_DocumentoDesafiliacion_aud_REV

ALTER TABLE aud.DocumentoEntidadPagadora_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoEntidadPagadora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoEntidadPagadora_aud CHECK CONSTRAINT FK_DocumentoEntidadPagadora_aud_REV

ALTER TABLE aud.DocumentoSoporte_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoSoporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoSoporte_aud CHECK CONSTRAINT FK_DocumentoSoporte_aud_REV

ALTER TABLE aud.DocumentoSoporteOferente_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoSoporteOferente_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoSoporteOferente_aud CHECK CONSTRAINT FK_DocumentoSoporteOferente_aud_REV

ALTER TABLE aud.DocumentoSoporteProyectoVivienda_aud  WITH CHECK ADD  CONSTRAINT FK_DocumentoSoporteProyectoVivienda_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.DocumentoSoporteProyectoVivienda_aud CHECK CONSTRAINT FK_DocumentoSoporteProyectoVivienda_aud_REV

ALTER TABLE aud.EjecucionProcesoAsincrono_aud  WITH CHECK ADD  CONSTRAINT FK_EjecucionProcesoAsincrono_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.EjecucionProcesoAsincrono_aud CHECK CONSTRAINT FK_EjecucionProcesoAsincrono_aud_REV

ALTER TABLE aud.EjecucionProgramada_aud  WITH CHECK ADD  CONSTRAINT FK_EjecucionProgramada_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.EjecucionProgramada_aud CHECK CONSTRAINT FK_EjecucionProgramada_aud_REV

ALTER TABLE aud.ElementoDireccion_aud  WITH CHECK ADD  CONSTRAINT FK_ElementoDireccion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ElementoDireccion_aud CHECK CONSTRAINT FK_ElementoDireccion_aud_REV

ALTER TABLE aud.Empleador_aud  WITH CHECK ADD  CONSTRAINT FK_Empleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Empleador_aud CHECK CONSTRAINT FK_Empleador_aud_REV

ALTER TABLE aud.Empresa_aud  WITH CHECK ADD  CONSTRAINT FK_Empresa_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Empresa_aud CHECK CONSTRAINT FK_Empresa_aud_REV

ALTER TABLE aud.EntidadDescuento_aud  WITH CHECK ADD  CONSTRAINT FK_EntidadDescuento_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.EntidadDescuento_aud CHECK CONSTRAINT FK_EntidadDescuento_aud_REV

ALTER TABLE aud.EntidadPagadora_aud  WITH CHECK ADD  CONSTRAINT FK_EntidadPagadora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.EntidadPagadora_aud CHECK CONSTRAINT FK_EntidadPagadora_aud_REV

ALTER TABLE aud.EscalamientoSolicitud_aud  WITH CHECK ADD  CONSTRAINT FK_EscalamientoSolicitud_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.EscalamientoSolicitud_aud CHECK CONSTRAINT FK_EscalamientoSolicitud_aud_REV

ALTER TABLE aud.EtiquetaCorrespondenciaRadicado_aud  WITH CHECK ADD  CONSTRAINT FK_EtiquetaCorrespondenciaRadicado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.EtiquetaCorrespondenciaRadicado_aud CHECK CONSTRAINT FK_EtiquetaCorrespondenciaRadicado_aud_REV

ALTER TABLE aud.ExclusionCartera_aud  WITH CHECK ADD  CONSTRAINT FK_ExclusionCartera_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ExclusionCartera_aud CHECK CONSTRAINT FK_ExclusionCartera_aud_REV

ALTER TABLE aud.ExpulsionSubsanada_aud  WITH CHECK ADD  CONSTRAINT FK_ExpulsionSubsanada_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ExpulsionSubsanada_aud CHECK CONSTRAINT FK_ExpulsionSubsanada_aud_REV

ALTER TABLE aud.FormaPagoModalidad_aud  WITH CHECK ADD  CONSTRAINT FK_FormaPagoModalidad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.FormaPagoModalidad_aud CHECK CONSTRAINT FK_FormaPagoModalidad_aud_REV

ALTER TABLE aud.GestionNotiNoEnviadas_aud  WITH CHECK ADD  CONSTRAINT FK_GestionNotiNoEnviadas_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.GestionNotiNoEnviadas_aud CHECK CONSTRAINT FK_GestionNotiNoEnviadas_aud_REV

ALTER TABLE aud.GlosaComentarioNovedad_aud  WITH CHECK ADD  CONSTRAINT FK_GlosaComentarioNovedad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.GlosaComentarioNovedad_aud CHECK CONSTRAINT FK_GlosaComentarioNovedad_aud_REV

ALTER TABLE aud.GradoAcademico_aud  WITH CHECK ADD  CONSTRAINT FK_GradoAcademico_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.GradoAcademico_aud CHECK CONSTRAINT FK_GradoAcademico_aud_REV

ALTER TABLE aud.GrupoFamiliar_aud  WITH CHECK ADD  CONSTRAINT FK_GrupoFamiliar_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.GrupoFamiliar_aud CHECK CONSTRAINT FK_GrupoFamiliar_aud_REV

ALTER TABLE aud.GrupoPrioridad_aud  WITH CHECK ADD  CONSTRAINT FK_GrupoPrioridad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.GrupoPrioridad_aud CHECK CONSTRAINT FK_GrupoPrioridad_aud_REV

ALTER TABLE aud.GrupoRequisito_aud  WITH CHECK ADD  CONSTRAINT FK_GrupoRequisito_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.GrupoRequisito_aud CHECK CONSTRAINT FK_GrupoRequisito_aud_REV

ALTER TABLE aud.HistoriaResultadoValidacion_aud  WITH CHECK ADD  CONSTRAINT FK_HistoriaResultadoValidacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.HistoriaResultadoValidacion_aud CHECK CONSTRAINT FK_HistoriaResultadoValidacion_aud_REV

ALTER TABLE aud.InformacionFaltanteAportante_aud  WITH CHECK ADD  CONSTRAINT FK_InformacionFaltanteAportante_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.InformacionFaltanteAportante_aud CHECK CONSTRAINT FK_InformacionFaltanteAportante_aud_REV

ALTER TABLE aud.Infraestructura_aud  WITH CHECK ADD  CONSTRAINT FK_Infraestructura_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Infraestructura_aud CHECK CONSTRAINT FK_Infraestructura_aud_REV

ALTER TABLE aud.InhabilidadSubsidioFovis_aud  WITH CHECK ADD  CONSTRAINT FK_InhabilidadSubsidioFovis_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.InhabilidadSubsidioFovis_aud CHECK CONSTRAINT FK_InhabilidadSubsidioFovis_aud_REV

ALTER TABLE aud.IntegranteHogar_aud  WITH CHECK ADD  CONSTRAINT FK_IntegranteHogar_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntegranteHogar_aud CHECK CONSTRAINT FK_IntegranteHogar_aud_REV

ALTER TABLE aud.IntentoAfiliacion_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoAfiliacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoAfiliacion_aud CHECK CONSTRAINT FK_IntentoAfiliacion_aud_REV

ALTER TABLE aud.IntentoAfiliRequisito_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoAfiliRequisito_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoAfiliRequisito_aud CHECK CONSTRAINT FK_IntentoAfiliRequisito_aud_REV

ALTER TABLE aud.IntentoLegalizacionDesembolso_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoLegalizacionDesembolso_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoLegalizacionDesembolso_aud CHECK CONSTRAINT FK_IntentoLegalizacionDesembolso_aud_REV

ALTER TABLE aud.IntentoLegalizacionDesembolsoRequisito_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoLegalizacionDesembolsoRequisito_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoLegalizacionDesembolsoRequisito_aud CHECK CONSTRAINT FK_IntentoLegalizacionDesembolsoRequisito_aud_REV

ALTER TABLE aud.IntentoNovedad_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoNovedad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoNovedad_aud CHECK CONSTRAINT FK_IntentoNovedad_aud_REV

ALTER TABLE aud.IntentoNoveRequisito_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoNoveRequisito_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoNoveRequisito_aud CHECK CONSTRAINT FK_IntentoNoveRequisito_aud_REV

ALTER TABLE aud.IntentoPostulacion_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoPostulacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoPostulacion_aud CHECK CONSTRAINT FK_IntentoPostulacion_aud_REV

ALTER TABLE aud.IntentoPostulacionRequisito_aud  WITH CHECK ADD  CONSTRAINT FK_IntentoPostulacionRequisito_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.IntentoPostulacionRequisito_aud CHECK CONSTRAINT FK_IntentoPostulacionRequisito_aud_REV

ALTER TABLE aud.ItemChequeo_aud  WITH CHECK ADD  CONSTRAINT FK_ItemChequeo_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ItemChequeo_aud CHECK CONSTRAINT FK_ItemChequeo_aud_REV

ALTER TABLE aud.JefeHogar_aud  WITH CHECK ADD  CONSTRAINT FK_JefeHogar_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.JefeHogar_aud CHECK CONSTRAINT FK_JefeHogar_aud_REV

ALTER TABLE aud.LegalizacionDesembolso_aud  WITH CHECK ADD  CONSTRAINT FK_LegalizacionDesembolso_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.LegalizacionDesembolso_aud CHECK CONSTRAINT FK_LegalizacionDesembolso_aud_REV

ALTER TABLE aud.Licencia_aud  WITH CHECK ADD  CONSTRAINT FK_Licencia_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Licencia_aud CHECK CONSTRAINT FK_Licencia_aud_REV

ALTER TABLE aud.LicenciaDetalle_aud  WITH CHECK ADD  CONSTRAINT FK_LicenciaDetalle_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.LicenciaDetalle_aud CHECK CONSTRAINT FK_LicenciaDetalle_aud_REV

ALTER TABLE aud.LineaCobro_aud  WITH CHECK ADD  CONSTRAINT FK_LineaCobro_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.LineaCobro_aud CHECK CONSTRAINT FK_LineaCobro_aud_REV

ALTER TABLE aud.ListaEspecialRevision_aud  WITH CHECK ADD  CONSTRAINT FK_ListaEspecialRevision_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ListaEspecialRevision_aud CHECK CONSTRAINT FK_ListaEspecialRevision_aud_REV

ALTER TABLE aud.MedioCheque_aud  WITH CHECK ADD  CONSTRAINT FK_MedioCheque_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioCheque_aud CHECK CONSTRAINT FK_MedioCheque_aud_REV

ALTER TABLE aud.MedioConsignacion_aud  WITH CHECK ADD  CONSTRAINT FK_MedioConsignacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioConsignacion_aud CHECK CONSTRAINT FK_MedioConsignacion_aud_REV

ALTER TABLE aud.MedioDePago_aud  WITH CHECK ADD  CONSTRAINT FK_MedioDePago_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioDePago_aud CHECK CONSTRAINT FK_MedioDePago_aud_REV

ALTER TABLE aud.MedioEfectivo_aud  WITH CHECK ADD  CONSTRAINT FK_MedioEfectivo_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioEfectivo_aud CHECK CONSTRAINT FK_MedioEfectivo_aud_REV

ALTER TABLE aud.MedioPagoPersona_aud  WITH CHECK ADD  CONSTRAINT FK_MedioPagoPersona_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioPagoPersona_aud CHECK CONSTRAINT FK_MedioPagoPersona_aud_REV

ALTER TABLE aud.MedioPagoProyectoVivienda_aud  WITH CHECK ADD  CONSTRAINT FK_MedioPagoProyectoVivienda_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioPagoProyectoVivienda_aud CHECK CONSTRAINT FK_MedioPagoProyectoVivienda_aud_REV

ALTER TABLE aud.MediosPagoCCF_aud  WITH CHECK ADD  CONSTRAINT FK_MediosPagoCCF_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MediosPagoCCF_aud CHECK CONSTRAINT FK_MediosPagoCCF_aud_REV

ALTER TABLE aud.MedioTarjeta_aud  WITH CHECK ADD  CONSTRAINT FK_MedioTarjeta_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioTarjeta_aud CHECK CONSTRAINT FK_MedioTarjeta_aud_REV

ALTER TABLE aud.MedioTransferencia_aud  WITH CHECK ADD  CONSTRAINT FK_MedioTransferencia_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MedioTransferencia_aud CHECK CONSTRAINT FK_MedioTransferencia_aud_REV

ALTER TABLE aud.MotivoNoGestionCobro_aud  WITH CHECK ADD  CONSTRAINT FK_MotivoNoGestionCobro_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MotivoNoGestionCobro_aud CHECK CONSTRAINT FK_MotivoNoGestionCobro_aud_REV

ALTER TABLE aud.MovimientoAjusteAporte_aud  WITH CHECK ADD  CONSTRAINT FK_MovimientoAjusteAporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MovimientoAjusteAporte_aud CHECK CONSTRAINT FK_MovimientoAjusteAporte_aud_REV

ALTER TABLE aud.MovimientoAporte_aud  WITH CHECK ADD  CONSTRAINT FK_MovimientoAporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.MovimientoAporte_aud CHECK CONSTRAINT FK_MovimientoAporte_aud_REV

ALTER TABLE aud.Municipio_aud  WITH CHECK ADD  CONSTRAINT FK_Municipio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Municipio_aud CHECK CONSTRAINT FK_Municipio_aud_REV

ALTER TABLE aud.NotificacionDestinatario_aud  WITH CHECK ADD  CONSTRAINT FK_NotificacionDestinatario_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.NotificacionDestinatario_aud CHECK CONSTRAINT FK_NotificacionDestinatario_aud_REV

ALTER TABLE aud.NotificacionEnviada_aud  WITH CHECK ADD  CONSTRAINT FK_NotificacionEnviada_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.NotificacionEnviada_aud CHECK CONSTRAINT FK_NotificacionEnviada_aud_REV

ALTER TABLE aud.NotificacionPersonal_aud  WITH CHECK ADD  CONSTRAINT FK_NotificacionPersonal_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.NotificacionPersonal_aud CHECK CONSTRAINT FK_NotificacionPersonal_aud_REV

ALTER TABLE aud.NotificacionPersonalDocumento_aud  WITH CHECK ADD  CONSTRAINT FK_NotificacionPersonalDocumento_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.NotificacionPersonalDocumento_aud CHECK CONSTRAINT FK_NotificacionPersonalDocumento_aud_REV

ALTER TABLE aud.NovedadDetalle_aud  WITH CHECK ADD  CONSTRAINT FK_NovedadPila_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.NovedadDetalle_aud CHECK CONSTRAINT FK_NovedadPila_aud_REV

ALTER TABLE aud.OcupacionProfesion_aud  WITH CHECK ADD  CONSTRAINT FK_OcupacionProfesion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.OcupacionProfesion_aud CHECK CONSTRAINT FK_OcupacionProfesion_aud_REV

ALTER TABLE aud.Oferente_aud  WITH CHECK ADD  CONSTRAINT FK_Oferente_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Oferente_aud CHECK CONSTRAINT FK_Oferente_aud_REV

ALTER TABLE aud.OperadorInformacion_aud  WITH CHECK ADD  CONSTRAINT FK_OperadorInformacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.OperadorInformacion_aud CHECK CONSTRAINT FK_OperadorInformacion_aud_REV

ALTER TABLE aud.OperadorInformacionCcf_aud  WITH CHECK ADD  CONSTRAINT FK_OperadorInformacionCcf_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.OperadorInformacionCcf_aud CHECK CONSTRAINT FK_OperadorInformacionCcf_aud_REV

ALTER TABLE aud.PagoPeriodoConvenio_aud  WITH CHECK ADD  CONSTRAINT FK_PagoPeriodoConvenio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PagoPeriodoConvenio_aud CHECK CONSTRAINT FK_PagoPeriodoConvenio_aud_REV

ALTER TABLE aud.ParametrizacionCartera_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionCartera_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionCartera_aud CHECK CONSTRAINT FK_ParametrizacionCartera_aud_REV

ALTER TABLE aud.ParametrizacionCondicionesSubsidio_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionCondicionesSubsidio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionCondicionesSubsidio_aud CHECK CONSTRAINT FK_ParametrizacionCondicionesSubsidio_aud_REV

ALTER TABLE aud.ParametrizacionConveniosPago_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionConveniosPago_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionConveniosPago_aud CHECK CONSTRAINT FK_ParametrizacionConveniosPago_aud_REV

ALTER TABLE aud.ParametrizacionCriterioGestionCobro_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionCriterioGestionCobro_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionCriterioGestionCobro_aud CHECK CONSTRAINT FK_ParametrizacionCriterioGestionCobro_aud_REV

ALTER TABLE aud.ParametrizacionDesafiliacion_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionDesafiliacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionDesafiliacion_aud CHECK CONSTRAINT FK_ParametrizacionDesafiliacion_aud_REV

ALTER TABLE aud.ParametrizacionEjecucionProgramada_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionEjecucionProgramada_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionEjecucionProgramada_aud CHECK CONSTRAINT FK_ParametrizacionEjecucionProgramada_aud_REV

ALTER TABLE aud.ParametrizacionExclusiones_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionExclusiones_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionExclusiones_aud CHECK CONSTRAINT FK_ParametrizacionExclusiones_aud_REV

ALTER TABLE aud.ParametrizacionFiscalizacion_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionFiscalizacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionFiscalizacion_aud CHECK CONSTRAINT FK_ParametrizacionFiscalizacion_aud_REV

ALTER TABLE aud.ParametrizacionFOVIS_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionFOVIS_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionFOVIS_aud CHECK CONSTRAINT FK_ParametrizacionFOVIS_aud_REV

ALTER TABLE aud.ParametrizacionGestionCobro_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionGestionCobro_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionGestionCobro_aud CHECK CONSTRAINT FK_ParametrizacionGestionCobro_aud_REV

ALTER TABLE aud.ParametrizacionLiquidacionSubsidio_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionLiquidacionSubsidio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionLiquidacionSubsidio_aud CHECK CONSTRAINT FK_ParametrizacionLiquidacionSubsidio_aud_REV

ALTER TABLE aud.ParametrizacionMetodoAsignacion_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionMetodoAsignacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionMetodoAsignacion_aud CHECK CONSTRAINT FK_ParametrizacionMetodoAsignacion_aud_REV

ALTER TABLE aud.ParametrizacionModalidad_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionModalidad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionModalidad_aud CHECK CONSTRAINT FK_ParametrizacionModalidad_aud_REV

ALTER TABLE aud.ParametrizacionNovedad_aud  WITH CHECK ADD  CONSTRAINT FK_Novedad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionNovedad_aud CHECK CONSTRAINT FK_Novedad_aud_REV

ALTER TABLE aud.ParametrizacionPreventiva_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizacionPreventiva_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizacionPreventiva_aud CHECK CONSTRAINT FK_ParametrizacionPreventiva_aud_REV

ALTER TABLE aud.ParametrizaEnvioComunicado_aud  WITH CHECK ADD  CONSTRAINT FK_ParametrizaEnvioComunicado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ParametrizaEnvioComunicado_aud CHECK CONSTRAINT FK_ParametrizaEnvioComunicado_aud_REV

ALTER TABLE aud.Parametro_aud  WITH CHECK ADD  CONSTRAINT FK_Parametro_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Parametro_aud CHECK CONSTRAINT FK_Parametro_aud_REV

ALTER TABLE aud.Periodo_aud  WITH CHECK ADD  CONSTRAINT FK_Periodo_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Periodo_aud CHECK CONSTRAINT FK_Periodo_aud_REV

ALTER TABLE aud.PeriodoBeneficio_aud  WITH CHECK ADD  CONSTRAINT FK_PeriodoBeneficio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PeriodoBeneficio_aud CHECK CONSTRAINT FK_PeriodoBeneficio_aud_REV

ALTER TABLE aud.PeriodoExclusionMora_aud  WITH CHECK ADD  CONSTRAINT FK_PeriodoExclusionMora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PeriodoExclusionMora_aud CHECK CONSTRAINT FK_PeriodoExclusionMora_aud_REV

ALTER TABLE aud.PeriodoLiquidacion_aud  WITH CHECK ADD  CONSTRAINT FK_PeriodoLiquidacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PeriodoLiquidacion_aud CHECK CONSTRAINT FK_PeriodoLiquidacion_aud_REV

ALTER TABLE aud.Persona_aud  WITH CHECK ADD  CONSTRAINT FK_Persona_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Persona_aud CHECK CONSTRAINT FK_Persona_aud_REV

ALTER TABLE aud.PersonaDetalle_aud  WITH CHECK ADD  CONSTRAINT FK_PersonaDetalle_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PersonaDetalle_aud CHECK CONSTRAINT FK_PersonaDetalle_aud_REV

ALTER TABLE aud.PlantillaComunicado_aud  WITH CHECK ADD  CONSTRAINT FK_PlantillaComunicado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PlantillaComunicado_aud CHECK CONSTRAINT FK_PlantillaComunicado_aud_REV

ALTER TABLE aud.PostulacionFOVIS_aud  WITH CHECK ADD  CONSTRAINT FK_PostulacionFOVIS_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PostulacionFOVIS_aud CHECK CONSTRAINT FK_PostulacionFOVIS_aud_REV

ALTER TABLE aud.PrioridadDestinatario_aud  WITH CHECK ADD  CONSTRAINT FK_PrioridadDestinatario_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.PrioridadDestinatario_aud CHECK CONSTRAINT FK_PrioridadDestinatario_aud_REV

ALTER TABLE aud.ProductoNoConforme_aud  WITH CHECK ADD  CONSTRAINT FK_ProductoNoConforme_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ProductoNoConforme_aud CHECK CONSTRAINT FK_ProductoNoConforme_aud_REV

ALTER TABLE aud.ProyectoSolucionVivienda_aud  WITH CHECK ADD  CONSTRAINT FK_ProyectoSolucionVivienda_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ProyectoSolucionVivienda_aud CHECK CONSTRAINT FK_ProyectoSolucionVivienda_aud_REV

ALTER TABLE aud.RangoTopeValorSFV_aud  WITH CHECK ADD  CONSTRAINT FK_RangoTopeValorSFV_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RangoTopeValorSFV_aud CHECK CONSTRAINT FK_RangoTopeValorSFV_aud_REV

ALTER TABLE aud.RecursoComplementario_aud  WITH CHECK ADD  CONSTRAINT FK_RecursoComplementario_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RecursoComplementario_aud CHECK CONSTRAINT FK_RecursoComplementario_aud_REV

ALTER TABLE aud.RegistroEstadoAporte_aud  WITH CHECK ADD  CONSTRAINT FK_RegistroEstadoAporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RegistroEstadoAporte_aud CHECK CONSTRAINT FK_RegistroEstadoAporte_aud_REV

ALTER TABLE aud.RegistroNovedadFutura_aud  WITH CHECK ADD  CONSTRAINT FK_RegistroNovedadFutura_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RegistroNovedadFutura_aud CHECK CONSTRAINT FK_RegistroNovedadFutura_aud_REV

ALTER TABLE aud.RegistroPersonaInconsistente_aud  WITH CHECK ADD  CONSTRAINT FK_RegistroPersonaInconsistente_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RegistroPersonaInconsistente_aud CHECK CONSTRAINT FK_RegistroPersonaInconsistente_aud_REV

ALTER TABLE aud.RelacionGrupoFamiliar_aud  WITH CHECK ADD  CONSTRAINT FK_RelacionGrupoFamiliar_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RelacionGrupoFamiliar_aud CHECK CONSTRAINT FK_RelacionGrupoFamiliar_aud_REV

ALTER TABLE aud.Requisito_aud  WITH CHECK ADD  CONSTRAINT FK_Requisito_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Requisito_aud CHECK CONSTRAINT FK_Requisito_aud_REV

ALTER TABLE aud.RequisitoCajaClasificacion_aud  WITH CHECK ADD  CONSTRAINT FK_RequisitoCajaClasificacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RequisitoCajaClasificacion_aud CHECK CONSTRAINT FK_RequisitoCajaClasificacion_aud_REV

ALTER TABLE aud.ResultadoEjecucionProgramada_aud  WITH CHECK ADD  CONSTRAINT FK_ResultadoEjecucionProgramada_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ResultadoEjecucionProgramada_aud CHECK CONSTRAINT FK_ResultadoEjecucionProgramada_aud_REV

ALTER TABLE aud.RevisionEntidad  WITH CHECK ADD  CONSTRAINT FK_RevisionEntidad_reeRevision FOREIGN KEY(reeRevision)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RevisionEntidad CHECK CONSTRAINT FK_RevisionEntidad_reeRevision

ALTER TABLE aud.RolAfiliado_aud  WITH CHECK ADD  CONSTRAINT FK_RolAfiliado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RolAfiliado_aud CHECK CONSTRAINT FK_RolAfiliado_aud_REV

ALTER TABLE aud.RolContactoEmpleador_aud  WITH CHECK ADD  CONSTRAINT FK_RolContactoEmpleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.RolContactoEmpleador_aud CHECK CONSTRAINT FK_RolContactoEmpleador_aud_REV

ALTER TABLE aud.SedeCajaCompensacion_aud  WITH CHECK ADD  CONSTRAINT FK_SedeCajaCompensacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SedeCajaCompensacion_aud CHECK CONSTRAINT FK_SedeCajaCompensacion_aud_REV

ALTER TABLE aud.SitioPago_aud  WITH CHECK ADD  CONSTRAINT FK_SitioPago_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SitioPago_aud CHECK CONSTRAINT FK_SitioPago_aud_REV

ALTER TABLE aud.SocioEmpleador_aud  WITH CHECK ADD  CONSTRAINT FK_SocioEmpleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SocioEmpleador_aud CHECK CONSTRAINT FK_SocioEmpleador_aud_REV

ALTER TABLE aud.Solicitud_aud  WITH CHECK ADD  CONSTRAINT FK_Solicitud_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Solicitud_aud CHECK CONSTRAINT FK_Solicitud_aud_REV

ALTER TABLE aud.SolicitudAfiliaciEmpleador_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudAfiliaciEmpleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudAfiliaciEmpleador_aud CHECK CONSTRAINT FK_SolicitudAfiliaciEmpleador_aud_REV

ALTER TABLE aud.SolicitudAfiliacionPersona_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudAfiliacionPersona_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudAfiliacionPersona_aud CHECK CONSTRAINT FK_SolicitudAfiliacionPersona_aud_REV

ALTER TABLE aud.SolicitudAnalisisNovedadFovis_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudAnalisisNovedadFovis_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudAnalisisNovedadFovis_aud CHECK CONSTRAINT FK_SolicitudAnalisisNovedadFovis_aud_REV

ALTER TABLE aud.SolicitudAporte_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudAporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudAporte_aud CHECK CONSTRAINT FK_SolicitudAporte_aud_REV

ALTER TABLE aud.SolicitudAsignacion_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudAsignacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudAsignacion_aud CHECK CONSTRAINT FK_SolicitudAsignacion_aud_REV

ALTER TABLE aud.SolicitudAsociacionPersonaEntidadPagadora_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudAsociacionPersonaEntidadPagadora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudAsociacionPersonaEntidadPagadora_aud CHECK CONSTRAINT FK_SolicitudAsociacionPersonaEntidadPagadora_aud_REV

ALTER TABLE aud.SolicitudCierreRecaudo_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudCierreRecaudo_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudCierreRecaudo_aud CHECK CONSTRAINT FK_SolicitudCierreRecaudo_aud_REV

ALTER TABLE aud.SolicitudCorreccionAporte_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudCorreccionAporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudCorreccionAporte_aud CHECK CONSTRAINT FK_SolicitudCorreccionAporte_aud_REV

ALTER TABLE aud.SolicitudDesafiliacion_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudDesafiliacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudDesafiliacion_aud CHECK CONSTRAINT FK_SolicitudDesafiliacion_aud_REV

ALTER TABLE aud.SolicitudDevolucionAporte_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudDevolucionAporte_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudDevolucionAporte_aud CHECK CONSTRAINT FK_SolicitudDevolucionAporte_aud_REV

ALTER TABLE aud.SolicitudFiscalizacion_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudFiscalizacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudFiscalizacion_aud CHECK CONSTRAINT FK_SolicitudFiscalizacion_aud_REV

ALTER TABLE aud.SolicitudGestionCobroElectronico_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudGestionCobroElectronico_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudGestionCobroElectronico_aud CHECK CONSTRAINT FK_SolicitudGestionCobroElectronico_aud_REV

ALTER TABLE aud.SolicitudGestionCobroFisico_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudGestionCobroFisico_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudGestionCobroFisico_aud CHECK CONSTRAINT FK_SolicitudGestionCobroFisico_aud_REV

ALTER TABLE aud.SolicitudGestionCobroManual_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudGestionCobroManual_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudGestionCobroManual_aud CHECK CONSTRAINT FK_SolicitudGestionCobroManual_aud_REV

ALTER TABLE aud.SolicitudGestionCruce_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudGestionCruce_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudGestionCruce_aud CHECK CONSTRAINT FK_SolicitudGestionCruce_aud_REV

ALTER TABLE aud.SolicitudLegalizacionDesembolso_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudLegalizacionDesembolso_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudLegalizacionDesembolso_aud CHECK CONSTRAINT FK_SolicitudLegalizacionDesembolso_aud_REV

ALTER TABLE aud.SolicitudLiquidacionSubsidio_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudLiquidacionSubsidio_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudLiquidacionSubsidio_aud CHECK CONSTRAINT FK_SolicitudLiquidacionSubsidio_aud_REV

ALTER TABLE aud.SolicitudNovedad_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudNovedad_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudNovedad_aud CHECK CONSTRAINT FK_SolicitudNovedad_aud_REV

ALTER TABLE aud.SolicitudNovedadEmpleador_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudNovedadEmpleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudNovedadEmpleador_aud CHECK CONSTRAINT FK_SolicitudNovedadEmpleador_aud_REV

ALTER TABLE aud.SolicitudNovedadFovis_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudNovedadFovis_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudNovedadFovis_aud CHECK CONSTRAINT FK_SolicitudNovedadFovis_aud_REV

ALTER TABLE aud.SolicitudNovedadPersona_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudNovedadPersona_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudNovedadPersona_aud CHECK CONSTRAINT FK_SolicitudNovedadPersona_aud_REV

ALTER TABLE aud.SolicitudNovedadPersonaFovis_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudNovedadPersonaFovis_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudNovedadPersonaFovis_aud CHECK CONSTRAINT FK_SolicitudNovedadPersonaFovis_aud_REV

ALTER TABLE aud.SolicitudNovedadPila_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudNovedadPila_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudNovedadPila_aud CHECK CONSTRAINT FK_SolicitudNovedadPila_aud_REV

ALTER TABLE aud.SolicitudPostulacion_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudPostulacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudPostulacion_aud CHECK CONSTRAINT FK_SolicitudPostulacion_aud_REV

ALTER TABLE aud.SolicitudPreventiva_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudPreventiva_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudPreventiva_aud CHECK CONSTRAINT FK_SolicitudPreventiva_aud_REV

ALTER TABLE aud.SolicitudPreventivaAgrupadora_aud  WITH CHECK ADD  CONSTRAINT FK_SolicitudPreventivaAgrupadora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudPreventivaAgrupadora_aud CHECK CONSTRAINT FK_SolicitudPreventivaAgrupadora_aud_REV

ALTER TABLE aud.SolicitudVerificacionFovis_aud WITH CHECK ADD CONSTRAINT FK_SolicitudVerificacionFovis_aud_REV FOREIGN KEY(REV) 
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SolicitudVerificacionFovis_aud CHECK CONSTRAINT FK_SolicitudVerificacionFovis_aud_REV

ALTER TABLE aud.SucursalEmpresa_aud  WITH CHECK ADD  CONSTRAINT FK_SucursalEmpresa_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SucursalEmpresa_aud CHECK CONSTRAINT FK_SucursalEmpresa_aud_REV

ALTER TABLE aud.SucursaRolContactEmpleador_aud  WITH CHECK ADD  CONSTRAINT FK_SucursaRolContactEmpleador_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.SucursaRolContactEmpleador_aud CHECK CONSTRAINT FK_SucursaRolContactEmpleador_aud_REV

ALTER TABLE aud.Tarjeta_aud  WITH CHECK ADD  CONSTRAINT FK_Tarjeta_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Tarjeta_aud CHECK CONSTRAINT FK_Tarjeta_aud_REV

ALTER TABLE aud.TasasInteresMora_aud  WITH CHECK ADD  CONSTRAINT FK_TasasInteresMora_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.TasasInteresMora_aud CHECK CONSTRAINT FK_TasasInteresMora_aud_REV

ALTER TABLE aud.TipoInfraestructura_aud  WITH CHECK ADD  CONSTRAINT FK_TipoInfraestructura_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.TipoInfraestructura_aud CHECK CONSTRAINT FK_TipoInfraestructura_aud_REV

ALTER TABLE aud.TipoTenencia_aud  WITH CHECK ADD  CONSTRAINT FK_TipoTenencia_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.TipoTenencia_aud CHECK CONSTRAINT FK_TipoTenencia_aud_REV

ALTER TABLE aud.TipoVia_aud  WITH CHECK ADD  CONSTRAINT FK_TipoVia_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.TipoVia_aud CHECK CONSTRAINT FK_TipoVia_aud_REV

ALTER TABLE aud.Ubicacion_aud  WITH CHECK ADD  CONSTRAINT FK_Ubicacion_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Ubicacion_aud CHECK CONSTRAINT FK_Ubicacion_aud_REV

ALTER TABLE aud.UbicacionEmpresa_aud  WITH CHECK ADD  CONSTRAINT FK_UbicacionEmpresa_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.UbicacionEmpresa_aud CHECK CONSTRAINT FK_UbicacionEmpresa_aud_REV

ALTER TABLE aud.ValidacionProceso_aud  WITH CHECK ADD  CONSTRAINT FK_ValidacionProceso_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.ValidacionProceso_aud CHECK CONSTRAINT FK_ValidacionProceso_aud_REV

ALTER TABLE aud.VariableComunicado_aud  WITH CHECK ADD  CONSTRAINT FK_VariableComunicado_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.VariableComunicado_aud CHECK CONSTRAINT FK_VariableComunicado_aud_REV

ALTER TABLE aud.Visita_aud  WITH CHECK ADD  CONSTRAINT FK_Visita_aud_REV FOREIGN KEY(REV)
REFERENCES aud.Revision (revId)

ALTER TABLE aud.Visita_aud CHECK CONSTRAINT FK_Visita_aud_REV;

