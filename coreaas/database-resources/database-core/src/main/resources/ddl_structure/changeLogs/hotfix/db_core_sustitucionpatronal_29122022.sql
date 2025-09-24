--1. Insertamos en FileDefinitionLoadType
if not exists (select * from core.dbo.FileDefinitionLoadType where id = 12127)
begin
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (12127,'Archivo actualizacion Sustitucion Patronal','Plantilla archivos para actualizacion Sustitucion Patronal');
end
--2. Insertamos en Parametro
if not exists (select * from core.dbo.Parametro where prmNombre = 'FILE_DEFINITION_ID_NOV_SUSTITUCION_PATRONAL')
begin
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_NOV_SUSTITUCION_PATRONAL','12127','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para actualizacion sustitucion patronal');
end
--3. Insertamos en Constante
if not exists (select * from core.dbo.Constante where cnsNombre = 'FILE_DEFINITION_ID_NOV_SUSTITUCION_PATRONAL')
begin
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) 
VALUES (N'FILE_DEFINITION_ID_NOV_SUSTITUCION_PATRONAL', N'12127', N'Identificador de definición de archivo de componente FileProcessing para actualizacion sustitucion patronal')
end
--4. Insertamos en FileDefinitionLoad !!!!!
if not exists (select * from core.dbo.FileDefinitionLoad where id = 12127)
begin
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (12127,'.','Archivo novedad sustitucion patronal',NULL,NULL,0,NULL,NULL,0,12127);
end
--5. Insertamos en LineLoadCatalog
if not exists (select * from core.dbo.LineLoadCatalog where id = 12127)
begin
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (12127,'com.asopagos.novedades.personas.web.load.SustitucionPatronalPersistLine','Estructura del archivo de sustitucion patronal','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL);
end
--6. Insertamos en LineDefinitionLoad
if not exists (select * from core.dbo.LineDefinitionLoad where id = 12127)
begin
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (12127,NULL,NULL,1,NULL,1,12127,12127);
end
--7. Insertamos en ValidatorCatalog
if not exists (select * from core.dbo.ValidatorCatalog where id = 12127)
begin
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (12127,'com.asopagos.novedades.personas.web.load.validator.SustitucionPatronalLineValidator','Validador de línea de carga sustitucion patronal','Validador de línea de carga sustitucion patronal','LINE',NULL);
end
--8. Insertamos en ValidatorDefinition
if not exists (select * from core.dbo.ValidatorDefinition where id = 12127)
begin
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (12127,0,0,1,NULL,NULL,12127,12127);
end
--9. Insertamos en FieldLoadCatalog
if not exists (select * from core.dbo.FieldLoadCatalog where id in (32133173,32133174,32133175,32133176,32133177,32133178,32133179,32133180,32133181))
begin
--12127
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133173,'STRING','Tipo de identificación del empleador origen',NULL,NULL,'tipoIdenEmpleadorOrigen',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133174,'STRING','Número de identificación del empleador origen',NULL,NULL,'numeroIdenEmpleadorOrigen',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133175,'STRING','Serial de la empresa',NULL,NULL,'serialEmpresa',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133176,'STRING','Tipo de identificación del empleador destino',NULL,NULL,'tipoIdenEmpleadorDestino',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133177,'STRING','Número de identificación del empleador destino',NULL,NULL,'numeroIdenEmpleadorDestino',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133178,'STRING','Sucursal del empleador destino',NULL,NULL,'sucursal',6,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133179,'STRING','Tipo de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'tipoIdenAfiliado',7,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133180,'STRING','Número de identificación del trabajador o cotizante o cabeza de familia',NULL,NULL,'numeroIdenAfiliado',8,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133181,'STRING','Fecha de afiliacion',NULL,NULL,'fechaAfiliacionAfiliado',9,0);
end
--10. Insertamos en FieldDefinitionLoad

if not exists (select * from  core.dbo.FieldDefinitionLoad where id in (32133173,32133174,32133175,32133176,32133177,32133178,32133179,32133180,32133181))
begin
--12127
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133173,NULL,NULL,NULL,'Tipo de identificación del empleador origen',0,0,1,32133173,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133174,NULL,NULL,NULL,'Número de identificación del empleador origen',0,0,1,32133174,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133175,NULL,NULL,NULL,'Serial de la empresa',0,0,0,32133175,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133176,NULL,NULL,NULL,'Tipo de identificación del empleador destino',0,0,1,32133176,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133177,NULL,NULL,NULL,'Número de identificación del empleador destino',0,0,1,32133177,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133178,NULL,NULL,NULL,'Sucursal del empleador destino',0,0,1,32133178,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133179,NULL,NULL,NULL,'Tipo de identificación del trabajador ',0,0,1,32133179,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133180,NULL,NULL,NULL,'Número de identificación del trabajador',0,0,1,32133180,12127);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133181,NULL,NULL,NULL,'Fecha de afiliacion',0,0,1,32133181,12127);
end
--11. Alter table ConsolaEstadoCargueMasivo
--Este Alter se estan realizando en el archivo core_chk_cnstrt_creation.sql
--ALTER TABLE ConsolaEstadoCargueMasivo DROP CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo
--ALTER TABLE ConsolaEstadoCargueMasivo ADD CONSTRAINT CK_ConsolaEstadoCargueMasivo_cecTipoProcesoMasivo check (cecTipoProcesoMasivo in ('CARGUE_DE_AFILIACION_MULTIPLE_122','CARGUE_DE_PILA','CARGUE_DE_PILA_OF','CARGUE_DE_NOVEDAD_MULTIPLE_135','CARGUE_SUPERVIVENCIA','CARGUE_CERTIFICADOS_ESCOLARES','CARGUE_PENSIONADOS_BENEFICIARIOS','CARGUE_DE_NOVEDAD_ACTUALIZA_INFO','CARGUE_DE_ARCHIVO_DESCUENTOS','CARGUE_DE_ARCHIVO_RETIRO_TERCER_PAGADOR','CARGUE_CRUCE_FOVIS','CARGUE_MANUAL_DE_ARCH_TERCEROS_PAG','CARGUE_DE_NOVEDAD_RETIRO_TRABAJADORES','CARGUE_DE_NOVEDAD_REINTEGRO_TRABAJADORES','CARGUE_DE_NOVEDAD_ACTUALIZACION_SUCURSAL','CARGUE_DE_NOVEDAD_CONFIRMACION_AB','CARGUE_DE_NOVEDAD_SUSTITUCION_PATRONAL'));

--GLPI 67296 - mcuellar: cc sustitucion patronal
update FieldLoadCatalog set description='Empleador origen-Fecha fin labores con el empleador origen', name='fechaFinLaboresEmpleadorOrigen' where id=32133181;
update FieldDefinitionLoad set label='Empleador origen-Fecha fin labores con el empleador origen' where id=32133181;

if not exists (select * from core.dbo.FieldLoadCatalog where id = 400000032)
begin
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (400000032,'STRING','Empleador destino-Fecha inicio labores con el empleadore destino',NULL,NULL,'fechaInicioLaboresEmpleadorDestino',10,0);
end

if not exists (select * from core.dbo.FieldDefinitionLoad where id = 400000032)
begin
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000032,NULL,NULL,NULL,'Empleador destino-Fecha inicio labores con el empleadore destino',0,0,1,400000032,12127);
end