BEGIN
   IF NOT EXISTS (SELECT prmValor FROM Parametro WHERE prmValor = '1700')
   BEGIN
      INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
VALUES ('FILE_DEFINITION_ID_AFILIACION_CARGUE_TRASLADO_CCF','1700','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de Archivo de afiliaciones masivas ccf')
   END
END
-------
BEGIN
   IF NOT EXISTS (SELECT id FROM FileDefinitionLoadType WHERE id = 1700)
   BEGIN
      INSERT FileDefinitionLoadType (id,name,description) 
VALUES (1700,'traslado afiliaciones masivas ccf','Identificador de definición de archivo de afiliaciones masivas ccf')
   END
END

--------
BEGIN
   IF NOT EXISTS (SELECT [cnsValor] FROM [Constante] WHERE [cnsValor] = N'1700')
   BEGIN
       INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) 
		VALUES (N'FILE_DEFINITION_ID_AFILIACION_CARGUE_TRASLADO_CCF', N'1700', N'Identificador de definición de archivo de componente FileProcessing para cargue de Archivo de afiliaciones masivas ccf')
   END
END

-------------

BEGIN
   IF NOT EXISTS (SELECT id FROM FileDefinitionLoad WHERE id = 1700)
   BEGIN
	INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
	VALUES (1700,'.','Archivo de afiliaciones masivas para traslado de empresas',NULL,NULL,0,NULL,NULL,0,1700);
	END
END

---------
BEGIN
   IF NOT EXISTS (SELECT id FROM LineLoadCatalog WHERE id = 1700)
   BEGIN
	INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
	VALUES (1700,'com.asopagos.novedades.personas.web.load.TrasladoMasivosEmpresasCCFPersistLine','Estructura del archivo de afiliaciones masivas ccf','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL)
	END
END
-------------

BEGIN
   IF NOT EXISTS (SELECT id FROM LineDefinitionLoad WHERE id = 1700)
   BEGIN
	INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
	VALUES (1700,NULL,NULL,1,NULL,1,1700,1700)
	END
END
--------
BEGIN
   IF NOT EXISTS (SELECT id FROM ValidatorCatalog WHERE id = 1700)
   BEGIN
	INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
	VALUES (1700,'com.asopagos.novedades.personas.web.load.validator.TrasladoMasivosEmpresasCCFLineValidator','Validador de linea de afiliaciones masivas ccf','Validador de linea de afiliaciones masivas ccf','LINE',NULL)
	END
END
--------
BEGIN
   IF NOT EXISTS (SELECT id FROM ValidatorDefinition WHERE id = 1700)
   BEGIN
	INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
	VALUES (1700,0,0,1,NULL,NULL,1700,1700)
   END
END
--------
BEGIN
   IF NOT EXISTS (SELECT id FROM FieldLoadCatalog WHERE id IN (300000700,
300000701,
300000702,
300000703,
300000704,
300000705,
300000706,
300000707,
300000708,
300000709,
300000710,
300000711,
300000712,
300000713,
300000714,
300000715,
300000716,
300000717,
300000718,
300000719,
300000720,
300000721,
300000722,
300000723,
300000724,
300000725,
300000726
))
   BEGIN
	insert into FieldLoadCatalog (id,dataType, description, name, fieldOrder) values
		(300000700,'STRING', 'Tipo documento empresa', 'trasladoMasivosEmpresasCCFTipoDocumentoEmpresa',1),
		(300000701,'STRING', 'Numero documento empresa', 'trasladoMasivosEmpresasCCFNumeroDocumentoEmpresa',2),
		(300000702,'STRING', 'Tipo documento afiliado', 'trasladoMasivosEmpresasCCFTipoDocumentoAfiliado',3),
		(300000703,'STRING', 'Numero documento afiliado', 'trasladoMasivosEmpresasCCFNumeroDocumentoAfiliado',4),
		(300000704,'STRING', 'Primer nombre afiliado', 'trasladoMasivosEmpresasCCFPrimerNombreAfiliado',5),
		(300000705,'STRING', 'Segundo nombre afiliado', 'trasladoMasivosEmpresasCCFSegundoNombreAfiliado',6),
		(300000706,'STRING', 'Primer apellido afiliado', 'trasladoMasivosEmpresasCCFPrimerApellidoAfiliado',7),
		(300000707,'STRING', 'Segundo apellido afiliado', 'trasladoMasivosEmpresasCCFSegundoApellidoAfiliado',8),
		(300000708,'STRING', 'Fecha nacimiento afiliado', 'trasladoMasivosEmpresasCCFFechaNacimientoAfiliado',9),
		(300000709,'STRING', 'Sexo afiliado', 'trasladoMasivosEmpresasCCFSexoAfiliado',10),
		(300000710,'STRING', 'Orientacion sexual afiliado', 'trasladoMasivosEmpresasCCFOrientacionSexualAfiliado',11),
		(300000711,'STRING', 'Nivel escolaridad afiiado', 'trasladoMasivosEmpresasCCFNivelEscolaridadAfiliado',12),--
		(300000712,'STRING', 'Codigo Ocupacion afiliado', 'trasladoMasivosEmpresasCCFCodigoOcupacionAfiliado',13),
		(300000713,'STRING', 'Factor vulnerabilidad afiliado', 'trasladoMasivosEmpresasCCFFactorVulnerabilidadAfiliado',14),
		(300000714,'STRING', 'Estado civil afiliado', 'trasladoMasivosEmpresasCCFEstadoCivilAfiliado',15),
		(300000715,'STRING', 'Pertenencia etnica afiliado', 'trasladoMasivosEmpresasCCFPertenenciaEtnicaAfiliado',16),--
		(300000716,'STRING', 'Pais residencia afiliado', 'trasladoMasivosEmpresasCCFPaisResidenciaAfiliado',17),
		(300000717,'STRING', 'Codigo municipio afiliado', 'trasladoMasivosEmpresasCCFCodigoMunicipioAfiliado',18),
		(300000718,'STRING', 'Area geografica residencia afiliado', 'trasladoMasivosEmpresasCCFAreaGeograficaResidenciaAfiliado',19),
		(300000719,'STRING', 'Codigo municipio labor afiliado', 'trasladoMasivosEmpresasCCFCodigoMunicipioLaborAfiliado',20),
		(300000720,'STRING', 'Area geografica labor afiliado', 'trasladoMasivosEmpresasCCFAreaGeograficaLaborAfiliado',21),
		(300000721,'STRING', 'Salario basico afiliado', 'trasladoMasivosEmpresasCCFSalariaBasicoAfiliado',22),
		(300000722,'STRING', 'Tipo afiliado', 'trasladoMasivosEmpresasCCFTipoAfiliado',23),
		(300000723,'STRING', 'Categoria afiliado', 'trasladoMasivosEmpresasCCFCategoriaAfiliado',24),
		(300000724,'STRING', 'Beneficiario cuota monetaria afiliado', 'trasladoMasivosEmpresasCCFBeneficiarioCuotaMonetariaAfiliado',25),
		(300000725,'STRING', 'Direccion afiliado', 'trasladoMasivosEmpresasCCFDireccionAfiliado',26),
		(300000726,'STRING', 'Telefono afiliado', 'trasladoMasivosEmpresasCCFTelefonoAfiliado',27)
   END
END
------------------
BEGIN
   IF NOT EXISTS (SELECT id FROM FieldDefinitionLoad WHERE id IN (300000700,
300000701,
300000702,
300000703,
300000704,
300000705,
300000706,
300000707,
300000708,
300000709,
300000710,
300000711,
300000712,
300000713,
300000714,
300000715,
300000716,
300000717,
300000718,
300000719,
300000720,
300000721,
300000722,
300000723,
300000724,
300000725,
300000726
))
   BEGIN
insert into FieldDefinitionLoad (id,finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required,fieldLoadCatalog_id, lineDefinition_id) values
	(300000700,NULL,NULL,NULL, 'Tipo documento empresa', 0,0,0,300000700,1700),
	(300000701,NULL,NULL,NULL, 'Numero documento empresa', 0,0,0,300000701,1700),
	(300000702,NULL,NULL,NULL, 'Tipo documento afiliado', 0,0,0,300000702,1700),
	(300000703,NULL,NULL,NULL, 'Numero documento afiliado', 0,0,0,300000703,1700),
	(300000704,NULL,NULL,NULL, 'Primer nombre afiliado', 0,0,0,300000704,1700),
	(300000705,NULL,NULL,NULL, 'Segundo nombre afiliado', 0,0,0,300000705,1700),
	(300000706,NULL,NULL,NULL, 'Primer apellido afiliado', 0,0,0,300000706,1700),
	(300000707,NULL,NULL,NULL, 'Segundo apellido afiliado', 0,0,0,300000707,1700),
	(300000708,NULL,NULL,NULL, 'Fecha nacimiento afiliado', 0,0,0,300000708,1700),
	(300000709,NULL,NULL,NULL, 'Sexo afiliado', 0,0,0,300000709,1700),
	(300000710,NULL,NULL,NULL, 'Orientacion sexual afiliado', 0,0,0,300000710,1700),
	(300000711,NULL,NULL,NULL, 'Nivel escolaridad afiiado', 0,0,0,300000711,1700),--
	(300000712,NULL,NULL,NULL, 'Codigo Ocupacion afiliado', 0,0,0,300000712,1700),
	(300000713,NULL,NULL,NULL, 'Factor vulnerabilidad afiliado', 0,0,0,300000713,1700),
	(300000714,NULL,NULL,NULL, 'Estado civil afiliado', 0,0,0,300000714,1700),
	(300000715,NULL,NULL,NULL, 'Pertenencia etnica afiliado', 0,0,0,300000715,1700),--
	(300000716,NULL,NULL,NULL, 'Pais residencia afiliado', 0,0,0,300000716,1700),
	(300000717,NULL,NULL,NULL, 'Codigo municipio afiliado', 0,0,0,300000717,1700),
	(300000718,NULL,NULL,NULL, 'Area geografica residencia afiliado', 0,0,0,300000718,1700),
	(300000719,NULL,NULL,NULL, 'Codigo municipio labor afiliado', 0,0,0,300000719,1700),
	(300000720,NULL,NULL,NULL, 'Area geografica labor afiliado', 0,0,0,300000720,1700),
	(300000721,NULL,NULL,NULL, 'Salario basico afiliado', 0,0,0,300000721,1700),
	(300000722,NULL,NULL,NULL, 'Tipo afiliado', 0,0,0,300000722,1700),
	(300000723,NULL,NULL,NULL, 'Categoria afiliado', 0,0,0,300000723,1700),
	(300000724,NULL,NULL,NULL, 'Beneficiario cuota monetaria afiliado', 0,0,0,300000724,1700),
	(300000725,NULL,NULL,NULL, 'Direccion afiliado', 0,0,0,300000725,1700),
	(300000726,NULL,NULL,NULL, 'Telefono afiliado', 0,0,0,300000726,1700)
   END
END