--liquibase formatted sql

--changeset jusanchez:01
--comment: Se agregan nuevos campos en la tabla Parametro
ALTER TABLE Parametro ADD prmCargaInicio bit 
ALTER TABLE Parametro ADD prmSubCategoriaParametro varchar(23)

--changeset jusanchez:02
--comment: Actualizacion en la tabla Parametro
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE Parametro SET prmSubCategoriaParametro='ECM', prmCargaInicio='false' WHERE prmNombre = 'ECM_HOST';
UPDATE Parametro SET prmSubCategoriaParametro='ECM', prmCargaInicio='false' WHERE prmNombre = 'ECM_USERNAME';
UPDATE Parametro SET prmSubCategoriaParametro='ECM', prmCargaInicio='false' WHERE prmNombre = 'ECM_PASSWORD';
UPDATE Parametro SET prmSubCategoriaParametro='KEYCLOAK', prmCargaInicio='false' WHERE prmNombre = 'KEYCLOAK_ENDPOINT';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_SERVER_URL';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_CLIENT_WEB_CLIENT_SECRET';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_INTEGRATION_WEB_CLIENT_SECRET';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_INTEGRATION_WEB_PUBLIC_CLIENT_SECRET';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_EMPLEADORES_WEB_CLIENT_SECRET';
UPDATE Parametro SET prmSubCategoriaParametro='VALOR_GLOBAL_TECNICO', prmCargaInicio='false' WHERE prmNombre = 'SEC_INITIAL_CHARACTERS_PASSWORD';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.user';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.password';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.host';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.port';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = 'TIEMPO_REINTEGRO';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_INTEGRATION_WEB_DOMAIN_NAME';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_EMPLEADORES_WEB_DOMAIN_NAME';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_CLIENT_WEB_DOMAIN_NAME';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_CLIENT_WEB_CLIENT_ID';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_INTEGRATION_WEB_CLIENT_ID';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_EMPLEADORES_WEB_CLIENT_ID';
UPDATE Parametro SET prmSubCategoriaParametro='IDM', prmCargaInicio='false' WHERE prmNombre = 'IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_DEPLOYMENT_ID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_AFIL_DEP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_AFIL_IND_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_AFIL_EMP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_AFIL_PERS_PRES_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.from';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.sendpartial';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.auth';
UPDATE Parametro SET prmSubCategoriaParametro='MAIL_SMTP', prmCargaInicio='false' WHERE prmNombre = 'mail.smtp.starttls.enable';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = '112_ABRIR_LINK_TIMER';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = '112_DILIGENCIAR_FORMULARIO_TIMER';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = '112_IMPRIMIR_FORMULARIO_TIMER';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = '112_CORREGIR_INFORMACION_TIMER';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = '122_CORREGIR_INFORMACION_TIMER';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = '123_ABRIR_LINK_TIMER';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = '123_DILIGENCIAR_FORMULARIO_TIMER';
UPDATE Parametro SET prmSubCategoriaParametro='CAJA_COMPENSACION', prmCargaInicio='false' WHERE prmNombre = 'CAJA_COMPENSACION_NUMERO_SOLICITUD_PRE_IMPRESO';
UPDATE Parametro SET prmSubCategoriaParametro='VALOR_GLOBAL_NEGOCIO', prmCargaInicio='false' WHERE prmNombre = 'SMMLV';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_NOVE_EMP_PRES_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_NOVE_EMP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_NOVE_PER_PRES_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_NOVE_DEP_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='BPM_PROCESS', prmCargaInicio='false' WHERE prmNombre = 'BPMS_PROCESS_NOVE_PER_WEB_DEPLOYMENTID';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_AFILIACION_MULTIPLES';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_NOVEDADES_MULTIPLES';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_INFORMACION_PENSIONADO';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_DETALLE_PENSIONADO';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_PILA_ARCHIVO_FINANCIERO';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = 'TIEMPO_DESISTIR_CARGA_MULTIPLE_NOVEDADES';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_ENCONTRADO_RNEC';
UPDATE Parametro SET prmSubCategoriaParametro='FILE_DEFINITION', prmCargaInicio='false' WHERE prmNombre = 'FILE_DEFINITION_ID_ARCHIVO_SUPERVIVENCIA_NO_ENCONTRADO_RNEC';
UPDATE Parametro SET prmSubCategoriaParametro='EJECUCION_TIMER', prmCargaInicio='false' WHERE prmNombre = 'DIAS_NOTIFICACION_EXPIRACION_CONTRASENA';

