--liquibase formatted sql

--changeset abaquero:01
--comment: insert tablas ValidatorCatalog, ValidatorDefinition, ValidatorParameter
INSERT ValidatorCatalog (id, className, description, name, scope, tenantId)  VALUES (211076, 'com.asopagos.pila.validadores.bloque2.ValidadorMoraInDependiente', 'Validador que ejecuta dias mora para InDep', 'Días mora InDep Validator', 'LINE', null);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110342, 0, 0, 116, null, null, 6, 211076, 2, 1);
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id) VALUES (211375, 'STRING', 'Campo de los días de mora', null, 'campoDiasMora', 211076);
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id) VALUES (211376, 'STRING', 'Nombre del campo a validar', null, 'nombreCampo', 211076);
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id) VALUES (211377, 'STRING', 'Tipo Error', null, 'tipoError', 211076);
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id) VALUES (211378, 'STRING', 'Código del Campo', null, 'codigoCampo', 211076);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111716, 'archivoIregistro3-2campo3', 2110342, 211375);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111717, 'Archivo Tipo I - Registro 3 Renglón 36 - Campo 3: Días de mora ', 2110342, 211376);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111718, 'TIPO_2', 2110342, 211377);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111719, 'I3_23', 2110342, 211378);

--changeset abaquero:02
--comment: insert tablas ValidatorCatalog, ValidatorDefinition, ValidatorParameter
UPDATE ValidatorDefinition SET state = 0 WHERE id IN (2110026,2110313,2110316);

--changeset clmarin:03
--comment: cambio tipo de dato campo sciResumen
ALTER TABLE SolicitudCierreRecaudo ALTER COLUMN sciResumen text;

--changeset clmarin:04
--comment: cambio tipo de dato campo sciResumen
ALTER TABLE aud.SolicitudCierreRecaudo_aud ALTER COLUMN sciResumen text;