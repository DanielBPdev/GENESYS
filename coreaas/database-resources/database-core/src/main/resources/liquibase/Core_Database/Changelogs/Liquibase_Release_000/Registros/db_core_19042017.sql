--liquibase formatted sql

--changeset jzambrano:01 
--comment: Insercion en las tablas FileDefinitionType,FileDefinition,LineCatalog,LineDefinition,FieldCatalog,FieldDefinition,GRAPHICFEATURE

INSERT FileDefinitionType (ID,DESCRIPTION,NAME) VALUES ('2','Generacion de reporte de consulta para verificar la supervivencia de una persona ','Generar reporte de consulta supervivenvia');

INSERT FileDefinition (ID,DECIMALSEPARATOR,NOMBRE,TENANTID,THOUSANDSSEPARATOR,COMPRESSALL,COMPRESSEACHFILE,ENCRYPTEDFILEEXTENSION,ENCRYPTERCLASS,finalConditionsClass,nextLineSeparator,signedFileExtension,signerClass,fileDefinitionType_id,fileLocation_id) VALUES ('2',',','Generacion de reporte de supervivencia',null,null,'0','0',null,null,null,'\r\n',null,null,'2',null);

INSERT LineCatalog (id,className,description,name,tenantId,paginated,query,queryType) VALUES ('2','com.asopagos.novedades.dto.EstructuraControlReporteSupervivenciaDataLine','Información de registro de control','Información de control',null,'0',null,null);
INSERT LineCatalog (id,className,description,name,tenantId,paginated,query,queryType) VALUES ('3','com.asopagos.novedades.dto.EstructuraReporteSupervivenciaDataLine','Información de Consulta de las personas a agregar','Información de personas',null,'0',null,null);

INSERT LineDefinition (ID,GENERATELINEFOOTER,GENERATERHEADERLINE,LINEORDER,NUMBERGROUP,REQUIRED,FILEDEFINITION_ID,LINECATALOG_ID) VALUES ('2','0','0','1',null,'1','2','2');
INSERT LineDefinition (ID,GENERATELINEFOOTER,GENERATERHEADERLINE,LINEORDER,NUMBERGROUP,REQUIRED,FILEDEFINITION_ID,LINECATALOG_ID) VALUES ('3','0','0','1',null,'1','2','3');

INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('5','STRING','Tipo de registro',null,null,'tipoRegistro',null,null,null,'2');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('6','STRING','Código de la Entidad',null,null,'codigoEntidad',null,null,null,'2');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('7','STRING','Fecha inicial del período de la información',null,null,'fechaInicial',null,null,null,'2');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('8','STRING','Fecha final del período de la información',null,null,'fechaFinal',null,null,null,'2');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('9','STRING','Total de registros relacionados en el archivo',null,null,'totalRegistros',null,null,null,'2');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('10','STRING','Nombre del Archivo de Consulta',null,null,'nombreArchivo',null,null,null,'2');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('11','STRING','Tipo de registro',null,null,'tipoRegistro',null,null,null,'3');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('12','STRING','Tipo de identificación de la persona a consultar',null,null,'tipoIdentificacion',null,null,null,'3');
INSERT FieldCatalog (ID,DATATYPE,DESCRIPTION,MAXDECIMALSIZE,MINDECIMALSIZE,NAME,PAGINATED,QUERY,ROUNDMODE,IDLINECATALOG) VALUES ('13','STRING','Número de identificación de la persona a consultar',null,null,'numeroIdentificacion',null,null,null,'3');

INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('5',null,null,'Tipo de registro','1',null,'NONE','0','5','2'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('6',null,null,'Código de la Entidad','2',null,'NONE','0','6','2'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('7',null,null,'Fecha inicial del período de la información','3',null,'NONE','0','7','2'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('8',null,null,'Fecha final del período de la información','4',null,'NONE','0','8','2'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('9',null,null,'Total de registros relacionados en el archivo','5',null,'NONE','0','9','2'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('10',null,null,'Nombre del Archivo de Consulta','6',null,'NONE','0','10','2'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('11',null,null,'Tipo de registro','1',null,'NONE','0','11','3'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('12',null,null,'Tipo Identificación','2',null,'NONE','0','12','3'); 
INSERT FieldDefinition (ID,INITIALPOSITION,FINALPOSITION,LABEL,FIELDORDER,FORMATDATE,FOOTEROPERATION,GENERATEEVIDENCE,FIELDCATALOG_ID,LINEDEFINITION_ID) VALUES ('13',null,null,'Número de identificación','3',null,'NONE','0','13','3'); 

INSERT GRAPHICFEATURE (NAME,DEFAULTVALUE,DESCRIPTION,DATATYPE,FILEFORMAT,RESTRICTIONS) VALUES ('delimitador',',','delimitador de campo','CHAR','DELIMITED_TEXT_PLAIN',null);

--changeset atoro:02 
--comment: Insercion en la tabla ValidacionProceso
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_DEPENDIENTE_WEB','CARGA_MULTIPLE_NOVEDADES','VALIDACION_AFILIADO_ACTIVO','GENERAL',1,'ACTIVO');
 