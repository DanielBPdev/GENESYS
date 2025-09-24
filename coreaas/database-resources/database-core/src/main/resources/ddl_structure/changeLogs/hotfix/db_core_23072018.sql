--liquibase formatted sql

--changeset dsuesca:01
--comment: Se eliminan datos de liquibase para ejectur nuevamente scripts de comments
DELETE FROM DATABASECHANGELOG WHERE FILENAME = 'ddl_structure/core_comments.sql';

--changeset jocorrea:02
--comment: Se eliminan datos de liquibase para ejectur nuevamente scripts de comments
DELETE ValidacionProceso WHERE vapBloque = 'HABILITACION_POSTULACION_RECHAZADA' AND vapValidacion = 'VALIDACION_POSTULACION_RECHAZADA_51_FOVIS';

--changeset abaquero:03
--comment: Insert tablas ValidatorDefinition y ValidatorParamValue
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110346, 0, 0, 1, null, null, 1, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110347, 0, 0, 1, null, null, 2, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110348, 0, 0, 1, null, null, 3, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110349, 0, 0, 1, null, null, 5, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110350, 0, 0, 1, null, null, 6, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110351, 0, 0, 1, null, null, 7, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110352, 0, 0, 1, null, null, 8, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110353, 0, 0, 1, null, null, 10, 211037, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110354, 0, 0, 1, null, null, 11, 211037, 1, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110355, 0, 0, 1, null, null, 12, 211037, 1, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110356, 0, 0, 1, null, null, 15, 211037, 1, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  VALUES (2110357, 0, 0, 1, null, null, 16, 211037, 2, 1);

INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111733, 'tipoArchivo', 2110346, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111734, 'NA', 2110346, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111735, 'N/A', 2110346, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111736, 'tipoArchivo', 2110347, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111737, 'NA', 2110347, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111738, 'N/A', 2110347, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111739, 'tipoArchivo', 2110348, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111740, 'NA', 2110348, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111741, 'N/A', 2110348, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111742, 'tipoArchivo', 2110349, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111743, 'NA', 2110349, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111744, 'N/A', 2110349, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111745, 'tipoArchivo', 2110350, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111746, 'NA', 2110350, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111747, 'N/A', 2110350, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111748, 'tipoArchivo', 2110351, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111749, 'NA', 2110351, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111750, 'N/A', 2110351, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111751, 'tipoArchivo', 2110352, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111752, 'NA', 2110352, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111753, 'N/A', 2110352, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111754, 'tipoArchivo', 2110353, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111755, 'NA', 2110353, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111756, 'N/A', 2110353, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111757, 'tipoArchivo', 2110354, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111758, 'NA', 2110354, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111759, 'N/A', 2110354, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111760, 'tipoArchivo', 2110355, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111761, 'NA', 2110355, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111762, 'N/A', 2110355, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111763, 'tipoArchivo', 2110356, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111764, 'NA', 2110356, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111765, 'N/A', 2110356, 211199);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111766, 'tipoArchivo', 2110357, 211197);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111767, 'NA', 2110357, 211198);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111768, 'N/A', 2110357, 211199);
