
if not exists (select 1 from Constante where cnsNombre = 'FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_DEVOLUCION') insert into Constante(cnsNombre, cnsValor, cnsDescripcion) values ('FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_DEVOLUCION', '1604', 'Identificador de definici�n de devoluci�n para aportes masivos');
if not exists (select 1 from Constante where cnsNombre = 'FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_RECAUDO') insert into Constante(cnsNombre, cnsValor, cnsDescripcion) values ('FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_RECAUDO', '1605', 'Identificador de definici�n de recaudo para aportes masivos');

if not exists (select 1 from Parametro where prmNombre = 'FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_DEVOLUCION') insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion) values ('FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_DEVOLUCION', '1604', 0, 'FILE_DEFINITION', 'Identificador de definici�n de devoluci�n para aportes masivos');
if not exists (select 1 from Parametro where prmNombre = 'FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_RECAUDO') insert into Parametro (prmNombre, prmValor, prmCargaInicio, prmSubCategoriaParametro, prmDescripcion) values ('FILE_DEFINITION_ID_ARCHIVO_APORTES_MASIVOS_RECAUDO', '1605', 0, 'FILE_DEFINITION', 'Identificador de definicion de aportes para aportes masivos');

if not exists (select 1 from FileDefinitionLoadType where id = 1604) INSERT [dbo].[FileDefinitionLoadType] ([id], [description], [name]) VALUES (1604, N'Archivo Devoluci?n para aportes masivos', N'Archivo Devoluci?n para aportes masivos')
if not exists (select 1 from FileDefinitionLoadType where id = 1605)INSERT [dbo].[FileDefinitionLoadType] ([id], [description], [name]) VALUES (1605, N'Archivo recaudo para aportes masivos', N'Archivo recaudo para aportes masivos')


if not exists (select 1 from FileDefinitionLoad where id = 1604) insert into FileDefinitionLoad(id, decimalSeparator, nombre, excludeLines, useCharacters, fileDefinitionType_id) values (1604, '.', 'Archivo Devolución para aportes masivos', 0, 0, 1604);
if not exists (select 1 from FileDefinitionLoad where id = 1605) insert into FileDefinitionLoad(id, decimalSeparator, nombre, excludeLines, useCharacters, fileDefinitionType_id) values (1605, '.', 'Archivo recaudo para aportes masivos', 0, 0, 1605);



if not exists (select 1 from LineLoadCatalog where id = 1604) INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (1604,'com.asopagos.aportes.masivos.service.load.persistence.AportesMasivosDevolucionPersistLine','Estructura del archivo de actualizacíon devolución aportes masivos','Estructura de respuesta con los registros encontrados',NULL,1,'|',NULL);

if not exists (select 1 from LineLoadCatalog where id = 1605) INSERT LineLoadCatalog (id,classname,description,name,tenantId,lineOrder,lineSeparator,targetEntity) 
VALUES (1605,'com.asopagos.aportes.masivos.service.load.persistence.AportesMasivosRecaudoManualPersistLine','Estructura del archivo de actualizacion aportes aportes masivos','Estructura de respuesta con los registros encontrados',NULL,1,'|',NULL);

if not exists (select 1 from LineDefinitionLoad where id = 1604) INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (1604,NULL,NULL,1,NULL,1,1604,1604);


if not exists (select 1 from LineDefinitionLoad where id = 1605) INSERT LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id) 
VALUES (1605,NULL,NULL,1,NULL,1,1605,1605);


if not exists (select 1 from ValidatorCatalog where id = 1604) INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (1604,'com.asopagos.aportes.masivos.service.load.validator.AportesMasivosDevolucionLineValidator','Validador de línea de carga actualización devolución aportes masivos','Validador de línea de carga actualizaóon devolución aportes masivos','LINE',NULL);

if not exists (select 1 from ValidatorCatalog where id = 1605) INSERT ValidatorCatalog (id,className,description,name,scope,tenantId) 
VALUES (1605,'com.asopagos.aportes.masivos.service.load.validator.AportesMasivosRecaudoManualLineValidator','Validador de línea de carga actualización recaudo aportes masivos','Validador de línea de carga actualización recaudo aportes masivos','LINE',NULL);

if not exists (select 1 from ValidatorCatalog where id = 1604) INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (1604,0,0,1,NULL,NULL,1604,1604);

if not exists (select 1 from ValidatorCatalog where id = 1605) INSERT ValidatorDefinition (id,excludeLine,stopProcess,validatorOrder,fieldDefinition_id,fileDefinitionLoad_id,lineDefinition_id,validatorCatalog_id) 
VALUES (1605,0,0,1,NULL,NULL,1605,1605);

if not exists (select 1 from fieldLoadCatalog where id = 300000100)  insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (300000100, 'STRING', 'Tipo identificación del aportante', 'recaudoMasivoTipoDocumentoAportante', 2, 0);
if not exists (select 1 from fieldLoadCatalog where id = 300000101)  insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (300000101, 'STRING', 'Número identificación del aportante', 'recaudoMasivoNumeroDocumentoAportante', 3, 0);
if not exists (select 1 from fieldLoadCatalog where id = 300000102) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (300000102, 'STRING', 'Razón social', 'recaudoMasivoRazonSocial', 4, 0);
if not exists (select 1 from fieldLoadCatalog where id = 300000103)  insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (300000103, 'STRING', 'periodo', 'recaudoMasivoPeriodoPago', 5, 0);
if not exists (select 1 from fieldLoadCatalog where id = 300000104)  insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (300000104, 'STRING', 'Tipo de aportante', 'recaudoMasivoTipoAportante', 1, 0);


if not exists (select 1 from fieldLoadCatalog where id = 400000400) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000400, 'STRING', 'Tipo de aportante', 'recaudoMasivoTipoAportante', 1, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000401) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000401, 'STRING', 'Tipo identificación del aportante', 'recaudoMasivoTipoDocumentoAportante', 2, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000402) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000402, 'STRING', 'Número identificación del aportante', 'recaudoMasivoNumeroDocumentoAportante', 3, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000403) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000403, 'STRING', 'Razón social', 'recaudoMasivoRazonSocial', 4, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000404) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000404, 'STRING', 'id departamento', 'recaudoMasivoIdDepartamento', 5, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000405) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000405, 'STRING', 'id municipio', 'recaudoMasivoIdMunicipio', 6, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000406) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000406, 'STRING', 'periodo pago', 'recaudoMasivoPeriodoPago', 7, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000407) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000407, 'STRING', 'tipo cotizante', 'recaudoMasivoTipoCotizante', 8, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000408) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000408, 'STRING', 'tipo documento cotizante', 'recaudoMasivoTipoDocumentoCotizante', 9, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000409) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000409, 'STRING', 'numero documento cotizante', 'recaudoMasivoNumeroDocumentoCotizante', 10, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000410) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000410, 'STRING', 'primer nombre cotizante', 'recaudoMasivoPrimerNombreCotizante', 11, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000411) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000411, 'STRING', 'segundo nombre cotizantee', 'recaudoMasivoSegundoNombreCotizante', 12, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000412) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000412, 'STRING', 'primer apellido cotizante', 'recaudoMasivoPrimerApellidoCotizante', 13, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000413) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000413, 'STRING', 'segundo apellido cotizante', 'recaudoMasivoSegundoApellidoCotizante', 14, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000414) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000414, 'STRING', 'fecha recepcion aporte', 'recaudoMasivoFechaRecepcionAporte', 15, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000415) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000415, 'STRING', 'fecha pago', 'recaudoMasivoFechaDepago', 16, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000416) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000416, 'STRING', 'concepto de pago', 'recaudoMasivoConceptoDePago', 17, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000417) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000417, 'STRING', 'ibc', 'recaudoMasivoIBC', 18, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000418) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000418, 'STRING', 'ing', 'recaudoMasivoING', 19, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000419) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000419, 'STRING', 'ret', 'recaudoMasivoRET', 20, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000420) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000420, 'STRING', 'irl', 'recaudoMasivoIRL', 21, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000421) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000421, 'STRING', 'vsp', 'recaudoMasivoVSP', 22, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000422) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000422, 'STRING', 'vst', 'recaudoMasivoVST', 23, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000423) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000423, 'STRING', 'sln', 'recaudoMasivoSLN', 24, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000424) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000424, 'STRING', 'ige', 'recaudoMasivoIGE', 25, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000425) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000425, 'STRING', 'lma', 'recaudoMasivoLMA', 26, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000426) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000426, 'STRING', 'vac', 'recaudoMasivoVAC', 27, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000427) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000427, 'STRING', 'salario basico', 'recaudoMasivoSalarioBasico', 28, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000428) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000428, 'STRING', 'dias cotizados', 'recaudoMasivoDiasCotizados', 29, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000429) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000429, 'STRING', 'dias mora', 'recaudoMasivoDiasMora', 30, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000430) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000430, 'STRING', 'tarifa', 'recaudoMasivoTarifa', 31, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000431) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000431, 'STRING', 'numero de horas laboradas', 'recaudoMasivoNumeroDeHorasLaboradas', 32, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000432) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000432, 'STRING', 'aporte obligatorio', 'recaudoMasivoAporteObligatorio', 33, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000433) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000433, 'STRING', 'valor intereses', 'recaudoMasivoValorIntereses', 34, 0); 
if not exists (select 1 from fieldLoadCatalog where id = 400000434) insert into fieldLoadCatalog(id, dataType, description, name, fieldOrder, tenantId) values (400000434, 'STRING', 'total aporte', 'recaudoMasivoTotalAporte', 35, 0);



if not exists (select 1 from FieldDefinitionLoad where id = 300000100) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (300000100, 'recaudoMasivoTipoDocumentoAportante', 0, 0, 1, 300000100, 1604)
if not exists (select 1 from FieldDefinitionLoad where id = 300000101) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (300000101, 'recaudoMasivoNumeroDocumentoAportante', 0, 0, 1, 300000101, 1604)
if not exists (select 1 from FieldDefinitionLoad where id = 300000102) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (300000102, 'recaudoMasivoRazonSocial', 0, 0, 1, 300000102, 1604)
if not exists (select 1 from FieldDefinitionLoad where id = 300000103) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (300000103, 'recaudoMasivoPeriodoPago', 0, 0, 1, 300000103, 1604)
if not exists (select 1 from FieldDefinitionLoad where id = 300000104) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (300000104, 'recaudoMasivoPeriodoPago', 0, 0, 1, 300000104, 1604)

if not exists (select 1 from FieldDefinitionLoad where id = 400000400) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000400, 'recaudoMasivoTipoAportante', 0, 0, 1, 400000400, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000401) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000401, 'recaudoMasivoTipoDocumentoAportante', 0, 0, 1, 400000401, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000402) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000402, 'recaudoMasivoNumeroDocumentoAportante', 0, 0, 1, 400000402, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000403) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000403, 'recaudoMasivoRazonSocial', 0, 0, 0, 400000403, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000404) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000404, 'recaudoMasivoIdDepartamento', 0, 0, 0, 400000404, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000405) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000405, 'recaudoMasivoIdMunicipio', 0, 0, 0, 400000405, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000406) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000406, 'recaudoMasivoPeriodoPago', 0, 0, 1, 400000406, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000407) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000407, 'recaudoMasivoTipoCotizante', 0, 0, 0, 400000407, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000408) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000408, 'recaudoMasivoTipoDocumentoCotizante', 0, 0, 1, 400000408, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000409) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000409, 'recaudoMasivoNumeroDocumentoCotizante', 0, 0, 1, 400000409, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000410) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000410, 'recaudoMasivoPrimerNombreCotizante', 0, 0, 0, 400000410, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000411) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000411, 'recaudoMasivoSegundoNombreCotizante', 0, 0, 0, 400000411, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000412) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000412, 'recaudoMasivoPrimerApellidoCotizante', 0, 0, 0, 400000412, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000413) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000413, 'recaudoMasivoSegundoApellidoCotizante', 0, 0, 0, 400000413, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000414) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000414, 'recaudoMasivoFechaRecepcionAporte', 0, 0, 0, 400000414, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000415) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000415, 'recaudoMasivoFechaDepago', 0, 0, 0, 400000415, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000416) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000416, 'recaudoMasivoConceptoDePago', 0, 0, 0, 400000416, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000417) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000417, 'recaudoMasivoIBC', 0, 0, 0, 400000417, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000418) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000418, 'recaudoMasivoING', 0, 0, 0, 400000418, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000419) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000419, 'recaudoMasivoRET', 0, 0, 0, 400000419, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000420) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000420, 'recaudoMasivoIRL', 0, 0, 0, 400000420, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000421) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000421, 'recaudoMasivoVSP', 0, 0, 0, 400000421, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000422) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000422, 'recaudoMasivoVST', 0, 0, 0, 400000422, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000423) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000423, 'recaudoMasivoSLN', 0, 0, 0, 400000423, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000424) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000424, 'recaudoMasivoIGE', 0, 0, 0, 400000424, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000425) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000425, 'recaudoMasivoLMA', 0, 0, 0, 400000425, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000426) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000426, 'recaudoMasivoVAC', 0, 0, 0, 400000426, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000427) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000427, 'recaudoMasivoSalarioBasico', 0, 0, 1, 400000427, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000428) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000428, 'recaudoMasivoDiasCotizados', 0, 0, 1, 400000428, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000429) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000429, 'recaudoMasivoDiasMora', 0, 0, 0, 400000429, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000430) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000430, 'recaudoMasivoTarifa', 0, 0, 1, 400000430, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000431) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000431, 'recaudoMasivoNumeroDeHorasLaboradas', 0, 0, 1, 400000431, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000432) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000432, 'recaudoMasivoAporteObligatorio', 0, 0, 1, 400000432, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000433) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000433, 'recaudoMasivoValorIntereses', 0, 0, 0, 400000433, 1605);
if not exists (select 1 from FieldDefinitionLoad where id = 400000434) INSERT INTO FieldDefinitionLoad(id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) values (400000434, 'recaudoMasivoTotalAporte', 0, 0, 1, 400000434, 1605);