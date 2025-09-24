--liquibase formatted sql

--changeset alopez:1 stripComments:false
/*22/09/2016-alopez-HU-112 tranversal*/
INSERT INTO PARAMETRO (prmNombre,prmValor) VALUES ('IDM_SERVER_URL','http://10.77.187.5:8082/auth/');
INSERT INTO PARAMETRO (prmNombre,prmValor) VALUES ('IDM_CLIENT_WEB_DOMAIN_NAME','app_web');
INSERT INTO PARAMETRO (prmNombre,prmValor) VALUES ('IDM_CLIENT_WEB_CLIENT_ID','clientes_web');
INSERT INTO PARAMETRO (prmNombre,prmValor) VALUES ('IDM_CLIENT_WEB_CLIENT_SECRET','=32e4816-0876-4711-add8-24da0215f663');


--changeset halzate:2 stripComments:false
/*22/09/2016-halzate-HU-331*/

DELETE FROM VariableComunicado;

--changeset halzate:3 stripComments:false
/*22/09/2016-halzate-HU-331*/
DBCC CHECKIDENT (VariableComunicado, RESEED,0);

--changeset halzate:4 stripComments:false
/*22/09/2016-halzate-HU-331*/
-- Creación de VariableComunicado
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razón social / Nombre','Nombre del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionEmpleador}','Tipo identificación empleador','Tipo de identificación del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${numeroIdentificacionEmpleador}','Número identificación empleador','Número de identificación del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadSede}','Ciudad sede','Ciudad de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razón social / Nombre','Nombre del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${direccionRepresentanteLegal}','Dirección  Representante Legal','Dirección capturada en Información de ubicación y correspondencia', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${telefonoRepresentanteLegal}','Teléfono Representante Legal','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadRepresentanteLegal}','Ciudad  Representante Legal','Municipio capturado en Información de ubicación y correspondencia', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de bienvenida para empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadSede}','Ciudad sede','Ciudad de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razón social / Nombre','Nombre del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${direccionRepresentanteLegal}','Dirección Representante Legal','Dirección capturada en Información de ubicación y correspondencia', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${telefonoRepresentanteLegal}','Teléfono  Representante Legal','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadRepresentanteLegal}','Ciudad  Representante Legal','Municipio capturado en Información de ubicación y correspondencia', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${sede}','Sede ','Nombre de la sede dónde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${direccionSede}','Dirección Sede','Dirección de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${parametroValorCuotaMonetariaUrbano}','Parámetro Valor cuota monetaria urbano','Valor de la cuota monetaria urbana parametrizada', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${parametroValorCuotaMonetariaRural}','Parámetro Valor cuota monetaria rural','Valor de la cuota monetaria rural parametrizada', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Carta de aceptación de empleador') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empresa') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empresa') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razón social / Nombre','Nombre del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empresa') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionEmpleador}','Tipo identificación empleador','Tipo de identificación del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empresa') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${numeroIdentificacionEmpleador}','Número identificación empleador','Número de identificación del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empresa') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de Ubicación de la Caja de Compensación','DEPARTAMENTO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de Ubicación de la Caja de Compensación','CIUDAD_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la Caja de Compensación','DIRECCION_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Número telefónico de la Caja de Compensación','TELEFONO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${tipoIdCcf}','Tipo ID CCF','Tipo documento de la Caja de Compensación','TIPO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${numeroIdCcf}','Número ID CCF','Número de documento de la Caja de Compensación','NUMERO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Dirección del sitio web de la Caja de Compensación','WEB_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;

--changeset halzate:5 stripComments:false
/*22/09/2016-halzate-HU-331*/

DELETE FROM Constante;

--changeset halzate:6 stripComments:false
/*22/09/2016-halzate-HU-331*/
DBCC CHECKIDENT (Constante, RESEED,0);

--changeset halzate:7 stripComments:false
/*22/09/2016-halzate-HU-331*/

INSERT Constante (cnsNombre, cnsValor) VALUES ('CAJA_COMPENSACION_ID', '1');
INSERT Constante (cnsNombre, cnsValor) VALUES ('CAJA_COMPENSACION_MEDIO_PAGO_PREFERENTE', '');
INSERT Constante (cnsNombre, cnsValor) VALUES ('NOMBRE_CCF','Caja de Compensación Familiar de Caldas');
INSERT Constante (cnsNombre, cnsValor) VALUES ('LOGO_DE_LA_CCF','####');
INSERT Constante (cnsNombre, cnsValor) VALUES ('DEPARTAMENTO_CCF','Caldas');
INSERT Constante (cnsNombre, cnsValor) VALUES ('CIUDAD_CCF','Manizales');
INSERT Constante (cnsNombre, cnsValor) VALUES ('DIRECCION_CCF','Cra 25 Calle 50');
INSERT Constante (cnsNombre, cnsValor) VALUES ('TELEFONO_CCF','57(6)8783111');
INSERT Constante (cnsNombre, cnsValor) VALUES ('TIPO_ID_CCF','NIT');
INSERT Constante (cnsNombre, cnsValor) VALUES ('NUMERO_ID_CCF','890806490');
INSERT Constante (cnsNombre, cnsValor) VALUES ('CAJA_COMPENSACION_SITE','CONFAHBT');
INSERT Constante (cnsNombre, cnsValor) VALUES ('KEY','euNuWPuienBrq2t/lEnpCw3f3IpFuQRA');


--changeset mgiraldo:8 stripComments:false 
/*22/09/2016-mgiraldo*/

ALTER TABLE PlantillaComunicado ADD CONSTRAINT UK_PlantillaComunicado_pcoEtiqueta UNIQUE (pcoEtiqueta);



