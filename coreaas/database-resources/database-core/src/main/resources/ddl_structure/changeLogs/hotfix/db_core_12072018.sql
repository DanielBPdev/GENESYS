--liquibase formatted sql

--changeset abaquero:01
--comment: Acualizaciones tabla ValidatorDefinition
UPDATE ValidatorDefinition SET state=1 WHERE id=2110301;
UPDATE ValidatorParamValue SET value='I121' WHERE id=2110083;
UPDATE ValidatorParamValue SET value='TIPO_2' WHERE id=2111542;
UPDATE ValidatorParamValue SET value='TIPO_2' WHERE id=2111548;

--changeset abaquero:02
--comment: Insersiones tabla ValidatorDefinition y ValidatorParamValue
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110345, 0, 0, 39, null, null, 3, 211060, 2, 1);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111726, 'Archivo Tipo IP - Registro 2 - Campo 21: Fecha de inicio VSP', 2110345, 211316);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111727, 'archivoIPregistro2campo17', 2110345, 211317);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111728, 'archivoIPregistro2campo21', 2110345, 211318);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111729, 'No', 2110345, 211320);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111730, 'TIPO_2', 2110345, 211321);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111731, 'IP221', 2110345, 211322);

--changeset jocorrea:03
--comment: Se crea el campo epaFechaCreacion
ALTER TABLE EntidadPagadora ADD epaFechaCreacion DATE;
ALTER TABLE aud.EntidadPagadora_aud ADD epaFechaCreacion DATE;

--changeset abaquero:04
--comment: Insersiones tabla ValidatorDefinition y ValidatorParamValue
UPDATE ValidatorParamValue SET value='IP17' WHERE id=2110958;