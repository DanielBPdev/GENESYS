IF NOT EXISTS (SELECT * FROM FileDefinitionLoadType WHERE ID = 12128)
BEGIN
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (12128,'Archivo servicios cobrados por efectivo','Estructura del archivo servicios cobrados por efectivo');
END 

update constante
set cnsvalor = '12128'
where cnsnombre = 'FILE_DEFINITION_ID_ARCHIVO_RETIRO_PAGOS_SUBSIDIO_MONETARIO'

IF NOT EXISTS (SELECT * FROM FileDefinitionLoad WHERE ID = 12128)
BEGIN
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (12128,'.','Estructura del archivo servicios cobrados por efectivo',NULL,NULL,0,NULL,NULL,0,12128);
END

IF NOT EXISTS (SELECT * FROM LineLoadCatalog WHERE ID = 12128)
BEGIN
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (12128,'com.asopagos.subsidiomonetario.pagos.persist.PagosSubsidioMonetarioPersistLine','Estructura del archivo servicios cobrados por efectivo','Archivo servicios cobrados por efectivo',NULL,1,'|',NULL);
END

IF NOT EXISTS (SELECT * FROM ValidatorCatalog WHERE ID = 12128)
BEGIN
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (12128,'com.asopagos.subsidiomonetario.pagos.load.validator.PagosSubsidioMonetarioLineValidator','validador de línea de carga de solicitud de conciliación de retiros del tercer pagador','Validador línea carga conciliación retiros del tercer pagador','LINE',NULL);
END

IF NOT EXISTS (SELECT * FROM ValidatorDefinition WHERE ID = 12128)
BEGIN
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (12128,0,0,1,NULL,NULL,12128,12128);
END

IF NOT EXISTS (SELECT * FROM FileDefinitionType WHERE ID = 12128)
BEGIN
insert into FileDefinitionType (id,description,name)
values(12128,'Archivo servicios cobrados por efectivo','Estructura del archivo servicios cobrados por efectivo')
END

IF NOT EXISTS (SELECT * FROM FileDefinition WHERE ID = 12128)
BEGIN
insert into FileDefinition (id,decimalSeparator,nombre,compressAll,compressEachFile,fileDefinitionType_id)values
(12128,',','Estructura del archivo de salida de descuentos NUEVO',0,0,12128)
END

update LineDefinitionLoad
set fileDefinition_id = 12128, lineLoadCatalog_id = 12128
where id = 12128
