--liquibase formatted sql

--changeset jocorrea:01
--comment: exclusiones FOVIS
INSERT ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES ('NOVEDAD_CAMBIO_OTROS_DATOS_JEFE_HOGAR', 'VALIDACION_JEFE_HOGAR_ACTIVO_6_FOVIS', 'NOVEDADES_FOVIS_ESPECIAL', 'ACTIVO', '1', 'JEFE_HOGAR', 1);
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_ESTADO_HOGAR_RECHAZADO_50_FOVIS',vapInversa = 1 WHERE vapBloque = 'RECHAZO_DE_POSTULACION' AND vapValidacion = 'VALIDACION_ESTADO_HOGAR_POSTULADO_49_FOVIS';

--changeset abaquero:02
--comment: actualizacion tabla ValidatorDefinition
UPDATE ValidatorDefinition SET state=1 WHERE id=2110195;

--changeset abaquero:03
--comment: actualizan y agregan configuraciones de validación de FileProcessing para archivos PILA
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  values (2110343, 0, 0, 45, 2110080, null, null, 211015, 2, 1);
INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder, fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id, validatorprofile, state)  values (2110344, 0, 0, 45, 2110082, null, null, 211015, 2, 1);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) values (2111720, 'fechaPagoPlanillaAsociada', 2110343, 211072);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) values (2111721, 'NA', 2110343, 211073);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) values (2111722, 'N/A', 2110343, 211074);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) values (2111723, 'numeroPlanillaAsociada', 2110344, 211072);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) values (2111724, 'NA', 2110344, 211073);
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) values (2111725, 'N/A', 2110344, 211074);
UPDATE dbo.ValidatorParamValue SET value='I119' WHERE id=2111706;
UPDATE dbo.ValidatorParamValue SET value='I121' WHERE id=2111705;

--changeset jocorrea:04
--comment: actualizacion tabla ValidatorDefinition
ALTER TABLE EjecucionProcesoAsincrono ADD epsIdProceso bigint;

--changeset jvelandia:05
--comment: actualizacion Constante
UPDATE CONSTANTE SET cnsValor='50.23.54.153' WHERE  cnsNombre='OPEN_OFFICE_END_POINT';

--changeset jocorrea:06
--comment: actualizacion tabla ValidatorDefinition
ALTER TABLE aud.EjecucionProcesoAsincrono_aud ADD epsIdProceso bigint;

--changeset abaquero:07
--comment: actualizacion tabla ValidatorParamValue
UPDATE ValidatorParamValue SET value='Archivo Tipo A - Registro 1 - Campo 8: Naturaleza jurídica' WHERE id=2110792;
UPDATE ValidatorParamValue SET value='A18' WHERE id=2110796;
UPDATE ValidatorParamValue SET value='Archivo Tipo A - Registro 1 - Campo 9: Tipo de persona' WHERE id=2110802;
UPDATE ValidatorParamValue SET value='A19' WHERE id=2110806;