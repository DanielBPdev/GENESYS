--liquibase formatted sql

--changeset mgiraldo:01 context:integracion runOnChange:true

UPDATE CONSTANTE SET cnsValor='CONFAHBT' where cnsNombre='CAJA_COMPENSACION_SITE' ;
UPDATE Parametro set prmValor='http://10.77.187.38:8282/alfresco' where prmNombre='ECM_HOST';
UPDATE Parametro SET prmValor='http://10.77.187.42/business-central' WHERE prmNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE Parametro set prmValor='http://10.77.187.5:8082/auth/' where prmNombre='IDM_SERVER_URL';
UPDATE Parametro set prmValor='http://10.77.187.5:8082/auth/realms/{realm}' where prmNombre='KEYCLOAK_ENDPOINT';

--changeset mgiraldo:02 context:pruebas runOnChange:true

UPDATE CONSTANTE SET cnsValor='CONFA' where cnsNombre='CAJA_COMPENSACION_SITE' ;
UPDATE Parametro set prmValor='http://10.77.187.38:8282/alfresco' where prmNombre='ECM_HOST';
UPDATE Parametro SET prmValor='http://10.77.187.51/business-central' WHERE prmNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE Parametro set prmValor='http://genesys_hbt.heinsohn.com.co:8082/auth/' where prmNombre='IDM_SERVER_URL';
UPDATE Parametro set prmValor='http://10.77.187.5:8082/auth/realms/{realm}' where prmNombre='KEYCLOAK_ENDPOINT';
--changeset mgiraldo:03 context:pruebas-asopagos runOnChange:true

UPDATE CONSTANTE SET cnsValor='CONFASOPAGOS' where cnsNombre='CAJA_COMPENSACION_SITE' ;
UPDATE Parametro set prmValor='http://10.77.187.38:8282/alfresco' where prmNombre='ECM_HOST';
UPDATE Parametro SET prmValor='http://10.77.187.3/business-central' WHERE prmNombre='BPMS_BUSINESS_CENTRAL_ENDPOINT';
UPDATE Parametro set prmValor='http://genesys.heinsohn.com.co:8082/auth/' where prmNombre='IDM_SERVER_URL';
UPDATE Parametro set prmValor='http://10.77.187.9:8082/auth/realms/{realm}' where prmNombre='KEYCLOAK_ENDPOINT';