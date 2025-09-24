--liquibase formatted sql

--changeset jocorrea:01
--comment: Insercion de registros en las tablas Parametro,FileDefinitionLoadType,FileDefinitionLoad,LineLoadCatalog,LineDefinitionLoad,ValidatorCatalog,ValidatorDefinition,FieldLoadCatalog,FieldDefinitionLoad

----241 - Estructura de los archivos de respuesta de empleadores
--Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES  ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_EMPLEADOR','1228','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion datos empleador');

--FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,name,description) VALUES (1228,'Archivo actualizacion datos empleador','Plantilla archivos de repuesta para actualizacion datos empleador');

--FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) VALUES (1228,'.','Archivo actualizacion datos empleador',NULL,NULL,0,NULL,NULL,0,1228);

--LineLoadCatalog
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) VALUES (1228,'com.asopagos.novedades.personas.web.load.ActualizacionInformacionEmpleadoresPersistLine','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

--LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) VALUES (1228,NULL,NULL,1,NULL,1,1228,1228);

--ValidatorCatalog
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) VALUES (1228,'com.asopagos.novedades.personas.web.load.validator.ActualizacionInformacionEmpleadoresLineValidator','Validador de línea de carga actualizacion datos empleador','Validador de línea de carga actualizacion datos empleador','LINE',NULL);

--ValidatorDefinition
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) VALUES (1228,0,0,1,NULL,NULL,1228,1228);

--FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241001,'STRING','Tipo de identificación del autorizado a reportar (empleador)',NULL,NULL,'tipoIdenEmpleador',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241002,'STRING','Número de identificación del autorizado a reportar (empleador)',NULL,NULL,'numeroIdenEmpleador',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241003,'STRING','Digito de verificación',NULL,NULL,'digitoVerificacion',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241004,'STRING','Razón social del autorizado a reportar(empleador)',NULL,NULL,'razonSocialEmpleador',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241005,'STRING','Naturaleza jurídica',NULL,NULL,'naturalezaJuridica',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241006,'STRING','Tipo de identificación del representante legal',NULL,NULL,'tipoIdenRepresentante',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241007,'STRING','Número de identificación del representante legal',NULL,NULL,'numeroIdenRepresentante',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241008,'STRING','Correo electrónico del representante legal',NULL,NULL,'correoElectronicoRepresentante',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241009,'STRING','Teléfono de contacto del representante legal',NULL,NULL,'telRepresentante',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241010,'STRING','Código del departamento de ubicación de la empresa',NULL,NULL,'codDepartamento' ,1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241011,'STRING','Código del municipio de ubicación de la empresa',NULL,NULL,'codMunicipio',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241012,'STRING','Dirección de ubicación de la empresa',NULL,NULL,'direccion',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241013,'STRING','Fecha de renovación de la Cámara de Comercio',NULL,NULL,'fechaRenovCamara',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241014,'STRING','Número de empleados',NULL,NULL,'nroEmpleados',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241015,'STRING','Actividad económica principal',NULL,NULL,'actiEconomicaPrinc',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241016,'STRING','Actividad económica secundaria',NULL,NULL,'actiEconomicaSecun',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241017,'STRING','Fecha de constitución de la empresa',NULL,NULL,'fechaConstitucion',1,0);

--FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241001,NULL,NULL,NULL,'Tipo de identificación del autorizado a reportar (empleador)',0,0,1,241001,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241002,NULL,NULL,NULL,'Número de identificación del autorizado a reportar (empleador)',0,0,1,241002,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241003,NULL,NULL,NULL,'Digito de verificación',0,0,0,241003,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241004,NULL,NULL,NULL,'Razón social del autorizado a reportar(empleador)',0,0,1,241004,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241005,NULL,NULL,NULL,'Naturaleza jurídica',0,0,1,241005,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241006,NULL,NULL,NULL,'Tipo de identificación del representante legal',0,0,1,241006,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241007,NULL,NULL,NULL,'Número de identificación del representante legal',0,0,1,241007,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241008,NULL,NULL,NULL,'Correo electrónico del representante legal',0,0,1,241008,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241009,NULL,NULL,NULL,'Teléfono de contacto del representante legal',0,0,1,241009,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241010,NULL,NULL,NULL,'Código del departamento de ubicación de la empresa',0,0,1,241010,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241011,NULL,NULL,NULL,'Código del municipio de ubicación de la empresa',0,0,1,241011,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241012,NULL,NULL,NULL,'Dirección de ubicación de la empresa',0,0,0,241012,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241013,NULL,NULL,NULL,'Fecha de renovación de la Cámara de Comercio',0,0,0,241013,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241014,NULL,NULL,NULL,'Número de empleados',0,0,1,241014,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241015,NULL,NULL,NULL,'Actividad económica principal',0,0,1,241015,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241016,NULL,NULL,NULL,'Actividad económica secundaria',0,0,0,241016,1228);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241017,NULL,NULL,NULL,'Fecha de constitución de la empresa',0,0,1,241017,1228);


----241 - Estructura de los archivos de respuesta de afiliados
---Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_AFILIADO','1229','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion datos afiliado');

--FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,name,description) VALUES (1229,'Archivo actualizacion datos afiliado','Plantilla archivos de repuesta para actualizacion datos afiliado');

--FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) VALUES (1229,'.','Archivo actualizacion datos afiliado',NULL,NULL,0,NULL,NULL,0,1229);

--LineLoadCatalog
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) VALUES (1229,'com.asopagos.novedades.personas.web.load.ActualizacionInformacionAfiliadosPersistLine','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

--LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) VALUES (1229,NULL,NULL,1,NULL,1,1229,1229);

--ValidatorCatalog
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) VALUES (1229,'com.asopagos.novedades.personas.web.load.validator.ActualizacionInformacionAfiliadosLineValidator','Validador de línea de carga actualizacion datos afiliado','Validador de línea de carga actualizacion datos afiliado','LINE',NULL);

--ValidatorDefinition
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) VALUES (1229,0,0,1,NULL,NULL,1229,1229);

--FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241101,'STRING','Tipo de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'tipoIdenAfiliado',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241102,'STRING','Número de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'numeroIdenAfiliado',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241103,'STRING','Primer apellido del trabajador o cabeza de familia',NULL,NULL,'primerApellidoAfiliado',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241104,'STRING','Segundo apellido del trabajador o cabeza de familia',NULL,NULL,'segundoApellidoAfiliado',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241105,'STRING','Primer nombre del trabajador o cabeza de familia',NULL,NULL,'primerNombreAfiliado',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241106,'STRING','Segundo nombre del trabajador o cabeza de familia',NULL,NULL,'segundoNombreAfiliado',6,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241107,'STRING','Fecha de nacimiento del trabajador o cabeza de familia',NULL,NULL,'fechaNacimientoAfiliado',7,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241108,'STRING','Genero del trabajador o cabeza de familia',NULL,NULL,'generoAfiliado',8,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241109,'STRING','Departamento de nacimiento del trabajador o cabeza de familia',NULL,NULL,'departamentoNacimientoAfiliado',9,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241110,'STRING','Municipio de nacimiento del trabajador o cabeza de familia',NULL,NULL,'municipioNacimientoAfiliado',10,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241111,'STRING','Nacionalidad del trabajador o cabeza de familia',NULL,NULL,'nacionalidadAfiliado' ,11,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241112,'STRING','Departamento de expedición del documento de identificación',NULL,NULL,'departamentoExpedicionDocumentoAfiliado',12,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241113,'STRING','Municipio de expedición del documento de identificación',NULL,NULL,'municipioExpedicionDocumentoAfiliado',13,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241114,'STRING','Fecha de expedición del documento de Identificación',NULL,NULL,'fechaExpedicionDocumentoAfiliado',14,0);

--FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241101,NULL,NULL,NULL,'Tipo de identificación del trabajador o cotizante',0,0,1,241101,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241102,NULL,NULL,NULL,'Número de identificación del trabajador o cotizante',0,0,1,241102,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241103,NULL,NULL,NULL,'Primer apellido del trabajador o cabeza de familia',0,0,1,241103,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241104,NULL,NULL,NULL,'Segundo apellido del trabajador o cabeza de familia',0,0,0,241104,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241105,NULL,NULL,NULL,'Primer nombre del trabajador o cabeza de familia',0,0,1,241105,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241106,NULL,NULL,NULL,'Segundo nombre del trabajador o cabeza de familia',0,0,0,241106,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241107,NULL,NULL,NULL,'Fecha de nacimiento del trabajador o cabeza de familia',0,0,1,241107,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241108,NULL,NULL,NULL,'Genero del trabajador o cabeza de familia',0,0,0,241108,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241109,NULL,NULL,NULL,'Departamento de nacimiento del trabajador o cabeza de familia',0,0,0,241109,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241110,NULL,NULL,NULL,'Municipio de nacimiento del trabajador o cabeza de familia',0,0,0,241110,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241111,NULL,NULL,NULL,'Nacionalidad del trabajador o cabeza de familia',0,0,0,241111,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241112,NULL,NULL,NULL,'Departamento de expedición del documento de identificación',0,0,0,241112,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241113,NULL,NULL,NULL,'Municipio de expedición del documento de identificación',0,0,0,241113,1229);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241114,NULL,NULL,NULL,'Fecha de expedición del documento de Identificación',0,0,0,241114,1229);


----241 - Estructura de los archivos de respuesta de beneficiarios
---Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_BENEFICIARIO','1230','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion datos beneficiarios');

--FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,name,description) VALUES (1230,'Archivo actualizacion datos beneficiario','Plantilla archivos de repuesta para actualizacion datos beneficiario');

--FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) VALUES (1230,'.','Archivo actualizacion datos beneficiario',NULL,NULL,0,NULL,NULL,0,1230);

--LineLoadCatalog
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) VALUES (1230,'com.asopagos.novedades.personas.web.load.ActualizacionInformacionBeneficiariosPersistLine','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

--LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) VALUES (1230,NULL,NULL,1,NULL,1,1230,1230);

--LineDefinitionLoad
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) VALUES (1230,'com.asopagos.novedades.personas.web.load.validator.ActualizacionInformacionBeneficiarioLineValidator','Validador de línea de carga actualizacion datos afiliado','Validador de línea de carga actualizacion datos afiliado','LINE',NULL);

--LineDefinitionLoad
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) VALUES (1230,0,0,1,NULL,NULL,1230,1230);

--FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241201,'STRING','Tipo de identificación del beneficiario o miembro del grupo familiar',NULL,NULL,'tipoIdenBeneficiario',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241202,'STRING','Número de identificación del beneficiario o miembro del grupo familiar',NULL,NULL,'numeroIdenBeneficiario',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241203,'STRING','Primer apellido del beneficiario o miembro del grupo familiar',NULL,NULL,'primerApellidoBeneficiario',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241204,'STRING','Segundo apellido del beneficiario o miembro del grupo familiar ',NULL,NULL,'segundoApellidoBeneficiario',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241205,'STRING','Primer nombre del beneficiario o miembro del grupo familiar',NULL,NULL,'primerNombreBeneficiario',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241206,'STRING','Segundo nombre del beneficiario o miembro del grupo familiar',NULL,NULL,'segundoNombreBeneficiario',6,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241207,'STRING','Condición especial de pago cuota monetaria',NULL,NULL,'condicionEspecial',7,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241208,'STRING','Fecha de nacimiento del beneficiario o miembro del grupo familiar',NULL,NULL,'fechaNacimientoBeneficiario',8,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241209,'STRING','Genero del beneficiario o miembro del grupo familiar',NULL,NULL,'generoBeneficiario',9,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241210,'STRING','Nacionalidad del beneficiario o miembro del grupo familiar',NULL,NULL,'nacionalidadBeneficiario',10,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (241211,'STRING','Nivel educativo',NULL,NULL,'nivelEducativoBeneficiario' ,11,0);

--FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241201,NULL,NULL,NULL,'Tipo de identificación del beneficiario o miembro del grupo familiar',0,0,1,241201,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241202,NULL,NULL,NULL,'Número de identificación del beneficiario o miembro del grupo familiar',0,0,1,241202,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241203,NULL,NULL,NULL,'Primer apellido del beneficiario o miembro del grupo familiar',0,0,1,241203,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241204,NULL,NULL,NULL,'Segundo apellido del beneficiario o miembro del grupo familiar',0,0,0,241204,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241205,NULL,NULL,NULL,'Primer nombre del beneficiario o miembro del grupo familiar',0,0,1,241205,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241206,NULL,NULL,NULL,'Segundo nombre del beneficiario o miembro del grupo familiar',0,0,0,241206,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241207,NULL,NULL,NULL,'Condición especial de pago cuota monetaria',0,0,0,241207,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241208,NULL,NULL,NULL,'Fecha de nacimiento del beneficiario o miembro del grupo familiar',0,0,1,241208,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241209,NULL,NULL,NULL,'Genero del beneficiario o miembro del grupo familiar',0,0,1,241209,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241210,NULL,NULL,NULL,'Nacionalidad del beneficiario o miembro del grupo familiar',0,0,0,241210,1230);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (241211,NULL,NULL,NULL,'Nivel educativo',0,0,1,241211,1230);


----242 - Estructura del archivo de consulta para verificar vigencia de certificados escolares para beneficiarios
---Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CERTIFICADO_ESCOLAR','1231','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion informacion certificado escolar beneficiarios');

--FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,name,description) VALUES (1231,'Archivo actualizacion certificado escolar beneficiario','Plantilla archivos de repuesta para actualizacion datos beneficiario');

--FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) VALUES (1231,'.','Archivo actualizacion certificado escolar beneficiario',NULL,NULL,0,NULL,NULL,0,1231);

--LineLoadCatalog
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) VALUES (1231,'com.asopagos.novedades.personas.web.load.ActualizacionInfoCertificadoEscorlarBeneficiarioPersistLine','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

--LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) VALUES  (1231,NULL,NULL,1,NULL,1,1231,1231);

--LineDefinitionLoad
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) VALUES (1231,'com.asopagos.novedades.personas.web.load.validator.ActualizacionInfoCertificadoEscorlarBeneficiarioLineValidator','Validador de línea de carga actualizacion datos afiliado','Validador de línea de carga actualizacion datos afiliado','LINE',NULL);

--LineDefinitionLoad
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) VALUES (1231,0,0,1,NULL,NULL,1231,1231);

--FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (242001,'STRING','Tipo de identificación del beneficiario',NULL,NULL,'tipoIdentificacion',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (242002,'STRING','Número de identificación del beneficiario',NULL,NULL,'numeroIdentificacion',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (242003,'STRING','Fecha inicio de vigencia de certificado escolar',NULL,NULL,'fechaInicioCertificadoEscolar',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (242004,'STRING','Fecha fin de vigencia de certificado escolar',NULL,NULL,'fechaFinCertificadoEscolar',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (242005,'STRING','Grado cursado del beneficiario',NULL,NULL,'gradoCursado',5,0);

--FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (242001,NULL,NULL,NULL,'Tipo de identificación',0,0,1,242001,1231);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (242002,NULL,NULL,NULL,'Número de identificación',0,0,1,242002,1231);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (242003,NULL,NULL,NULL,'Fecha inicio de vigencia de certificado escolar',0,0,1,242003,1231);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (242004,NULL,NULL,NULL,'Fecha fin de vigencia de certificado escolar',0,0,1,242004,1231);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (242005,NULL,NULL,NULL,'Grado cursado del beneficiario',0,0,1,242005,1231);

----243 - Estructura del archivo de consulta para verificar pensionados
---Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PENSIONADO','1232','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion informacion pensionados');

--FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,name,description) VALUES (1232,'Archivo actualizacion pensionados','Plantilla archivos de repuesta para actualizacion datos pensionado');

--FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) VALUES (1232,'.','Archivo actualizacion datos pensionado',NULL,NULL,0,NULL,NULL,0,1231);

--LineLoadCatalog
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) VALUES (1232,'com.asopagos.novedades.personas.web.load.ActualizacionInfoPensionadoPersistLine','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

--LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) VALUES (1232,NULL,NULL,1,NULL,1,1232,1232);

--LineDefinitionLoad
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) VALUES (1232,'com.asopagos.novedades.personas.web.load.validator.ActualizacionInfoPensionadoLineValidator','Validador de línea de carga actualizacion datos pensionado','Validador de línea de carga actualizacion datos pensionado','LINE',NULL);

--LineDefinitionLoad
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) VALUES (1232,0,0,1,NULL,NULL,1232,1232);

--FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (243001,'STRING','Tipo de identificación del beneficiario',NULL,NULL,'tipoIdentificacion',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (243002,'STRING','Número de identificación del beneficiario',NULL,NULL,'numeroIdentificacion',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (243003,'STRING','Nombres del beneficiario',NULL,NULL,'nombresBeneficiario',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (243004,'STRING','Apellidos del beneficiario',NULL,NULL,'apellidosBeneficiario',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (243005,'STRING','Administradora de pensiones',NULL,NULL,'administradorPension',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (243006,'STRING','¿Persona tiene condición de pensionado?',NULL,NULL,'esPensionado',6,0);

--FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (243001,NULL,NULL,NULL,'Tipo de identificación',0,0,1,243001,1232);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (243002,NULL,NULL,NULL,'Número de identificación',0,0,1,243002,1232);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (243003,NULL,NULL,NULL,'Nombres del beneficiario',0,0,1,243003,1232);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (243004,NULL,NULL,NULL,'Apellidos del beneficiario',0,0,1,243004,1232);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (243005,NULL,NULL,NULL,'Administradora de pensiones',0,0,1,243005,1232);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (243006,NULL,NULL,NULL,'¿Persona tiene condición de pensionado?',0,0,1,243006,1232);

---BPM
--Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPMS_PROCESS_NOVEDADES_ARCHIVOS_ACTUALIZACION_DEPLOYMENTID','com.asopagos.coreaas.bpm.novedades_archivos_actualizacion:novedades_archivos_actualizacion:0.0.2-SNAPSHOT',0,'BPM_PROCESS','Indicador de proceso BPM para el registro de novedades por archivo de actualizacion');