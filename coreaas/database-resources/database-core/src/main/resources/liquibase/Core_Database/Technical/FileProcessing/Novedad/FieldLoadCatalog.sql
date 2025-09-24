--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1244,'STRING','Tipo de identificación del trabajador',NULL,NULL,'tipoIdentificacion',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1245,'STRING','Número de identificación del trabajador',NULL,NULL,'numeroIdentificacion',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1246,'STRING','Primer nombre',NULL,NULL,'primerNombre',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1247,'STRING','Segundo nombre',NULL,NULL,'segundoNombre',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1248,'STRING','Primer apellido',NULL,NULL,'primerApellido',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1249,'STRING','Segundo apellido',NULL,NULL,'segundoApellido',6,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1250,'STRING','Departamento',NULL,NULL,'departamento',7,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1251,'STRING','Municipio',NULL,NULL,'municipio',8,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1252,'STRING','Dirección de residencia',NULL,NULL,'direccionResidencia',9,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1253,'STRING','Teléfono fijo',NULL,NULL,'telefonoFijo',10,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1254,'STRING','Teléfono celular',NULL,NULL,'telefonoCelular',11,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1255,'STRING','Correo electrónico',NULL,NULL,'correoElectronico',12,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1256,'STRING','Reside en sector rural',NULL,NULL,'resideSectorRural',13,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1257,'STRING','Clase de trabajador',NULL,NULL,'claseTrabajador',14,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1258,'STRING','Fecha de inicio de labores con empleador',NULL,NULL,'fechaLaboresEmpleador',15,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1259,'STRING','Valor salario mensual',NULL,NULL,'valorSalarioMensual',16,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1260,'STRING','Cambio de nivel educativo',NULL,NULL,'cambioNivelEducativo',17,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1261,'STRING','Condición casa propia',NULL,NULL,'condicionCasaPropia',18,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1262,'STRING','Código postal',NULL,NULL,'codigoPostal',19,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1263,'STRING','Cambio de tipo de salario',NULL,NULL,'cambioTipoSalario',20,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1264,'STRING','Cargo u oficio desempeñado',NULL,NULL,'cambioOficio',21,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1265,'STRING','Tipo de contrato',NULL,NULL,'tipoContrato',22,0);


