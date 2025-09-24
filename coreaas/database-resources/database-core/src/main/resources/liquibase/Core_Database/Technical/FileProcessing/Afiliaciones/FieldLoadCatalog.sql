--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1221,'STRING','Tipo de identificación',NULL,NULL,'tipoIdentificacion',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1222,'STRING','Número de identificación',NULL,NULL,'numeroIdentificacion',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1223,'STRING','Primer nombre',NULL,NULL,'primerNombre',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1224,'STRING','Segundo nombre',NULL,NULL,'segundoNombre',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1225,'STRING','Primer apellido',NULL,NULL,'primerApellido',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1226,'STRING','Segundo apellido',NULL,NULL,'segundoApellido',6,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1227,'STRING','Fecha de expedición de documento de identidad',NULL,NULL,'fechaExpDocIdentidad',7,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1228,'STRING','Género',NULL,NULL,'genero',8,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1229,'STRING','Fecha de nacimiento',NULL,NULL,'fechaNacimiento',9,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1230,'STRING','Cabeza de hogar',NULL,NULL,'cabezaHogar',10,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1231,'STRING','Departamento',NULL,NULL,'departamento',11,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1232,'STRING','Municipio',NULL,NULL,'municipio',12,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1233,'STRING','Dirección de residencia',NULL,NULL,'direccionResidencia',13,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1234,'STRING','Teléfono fijo',NULL,NULL,'telefonoFijo',14,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1235,'STRING','Teléfono celular',NULL,NULL,'telefonoCelular',15,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1236,'STRING','Correo electrónico',NULL,NULL,'correoElectronico',16,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1237,'STRING','Autorización de envío de correo electrónico',NULL,NULL,'autorizacionEnvioCorreo',17,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1238,'STRING','Autoriza utilización de datos personales',NULL,NULL,'autorizaUsoDatosPersonales',18,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1239,'STRING','Reside en sector rural',NULL,NULL,'resideSectorRural',19,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1240,'STRING','Clase de trabajador',NULL,NULL,'claseTrabajador',20,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1241,'STRING','Fecha de inicio de labores con empleador',NULL,NULL,'fechaInicioLaboresConEmpleador',21,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1242,'STRING','Valor salario mensual',NULL,NULL,'valorSalarioMensual',22,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (1243,'STRING','Estado civil',NULL,NULL,'estadoCivil',23,0);