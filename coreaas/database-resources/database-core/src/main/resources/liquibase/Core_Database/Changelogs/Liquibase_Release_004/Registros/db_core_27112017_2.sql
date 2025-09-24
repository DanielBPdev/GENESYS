--liquibase formatted sql

--changeset dasanchez:01 runOnChange:true
--comment: Insercion de registros para la configuracion de parametros
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('CAJA_COMPENSACION_MEDIO_PAGO_PREFERENTE','TARJETA',0,'CAJA_COMPENSACION','Indica  el medio de pago preferente para la caja de compensación actual');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FECHA_INICIO_LEY_1429','1293580800000',0,'VALOR_GLOBAL_NEGOCIO','Corresponde a la fecha en la que inició la ley 1429');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FECHA_FIN_LEY_1429','1419984000000',0,'VALOR_GLOBAL_NEGOCIO','Corresponde a la fecha en la que termina la ley 1429');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FECHA_FIN_LEY_590','963187200000',0,'VALOR_GLOBAL_NEGOCIO','Corresponde a la fecha en la que termina la ley 590');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('TIEMPO_REINTEGRO','2592000000',0,'EJECUCION_TIMER','Tiempo que tiene un empleador para volverse a afiliar sin realizar un nuevo tramite.');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('mail.smtp.from','NULL',0,'MAIL_SMTP','Se refiere el remitente en los envíos de correo de la solución');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('mail.smtp.sendpartial','TRUE',0,'MAIL_SMTP','Cuando en una lista de correos existen direcciones válidas e inválidas, esta propiedad indica que si el mensaje se puede enviar a las direcciones válidas o si debe fallar el envío completo');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('mail.smtp.auth','TRUE',0,'MAIL_SMTP','Indica si el servidor de correo utilizado para el envío requiere autenticación o no');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('mail.smtp.starttls.enable','TRUE',0,'MAIL_SMTP','Indica si el transporte de los mensajes de correo electrónico requiere ser transmitido a través de protocolo seguro');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('112_DILIGENCIAR_FORMULARIO_TIMER','5m',0,'EJECUCION_TIMER','TIEMPO LIMITE QUE TIENEN LOS EMPLEADORES QUE SE AFILIAN VÍA WEB PARA LLENAR EL FORMULARIO DE AFILIACIÓN.');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('CAJA_COMPENSACION_NUMERO_SOLICITUD_PRE_IMPRESO','NO',0,'CAJA_COMPENSACION','Indica si la caja de compensación maneja sus etiquetas de manera preimpresa o generada. ESTE PARÁMETRO ESTÁ DEPRECADO YA QUE AHORA SOLO SE MANEJAN ETIQUETAS GENERADAS');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('SMMLV','737717',0,'VALOR_GLOBAL_NEGOCIO','Valor del salario minimo mensual legal vigente');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('TIEMPO_DESISTIR_CARGA_MULTIPLE_NOVEDADES','2592000000',0,'EJECUCION_TIMER','Tiempo en el que una novedad que fue radicada por carga múltiple pase a ser desistida (sino se atiende desde la bandeja)');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('DIAS_NOTIFICACION_EXPIRACION_CONTRASENA','2',0,'EJECUCION_TIMER','Indica el número de dias antes de la expiración de la contraseña desde el cual el sistema debe empezar a notificar al usuario.');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('ACEPTA_DERECHOS_ACCESO','He leído y acepto los términos y condiciones de uso',1,'VALOR_GLOBAL_NEGOCIO','Corresponde al texto que se le muestra al usuarios la primera vez que ingresa al sistema para que acepte las condiciones de uso del sistema.');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('ACEPTACION_HIJOS_19_22_CON_ESTUDIO_CERTIFICADO','SI',0,'CAJA_COMPENSACION','Indica si la caja de compensación acepta estudiantes entre 19 y 22 años como beneficiarios');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('TARIFA_BASE_EMPLEADOR','0.04',0,'CAJA_COMPENSACION','Inidica la tarifa base que debe ser aplicada para los empleadores en el cálculo de los aportes');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('TIEMPO_INACTIVAR_EMPLEADOR','2592000000',0,'EJECUCION_TIMER','Parametro que indica los días para Desafiliar un Empleador');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('EDAD_RETIRO_BENEFICIARIO','18',0,'CAJA_COMPENSACION','Edad de retiro de beneficiario');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('CATEGORIA_CAMBIO_BENEFICIARIO','C',0,'CAJA_COMPENSACION','Categoria que aplica al beneficiario al cumplir la edad parametrizada.Debe existir en el sistema');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('EDAD_CAMBIO_CATEGORIA_BENEFICIARIO','25',0,'CAJA_COMPENSACION','Edad de beneficiario para el cambio de categoria. Debe ser mayor o igual a 18');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('MESES_MORA_DESAFILIACION','3',0,'EJECUCION_TIMER','Parametro que indica el número de meses en mora permitidos para Desafiliar');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('NUMERO_REGISTROS_LECTURA_AFILIACION_MULTIPLE','50',0,'FILE_DEFINITION',' Parámetro que contiene el número de registros de lectura de afiliación de personas dependientes');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('TIEMPO_MINIMO_PLANILLA','30',0,'VALOR_GLOBAL_NEGOCIO','Tiempo mínimo para la presentación de la planilla respecto a la fecha actual');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('REINTENTOS_PERSISTENCIA_CONTENIDO','2',0,'FILE_DEFINITION','Cantidad de reintentos automáticos para realizar la persistencia del contenido de los archivos');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('MARGEN_TOLERANCIA_DIFERENCIA_MORA_APORTE_PILA','0.05',0,'VALOR_GLOBAL_NEGOCIO','Margen de tolerancia en la comparación del valor de mora');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('MULTIPLICADOR_VALOR_MINIMO_SALARIO_INTEGRAL','13',0,'VALOR_GLOBAL_NEGOCIO','Multiplicador para determinar el valor mínimo para un salario integral');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('PORC_MAX_SOL_VIVIENDA_SFV','90',0,'VALOR_GLOBAL_NEGOCIO','Porcentaje máximo admisible del valor de la solución de vivienda para el cálculo del Subsidio Familiar de Vivienda');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('CANT_SMLMV_SAN_ANDRES_SFV','22',0,'VALOR_GLOBAL_NEGOCIO','Cantidad de SMLMV del SFV cuando el departamento del proyecto de solución de vivienda es Archipiélago de San Andrés, Providencia y Santa Catalina');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('CANT_SMLMV_SAN_ANDRES_HOGAR','4',0,'VALOR_GLOBAL_NEGOCIO','Cantidad de SMLMV del hogar cuando el departamento del proyecto de solución de vivienda es Archipiélago de San Andrés, Providencia y Santa Catalina');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST','10.77.187.3',0,'FTP_ARCHIVOS_DESCUENTO','Nombre del host correspondiente al servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_PUERTO','21',0,'FTP_ARCHIVOS_DESCUENTO','Puerto del servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO','XKjfmbRakYpbOFOtnSbHsA==',0,'FTP_ARCHIVOS_DESCUENTO','Usuario del servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_CONTRASENA','xPnc/tNg4jD9DEMLrTSY1g==',0,'FTP_ARCHIVOS_DESCUENTO','Constraseña para el usuario del servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('NIT_CATASTRO_ANTIOQUIA','8909002860',0,'VALOR_GLOBAL_NEGOCIO','Numero de identificacion de la entidad CATASTRO ANTIOQUIA');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('NIT_CATASTRO_BOGOTA','8999990619',0,'VALOR_GLOBAL_NEGOCIO','Numero de identificacion de la entidad CATASTRO BOGOTA');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('NIT_CATASTRO_CALI','8903990113',0,'VALOR_GLOBAL_NEGOCIO','Numero de identificacion de la entidad CATASTRO CALI');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('NIT_CATASTRO_MEDELLIN','890905211',0,'VALOR_GLOBAL_NEGOCIO','Numero de identificacion de la entidad CATASTRO MEDELLIN');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('NIT_IGAC','8999990049',0,'VALOR_GLOBAL_NEGOCIO','Numero de identificacion de la entidad IGAC');

--changeset dasanchez:02 runOnChange:true 
--comment: Insercion de registros para la configuracion de constantes
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('KEY','euNuWPuienBrq2t/lEnpCw3f3IpFuQRA','Corresponde  SALT para adicionar en las preguntas de los usuarios');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_CLIENT_WEB_CLIENT_SECRET','cd5f3ee7-39ad-4275-9e8c-cfeeae478280','Contraseña del cliente de keycloak usado para obtener tokens de acceso para los usuarios anonimos en el dominio "app_web"');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_INTEGRATION_WEB_CLIENT_SECRET','409e5a38-fc8c-475d-8f26-78d1fc1044b1','Contraseña del cliente de keycloak usado para obtener tokens de acceso en el dominio de integración para los usuarios anonimos.');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_INTEGRATION_WEB_PUBLIC_CLIENT_SECRET','a66e9334-fb15-4f7a-8858-3584d2c13588','Contraseña del  cliente de keycloak usado para obtener tokens en el dominio de integración');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('SEC_INITIAL_CHARACTERS_PASSWORD','8','Número de caracteres  que tiene una contraseña generada por el sistema');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_INTEGRATION_WEB_DOMAIN_NAME','Integracion','Dominio en keycloak donde se encuentran registrados los usuarios del sistema');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_CLIENT_WEB_DOMAIN_NAME','app_web','Dominio en keycloak usado para generar tokens para los usuarios anonimos');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_CLIENT_WEB_CLIENT_ID','clientes_web','Nombre del cliente en el dominio "app_web" usado para generar tokens anonimos');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_INTEGRATION_WEB_CLIENT_ID','realm-management','Nombre del cliente en el dominio de integración usado para generar tokens para los usuarios anonimos');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ID','clientes_web','Nombre de la aplicación web usada por los usuarios anonimos');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_DEPLOYMENT_ID','com.asopagos.coreaas.bpm.afiliacion_empresas_presencial:Afiliacion_empresas_presencial:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Afiliación de empresas presencial');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_AFIL_DEP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion_dependiente_web:afiliacion_dependiente_web:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Afiliación dependiente WEB');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_AFIL_IND_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion_independientes_web:afiliacion_independientes_web:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Afiliación de independientes WEB');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_AFIL_EMP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion_empresas_web:afiliacion_empresas_web:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Afiliación de empleador WEB');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_AFIL_PERS_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion-personas-presencial:afiliacion_personas_presencial:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Afiliación de personas presencial');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_NOVE_EMP_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_empleador_presencial:novedades_empleador_presencial:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Novedades de empresas presencial');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_NOVE_EMP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_empleador_web:novedades_empleador_web:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Novedades de empresas WEB');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_NOVE_PER_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_persona_presencial:novedades_persona_presencial:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Novedades de personas presencial');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_NOVE_DEP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_dependiente_web:novedades_dependiente_web:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Novedades dependiente WEB');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_NOVE_PER_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_persona_web:novedades_persona_web:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Novedades de personas WEB');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_AFILIACION_MULTIPLES','1220','Identificador de definición de archivo del componente FileProcessing para afiliación de personas múltiples');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDADES_MULTIPLES','1221','Identificador de definición de archivo del componente FileProcessing para novedades múltiples');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE','1','Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo A-AR');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_PILA_INFORMACION_PENSIONADO','2','Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo AP-APR');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE','3','Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo I-IR');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_PILA_DETALLE_PENSIONADO','4','Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo IP-IPR');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_PILA_ARCHIVO_FINANCIERO','5','Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo F');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_ENCONTRADO_RNEC','1222','Identificador de definición de archivo del componente FileProcessing para Arhivo de supervivencia encontrado');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_NO_ENCONTRADO_RNEC','1223','Identificador de definición de archivo del componente FileProcessing para Arhivo de supervivencia no encontrado');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_PAGO_MAN_APOR_DEPLOYMENTID','com.asopagos.coreaas.bpm.pago_manual_aportes:pago_manual_aportes:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Pago Manual de Aportes');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_PAGO_MANUAL_APORTES_DEP_IND','1212','Identificador de definición de archivos del componente FileProcessing para Arhivo Solicitud de pago manual de aportes');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('CANCELAR_SOLICITUD','/AfiliacionEmpleadoresWebCompositeService/rest/solicitudAfiliacionEmpleador/cancelarSolicitudTimeout','Ruta del servicio de cancelar solicitud por tiempo de expiración');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_DEVOLUCION_APORTES_DEPLOYMENTID','com.asopagos.coreaas.bpm.devolucion_aportes:devolucion_aportes:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Devolución de Aportes');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_CORRECCION_APORTES_DEPLOYMENTID','com.asopagos.coreaas.bpm.correcciones_aportes:correcciones_aportes:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Corrección de Aportes');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('CANCELAR_SOLICITUD_PERSONAS','/AfiliacionPersonasWebCompositeService/rest/afiliacionesPersonasWeb/cancelarSolicitudTimeout','Ruta del servicio de cancelar solicitud de afiliacion de personas por tiempo de expiración');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_EMPLEADOR','1228','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion datos empleador');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_AFILIADO','1229','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion datos afiliado');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_BENEFICIARIO','1230','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion datos beneficiarios');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CERTIFICADO_ESCOLAR','1231','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion informacion certificado escolar beneficiarios');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PENSIONADO','1232','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion informacion pensionados');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_NOVEDADES_ARCHIVOS_ACTUALIZACION_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_archivos_actualizacion:novedades_archivos_actualizacion:0.0.2-SNAPSHOT','Indicador de proceso BPM para el registro de novedades por archivo de actualizacion');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_GESTION_PREVENTIVA_CARTERA_DEPLOYMENTID','com.asopagos.coreaas.bpm.gestion_preventiva_cartera:gestion_preventiva_cartera:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Gestión Preventiva de Cartera');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_SUBSIDIO_MONETARIO_MASIVO_DEPLOYMENTID','com.asopagos.coreaas.bpm.subsidio_monetario_masivo:subsidio_monetario_masivo:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para subsidio monetario masivo');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_FISCALIZACION_CARTERA_DEPLOYMENTID','com.asopagos.coreaas.bpm.fiscalizacion_cartera:fiscalizacion_cartera:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para fiscalización de cartera');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_POST_FOVIS_PREC_DEPLOYMENTID','com.asopagos.coreaas.bpm.postulacion_fovis_presencial:postulacion_fovis_presencial:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Postulación FOVIS Presencial');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_POST_FOVIS_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.postulacion_fovis_web:postulacion_fovis_web:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para Postulación FOVIS Web');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_ENTIDADES_DESCUENTO','1233','identificador de definición de archivo de componente FileProcessing para cargue de archivo de entidad de descuento');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CEDULAS','321','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_AFILIADOS','322','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_BENEFICAIRIO','323','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATANT','324','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATBOG','325','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATCALI','326','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_CATMED','327','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_IGAC','328','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_NUEVO_HOGAR','329','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_FECHAS_CORTE','330','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_SISBEN','331','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_UNIDOS','332','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_FOVIS_CARGUE_CRUCE_REUNIDOS','333','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruce FOVIS');

--changeset rlopez:03 runOnChange:true 
--comment: Insercion de registro en la tabla constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('FILE_DEFINITION_ID_RESULTADOS_ENTIDAD_DESCUENTO','1234','Identificador  de definición de archivo de componente FileProcessing para generación de archivo de entidad de descuento');

--changeset flopez:04 runOnChange:true
--comment: Insercion de registro en la tabla constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_ASIGNACION_FOVIS_DEPLOYMENTID','com.asopagos.coreaas.bpm.asignacion_fovis:asignacion_fovis:0.0.2-SNAPSHOT','Identificador  de versión de proceso BPM para Asignación FOVIS');

--changeset jocampo:05 context:integracion runOnChange:true
--comment: Insercion de registro en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS','/subsidios/resultadosdescuentos/integracion/',0,'FTP_ARCHIVOS_DESCUENTO','Ruta  de los archivos de descuento generados');

--changeset jocampo:06 context:pruebas runOnChange:true
--comment: Insercion de registro en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS','/subsidios/resultadosdescuentos/pruebas/',0,'FTP_ARCHIVOS_DESCUENTO','Ruta  de los archivos de descuento generados');

--changeset fvasquez:07 runOnChange:true 
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_1','512.89*(1/B1)',0,'VALOR_GLOBAL_NEGOCIO','Fórmula  dinámica para cálculo de valor "Parte 1" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_1_B1','totalIngresosHogar/39880',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "B1" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_2','19.09*B2',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "Parte 2" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_3','40.71*B3',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "Parte 3" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_4','424*(B4/10000)',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "Parte 4" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_4_B4','totalAhorroPrevio/39980',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "B4" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_5','1.63*B5',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "Parte 5" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_6','46.93*B6',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "Parte 6" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_7','puntaje*1.03',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "Parte 7" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_8','puntaje*1.15',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de valor "Parte 8" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_PARTE_8_PF','puntaje*1(1+(1-SFVCalculado/SFVTope))',0,'VALOR_GLOBAL_NEGOCIO','Fórmula dinámica para cálculo de puntaje final "Parte 8" - HU-048');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('PUNTAJE_FOVIS_EDAD_MINIMA','65',0,'VALOR_GLOBAL_NEGOCIO','Edad mínima de un integrante de hogar para aumento de puntaje FOVIS - HU-048');

--changeset flopez:08 runOnChange:true
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('322_TIEMPO_CORRECCION_FOVIS_WEB','1d',0,'EJECUCION_TIMER','Tiempo  limite que tienen los usuarios WEB que se postulan en FOVIS para la corrección de información');

--changeset jocampo:09 context:asopagosSubsidio runOnChange:true
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('CAJA_COMPENSACION_DEPTO_ID','19','0','CAJA_COMPENSACION','Código  DANE del departamento de la CCF');

--changeset rlopez:10 runOnChange:true
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_PROTOCOLO','SFTP',0,'FTP_ARCHIVOS_DESCUENTO','Protocolo  de conexión al servidor FTP');

--changeset mosanchez:11 context:asopagos_funcional runOnChange:true
--comment:Insercion de registros y actualizacion en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS','/SFTP',0,'FTP_ARCHIVOS_DESCUENTO','Ruta  de los archivos de descuento en el servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS','/SFTP',0,'FTP_ARCHIVOS_DESCUENTO','Ruta de los archivos de descuento generados');
UPDATE Parametro SET prmValor = '52.177.189.64' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST';
UPDATE Parametro SET prmValor = '22' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_PUERTO';
UPDATE Parametro SET prmValor = 'SFTP' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO';
UPDATE Parametro SET prmValor = '84cVTIRf' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_CONTRASENA';

--changeset mosanchez:12 context:asopagos_confa runOnChange:true
--comment:Insercion de registros y actualizacion en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS','/SFTP',0,'FTP_ARCHIVOS_DESCUENTO','Ruta  de los archivos de descuento en el servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS','/SFTP',0,'FTP_ARCHIVOS_DESCUENTO','Ruta de los archivos de descuento generados');
UPDATE Parametro SET prmValor = '13.68.82.153' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST';
UPDATE Parametro SET prmValor = '22' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_PUERTO';
UPDATE Parametro SET prmValor = 'SFTP' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO';
UPDATE Parametro SET prmValor = '84cVTIRf' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_CONTRASENA';
UPDATE Parametro SET prmValor = '/SFTP' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS';
UPDATE Parametro SET prmValor = '/SFTP' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS';

--changeset mosanchez:13 context:asopagos_subsidio runOnChange:true
--comment:Insercion de registros y actualizacion en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubcategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS','/SFTP',0,'FTP_ARCHIVOS_DESCUENTO','Ruta  de los archivos de descuento en el servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS','/SFTP',0,'FTP_ARCHIVOS_DESCUENTO','Ruta de los archivos de descuento generados');
UPDATE Parametro SET prmValor = '52.177.189.64' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST';
UPDATE Parametro SET prmValor = '22' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_PUERTO';
UPDATE Parametro SET prmValor = 'SFTP' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO';
UPDATE Parametro SET prmValor = '84cVTIRf' WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_CONTRASENA';
