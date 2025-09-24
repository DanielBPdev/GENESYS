--liquibase formatted sql

--changeset abaquero:01
--comment: Insercion de registros en la tabla ValidatorDefinition y ValidatorParamValue
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state) VALUES (2110325,0,0,55,2110106,null,null,211002,2,1);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111616,'Archivo Tipo IP - Registro 2 - Campo 8: Colombiano en el exterior',2110325,211005);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111617,'X',2110325,211006);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111618,'0',2110325,211007);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111619,'TIPO_2',2110325,211008);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111620,'IP28',2110325,211009);
