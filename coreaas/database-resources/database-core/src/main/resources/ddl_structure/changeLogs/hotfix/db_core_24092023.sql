if NOT EXISTS (select * from FieldLoadCatalog where description = 'Letra: indicativo de correccion planilla pensionados A:C') 
BEGIN
    INSERT INTO FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId)
    VALUES (2110248,'STRING','Letra: indicativo de correccion planilla pensionados A:C',NULL,NULL,'archivoIPregistro2campo24',24,NULL)

    INSERT INTO FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id)
    VALUES(2110248,214,NULL,213,'archivoIPregistro2campo24',0,0,1,(SELECT ID FROM FieldLoadCatalog WHERE NAME='archivoIPregistro2campo24' AND description='Letra: indicativo de correccion planilla pensionados A:C'),9)

    INSERT ValidatorDefinition (id, excludeLine, stopProcess, validatorOrder,
    fieldDefinition_id, fileDefinitionLoad_id, lineDefinition_id, validatorCatalog_id,
    validatorprofile, state)  VALUES (2110358, 0, 0, 45, 2110248, null, null, 211003, 2, 1); 


    INSERT [ValidatorParamValue] ([id], [value], [validatorDefinition_id], [validatorParameter_id]) VALUES (2111770, N'Archivo Tipo IP - Registro 2 - Campo 24: correcciones', 2110358, 211010)
    INSERT [ValidatorParamValue] ([id], [value], [validatorDefinition_id], [validatorParameter_id]) VALUES (2111771, N'A,C', 2110358, 211011)
    INSERT [ValidatorParamValue] ([id], [value], [validatorDefinition_id], [validatorParameter_id]) VALUES (2111772, N'TIPO_2', 2110358, 211012)
    INSERT [ValidatorParamValue] ([id], [value], [validatorDefinition_id], [validatorParameter_id]) VALUES (2111773, N'IP224', 2110358, 211013)
END