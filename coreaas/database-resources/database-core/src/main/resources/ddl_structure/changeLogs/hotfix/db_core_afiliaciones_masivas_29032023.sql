-- 01: INSERT FieldLoadCatalog 
IF NOT EXISTS (SELECT * FROM FieldLoadCatalog WHERE id = 32133182) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133182,'STRING','Orientación sexual',NULL,NULL,'orientacionSexual',24,0);
IF NOT EXISTS (SELECT * FROM FieldLoadCatalog WHERE id = 32133183) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133183,'STRING','Factor vulnerabilidad',NULL,NULL,'factorVulnerabilidad',25,0);
IF NOT EXISTS (SELECT * FROM FieldLoadCatalog WHERE id = 32133184) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133184,'STRING','Pertenencia étnica',NULL,NULL,'pertenenciaEtnica',26,0);
IF NOT EXISTS (SELECT * FROM FieldLoadCatalog WHERE id = 32133185) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133185,'STRING','País de residencia',NULL,NULL,'paisResidencia',27,0);
IF NOT EXISTS (SELECT * FROM FieldLoadCatalog WHERE id = 32133186) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133186,'STRING','Ocupación',NULL,NULL,'ocupacion',28,0);
IF NOT EXISTS (SELECT * FROM FieldLoadCatalog WHERE id = 32133187) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133187,'STRING','Nivel de escolaridad',NULL,NULL,'nivelEscolaridad',29,0);
IF NOT EXISTS (SELECT * FROM FieldLoadCatalog WHERE id = 32133188) INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId) VALUES (32133188,'STRING','Municipio de labor',NULL,NULL,'municipioLabor',30,0);

--02: INSERT FieldDefinitionLoad
IF NOT EXISTS (SELECT * FROM FieldDefinitionLoad WHERE id = 32133182) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133182,NULL,NULL,NULL,'Orientación sexual',0,0,0,32133182,1221);
IF NOT EXISTS (SELECT * FROM FieldDefinitionLoad WHERE id = 32133183) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133183,NULL,NULL,NULL,'Factor vulnerabilidad',0,0,0,32133183,1221);
IF NOT EXISTS (SELECT * FROM FieldDefinitionLoad WHERE id = 32133184) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133184,NULL,NULL,NULL,'Pertenencia étnica',0,0,0,32133184,1221);
IF NOT EXISTS (SELECT * FROM FieldDefinitionLoad WHERE id = 32133185) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133185,NULL,NULL,NULL,'País de residencia',0,0,0,32133185,1221);
IF NOT EXISTS (SELECT * FROM FieldDefinitionLoad WHERE id = 32133186) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133186,NULL,NULL,NULL,'Ocupación',0,0,0,32133186,1221);
IF NOT EXISTS (SELECT * FROM FieldDefinitionLoad WHERE id = 32133187) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133187,NULL,NULL,NULL,'Nivel de escolaridad',0,0,0,32133187,1221);
IF NOT EXISTS (SELECT * FROM FieldDefinitionLoad WHERE id = 32133188) INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (32133188,NULL,NULL,NULL,'Municipio de labor',0,0,0,32133188,1221);

--03: Update parametro ARCHIVO_PLANTILLA_EJEMPLO_EXCEL_CARGUE_MASIVO_122

update parametro set prmValor = '471c0968-cbe2-41ce-a6f8-18b363f8ac92.xlsx_1682520957001317' where prmnombre = 'ARCHIVO_PLANTILLA_EJEMPLO_EXCEL_CARGUE_MASIVO_122'

--04: update parametro ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122

update parametro set prmValor = '65670cec-b672-4517-94ee-3d9985b23f21.xlsx_1681920266126932' where prmnombre = 'ARCHIVO_PLANTILLA_EXCEL_CARGUE_MASIVO_122'

-- GLPI 49369
update parametro set prmvalor = '500' where prmId = 48;
