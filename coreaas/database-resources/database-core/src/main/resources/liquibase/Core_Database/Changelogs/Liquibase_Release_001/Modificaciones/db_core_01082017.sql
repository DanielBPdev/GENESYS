--liquibase formatted sql

--changeset mosanchez:01
--comment: Eliminacion registro de la tabla Constante
DELETE FROM Constante WHERE cnsNombre = 'CIUDAD_CCF' AND cnsValor = 'TUNJA' AND cnsId = 9
ALTER TABLE Constante ADD CONSTRAINT UK_Constante_cnsNombre UNIQUE (cnsNombre);
ALTER TABLE Constante ADD cnsDescripcion varchar(250) NULL; 

--changeset mosanchez:02
--comment: Actualizacion de registro en la tabla Parametro
UPDATE Parametro SET prmDescripcion = 'Tiempo de expiración del token generado para las personas y empleadores que se afilian vía web' WHERE prmNombre = '112_ABRIR_LINK_TIMER';
UPDATE Parametro SET prmDescripcion = 'Tiempo limite que tienen los empleadores que se afilian vía web para llenar el formulario de afiliación' WHERE prmNombre = '112_CORREGIR_INFORMACION_TIMER';
UPDATE Parametro SET prmDescripcion = 'Tiempo limite que tienen los empleadores que se afilian vía web para llenar el formulario de afiliación' WHERE prmNombre = '112_DILIGENCIAR_FORMULARIO_TIMER';
UPDATE Parametro SET prmDescripcion = 'Tiempo limite que tienen los empleadores que se afilian vía web para llenar el formulario de afiliación' WHERE prmNombre = '112_IMPRIMIR_FORMULARIO_TIMER';
UPDATE Parametro SET prmDescripcion = 'Tiempo limite que tienen los trabajadores dependientes para corregir la información de afiliación' WHERE prmNombre = '122_CORREGIR_INFORMACION_TIMER';
UPDATE Parametro SET prmDescripcion = 'Tiempo limite que tienen los trabajadores independientes para diligenciar el formulario de afiliación' WHERE prmNombre = '123_ABRIR_LINK_TIMER';
UPDATE Parametro SET prmDescripcion = 'Tiempo limite que tienen los trabajadores independientes para usar el token de acceso' WHERE prmNombre = '123_DILIGENCIAR_FORMULARIO_TIMER';
UPDATE Parametro SET prmDescripcion = 'Corresponde al texto que se le muestra al usuarios la primera vez que ingresa al sistema para que acepte las condiciones de uso del sistema.' WHERE prmNombre = 'ACEPTA_DERECHOS_ACCESO';
UPDATE Parametro SET prmDescripcion = 'Indica si la caja de compensación acepta estudiantes entre 19 y 22 años como beneficiarios' WHERE prmNombre = 'ACEPTACION_HIJOS_19_22_CON_ESTUDIO_CERTIFICADO';
UPDATE Parametro SET prmDescripcion = 'Endpoint donde se encuentran disponibles los servicios del bpms' WHERE prmNombre = 'BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Afiliación dependiente WEB' WHERE prmNombre = 'BPMS_PROCESS_AFIL_DEP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Afiliación de empleador WEB' WHERE prmNombre = 'BPMS_PROCESS_AFIL_EMP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Afiliación de independientes WEB' WHERE prmNombre = 'BPMS_PROCESS_AFIL_IND_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Afiliación de personas presencial' WHERE prmNombre = 'BPMS_PROCESS_AFIL_PERS_PRES_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Afiliación de empresas presencial' WHERE prmNombre = 'BPMS_PROCESS_DEPLOYMENT_ID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Novedades dependiente WEB' WHERE prmNombre = 'BPMS_PROCESS_NOVE_DEP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Novedades de empresas presencial' WHERE prmNombre = 'BPMS_PROCESS_NOVE_EMP_PRES_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Novedades de empresas WEB' WHERE prmNombre = 'BPMS_PROCESS_NOVE_EMP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Novedades de personas presencial' WHERE prmNombre = 'BPMS_PROCESS_NOVE_PER_PRES_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Novedades de personas WEB' WHERE prmNombre = 'BPMS_PROCESS_NOVE_PER_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Identificador de versión de proceso BPM para Pago Manual de Aportes' WHERE prmNombre = 'BPMS_PROCESS_PAGO_MAN_APOR_DEPLOYMENTID';
UPDATE Parametro SET prmDescripcion = 'Indica si la caja de compensación maneja sus etiquetas de manera preimpresa o generada. ESTE PARÁMETRO ESTÁ DEPRECADO YA QUE AHORA SOLO SE MANEJAN ETIQUETAS GENERADAS' WHERE prmNombre = 'CAJA_COMPENSACION_NUMERO_SOLICITUD_PRE_IMPRESO';
UPDATE Parametro SET prmDescripcion = 'Indica el número de dias antes de la expiración de la contraseña desde el cual el sistema debe empezar a notificar al usuario.' WHERE prmNombre = 'DIAS_NOTIFICACION_EXPIRACION_CONTRASENA';
UPDATE Parametro SET prmDescripcion = 'Endpoint donde se encuentran disponibles los servicios del gestor documental' WHERE prmNombre = 'ECM_HOST';
UPDATE Parametro SET prmDescripcion = 'Contraseña del usuario usado para conectarse con el gestor documental' WHERE prmNombre = 'ECM_PASSWORD';
UPDATE Parametro SET prmDescripcion = 'Nombre de usuario usado para conectarse con el gestor documental' WHERE prmNombre = 'ECM_USERNAME';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo del componente FileProcessing para afiliación de personas múltiples' WHERE prmNombre = 'FILE_DEFINITION_ID_AFILIACION_MULTIPLES';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo del componente FileProcessing para Arhivo de supervivencia encontrado' WHERE prmNombre = 'FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_ENCONTRADO_RNEC';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo del componente FileProcessing para Arhivo de supervivencia no encontrado' WHERE prmNombre = 'FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_NO_ENCONTRADO_RNEC';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo del componente FileProcessing para novedades múltiples' WHERE prmNombre = 'FILE_DEFINITION_ID_NOVEDADES_MULTIPLES';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivos del componente FileProcessing para Arhivo Solicitud de pago manual de aportes' WHERE prmNombre = 'FILE_DEFINITION_ID_PAGO_MANUAL_APORTES_DEP_IND';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo F' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_ARCHIVO_FINANCIERO';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo I-IR' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo IP-IPR' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_DETALLE_PENSIONADO';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo A-AR' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE';
UPDATE Parametro SET prmDescripcion = 'Identificador de definición de archivo de componente FileProcessing para Archivo PILA tipo AP-APR' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_INFORMACION_PENSIONADO';
UPDATE Parametro SET prmDescripcion = 'Nombre del cliente en el dominio "app_web" usado para generar tokens anonimos' WHERE prmNombre = 'IDM_CLIENT_WEB_CLIENT_ID';
UPDATE Parametro SET prmDescripcion = 'Contraseña del cliente de keycloak usado para obtener tokens de acceso para los usuarios anonimos en el dominio "app_web"' WHERE prmNombre = 'IDM_CLIENT_WEB_CLIENT_SECRET';
UPDATE Parametro SET prmDescripcion = 'Dominio en keycloak usado para generar tokens para los usuarios anonimos' WHERE prmNombre = 'IDM_CLIENT_WEB_DOMAIN_NAME';
UPDATE Parametro SET prmDescripcion = 'Nombre del cliente en el dominio de integración usado para generar tokens para los usuarios anonimos' WHERE prmNombre = 'IDM_INTEGRATION_WEB_CLIENT_ID';
UPDATE Parametro SET prmDescripcion = 'Contraseña del cliente de keycloak usado para obtener tokens de acceso en el dominio de integración para los usuarios anonimos.' WHERE prmNombre = 'IDM_INTEGRATION_WEB_CLIENT_SECRET';
UPDATE Parametro SET prmDescripcion = 'Dominio en keycloak donde se encuentran registrados los usuarios del sistema' WHERE prmNombre = 'IDM_INTEGRATION_WEB_DOMAIN_NAME';
UPDATE Parametro SET prmDescripcion = 'Nombre de la aplicación web usada por los usuarios anonimos' WHERE prmNombre = 'IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ID';
UPDATE Parametro SET prmDescripcion = 'Contraseña del  cliente de keycloak usado para obtener tokens en el dominio de integración' WHERE prmNombre = 'IDM_INTEGRATION_WEB_PUBLIC_CLIENT_SECRET';
UPDATE Parametro SET prmDescripcion = 'Endpoint donde se encuentran disponibles los servicios de keycloak' WHERE prmNombre = 'IDM_SERVER_URL';
UPDATE Parametro SET prmDescripcion = 'Endpoint donde se encuentran disponibles los servicios de keycloak' WHERE prmNombre = 'KEYCLOAK_ENDPOINT';
UPDATE Parametro SET prmDescripcion = 'Indica si el servidor de correo utilizado para el envío requiere autenticación o no' WHERE prmNombre = 'mail.smtp.auth';
UPDATE Parametro SET prmDescripcion = 'Se refiere el remitente en los envíos de correo de la solución' WHERE prmNombre = 'mail.smtp.from';
UPDATE Parametro SET prmDescripcion = 'Nombre del servidor SMTP usado para enviar los correos electronicos' WHERE prmNombre = 'mail.smtp.host';
UPDATE Parametro SET prmDescripcion = 'Contraseña de la cuenta de correo desde la cual se envian las notificaciones' WHERE prmNombre = 'mail.smtp.password';
UPDATE Parametro SET prmDescripcion = 'Puerto de conexión al servidor SMTP' WHERE prmNombre = 'mail.smtp.port';
UPDATE Parametro SET prmDescripcion = 'Cuando en una lista de correos existen direcciones válidas e inválidas, esta propiedad indica que si el mensaje se puede enviar a las direcciones válidas o si debe fallar el envío completo' WHERE prmNombre = 'mail.smtp.sendpartial';
UPDATE Parametro SET prmDescripcion = 'Indica si el transporte de los mensajes de correo electrónico requiere ser transmitido a través de protocolo seguro' WHERE prmNombre = 'mail.smtp.starttls.enable';
UPDATE Parametro SET prmDescripcion = 'Cuenta de correo desde la cual se envian las notificaciones' WHERE prmNombre = 'mail.smtp.user';
UPDATE Parametro SET prmDescripcion = 'Número de caracteres  que tiene una contraseña generada por el sistema' WHERE prmNombre = 'SEC_INITIAL_CHARACTERS_PASSWORD';
UPDATE Parametro SET prmDescripcion = 'Valor del salario minimo mensual legal vigente' WHERE prmNombre = 'SMMLV';
UPDATE Parametro SET prmDescripcion = 'Inidica la tarifa base que debe ser aplicada para los empleadores en el cálculo de los aportes' WHERE prmNombre = 'TARIFA_BASE_EMPLEADOR';
UPDATE Parametro SET prmDescripcion = 'Tiempo en el que una novedad que fue radicada por carga múltiple pase a ser desistida (sino se atiende desde la bandeja)' WHERE prmNombre = 'TIEMPO_DESISTIR_CARGA_MULTIPLE_NOVEDADES';
UPDATE Parametro SET prmDescripcion = 'Corresponde al tiempo limite para inactivar una cuenta de usuario despues de la desafiliación de un empleador' WHERE prmNombre = 'TIEMPO_INACTIVAR_CUENTA';
UPDATE Parametro SET prmDescripcion = 'Tiempo que tiene un empleador para volverse a afiliar sin realizar un nuevo tramite.' WHERE prmNombre = 'TIEMPO_REINTEGRO';

--changeset mosanchez:03
--comment: Actualizacion de registro en la tabla Constante
UPDATE Constante SET cnsDescripcion = 'Nombre del SITE en el ECM para la caja de compensación' WHERE cnsNombre = 'CAJA_COMPENSACION_SITE';
UPDATE Constante SET cnsDescripcion = 'Corresponde SALT para adicionar en las preguntas de los usuarios' WHERE cnsNombre = 'KEY';
UPDATE Constante SET cnsDescripcion = 'Es el id en la solución genesys para la caja de compensación actual' WHERE cnsNombre = 'CAJA_COMPENSACION_ID';
UPDATE Constante SET cnsDescripcion = 'Es el código en pila para la caja de compensación actual' WHERE cnsNombre = 'CAJA_COMPENSACION_CODIGO';
UPDATE Constante SET cnsDescripcion = 'Indica el medio de pago preferente para la caja de compensación actual' WHERE cnsNombre = 'CAJA_COMPENSACION_MEDIO_PAGO_PREFERENTE';
UPDATE Constante SET cnsDescripcion = 'Corresponde al nombre de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'NOMBRE_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde a la ciudad de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'CIUDAD_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al departamento de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'DEPARTAMENTO_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al teléfono de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'TELEFONO_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al tipo de identificación de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'TIPO_ID_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al número de identificación de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'NUMERO_ID_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al id en el ECM del logo de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'LOGO_DE_LA_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al sitio web de la caja de la caja de compensación actual. Utilizado en los comunicados' WHERE cnsNombre = 'WEB_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al id en el ECM del logo de la superintendencia de servicios' WHERE cnsNombre = 'LOGO_SUPERSERVICIOS';
UPDATE Constante SET cnsDescripcion = 'Corresponde al id en el ECM de la firma del responsable de la caja de compensación actual. Utilizado en comunicados' WHERE cnsNombre = 'FIRMA_RESPONSABLE_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al cargo que tiene el representante legal de la caja' WHERE cnsNombre = 'CARGO_RESPONSABLE_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde a la fecha en la que inició la ley 1429' WHERE cnsNombre = 'FECHA_INICIO_LEY_1429';
UPDATE Constante SET cnsDescripcion = 'Corresponde a la fecha en la que termina la ley 1429' WHERE cnsNombre = 'FECHA_FIN_LEY_1429';
UPDATE Constante SET cnsDescripcion = 'Corresponde a la fecha en la que termina la ley 590' WHERE cnsNombre = 'FECHA_FIN_LEY_590';
UPDATE Constante SET cnsDescripcion = 'Corresponde al username del representante legal de la caja' WHERE cnsNombre = 'RESPONSABLE_NOVEDADES_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al tiempo de expiración del enlace web' WHERE cnsNombre = 'TIEMPO_EXPIRACION_ENLACE';

--changeset jusanchez:04
--comment: Se elimina registro de la tabla Parametro
DELETE Parametro WHERE prmNombre='CODIGO_FINANCIERO';
ALTER TABLE Parametro ALTER COLUMN prmDescripcion varchar(250) NOT NULL; 

--changeset mosanchez:05
--comment:Actualizacion de registro en la tabla Constante
UPDATE Constante SET cnsDescripcion = 'Corresponde a la dirección de la caja de compensación actual. Utilizadoen los comunicados' WHERE cnsNombre = 'DIRECCION_CCF';
UPDATE Constante SET cnsDescripcion = 'Corresponde al cargo que tiene el representante legal de la caja' WHERE cnsNombre = 'RESPONSABLE_CCF';
ALTER TABLE Constante ALTER COLUMN cnsDescripcion varchar(250) NOT NULL; 

--changeset jocampo:06
--comment: Se agrega y elimina campos en la tabla Requisito
ALTER TABLE Requisito DROP COLUMN reqTipoRequisito;
ALTER TABLE Requisito ALTER COLUMN reqDescripcion varchar (200) NOT NULL;
DELETE FROM GrupoRequisito;
DBCC CHECKIDENT ('GrupoRequisito');
DELETE FROM RequisitoCajaClasificacion;
DBCC CHECKIDENT ('RequisitoCajaClasificacion');
DELETE FROM ItemChequeo;
DBCC CHECKIDENT ('ItemChequeo');
DELETE FROM Requisito;
DBCC CHECKIDENT ('Requisito');


