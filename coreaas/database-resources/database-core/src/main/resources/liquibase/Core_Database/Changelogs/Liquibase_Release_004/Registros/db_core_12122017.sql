--liquibase formatted sql

--changeset abaquero:01
--comment: Insercion de registros en la tabla ValidatorDefinition y ValidatorParamValue
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state) VALUES (2110322,0,0,22,2110087,null,null,211021,2,1);
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state) VALUES (2110323,0,0,22,2110088,null,null,211021,2,1);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111602,'Archivo Tipo I - Registro 1 - Campo 26: Número total de empleados',2110322,211098);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111603,'1',2110322,211099);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111604,'99999',2110322,211100);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111605,'TIPO_2',2110322,211101);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111606,'I126',2110322,211102);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111607,'Archivo Tipo I - Registro 1 - Campo 27: Número total de afiliados a la administradora',2110323,211098);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111608,'1',2110323,211099);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111609,'99999',2110323,211100);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111610,'TIPO_2',2110323,211101);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111611,'I127',2110323,211102);

--changeset abaquero:02
--comment: Insercion de registros en la tabla ValidatorDefinition y ValidatorParamValue
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state) VALUES (2110324,0,0,36,2110099,null,null,211045,2,1);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111612,'Archivo Tipo I - Registro 2 - Campo 1: Secuencia',2110324,211233);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111613,'valorSecuenciaEsperado',2110324,211234);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111614,'TIPO_2',2110324,211235);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111615,'I21',2110324,211236);

