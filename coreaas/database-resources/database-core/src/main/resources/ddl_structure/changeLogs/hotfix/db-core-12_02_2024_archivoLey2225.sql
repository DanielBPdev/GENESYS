BEGIN
   IF NOT EXISTS (SELECT 1 FROM Parametro WHERE prmNombre = 'FILE_DEFINITION_ID_LEY_2225')
   BEGIN
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_LEY_2225','1800','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de archivo de ley 2225');
 END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM FileDefinitionLoadType WHERE id = 1800)
   BEGIN
INSERT FileDefinitionLoadType (id,name,description) 
VALUES (1800,'ley 2225','Identificador de definición de archivo de ley 2225');
   END
END

BEGIN
   IF NOT EXISTS (SELECT 1 FROM [Constante] WHERE [cnsNombre] = N'FILE_DEFINITION_ID_LEY_2225')
   BEGIN
INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) 
VALUES (N'FILE_DEFINITION_ID_LEY_2225', N'1800', N'Identificador de definición de archivo de componente FileProcessing para cargue de archivo de ley 2225');
   END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM FileDefinitionLoad WHERE id = 1800)
   BEGIN
INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
VALUES (1800,'.','Archivo de ley 2225',NULL,NULL,0,NULL,NULL,0,1800);
	END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM LineLoadCatalog WHERE id = 1800)
   BEGIN
INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (1800,'com.asopagos.novedades.personas.web.load.Ley2225PersistLine','Estructura del archivo de ley 2225','Estructura de respuesta con los registros encontrados',NULL,1,'|',NULL);
	END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM LineDefinitionLoad WHERE id = 1800)
   BEGIN
INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (1800,NULL,NULL,1,NULL,1,1800,1800);
	END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM ValidatorCatalog WHERE id = 1800)
   BEGIN
INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (1800,'com.asopagos.novedades.personas.web.load.validator.Ley2225LineValidator','Validador de linea de archivo de ley 2225','Validador de linea de archivo de ley 2225','LINE',NULL);
	END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM ValidatorDefinition WHERE id = 1800)
   BEGIN
INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (1800,0,0,1,NULL,NULL,1800,1800);
	END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM FieldLoadCatalog WHERE id IN (300001000,
300001001,
300001002,
300001003,
300001004,
300001005,
300001006,
300001007,
300001008,
300001009,
300001010,
300001011,
300001012,
300001013,
300001014,
300001015,
300001016,
300001017))
BEGIN
insert into FieldLoadCatalog (id,dataType, description, name, fieldOrder) values
	(300001000,'STRING', 'Tipo documento', 'ley2225TipoDocumento',1),
	(300001001,'STRING', 'Numero documento', 'ley2225NumeroDocumento',2),
	(300001002,'STRING', 'Primer nombre', 'ley2225PrimerNombre',3),
	(300001003,'STRING', 'Segundo nombre', 'ley2225SegundoNombre',4),
	(300001004,'STRING', 'Primer apellido', 'ley2225PrimerApellido',5),
	(300001005,'STRING', 'Segundo apellido', 'ley2225SegundoApellido',6),
	(300001006,'STRING', 'Fecha nacimiento', 'ley2225FechaNacimiento',7),
	(300001007,'STRING', 'Fecha recepcion documentos', 'ley2225FechaRecepcion',8),
	(300001008,'STRING', 'Departamento', 'ley2225Departamento',9),
	(300001009,'STRING', 'Municipio', 'ley2225Municipio',10),
	(300001010,'STRING', 'Direccion', 'ley2225Direccion',11),
	(300001011,'STRING', 'Genero', 'ley2225Genero',12),
	(300001012,'STRING', 'Estado civil', 'ley2225CCFEstadoCivil',13),
	(300001013,'STRING', 'Telefono', 'ley2225CCFTelefono',14),
	(300001014,'STRING', 'Celular', 'ley2225Celular',15),
	(300001015,'STRING', 'Correo electronico', 'ley2225CorreoElectronico',16),
	(300001016,'STRING', 'Pagador pension', 'ley2225PagadorPension',17),
	(300001017,'STRING', 'Valor mesada pensional', 'ley2225ValorMesadaPensional',18);
   END
END

BEGIN
   IF NOT EXISTS (SELECT id FROM FieldDefinitionLoad WHERE id IN (300001000,
300001001,
300001002,
300001003,
300001004,
300001005,
300001006,
300001007,
300001008,
300001009,
300001010,
300001011,
300001012,
300001013,
300001014,
300001015,
300001016,
300001017))
BEGIN
insert into FieldDefinitionLoad (id,finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required,fieldLoadCatalog_id, lineDefinition_id) values
	(300001000,NULL,NULL,NULL, 'Tipo documento', 0,0,0,300001000,1800),
	(300001001,NULL,NULL,NULL, 'Numero documento', 0,0,0,300001001,1800),
	(300001002,NULL,NULL,NULL, 'Primer nombre', 0,0,0,300001002,1800),
	(300001003,NULL,NULL,NULL, 'Segundo nombre', 0,0,0,300001003,1800),
	(300001004,NULL,NULL,NULL, 'Primer apellido', 0,0,0,300001004,1800),
	(300001005,NULL,NULL,NULL, 'Segundo apellido', 0,0,0,300001005,1800),
	(300001006,NULL,NULL,NULL, 'Fecha nacimiento', 0,0,0,300001006,1800),
	(300001007,NULL,NULL,NULL, 'Fecha recepcion documentos', 0,0,0,300001007,1800),
	(300001008,NULL,NULL,NULL, 'Departamento', 0,0,0,300001008,1800),
	(300001009,NULL,NULL,NULL, 'Municipio', 0,0,0,300001009,1800),
	(300001010,NULL,NULL,NULL, 'Direccion', 0,0,0,300001010,1800),
	(300001011,NULL,NULL,NULL, 'Genero', 0,0,0,300001011,1800),--
	(300001012,NULL,NULL,NULL, 'Estado civil', 0,0,0,300001012,1800),
	(300001013,NULL,NULL,NULL, 'Telefono', 0,0,0,300001013,1800),
	(300001014,NULL,NULL,NULL, 'Celular', 0,0,0,300001014,1800),
	(300001015,NULL,NULL,NULL, 'Correo electronico', 0,0,0,300001015,1800),--
	(300001016,NULL,NULL,NULL, 'Pagador pension', 0,0,0,300001016,1800),
	(300001017,NULL,NULL,NULL, 'Valor mesada pensional', 0,0,0,300001017,1800);
   END
END