BEGIN
   IF NOT EXISTS (SELECT prmValor FROM Parametro WHERE prmValor = '1701')
   BEGIN
	INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) 
	VALUES ('FILE_DEFINITION_ID_AFILIACION_CARGUE_TRASLADO_CCF_BENEFICIARIO','1701','0','FILE_DEFINITION','Identificador de definición de archivo de componente FileProcessing para cargue de Archivo de afiliaciones masivas ccf beneficiarios')
   END
END
-------
BEGIN
   IF NOT EXISTS (SELECT id FROM FileDefinitionLoadType WHERE id = 1701)
   BEGIN
	INSERT FileDefinitionLoadType (id,name,description) 
	VALUES (1701,'Archivo de afiliaciones masivas ccf a cargo','Identificar archivo de afiliaciones masivas ccf a cargo')
   END
END
------------
BEGIN
   IF NOT EXISTS (SELECT  [cnsValor] FROM [Constante] WHERE  [cnsValor] = N'1701')
   BEGIN
	INSERT [Constante] ([cnsNombre], [cnsValor], [cnsDescripcion]) 
	VALUES (N'FILE_DEFINITION_ID_AFILIACION_CARGUE_TRASLADO_CCF_BENEFICIARIO', N'1701', N'Identificador de definición de archivo de componente FileProcessing para cargue de Archivo de afiliaciones masivas ccf beneficiarios')
   END
END
-------------
BEGIN
   IF NOT EXISTS (SELECT id FROM FileDefinitionLoad WHERE id = 1701)
   BEGIN
	INSERT FileDefinitionLoad (id,decimalSeparator,nombre,tenantid,thousandsSeparator,excludeLines,registersRead,sheetNumberXls,useCharacters,fileDefinitionType_id) 
	VALUES (1701,'.','Archivo de afiliaciones masivas para traslado de empresas a cargo',NULL,NULL,0,NULL,NULL,0,1701)
   END
END
---------------
BEGIN
   IF NOT EXISTS (SELECT id FROM LineLoadCatalog WHERE id = 1701)
   BEGIN
	INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
	VALUES (1701,'com.asopagos.novedades.personas.web.load.TrasladoMasivosEmpresasCCFCargoPersistLine','Estructura del archivo de afiliaciones masivas ccf a cargo','Estructura de respuesta con los registros encontrados',NULL,1,',',NULL)
   END
END
----------------
BEGIN
   IF NOT EXISTS (SELECT id FROM LineDefinitionLoad WHERE id = 1701)
   BEGIN
	INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
	VALUES (1701,NULL,NULL,1,NULL,1,1701,1701)
   END
END
----------------
BEGIN
   IF NOT EXISTS (SELECT id FROM ValidatorCatalog WHERE id = 1701)
   BEGIN
	INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
	VALUES (1701,'com.asopagos.novedades.personas.web.load.validator.TrasladoMasivosEmpresasCCFCargoLineValidator','Validador archivo de afiliaciones masivas ccf a cargo','Validador archivo de afiliaciones masivas ccf a cargo','LINE',NULL)
   END
END
-----------
BEGIN
   IF NOT EXISTS (SELECT id FROM ValidatorDefinition WHERE id = 1701)
   BEGIN
	INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
	VALUES (1701,0,0,1,NULL,NULL,1701,1701)
   END
END
--------------
BEGIN
   IF NOT EXISTS (SELECT id FROM FieldLoadCatalog WHERE id IN (300000727,
300000728,
300000729,
300000730,
300000731,
300000732,
300000733,
300000734,
300000735,
300000736,
300000737,
300000738,
300000739,
300000740,
300000741,
300000742,
300000743,
300000744,
300000745,
300000746,
300000747,
300000748
))
   BEGIN
insert into FieldLoadCatalog (id,dataType, description, name, fieldOrder) values
	(300000727,'STRING', 'Tipo documento empresa', 'trasladoMasivosEmpresasCCFCargoTipoDocumentoEmpresa',1),
	(300000728,'STRING', 'Numero documento empresa', 'trasladoMasivosEmpresasCCFCargoNumeroDocumentoEmpresa',2),
	(300000729,'STRING', 'Tipo documento afiliado', 'trasladoMasivosEmpresasCCFCargoTipoDocumentoAfiliado',3),
	(300000730,'STRING', 'Numero documento afiliado', 'trasladoMasivosEmpresasCCFCargoNumeroDocumentoAfiliado',4),
	(300000731,'STRING', 'Tipo documento afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoTipoDocumentoAfiliadoCargo',5),
	(300000732,'STRING', 'Numero documento afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoNumeroDocumentoAfiliadoCargo',6),
	(300000733,'STRING', 'Primer nombre afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoPrimerNombreAfiliadoCargo',7),
	(300000734,'STRING', 'Segundo nombre afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoSegundoNombreAfiliadoCargo',8),
	(300000735,'STRING', 'Primer apellido afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoPrimerApellidoAfiliadoCargo',9),
	(300000736,'STRING', 'Segundo apellido afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoSegundoApellidoAfiliadoCargo',10),
	(300000737,'STRING', 'Fecha nacimiento afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoFechaNacimientoAfiliadoCargo',11),
	(300000738,'STRING', 'Sexo afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoSexoAfiliadoCargo',12),
	(300000739,'STRING', 'Parentesco persona a cargo', 'trasladoMasivosEmpresasCCFCargoParentescoPersonaCargo',13),
	(300000740,'STRING', 'Codigo municipio afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoCodigoMunicipioAfiliadoCargo',14),
	(300000741,'STRING', 'Area geografica residencia persona a cargo', 'trasladoMasivosEmpresasCCFCargoAreaGeograficaResidenciaAfiliadoCargo',15),
	(300000742,'STRING', 'Condicion discapacidad persona a cargo', 'trasladoMasivosEmpresasCCFCargoCondicionDiscapacidadAfiliadoCargo',16),--
	(300000743,'STRING', 'Tipo de cuota monetaria pagado persona a cargo', 'trasladoMasivosEmpresasCCFCargoTipoCoutaMonetariaPagadaAfiliadoCargo',17),
	(300000744,'STRING', 'Valor cuota monetaria pagado persona a cargo', 'trasladoMasivosEmpresasCCFCargoValorCoutaMonetariaPagadaAfiliadoCargo',18),
	(300000745,'STRING', 'Numero de cuotas pagadas persona a cargo', 'trasladoMasivosEmpresasCCFCargoNumeroCoutasPagadasAfiliadoCargo',19),
	(300000746,'STRING', 'Numero de periodos pagados persona a cargo', 'trasladoMasivosEmpresasCCFCargoNumeroPeriodosPagadosAfiliadoCargo',20),
	(300000747,'STRING', 'Direccion afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoDireccionAfiliadoCargo',21),
	(300000748,'STRING', 'Telefono afiliado a cargo', 'trasladoMasivosEmpresasCCFCargoTelefonoAfiliadoCargo',22)
   END
END
----------------
BEGIN
   IF NOT EXISTS (SELECT id FROM FieldDefinitionLoad WHERE id IN (300000727,
300000728,
300000729,
300000730,
300000731,
300000732,
300000733,
300000734,
300000735,
300000736,
300000737,
300000738,
300000739,
300000740,
300000741,
300000742,
300000743,
300000744,
300000745,
300000746,
300000747,
300000748
))
   BEGIN
insert into FieldDefinitionLoad (id,finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required,fieldLoadCatalog_id, lineDefinition_id) values
	(300000727,NULL,NULL,NULL, 'Tipo documento empresa', 0,0,0,300000727,1701),
	(300000728,NULL,NULL,NULL, 'Numero documento empresa', 0,0,0,300000728,1701),
	(300000729,NULL,NULL,NULL, 'Tipo documento afiliado', 0,0,0,300000729,1701),
	(300000730,NULL,NULL,NULL, 'Numero documento afiliado', 0,0,0,300000730,1701),
	(300000731,NULL,NULL,NULL, 'Tipo documento afiliado a cargo', 0,0,0,300000731,1701),
	(300000732,NULL,NULL,NULL, 'Numero documento afiliado a cargo', 0,0,0,300000732,1701),
	(300000733,NULL,NULL,NULL, 'Primer nombre afiliado a cargo', 0,0,0,300000733,1701),
	(300000734,NULL,NULL,NULL, 'Segundo nombre afiliado a cargo', 0,0,0,300000734,1701),
	(300000735,NULL,NULL,NULL, 'Primer apellido afiliado a cargo', 0,0,0,300000735,1701),
	(300000736,NULL,NULL,NULL, 'Segundo apellido afiliado a cargo', 0,0,0,300000736,1701),
	(300000737,NULL,NULL,NULL, 'Fecha nacimiento afiliado a cargo', 0,0,0,300000737,1701),
	(300000738,NULL,NULL,NULL, 'Sexo afiliado a cargo', 0,0,0,300000738,1701),--
	(300000739,NULL,NULL,NULL, 'Parentesco persona a cargo', 0,0,0,300000739,1701),
	(300000740,NULL,NULL,NULL, 'Codigo municipio afiliado a cargo', 0,0,0,300000740,1701),
	(300000741,NULL,NULL,NULL, 'Area geografica residencia persona a cargo', 0,0,0,300000741,1701),
	(300000742,NULL,NULL,NULL, 'Condicion discapacidad persona a cargo', 0,0,0,300000742,1701),--
	(300000743,NULL,NULL,NULL, 'Tipo de cuota monetaria pagado persona a cargo', 0,0,0,300000743,1701),
	(300000744,NULL,NULL,NULL, 'Valor cuota monetaria pagado persona a cargo', 0,0,0,300000744,1701),
	(300000745,NULL,NULL,NULL, 'Numero de cuotas pagadas persona a cargo', 0,0,0,300000745,1701),
	(300000746,NULL,NULL,NULL, 'Numero de periodos pagados persona a cargo', 0,0,0,300000746,1701),
	(300000747,NULL,NULL,NULL, 'Direccion afiliado a cargo', 0,0,0,300000747,1701),
	(300000748,NULL,NULL,NULL, 'Telefono afiliado a cargo', 0,0,0,300000748,1701)
   END
END