--liquibase formatted sql

--changeset dsuesca:01
--comment: Desactivación de validadores para tarifa, provisional
update ValidatorDefinition set state = 0 where id = 2110085;


--changeset dsuesca:02
--comment: Desactivación de validadores para tarifa, provisional
INSERT ProcessorCatalog
(id,className,description,name,scope,tenantId)
VALUES
(2,'com.asopagos.pila.Processor.ReemplazarValor','Clase que reemplaza un valor por otro'
,'Procesador de campo reemplazar valor'
,'FIELD'
,NULL)

INSERT ProcessorDefinition
(id,fieldDefinitionId,fieldDefinitionLoadId,lineDefinitionId,
lineDefinitionLoadId,processorOrder,processorCatalog_id)
VALUES
(2,NULL,2110012,NULL,
NULL,1,2);

INSERT ProcessorDefinition
(id,fieldDefinitionId,fieldDefinitionLoadId,lineDefinitionId,
lineDefinitionLoadId,processorOrder,processorCatalog_id)
VALUES
(3,NULL,2110046,NULL,
NULL,1,2)

INSERT ProcessorDefinition
(id,fieldDefinitionId,fieldDefinitionLoadId,lineDefinitionId,
lineDefinitionLoadId,processorOrder,processorCatalog_id)
VALUES
(4,NULL,2110073,NULL,
NULL,1,2)

INSERT ProcessorParameter
(id,dataType,description,mask,name,processorCatalog_id)
VALUES
(1,'STRING','Valor a ser reemplazado',NULL,'ACTUAL_VALOR',2)

INSERT ProcessorParameter
(id,dataType,description,mask,name,processorCatalog_id)
VALUES
(2,'STRING','Valor reemplazado',NULL,'NUEVO_VALOR',2)


--changeset dsuesca:03
--comment: Desactivación de validadores para tarifa, provisional
INSERT ProcessorParamValue
(id,value,processorDefinition_id,processorParameter_id)
VALUES
(1,'0',2,1)

INSERT ProcessorParamValue
(id,value,processorDefinition_id,processorParameter_id)
VALUES
(2,'0',3,1)

INSERT ProcessorParamValue
(id,value,processorDefinition_id,processorParameter_id)
VALUES
(3,'0',4,1)

INSERT ProcessorParamValue
(id,value,processorDefinition_id,processorParameter_id)
VALUES
(4,'001',2,2)

INSERT ProcessorParamValue
(id,value,processorDefinition_id,processorParameter_id)
VALUES
(5,'001',3,2)

INSERT ProcessorParamValue
(id,value,processorDefinition_id,processorParameter_id)
VALUES
(6,'001',4,2)

--changeset dsuesca:04
--comment: 
-- razonSocial,tipoDocApor,idApor,digitoVeri,codSucursal,nomSucursal,claseApor,naturaleza,tipoPersona,formaPresentacion,direccion,codCiudad,codDepartamento,telefono,fax,email,codOperador,periodo,tipoAportante,fechaMatricula,codDepartamento2,aporAcoge1429
UPDATE ValidatorParamValue
SET value = 'razonSocial,tipoDocApor,idApor,digitoVeri,codSucursal,nomSucursal,claseApor,naturaleza,tipoPersona,formaPresentacion,direccion,codCiudad,codDepartamento,telefono,fax,codOperador,periodo,tipoAportante,fechaMatricula,codDepartamento2,aporAcoge1429'
WHERE id = 2111158

-- 1-1,1-2,1-3,1-4,1-5,1-6,1-7,1-8,1-9,1-10,1-11,1-12,1-13,1-15,1-16,1-17,1-28,1-29,1-30,1-31,1-32,1-34
UPDATE ValidatorParamValue
SET value = '1-1,1-2,1-3,1-4,1-5,1-6,1-7,1-8,1-9,1-10,1-11,1-12,1-13,1-15,1-16,1-28,1-29,1-30,1-31,1-32,1-34'
WHERE id = 2111159

-- razonSocial,tipoDocApor,idApor,digitoVeri,codSucursal,nomSucursal,claseApor,naturaleza,tipoPersona,formaPresentacion,direccion,codCiudad,codDepartamento,telefono,fax,email,codOperador,periodo,tipoAportante,fechaMatricula,codDepartamento2,aporAcoge1429
UPDATE ValidatorParamValue
SET value = 'razonSocial,tipoDocApor,idApor,digitoVeri,codSucursal,nomSucursal,claseApor,naturaleza,tipoPersona,formaPresentacion,direccion,codCiudad,codDepartamento,telefono,fax,codOperador,periodo,tipoAportante,fechaMatricula,codDepartamento2,aporAcoge1429'
WHERE id = 2111164

-- 1-6,1-7,1-8,1-9,1-24,1-25,1-35,1-36,1-37,1-23,1-11,1-12,1-13,1-14,1-15,1-16,1-28,1-17,1-10,1-32,1-33,1-34
UPDATE ValidatorParamValue
SET value = '1-6,1-7,1-8,1-9,1-24,1-25,1-35,1-36,1-37,1-23,1-11,1-12,1-13,1-14,1-15,1-16,1-28,1-10,1-32,1-33,1-34'
WHERE id = 2111165

--changeset dsuesca:05
--comment: ajuste valor a reemplazar
update ProcessorParamValue
set value = '000'
where id in (1,2,3);

--changeset dsuesca:06
--comment: ajuste valor a reemplazar
UPDATE ValidatorParamValue
SET value = '1-6,1-7,1-8,1-9,1-24,1-25,1-35,1-36,1-37,1-23,1-11,1-12,1-13,1-14,1-15,1-28,1-17,1-10,1-32,1-33,1-34'
WHERE id = 2111165