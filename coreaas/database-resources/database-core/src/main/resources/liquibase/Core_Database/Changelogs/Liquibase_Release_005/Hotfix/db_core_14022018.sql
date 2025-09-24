--liquibase formatted sql

--changeset abaquero:01
--comment:Insercion y actualizacion de registros en las tablas ValidatorDefinition y ValidatorParamValue
-- 0233402
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state)  VALUES (2110326,0,0,3,2110162,null,null,211010,2,1);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111625,'Archivo Tipo IP - Registro 1 - Campo 7: Tipo documento del pagador de pensiones',2110326,211043);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111626,'tipoDocumento',2110326,211044);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111627,'Nombre del archivo',2110326,211045);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111628,'TIPO_2',2110326,211046);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111629,'IP17',2110326,211047);

-- 0233465
UPDATE ValidatorParamValue set value = '5' where id = 2111069;

-- 0233497
-- 0233503
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state)  VALUES (2110327,0,0,33,2110190,null,null,211002,2,1);
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state)  VALUES (2110328,0,0,33,2110191,null,null,211002,2,1);
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state)  VALUES (2110329,0,0,33,2110192,null,null,211002,2,1);
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id,validatorprofile,state)  VALUES (2110330,0,0,33,2110193,null,null,211002,2,1);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111630,'Archivo Tipo IP - Registro 2 - Campo 15: ING: Ingreso',2110327,211005);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111631,'X',2110327,211006);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111632,'0',2110327,211007);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111633,'TIPO_2',2110327,211008);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111634,'IP215',2110327,211009);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111635,'Archivo Tipo IP - Registro 2 - Campo 16: RET: Retiro',2110328,211005);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111636,'X',2110328,211006);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111637,'0',2110328,211007);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111638,'TIPO_2',2110328,211008);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111639,'IP216',2110328,211009);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111640,'Archivo Tipo IP - Registro 2 - Campo 17: VSP: Variación permanente de la mesada pensional',2110329,211005);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111641,'X',2110329,211006);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111642,'0',2110329,211007);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111643,'TIPO_2',2110329,211008);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111644,'IP217',2110329,211009);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111645,'Archivo Tipo IP - Registro 2 - Campo 18: SUS: Suspensión',2110330,211005);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111646,'X',2110330,211006);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111647,'0',2110330,211007);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111648,'TIPO_2',2110330,211008);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111649,'IP218',2110330,211009);
