
--GLPI 85501
IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_HOST')
	BEGIN
		INSERT INTO Parametro ([prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla]) 
        VALUES (N'FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_HOST', N'52.167.227.3', 0, N'FTP_ARCHIVOS_CRUCES', N'Nombre del host correspondiente al servidor FTP','IP',1)
	END

IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_CRUCES_APORTES_PUERTO')
	BEGIN
    INSERT INTO Parametro ([prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla]) 
    VALUES (N'FTP_ARCHIVOS_CRUCES_APORTES_PUERTO', N'22', 0, N'FTP_ARCHIVOS_CRUCES', N'Puerto del servidor FTP','NUMBER',1)
	END

IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_USUARIO')
BEGIN
    INSERT INTO Parametro ([prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla]) 
    VALUES (N'FTP_ARCHIVOS_CRUCES_APORTES_NOMBRE_USUARIO', N'0fUvPCe/8qA=', 0, N'FTP_ARCHIVOS_CRUCES', N'Usuario del servidor FTP','NUMBER',1)
END

IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_CRUCES_APORTES_CONTRASENA')
BEGIN
    INSERT INTO Parametro  ([prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla]) 
    VALUES (N'FTP_ARCHIVOS_CRUCES_APORTES_CONTRASENA', N'fs4kQ3Nk+YlPKNvE4srzrA==', 0, N'FTP_ARCHIVOS_CRUCES', N'Constraseña para el usuario del servidor FTP','PASSWORD',1)
END

IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_CRUCES_APORTES_URL_ARCHIVOS')
BEGIN
    INSERT INTO  [Parametro] ( [prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla])
    VALUES (N'FTP_ARCHIVOS_CRUCES_APORTES_URL_ARCHIVOS', N'/home/confa/CRUCES_APORTES/IN', 0, N'FTP_ARCHIVOS_CRUCES', N'Ruta de los archivos de cruces Aportes en el servidor FTP','ROUTE',1)
END
IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS')
BEGIN
    INSERT INTO  [Parametro] ( [prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla])
    VALUES (N'FTP_ARCHIVOS_DESCUENTO_URL_RESULTADOS', N'/home/confa/CRUCES_APORTES/OUT', 0, N'FTP_ARCHIVOS_CRUCES', N'Ruta  de los archivos de cruces aportes generados','ROUTE',1)
END

IF NOT EXISTS (SELECT * FROM Parametro WHERE prmNombre = 'FTP_ARCHIVOS_CRUCES_APORTES_PROTOCOLO')
BEGIN
    INSERT [Parametro] ([prmNombre], [prmValor], [prmCargaInicio], [prmSubCategoriaParametro], [prmDescripcion],[prmTipoDato],[prmVisualizarPantalla])
    VALUES (N'FTP_ARCHIVOS_CRUCES_APORTES_PROTOCOLO', N'SFTP', 0, N'FTP_ARCHIVOS_CRUCES', N'Protocolo  de conexión al servidor FTP','TEXT',1)
 END

IF NOT EXISTS (SELECT * FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'CARGA_AUTOMATICA_ARCHIVOS_CRUCES_APORTES')
BEGIN
    INSERT INTO [ParametrizacionEjecucionProgramada] ( [pepProceso], [pepHoras], [pepMinutos], [pepSegundos], [pepDiaSemana], [pepDiaMes], [pepMes], [pepAnio], [pepFechaInicio], [pepFechaFin], [pepFrecuencia], [pepEstado])
    VALUES (N'CARGA_AUTOMATICA_ARCHIVOS_CRUCES_APORTES', N'13', N'00', N'00', NULL, NULL, NULL, NULL, NULL, NULL, N'DIARIO', N'ACTIVO')
 END
declare @id_FileDefinitionLoadType BIGINT = (select top 1 id+1 from FileDefinitionLoadType f order by 1 desc) --APROBADO POR JUAN PABLO  12129
IF NOT EXISTS (SELECT * FROM Constante WHERE cnsNombre = 'FILE_DEFINITION_ID_ARCHIVO_CRUCES_APORTES')
BEGIN
    INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) 
    VALUES ('FILE_DEFINITION_ID_ARCHIVO_CRUCES_APORTES',@id_FileDefinitionLoadType,'identificador de definición de archivo de componente FileProcessing para cargue de archivo de cruces Aportes');
 END


--Tabla FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,description,name) VALUES (@id_FileDefinitionLoadType,'Carga de archivo cruces Aportes ','Plantilla archivo Cruces Aportes definidos por ccf');
--Tabla FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalseparator,nombre,tenantid,thousandsseparator,excludelines,registersread,sheetnumberxls,usecharacters,filedefinitiontype_id) VALUES (12129,'.','Solicitud de archivos de cruces',null,null,0,null,null,0,1233);

--PARAMETROS DE LA LINEA
--Tabla LineLoadCatalog
INSERT LineLoadCatalog (id,className,description,name,tenantid,lineorder,lineseparator,targetentity) VALUES (12129,'com.asopagos.aportes.masivos.service.load.persistence.ArchivoCrucesAportesPersistLine','Información de la solicitud del archivo cruces con los valores a pignorar','Información del archivo cruces',null,1,'|',null);
--Tabla LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numbergroup,required,requiredingroup,rollbackorder,filedefinition_id,lineloadcatalog_id) VALUES (12129,null,null,1,null,1,12129,12129);

--PARAMETROS DE LOS CAMPOS--
--Tabla FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000435,'STRING','Tipo de identificación del Aportante',NULL,NULL,'tipoIdentificacionAportante',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000436,'STRING','Número de identificación del Aportante',NULL,NULL,'numeroIdentificacionAportante',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000437,'STRING','Nombres  razon Social',NULL,NULL,'razonSocial',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000438,'STRING','Tipo Aportante ',NULL,NULL,'tipoAportante',4,0);

--Tabla FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000435,NULL,NULL,NULL,'Tipo de identificación del Aportante',0,0,1,400000435,12129);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000436,NULL,NULL,NULL,'Número de identificación del Aportante',0,0,1,400000436,12129);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000437,NULL,NULL,NULL,'Nombres  razon Social',0,0,1,400000437,12129);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000438,NULL,NULL,NULL,'Tipo  Aportante',0,0,1,400000438,12129);


--Validador De Nombre Archivo

INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) VALUES (211077,'com.asopagos.aportes.masivos.service.load.validator.ArchivoCrucesFileValidator','Validador de archivo de cruces Aportes','Validador de archivo','FILE',null);
--Tabla ValidatorParameter
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorcatalog_id) VALUES (211381,'STRING','Expresión regular',NULL,'fileNamePattern',211077);
--Tabla ValidadorDefinition
INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id) VALUES (2110359,1,NULL,1,NULL,12129,12129,211077);
--Tabla ValidatorParamValue
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111774,'CRUCE_CCF[0-9]{2}_[0-9]{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)',2110359,211381);

----------
--------Archivo de Salida FTP CRUCES APORTES
----------
IF NOT EXISTS (SELECT * FROM Constante WHERE cnsNombre = 'FILE_DEFINITION_ID_RESULTADOS_CRUCE_APORTES')
BEGIN
    INSERT [Constante] ( [cnsNombre], [cnsValor], [cnsDescripcion])
     VALUES ( N'FILE_DEFINITION_ID_RESULTADOS_CRUCE_APORTES', N'1239', N'Identificador  de definición de archivo de componente FileProcessing para generación de archivo de cruce de aportes')
 END

INSERT FileDefinitionType (id,description,name) VALUES (1239,'Archivo con los resultados del proceso cruce de aportes+','Estructura del archivo de salida de descuentos');

--Tabla FileDefinition
INSERT FileDefinition (id,decimalSeparator,nombre,tenantId,thousandsSeparator,activeHistoric,compressAll,compressEachFile,creationDate,encryptedFileExtension,encrypterClass,finalConditionsClass,nextLineSeparator,registersBlockSize,signedFileExtension,signerClass,fileDefinitionType_id,fileLocation_id) VALUES (1239,'.','Estructura del archivo de salida de cruces aportes',null,null,null,0,0,null,null,null,null,null,null,null,null,1239,null);

--Tabla LineCatalog
INSERT LineCatalog (id,className,description,name,tenantId,paginated,query,queryType) VALUES (9,'com.asopagos.aportes.masivos.service.load.source.DataSourceLineRegistroDescuentos','Información de los valores de cruce de aportes','Estructura archivo salida cruce de aportes',null,0,null,null);

--Tabla LineDefinition
INSERT LineDefinition (id,alternateDetail,generateLineFooter,generaterHeaderLine,lineOrder,masterField,masterFieldReference,masterLine,numberGroup,parentLine,query,required,fileDefinition_id,lineCatalog_id) VALUES (9,null,0,0,1,null,null,null,null,null,null,1,1239,9);

--Tabla FieldCatalog
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (86,'STRING','Tipo de identificación del aportante',null,null,'tipoIdentificacionAportante',null,null,null,null,null,9);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (87,'STRING','Número de identificación del aportante',null,null,'numeroIdentificacionAportante',null,null,null,null,null,9);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (88,'STRING','Nombre/Razón social',null,null,'nombreAportante',null,null,null,null,null,9);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (89,'STRING','Tipo aportante',null,null,'tipoAportante',null,null,null,null,null,9);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (90,'STRING','Estado en CFF',null,null,'estadoCCF',null,null,null,null,null,9);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (91,'STRING','Tipo de afiliación en CFF',null,null,'tipoAfiliacion',null,null,null,null,null,9);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (92,'DATE','Fecha de retiro',null,null,'fechaRetiro',null,null,null,null,null,9);


--Tabla FieldDefinition
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (86,null,null,null,'Tipo de identificación del aportante',1,'NONE',0,86,9);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (87,null,null,null,'Número de identificación del aportante',2,'NONE',0,87,9);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (88,null,null,null,'Nombre/Razón social',3,'NONE',0,88,9);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (89,null,null,null,'Tipo aportante',4,'NONE',0,89,9);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (90,null,null,null,'Estado en CFF',5,'NONE',0,90,9);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (91,null,null,null,'Tipo de afiliación en CFF',6,'NONE',0,91,9);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (92,null,null,null,'Fecha de retiro',7,'NONE',0,92,9);


