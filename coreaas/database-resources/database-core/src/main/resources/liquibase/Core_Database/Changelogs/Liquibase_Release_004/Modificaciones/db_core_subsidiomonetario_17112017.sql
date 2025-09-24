--liquibase formatted sql

--changeset rlopez:01
--comment:Actualizacion de registro en la tabla ValidatorParameter
UPDATE ValidatorParameter SET name = 'fileNamePattern' WHERE id = 12331;

--changeset mosanchez:02
--comment: Se modifica campo en la tabla ParametrizacionSubsidioLiquidacion
EXEC sp_rename 'dbo.ParametrizacionLiquidacionSubsidio.pcsSMLMV', 'plsSMLMV', 'COLUMN';;

--changeset rlopez:03
--comment: Se adicionan campos a la tabla SolicitudLiquidacionSubsidio
ALTER TABLE SolicitudLiquidacionSubsidio ADD slsFechaEvaluacionPrimerNivel DATETIME NULL;
ALTER TABLE SolicitudLiquidacionSubsidio ADD slsFechaEvaluacionSegundoNivel DATETIME NULL;

--changeset rlopez:04
--comment:Insercion de registros en las tablas del Framework Lion
--Tabla FileDefinitionType
INSERT FileDefinitionType (id,description,name) VALUES (1234,'Archivo con los resultados del proceso de pignoración','Estructura del archivo de salida de descuentos');

--Tabla FileDefinition
INSERT FileDefinition (id,decimalSeparator,nombre,tenantId,thousandsSeparator,activeHistoric,compressAll,compressEachFile,creationDate,encryptedFileExtension,encrypterClass,finalConditionsClass,nextLineSeparator,registersBlockSize,signedFileExtension,signerClass,fileDefinitionType_id,fileLocation_id) VALUES (1234,'.','Estructura del archivo de salida de descuentos',null,null,null,0,0,null,null,null,null,null,null,null,null,1234,null);

--Tabla LineCatalog
INSERT LineCatalog (id,className,description,name,tenantId,paginated,query,queryType) VALUES (4,'com.asopagos.entidaddescuento.load.source.DataSourceLineRegistroDescuentos','Información de los valores pignorados en el proceso de liquidación','Estructura archivo salida entidades descuento',null,0,null,null);

--Tabla LineDefinition
INSERT LineDefinition (id,alternateDetail,generateLineFooter,generaterHeaderLine,lineOrder,masterField,masterFieldReference,masterLine,numberGroup,parentLine,query,required,fileDefinition_id,lineCatalog_id) VALUES (4,null,0,0,1,null,null,null,null,null,null,1,1234,4);

--Tabla FieldCatalog
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (14,'STRING','Tipo de identificación del trabajador',null,null,'tipoIdentificacionTrabajador',null,null,null,null,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (15,'STRING','Número de identificación del trabajador',null,null,'numeroIdentificacionTrabajador',null,null,null,null,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (16,'STRING','Nombres y apellidos del trabajador',null,null,'nombreTrabajador',null,null,null,null,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (17,'STRING','Tipo de identificación del administrador',null,null,'tipoIdentificacionAdministrador',null,null,null,null,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (18,'STRING','Número de identificación del administrador',null,null,'numeroIdentificacionAdministrador',null,null,null,null,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (19,'STRING','Código Grupo Familiar',null,null,'codigoGrupoFamiliar',null,null,null,null,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (20,'BIGDECIMAL','Valor a pignorar',5,0,'valorPignorar',null,null,null,7,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (21,'BIGDECIMAL','Valor pignorado',5,0,'valorPignorado',null,null,null,7,null,4);
INSERT FieldCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,label,paginated,query,roundMode,fieldGCatalog_id,idLineCatalog) VALUES (22,'STRING','Resultado de pignoración',null,null,'resultado',null,null,null,null,null,4);

--Tabla FieldDefinition
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (14,null,null,null,'Tipo de identificación del trabajador',1,'NONE',0,14,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (15,null,null,null,'Número de identificación del trabajador',2,'NONE',0,15,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (16,null,null,null,'Nombres y apellidos del administrador',3,'NONE',0,16,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (17,null,null,null,'Tipo de identificación del administrador',4,'NONE',0,17,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (18,null,null,null,'Número de identificación del administrador',5,'NONE',0,18,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (19,null,null,null,'Código Grupo Familiar',6,'NONE',0,19,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (20,null,null,null,'Valor a pignorar',7,'NONE',0,20,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (21,null,null,null,'Valor pignorado',8,'NONE',0,21,4);
INSERT FieldDefinition (id,finalPosition,formatDate,initialPosition,label,fieldOrder,footerOperation,generateEvidence,fieldCatalog_id,lineDefinition_id) VALUES (22,null,null,null,'Resultado de pignoración',9,'NONE',0,22,4);

--changeset jocampo:05
--comment:Se modifica tamaño de campo en la tabla SolicitudLiquidacionSubsidio
ALTER TABLE SolicitudLiquidacionSubsidio ALTER COLUMN slsTipoLiquidacion VARCHAR(33) NOT NULL;

--changeset jocampo:06
--comment:Se actualizan registros en la tabla SolicitudLiquidacionSubsidio
UPDATE SolicitudLiquidacionSubsidio SET slsRazonRechazoLiquidacion ='RAZON1' WHERE slsRazonRechazoLiquidacion ='NO_CONFORME_CON_RESULTADOS';

--changeset jocampo:07
--comment:Se actualizan registros en la tabla SolicitudLiquidacionSubsidio
UPDATE SolicitudLiquidacionSubsidio SET slsRazonRechazoLiquidacion = NULL WHERE slsRazonRechazoLiquidacion not in ('NO_CONFORME_CON_RESULTADOS','INCONSISTENCIAS_VALIDACIONES_FUENTES_EXTERNAS','DECISION_INTERRUMPIR_PROCESO','OTRA');

--changeset rlopez:08
--comment:Se adicionan registros en la tabla GraphicFeature
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('border','true','define si las celdas llevan borde','BOOLEAN','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('sheetname','Hoja','nombre de la hoja','STRING','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('cellMaxSize','20','indica el número de caracteres que se podran visualizar al momento de establecer el ancho de una columna','INT','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('cellBackground','9','color de fondo de una celda en un archivo XLS','INT','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('cellTypeFont','Arial','tipo de fuente de la letra de una celda de un archivo de tipo XLS','STRING','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('cellFontSize','10','tamaño de la letra de una celda de un archivo de tipo XLS','INT','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('cellFontBold','false','indica si la letra de una celda de un archivo de tipo XLS debe tener negrilla','BOOLEAN','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('cellFontItalic','false','indica si la letra de una celda de un archivo de tipo XLS debe estar en cursiva','BOOLEAN','EXCEL_XLSX',null);
INSERT GraphicFeature (name,defaultValue,description,dataType,fileFormat,restrictions) VALUES ('cellFontColor','8','color de la letra de una celda de un archivo de tipo XLS','INT','EXCEL_XLSX',null);

--changeset rarboleda:09
--comment:Insercion de registro en la tabla ParametrizacionEjecucionProgramada
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepSegundos,pepDiaSemana,pepDiaMes,pepMes,pepAnio,pepFechaInicio,pepFechaFin,pepFrecuencia,pepEstado) VALUES ('EJECUTAR_ORQUESTADOR_STAGING_SUBSIDIO','00','00','00',NULL,NULL,NULL,NULL,NULL,NULL,'DIARIO','ACTIVO');

--changeset jocampo:10
--comment:Se eliminan las tablas SubsidioMonetarioValorPignorado y ArchivoEntidadDescuentoSubsidioPignorado
ALTER TABLE SubsidioMonetarioValorPignorado DROP CONSTRAINT FK_SubsidioMonetarioValorPignorado_smvArchivoEntidadDescuentoSubsidioPignorado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SubsidioMonetarioValorPignorado_smvTipoIdentificacionAdministrador')) ALTER TABLE SubsidioMonetarioValorPignorado DROP CONSTRAINT CK_SubsidioMonetarioValorPignorado_smvTipoIdentificacionAdministrador;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_SubsidioMonetarioValorPignorado_smvTipoIdentificacionTrabajador')) ALTER TABLE SubsidioMonetarioValorPignorado DROP CONSTRAINT CK_SubsidioMonetarioValorPignorado_smvTipoIdentificacionTrabajador;
DROP TABLE SubsidioMonetarioValorPignorado;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoEntidadDescuentoSubsidioPignorado_ardCausalAnulacion')) ALTER TABLE ArchivoEntidadDescuentoSubsidioPignorado DROP CONSTRAINT CK_ArchivoEntidadDescuentoSubsidioPignorado_ardCausalAnulacion;
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_ArchivoEntidadDescuentoSubsidioPignorado_ardEstado')) ALTER TABLE ArchivoEntidadDescuentoSubsidioPignorado DROP CONSTRAINT CK_ArchivoEntidadDescuentoSubsidioPignorado_ardEstado;
DROP TABLE ArchivoEntidadDescuentoSubsidioPignorado;