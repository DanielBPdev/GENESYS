--liquibase formatted sql

--changeset heinsohn:01
--comment: Insercion inicial PARAMETRO
--Valores dependientes de cada ambiente de instalación.
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_BUSINESS_CENTRAL_ENDPOINT','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('ECM_HOST','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('ECM_USERNAME','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('ECM_PASSWORD','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('KEYCLOAK_ENDPOINT','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_SERVER_URL','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_CLIENT_WEB_CLIENT_SECRET','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_CLIENT_SECRET','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_PUBLIC_CLIENT_SECRET','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_EMPLEADORES_WEB_CLIENT_SECRET','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('SEC_INITIAL_CHARACTERS_PASSWORD','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.user','<ENVIRONMENT>');  
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.password','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.host','<ENVIRONMENT>');  
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.port','<ENVIRONMENT>');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('TIEMPO_REINTEGRO','<ENVIRONMENT>');


--Parametrización módulo de seguridad
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_DOMAIN_NAME','Integracion');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_EMPLEADORES_WEB_DOMAIN_NAME','empleadores_domain');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_CLIENT_WEB_DOMAIN_NAME','app_web');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_CLIENT_WEB_CLIENT_ID','clientes_web');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_CLIENT_ID','realm-management');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_EMPLEADORES_WEB_CLIENT_ID','realm-management');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_PUBLIC_CLIENT_ID','clientes_web');

--BPMS Settings
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_DEPLOYMENT_ID','com.asopagos.coreaas.bpm.afiliacion_empresas_presencial:Afiliacion_empresas_presencial:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_AFIL_DEP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion_dependiente_web:afiliacion_dependiente_web:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_AFIL_IND_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion_independientes_web:afiliacion_independientes_web:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_AFIL_EMP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion_empresas_web:afiliacion_empresas_web:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_AFIL_PERS_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.afiliacion-personas-presencial:afiliacion_personas_presencial:0.0.2-SNAPSHOT');

--SMTP Server Settings  
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.from',null);
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.sendpartial','TRUE');  
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.auth','TRUE');  
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('mail.smtp.starttls.enable','TRUE');  

INSERT PARAMETRO (prmNombre,prmValor) VALUES ('112_ABRIR_LINK_TIMER','1d');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('112_DILIGENCIAR_FORMULARIO_TIMER','1d');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('112_IMPRIMIR_FORMULARIO_TIMER','1d');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('112_CORREGIR_INFORMACION_TIMER','1d');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('122_CORREGIR_INFORMACION_TIMER','1d');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('123_ABRIR_LINK_TIMER','1d');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('123_DILIGENCIAR_FORMULARIO_TIMER','1d');

--BUSINESS Settings
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('CAJA_COMPENSACION_NUMERO_SOLICITUD_PRE_IMPRESO','NO');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('SMMLV','737717');

INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_NOVE_EMP_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_empleador_presencial:novedades_empleador_presencial:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_NOVE_EMP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_empleador_web:novedades_empleador_web:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_NOVE_PER_PRES_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_persona_presencial:novedades_persona_presencial:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_NOVE_DEP_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_dependiente_web:novedades_dependiente_web:0.0.2-SNAPSHOT');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('BPMS_PROCESS_NOVE_PER_WEB_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_persona_web:novedades_persona_web:0.0.2-SNAPSHOT');

INSERT PARAMETRO (prmNombre,prmValor) VALUES ('FILE_DEFINITION_ID_AFILIACION_MULTIPLES','1220');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('FILE_DEFINITION_ID_NOVEDADES_MULTIPLES','1221');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('FILE_DEFINITION_ID_PILA_INFORMACION_INDEPENDIENTE_DEPENDIENTE','1');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('FILE_DEFINITION_ID_PILA_INFORMACION_PENSIONADO','2');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('FILE_DEFINITION_ID_PILA_DETALLE_INDEPENDIENTE_DEPENDIENTE','3');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('FILE_DEFINITION_ID_PILA_DETALLE_PENSIONADO','4');
INSERT PARAMETRO (prmNombre,prmValor) VALUES ('FILE_DEFINITION_ID_PILA_ARCHIVO_FINANCIERO','5');

