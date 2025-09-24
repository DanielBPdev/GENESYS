--liquibase formatted sql

--changeset abaquero:01
--comment: Insercion de registros en la tabla ValidatorDefinition y ValidatorParamValue
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorCatalog_id) VALUES (211357,'STRING','Campo Tipo Documento',null,'campoTipoDocumento',211051);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111621,'archivoIregistro1campo7',2110010,211357);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111622,'archivoAregistro1campo2',2110129,211357);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111623,'archivoAPregistro1campo2',2110159,211357);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111624,'archivoIPregistro1campo7',2110182,211357);
