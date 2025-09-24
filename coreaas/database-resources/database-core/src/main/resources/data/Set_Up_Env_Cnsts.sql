--liquibase formatted sql

--changeSET heinsohn:01 context:integracion runOnChange:true
--comment: Configuracion del ambiente ambiente integración COMFABOY
UPDATE CONSTANTE SET cnsValor='http://10.77.187.51/business-central' WHERE cnsNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE CONSTANTE SET cnsValor='CCF10' WHERE cnsNombre='CAJA_COMPENSACION_CODIGO';
UPDATE CONSTANTE SET cnsValor='CONFAHBT' WHERE cnsNombre='CAJA_COMPENSACION_SITE';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.38:8282/alfresco' WHERE cnsNombre='ECM_HOST';
UPDATE CONSTANTE SET cnsValor='Asdf1234$' WHERE cnsNombre='ECM_PASSWORD';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.5:8082/auth/' WHERE cnsNombre='IDM_SERVER_URL';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.5:8082/auth/realms/{realm}' WHERE cnsNombre='KEYCLOAK_ENDPOINT';


--changeSET heinsohn:02 context:pruebas runAlways:true runOnChange:true
--comment: Configuracion del ambiente pruebas HBT COMFACAUCA 
UPDATE CONSTANTE SET cnsValor='http://10.77.187.42/business-central' WHERE cnsNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE CONSTANTE SET cnsValor='CCF14' WHERE cnsNombre='CAJA_COMPENSACION_CODIGO';
UPDATE CONSTANTE SET cnsValor='CONFA' WHERE cnsNombre='CAJA_COMPENSACION_SITE';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.38:8282/alfresco' WHERE cnsNombre='ECM_HOST';
UPDATE CONSTANTE SET cnsValor='Asdf1234$' WHERE cnsNombre='ECM_PASSWORD';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.5:8082/auth/' WHERE cnsNombre='IDM_SERVER_URL';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.5:8082/auth/realms/{realm}' WHERE cnsNombre='KEYCLOAK_ENDPOINT';


--changeSET heinsohn:04 context:integracion-asopagos 
--comment: Configuracion del ambiente integracion asopagos integracionasopagos
UPDATE CONSTANTE SET cnsValor='http://10.77.187.13/business-central' WHERE cnsNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE CONSTANTE SET cnsValor='CCF04' WHERE cnsNombre='CAJA_COMPENSACION_CODIGO';
UPDATE CONSTANTE SET cnsValor='integracionasopagos' WHERE cnsNombre='CAJA_COMPENSACION_SITE';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.38:8282/alfresco' WHERE cnsNombre='ECM_HOST';
UPDATE CONSTANTE SET cnsValor='Asdf1234$' WHERE cnsNombre='ECM_PASSWORD';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.5:8082/auth/' WHERE cnsNombre='IDM_SERVER_URL';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.5:8082/auth/realms/{realm}' WHERE cnsNombre='KEYCLOAK_ENDPOINT';


--changeSET heinsohn:05 context:asopagos_confa runOnChange:true
--comment: Configuracion del ambiente asopagos_confa
UPDATE CONSTANTE SET cnsValor='http://10.0.1.4/business-central' WHERE cnsNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE CONSTANTE SET cnsValor='CCF11' WHERE cnsNombre='CAJA_COMPENSACION_CODIGO';
UPDATE CONSTANTE SET cnsValor='FUNCIONALES' WHERE cnsNombre='CAJA_COMPENSACION_SITE';
UPDATE CONSTANTE SET cnsValor='http://10.0.1.5:8080/alfresco' WHERE cnsNombre='ECM_HOST';
UPDATE CONSTANTE SET cnsValor='SK-[:gq{1S' WHERE cnsNombre='ECM_PASSWORD';
UPDATE CONSTANTE SET cnsValor='http://pruebasconfa.eastus2.cloudapp.azure.com:8082/auth/' WHERE cnsNombre='IDM_SERVER_URL';
UPDATE CONSTANTE SET cnsValor='http://pruebasconfa.eastus2.cloudapp.azure.com:8082/auth/realms/{realm}' WHERE cnsNombre='KEYCLOAK_ENDPOINT';


--changeSET heinsohn:06 context:asopagos_funcional runOnChange:true
--comment: Configuracion del ambiente asopagos_funcional
UPDATE CONSTANTE SET cnsValor='http://10.0.1.8/business-central' WHERE cnsNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE CONSTANTE SET cnsValor='CCF11' WHERE cnsNombre='CAJA_COMPENSACION_CODIGO';
UPDATE CONSTANTE SET cnsValor='FUNCIONALES' WHERE cnsNombre='CAJA_COMPENSACION_SITE';
UPDATE CONSTANTE SET cnsValor='http://10.0.1.5:8080/alfresco' WHERE cnsNombre='ECM_HOST';
UPDATE CONSTANTE SET cnsValor='SK-[:gq{1S' WHERE cnsNombre='ECM_PASSWORD';
UPDATE CONSTANTE SET cnsValor='http://comfa.eastus2.cloudapp.azure.com:8082/auth/' WHERE cnsNombre='IDM_SERVER_URL';
UPDATE CONSTANTE SET cnsValor='http://comfa.eastus2.cloudapp.azure.com:8082/auth/realms/{realm}' WHERE cnsNombre='KEYCLOAK_ENDPOINT';


--changeSET heinsohn:07 context:asopagosSubsidio runOnChange:true
--comment: Configuracion del ambiente ASOPAGOSSUBSIDIO
UPDATE CONSTANTE SET cnsValor='http://40.84.32.126/business-central' WHERE cnsNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE CONSTANTE SET cnsValor='CCF10' WHERE cnsNombre='CAJA_COMPENSACION_CODIGO';
UPDATE CONSTANTE SET cnsValor='FUNCIONALES' WHERE cnsNombre='CAJA_COMPENSACION_SITE';
UPDATE CONSTANTE SET cnsValor='http://10.77.187.38:8282/alfresco' WHERE cnsNombre='ECM_HOST';
UPDATE CONSTANTE SET cnsValor='Asdf1234$' WHERE cnsNombre='ECM_PASSWORD';
UPDATE CONSTANTE SET cnsValor='http://40.84.32.126:8082/auth/' WHERE cnsNombre='IDM_SERVER_URL';
UPDATE CONSTANTE SET cnsValor='http://40.84.32.126:8082/auth/realms/{realm}' WHERE cnsNombre='KEYCLOAK_ENDPOINT';


--changeSET heinsohn:08 context:asopagos_subsidio runOnChange:true
--comment: Configuracion del ambiente asopagos_funcional - Asopagos
UPDATE CONSTANTE SET cnsValor='http://10.0.1.10/business-central' WHERE cnsNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE CONSTANTE SET cnsValor='CCF11' WHERE cnsNombre='CAJA_COMPENSACION_CODIGO';
UPDATE CONSTANTE SET cnsValor='FUNCIONALES' WHERE cnsNombre='CAJA_COMPENSACION_SITE';
UPDATE CONSTANTE SET cnsValor='http://10.0.1.5:8080/alfresco' WHERE cnsNombre='ECM_HOST';
UPDATE CONSTANTE SET cnsValor='SK-[:gq{1S' WHERE cnsNombre='ECM_PASSWORD';
UPDATE CONSTANTE SET cnsValor='http://10.0.1.10:8082/auth/' WHERE cnsNombre='IDM_SERVER_URL';
UPDATE CONSTANTE SET cnsValor='http://10.0.1.10:8082/auth/realms/{realm}' WHERE cnsNombre='KEYCLOAK_ENDPOINT';

