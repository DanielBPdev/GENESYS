--1. Insertamos en FileDefinitionLoadType
if not exists(select * from FileDefinitionLoadType where id = 12122)
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (12122,'Archivo actualizacion retiro afiliado','Plantilla archivos para actualizacion retiro afiliado');

if not exists(select * from FileDefinitionLoadType where id = 12123)
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (12123,'Archivo actualizacion retiro beneficiario','Plantilla archivos para actualizacion retiro benficiario');

if not exists(select * from FileDefinitionLoadType where id = 12124)
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (12124,'Archivo actualizacion reintegro afiliado','Plantilla archivos para actualizacion reintegro afiliado');

if not exists(select * from FileDefinitionLoadType where id = 12125)
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (12125,'Archivo actualizacion sucursal trabajador','Plantilla archivos para actualizacion sucursal trabajador');

--2. Insertamos en Parametro

if not exists(select * from Parametro where prmNombre = 'FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_AFILIADO')
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_AFILIADO','12122','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo retiro afiliado');

if not exists(select * from Parametro where prmNombre = 'FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_BENEFICIARIO')
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_BENEFICIARIO','12123','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo retiro beneficiario');

if not exists(select * from Parametro where prmNombre = 'FILE_DEFINITION_ID_NOVEDAD_CARGUE_REINTEGRO_AFILIADO')
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_REINTEGRO_AFILIADO','12124','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo reintegro afiliado');

if not exists(select * from Parametro where prmNombre = 'FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_SUCURSAL')
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_SUCURSAL','12125','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion sucursal');


--3. Insertamos en Constante

SET IDENTITY_INSERT [Constante] ON

if not exists(select * from Constante where cnsId = 114)
INSERT [Constante] ([cnsId], [cnsNombre], [cnsValor], [cnsDescripcion]) 
VALUES (114, N'FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_AFILIADO', N'12122', N'Identificador de definición de archivo de componente FileProcessing para cargue de archivo retiro afiliado')

if not exists(select * from Constante where cnsId = 115)
INSERT [Constante] ([cnsId], [cnsNombre], [cnsValor], [cnsDescripcion]) 
VALUES (115, N'FILE_DEFINITION_ID_NOVEDAD_CARGUE_RETIRO_BENEFICIARIO', N'12123', N'Identificador de definición de archivo de componente FileProcessing para cargue de archivo retiro beneficiario')

if not exists(select * from Constante where cnsId = 116)
INSERT [Constante] ([cnsId], [cnsNombre], [cnsValor], [cnsDescripcion]) 
VALUES (116, N'FILE_DEFINITION_ID_NOVEDAD_CARGUE_REINTEGRO_AFILIADO', N'12124', N'Identificador de definición de archivo de componente FileProcessing para cargue de archivo reintegro afiliado')

if not exists(select * from Constante where cnsId = 117)
INSERT [Constante] ([cnsId], [cnsNombre], [cnsValor], [cnsDescripcion]) 
VALUES (117, N'FILE_DEFINITION_ID_NOVEDAD_CARGUE_ACTUALIZACION_SUCURSAL', N'12125', N'Identificador de definición de archivo de componente FileProcessing para cargue de archivo actualizacion sucursal')

SET IDENTITY_INSERT [Constante] OFF

--4. Insertamos en FileDefinitionLoad


if not exists(select * from FileDefinitionLoad where id = 12122)
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (12122,'.','Archivo retiro informacion afiliado',NULL,NULL,0,NULL,NULL,0,12122);

if not exists(select * from FileDefinitionLoad where id = 12123)
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (12123,'.','Archivo retiro informacion beneficiario',NULL,NULL,0,NULL,NULL,0,12123);

if not exists(select * from FileDefinitionLoad where id = 12124)
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (12124,'.','Archivo reintegro informacion afiliado',NULL,NULL,0,NULL,NULL,0,12124);

if not exists(select * from FileDefinitionLoad where id = 12125)
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (12125,'.','Archivo actualizacion sucursal',NULL,NULL,0,NULL,NULL,0,12125);

--5. Insertamos en LineLoadCatalog


if not exists(select * from LineLoadCatalog where id = 12122)
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (12122,'com.asopagos.novedades.personas.web.load.ActualizacionRetiroAfiliadoPersistLine','Estructura del archivo de actualizacíon retiro afiliado','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

if not exists(select * from LineLoadCatalog where id = 12123)
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (12123,'com.asopagos.novedades.personas.web.load.ActualizacionRetiroBeneficiarioPersistLine','Estructura del archivo de actualizacíon retiro beneficiario','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

if not exists(select * from LineLoadCatalog where id = 12124)
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (12124,'com.asopagos.novedades.personas.web.load.ActualizacionReintegroAfiliadoPersistLine','Estructura del archivo de actualizacíon reintegro afiliado','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

if not exists(select * from LineLoadCatalog where id = 12125)
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (12125,'com.asopagos.novedades.personas.web.load.ActualizacionSucursalesTrabajadorPersistLine','Estructura del archivo de actualizacíon sucursal trabajador','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);

--6. Insertamos en LineDefinitionLoad

if not exists(select * from LineDefinitionLoad where id = 12122)
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (12122,NULL,NULL,1,NULL,1,12122,12122);

if not exists(select * from LineDefinitionLoad where id = 12123)
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (12123,NULL,NULL,1,NULL,1,12123,12123);

if not exists(select * from LineDefinitionLoad where id = 12124)
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (12124,NULL,NULL,1,NULL,1,12124,12124);

if not exists(select * from LineDefinitionLoad where id = 12125)
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (12125,NULL,NULL,1,NULL,1,12125,12125);

--7. Insertamos en ValidatorCatalog

if not exists(select * from ValidatorCatalog where id = 12122)
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (12122,'com.asopagos.novedades.personas.web.load.validator.ActualizacionRetiroAfiliadoLineValidator','Validador de línea de carga actualizacíon retiro afiliado','Validador de línea de carga actualizacíon retiro afiliado','LINE',NULL);

if not exists(select * from ValidatorCatalog where id = 12123)
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (12123,'com.asopagos.novedades.personas.web.load.validator.ActualizacionRetiroBeneficiarioLineValidator','Validador de línea de carga actualizacíon retiro beneficiario','Validador de línea de carga actualizacíon retiro beneficiario','LINE',NULL);

if not exists(select * from ValidatorCatalog where id = 12124)
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (12124,'com.asopagos.novedades.personas.web.load.validator.ActualizacionReintegroAfiliadoLineValidator','Validador de línea de carga actualizacíon reintegro afiliado','Validador de línea de carga actualizacíon reintegro afiliado','LINE',NULL);

if not exists(select * from ValidatorCatalog where id = 12125)
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (12125,'com.asopagos.novedades.personas.web.load.validator.ActualizacionSucursalesTrabajadorLineValidator','Validador de línea de carga actualizacíon sucursal trabajador','Validador de línea de carga actualizacíon sucursal trabajador','LINE',NULL);

--8. Insertamos en ValidatorDefinition

if not exists(select * from ValidatorDefinition where id = 12122)
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (12122,0,0,1,NULL,NULL,12122,12122);

if not exists(select * from ValidatorDefinition where id = 12123)
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (12123,0,0,1,NULL,NULL,12123,12123);

if not exists(select * from ValidatorDefinition where id = 12124)
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (12124,0,0,1,NULL,NULL,12124,12124);

if not exists(select * from ValidatorDefinition where id = 12125)
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (12125,0,0,1,NULL,NULL,12125,12125);

--9. Insertamos en FieldLoadCatalog

--12122
if not exists(select * from FieldLoadCatalog where id = 32133135) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133135,'STRING','Tipo de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'tipoIdenAfiliado',4,0);
if not exists(select * from FieldLoadCatalog where id = 32133136) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133136,'STRING','Número de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'numeroIdenAfiliado',5,0);
if not exists(select * from FieldLoadCatalog where id = 32133137) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133137,'STRING','Sucursal del empleador del trabajador o cabeza de familia',NULL,NULL,'sucursal',3,0);
if not exists(select * from FieldLoadCatalog where id = 32133138) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133138,'STRING','Tipo de identificación del empleador del trabajador o cotizante',NULL,NULL,'tipoIdenEmpleador',1,0);
if not exists(select * from FieldLoadCatalog where id = 32133139) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133139,'STRING','Número de identificación del empleador del trabajador o cotizante',NULL,NULL,'numeroIdenEmpleador',2,0);
if not exists(select * from FieldLoadCatalog where id = 32133140) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133140,'STRING','Fecha inicio labores del trabajador o cabeza de familia',NULL,NULL,'fechaLaboresEmpleador',6,0);
if not exists(select * from FieldLoadCatalog where id = 32133141) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133141,'STRING','Fecha de afiliacion del trabajador o cabeza de familia',NULL,NULL,'fechaAfiliacionAfiliado',7,0);
if not exists(select * from FieldLoadCatalog where id = 32133142) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133142,'STRING','Valor salario mensual del trabajador o cabeza de familia',NULL,NULL,'valorSalarioMensual',8,0);
if not exists(select * from FieldLoadCatalog where id = 32133143) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133143,'STRING','Fecha de retiro del trabajador o cabeza de familia',NULL,NULL,'fechaRetiroAfiliado',9,0);
if not exists(select * from FieldLoadCatalog where id = 32133144) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133144,'STRING','Motivo de retiro del trabajador o cabeza de familia',NULL,NULL,'motivoRetiroAfiliado',10,0);

--12123
if not exists(select * from FieldLoadCatalog where id = 32133145) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133145,'STRING','Tipo de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'tipoIdenAfiliado',1,0);
if not exists(select * from FieldLoadCatalog where id = 32133146) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133146,'STRING','Número de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'numeroIdenAfiliado',2,0);
if not exists(select * from FieldLoadCatalog where id = 32133147) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133147,'STRING','Tipo de identificación del Beneficiario',NULL,NULL,'tipoIdenBeneficiario',3,0);
if not exists(select * from FieldLoadCatalog where id = 32133148) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133148,'STRING','Número de identificación del Beneficiario',NULL,NULL,'numeroIdenBeneficiario',4,0);
if not exists(select * from FieldLoadCatalog where id = 32133149) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133149,'STRING','Fecha de retiro del Beneficiario',NULL,NULL,'fechaRetiroBeneficiario',5,0);
if not exists(select * from FieldLoadCatalog where id = 32133150) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133150,'STRING','Motivo de retiro del Beneficiario',NULL,NULL,'motivoRetiroBeneficiario',6,0);

--12124
if not exists(select * from FieldLoadCatalog where id = 32133151) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133151,'STRING','Tipo de identificación del empleador del trabajador o cotizante',NULL,NULL,'tipoIdenEmpleador',1,0);
if not exists(select * from FieldLoadCatalog where id = 32133152) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133152,'STRING','Número de identificación del empleador del trabajador o cotizante',NULL,NULL,'numeroIdenEmpleador',2,0);
if not exists(select * from FieldLoadCatalog where id = 32133153) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133153,'STRING','Serial del empleador del trabajador o cabeza de familia',NULL,NULL,'serialEmpleador',3,0);
if not exists(select * from FieldLoadCatalog where id = 32133154) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133154,'STRING','Tipo de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'tipoIdenAfiliado',4,0);
if not exists(select * from FieldLoadCatalog where id = 32133155) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133155,'STRING','Número de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'numeroIdenAfiliado',5,0);
if not exists(select * from FieldLoadCatalog where id = 32133156) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133156,'STRING','Sucursal del empleador del trabajador o cabeza de familia',NULL,NULL,'sucursal',6,0);
if not exists(select * from FieldLoadCatalog where id = 32133157) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133157,'STRING','Fecha de afiliacion del trabajador o cabeza de familia',NULL,NULL,'fechaAfiliacionAfiliado',7,0);
if not exists(select * from FieldLoadCatalog where id = 32133158) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133158,'STRING','Valor salario mensual del trabajador o cabeza de familia',NULL,NULL,'valorSalarioMensual',8,0);

--12125
if not exists(select * from FieldLoadCatalog where id = 32133159) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133159,'STRING','Tipo de identificación del empleador del trabajador o cotizante',NULL,NULL,'tipoIdenEmpleador',1,0);
if not exists(select * from FieldLoadCatalog where id = 32133160) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133160,'STRING','Número de identificación del empleador del trabajador o cotizante',NULL,NULL,'numeroIdenEmpleador',2,0);
if not exists(select * from FieldLoadCatalog where id = 32133161) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133161,'STRING','Sucursal del trabajador o cabeza de familia',NULL,NULL,'sucursal',3,0);
if not exists(select * from FieldLoadCatalog where id = 32133162) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133162,'STRING','Tipo de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'tipoIdenAfiliado',4,0);
if not exists(select * from FieldLoadCatalog where id = 32133163) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133163,'STRING','Número de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'numeroIdenAfiliado',5,0);

--10. Insertamos en FieldDefinitionLoad

--12122
if not exists(select * from FieldDefinitionLoad where id = 32133135) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133135,NULL,NULL,NULL,'Tipo de identificación del trabajador o cotizante',0,0,1,32133135,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133136) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133136,NULL,NULL,NULL,'Número de identificación del trabajador o cotizante',0,0,1,32133136,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133137) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133137,NULL,NULL,NULL,'Sucursal del empleador del trabajador o cabeza de familia',0,0,1,32133137,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133138) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133138,NULL,NULL,NULL,'Tipo de identificación del empleador del trabajador o cotizante',0,0,1,32133138,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133139) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133139,NULL,NULL,NULL,'Número de identificación del empleador del trabajador o cotizante',0,0,1,32133139,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133140) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133140,NULL,NULL,NULL,'Fecha inicio labores del trabajador o cabeza de familia',0,0,0,32133140,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133141) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133141,NULL,NULL,NULL,'Fecha de afiliacion del trabajador o cabeza de familia',0,0,1,32133141,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133142) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133142,NULL,NULL,NULL,'Valor salario mensual del trabajador o cabeza de familia',0,0,0,32133142,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133143) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133143,NULL,NULL,NULL,'Fecha de retiro del trabajador o cabeza de familia',0,0,1,32133143,12122);
if not exists(select * from FieldDefinitionLoad where id = 32133144) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133144,NULL,NULL,NULL,'Motivo de retiro del trabajador o cabeza de familia',0,0,1,32133144,12122);

--12123
if not exists(select * from FieldDefinitionLoad where id = 32133145) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133145,NULL,NULL,NULL,'Tipo de identificación del trabajador o cotizante',0,0,1,32133145,12123);
if not exists(select * from FieldDefinitionLoad where id = 32133146) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133146,NULL,NULL,NULL,'Número de identificación del trabajador o cotizante',0,0,1,32133146,12123);
if not exists(select * from FieldDefinitionLoad where id = 32133147) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133147,NULL,NULL,NULL,'Tipo de identificación del Beneficiario',0,0,1,32133147,12123);
if not exists(select * from FieldDefinitionLoad where id = 32133148) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133148,NULL,NULL,NULL,'Número de identificación del Beneficiario',0,0,1,32133148,12123);
if not exists(select * from FieldDefinitionLoad where id = 32133149) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133149,NULL,NULL,NULL,'Fecha de retiro del Beneficiario',0,0,1,32133149,12123);
if not exists(select * from FieldDefinitionLoad where id = 32133150) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133150,NULL,NULL,NULL,'Motivo de retiro del Beneficiario',0,0,1,32133150,12123);

--12124
if not exists(select * from FieldDefinitionLoad where id = 32133151) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133151,NULL,NULL,NULL,'Tipo de identificación del trabajador o cotizante',0,0,1,32133151,12124);
if not exists(select * from FieldDefinitionLoad where id = 32133152) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133152,NULL,NULL,NULL,'Número de identificación del trabajador o cotizante',0,0,1,32133152,12124);
if not exists(select * from FieldDefinitionLoad where id = 32133153) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133153,NULL,NULL,NULL,'Serial del empleador del trabajador o cabeza de familia',0,0,0,32133153,12124);
if not exists(select * from FieldDefinitionLoad where id = 32133154) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133154,NULL,NULL,NULL,'Tipo de identificación del empleador del trabajador o cotizante',0,0,1,32133154,12124);
if not exists(select * from FieldDefinitionLoad where id = 32133155) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133155,NULL,NULL,NULL,'Número de identificación del empleador del trabajador o cotizante',0,0,1,32133155,12124);
if not exists(select * from FieldDefinitionLoad where id = 32133156) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133156,NULL,NULL,NULL,'Sucursal del empleador del trabajador o cabeza de familia',0,0,1,32133156,12124);
if not exists(select * from FieldDefinitionLoad where id = 32133157) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133157,NULL,NULL,NULL,'Fecha de afiliacion del trabajador o cabeza de familia',0,0,1,32133157,12124);
if not exists(select * from FieldDefinitionLoad where id = 32133158) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133158,NULL,NULL,NULL,'Valor salario mensual del trabajador o cabeza de familia',0,0,1,32133158,12124);

--12125
if not exists(select * from FieldDefinitionLoad where id = 32133159) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133159,NULL,NULL,NULL,'Tipo de identificación del trabajador o cotizante',0,0,1,32133159,12125);
if not exists(select * from FieldDefinitionLoad where id = 32133160) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133160,NULL,NULL,NULL,'Número de identificación del trabajador o cotizante',0,0,1,32133160,12125);
if not exists(select * from FieldDefinitionLoad where id = 32133161) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133161,NULL,NULL,NULL,'Sucursal del empleador del trabajador o cabeza de familia',0,0,1,32133161,12125);
if not exists(select * from FieldDefinitionLoad where id = 32133162) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133162,NULL,NULL,NULL,'Tipo de identificación del empleador del trabajador o cotizante',0,0,1,32133162,12125);
if not exists(select * from FieldDefinitionLoad where id = 32133163) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133163,NULL,NULL,NULL,'Número de identificación del empleador del trabajador o cotizante',0,0,1,32133163,12125);