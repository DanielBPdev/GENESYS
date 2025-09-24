--liquibase formatted sql

--changeset jocampo:01
--comment: Se agregan tablas y campos a tablas relacionados con el proceso Subsidio Monetario
--Creacion de la tabla Periodo
CREATE TABLE Periodo(
	priId BIGINT NOT NULL IDENTITY(1,1), 
	priPeriodo DATE NOT NULL,
CONSTRAINT PK_Periodo_priId PRIMARY KEY (priId)
);
ALTER TABLE Periodo ADD CONSTRAINT UK_Periodo_priPeriodo UNIQUE (priPeriodo); 

--Creacion de la tabla SolicitudLiquidacionSubsidio
CREATE TABLE SolicitudLiquidacionSubsidio(
	slsId BIGINT NOT NULL IDENTITY(1,1), 
	slsSolicitudGlobal BIGINT NOT NULL,
	slsFechaCorteAporte DATETIME NULL,
	slsFechaInicio DATETIME NULL,
	slsFechaFin DATETIME NULL,
	slsTipoLiquidacion VARCHAR(10) NOT NULL,
	slsTipoLiquidacionEspecifica VARCHAR(30) NULL,
	slsEstadoLiquidacion VARCHAR(25) NOT NULL,
	slsTipoEjecucionProceso VARCHAR(10) NOT NULL,
	slsFechaEjecucionProgramada DATETIME NULL,
	slsUsuarioEvaluacionPrimerNivel VARCHAR(50) NULL,
	slsObservacionesPrimerNivel VARCHAR(250) NULL,
	slsUsuarioEvaluacionSegundoNivel VARCHAR(50) NULL,
	slsObservacionesSegundoNivel VARCHAR(250) NULL,
	slsRazonRechazoLiquidacion VARCHAR(250) NULL,
	slsObservacionesProceso VARCHAR(250) NULL,
CONSTRAINT PK_SolicitudLiquidacionSubsidio_slsId PRIMARY KEY (slsId)
);
ALTER TABLE SolicitudLiquidacionSubsidio ADD CONSTRAINT FK_SolicitudLiquidacionSubsidio_plsSolicitudGlobal FOREIGN KEY (slsSolicitudGlobal) REFERENCES Solicitud(solId);

--Creacion de la tabla PeriodoLiquidacion
CREATE TABLE PeriodoLiquidacion(
	pelId BIGINT NOT NULL IDENTITY(1,1),
	pelSolicitudLiquidacionSubsidio BIGINT NOT NULL,
	pelPeriodo BIGINT NOT NULL,
	pelTipoPeriodo VARCHAR(10) NOT NULL,
CONSTRAINT PK_PeriodoLiquidacion_pelId PRIMARY KEY (pelId)
);
ALTER TABLE PeriodoLiquidacion ADD CONSTRAINT FK_PeriodoLiquidacion_pelPeriodo FOREIGN KEY (pelPeriodo) REFERENCES Periodo(priId);
ALTER TABLE PeriodoLiquidacion ADD CONSTRAINT FK_PeriodoLiquidacion_pelSolicitudLiquidacionSubsidio FOREIGN KEY (pelSolicitudLiquidacionSubsidio) REFERENCES SolicitudLiquidacionSubsidio(slsId);

--Creacion de la tabla EntidadDescuento
CREATE TABLE EntidadDescuento(
	endId BIGINT NOT NULL IDENTITY(1,1),
	endCodigo BIGINT NOT NULL,
	endTipo VARCHAR(10) NOT NULL,
	endEmpresa BIGINT NOT NULL,
	endPrioridad INT NOT NULL,
	endEstado VARCHAR(10) NOT NULL,
	endNombreContacto VARCHAR(250) NULL,
	endObservaciones VARCHAR(250) NULL,
	endFechaCreacion DATE NOT NULL,
CONSTRAINT PK_EntidadDescuento_endId PRIMARY KEY (endId)
);
ALTER TABLE EntidadDescuento ADD CONSTRAINT FK_EntidadDescuento_endEmpresa FOREIGN KEY (endEmpresa) REFERENCES Empresa(empId); 

--Creacion de la tabla ParametrizacionCondicionesSubsidio
CREATE TABLE ParametrizacionCondicionesSubsidio(
	pcsId BIGINT NOT NULL IDENTITY(1,1),
	pcsAnioVigenciaParametrizacion INT NOT NULL,
	pcsPeriodoInicio DATE NOT NULL,
	pcsPeriodoFin DATE NOT NULL,
	pcsValorCuotaAnualBase NUMERIC(19,5) NOT NULL,
	pcsValorCuotaAnualAgraria NUMERIC(19,5) NOT NULL,
	pcsProgramaDecreto4904 BIT NOT NULL,
	pcsRetroactivoNovInvalidez BIT NOT NULL,
	pcsRetroactivoReingresoEmpleadores BIT NOT NULL,
CONSTRAINT PK_ParametrizacionCondicionesSubsidio_pcsId PRIMARY KEY (pcsId)
);

--Creacion de la tabla ParametrizacionLiquidacionSubsidio
CREATE TABLE ParametrizacionLiquidacionSubsidio(
	plsId BIGINT NOT NULL IDENTITY(1,1),
	plsAnioVigenciaParametrizacion INT NOT NULL,
	plsPeriodoInicio DATE NOT NULL,
	plsPeriodoFin DATE NOT NULL,
	plsFactorCuotaInvalidez NUMERIC(19,5) NOT NULL,
	plsFactorPorDefuncion NUMERIC(19,5) NOT NULL,
	plsHorasTrabajadas INT NOT NULL,
CONSTRAINT PK_ParametrizacionLiquidacionSubsidio_psuId PRIMARY KEY (plsId)
);

--changeset jocampo:02
--comment: Se modifica campo de la tabla EntidadDescuento
ALTER TABLE EntidadDescuento ALTER COLUMN endEmpresa BIGINT NULL;

--changeset jocampo:03
--comment: Se modifica campo de la tabla EntidadDescuento
ALTER TABLE ParametrizacionLiquidacionSubsidio ADD pcsSMLMV NUMERIC(19,5) NULL;

--changeset rlopez:05
--comment:Insercion de registros de las tablas ParametrizacionLiquidacionSubsidio y ParametrizacionCondicionesSubsidio
INSERT ParametrizacionLiquidacionSubsidio (plsAnioVigenciaParametrizacion,plsPeriodoInicio,plsPeriodoFin,plsFactorCuotaInvalidez,plsFactorPorDefuncion,plsHorasTrabajadas,pcsSMLMV) VALUES (2016,'2016-01-02','2016-12-28',2,3,96,780000);
INSERT ParametrizacionCondicionesSubsidio (pcsAnioVigenciaParametrizacion,pcsPeriodoInicio,pcsPeriodoFin,pcsValorCuotaAnualBase,pcsValorCuotaAnualAgraria,pcsProgramaDecreto4904,pcsRetroactivoNovInvalidez,pcsRetroactivoReingresoEmpleadores) VALUES (2016,'2016-01-02','2016-12-28',25000,28000,1,1,1);

--changeset jocampo:06
--comment: Se adiciona campo en la tabla EntidadDescuento
ALTER TABLE EntidadDescuento ADD endNombre VARCHAR(250) NULL;

--changeset jocampo:07
--comment: Se adiciona constraint en la tabla EntidadDescuento
ALTER TABLE EntidadDescuento ADD CONSTRAINT UK_EntidadDescuento_endCodigo UNIQUE (endCodigo); 
ALTER TABLE EntidadDescuento ADD CONSTRAINT UK_EntidadDescuento_endPrioridad UNIQUE (endPrioridad);

--changeset jocampo:08
--comment: Insercion de registros en la tabla Periodo
INSERT Periodo (priPeriodo) VALUES ('2017-07-01');
INSERT Periodo (priPeriodo) VALUES ('2017-08-01');
INSERT Periodo (priPeriodo) VALUES ('2017-09-01');
INSERT Periodo (priPeriodo) VALUES ('2017-10-01');
INSERT Periodo (priPeriodo) VALUES ('2017-11-01');

--changeset rlopez:09
--comment: Creacion de las tablas ArchivoEntidadDescuentoSubsidioPignorado y SubsidioMonetarioValorPignorado
--Creacion de la tabla ArchivoEntidadDescuentoSubsidioPignorado
CREATE TABLE ArchivoEntidadDescuentoSubsidioPignorado(
	ardId BIGINT NOT NULL IDENTITY(1,1),
	ardFechaRecepcion DATETIME NOT NULL,
	ardFechaCargue DATETIME NULL,
	ardCodigoIdentificacionECM VARCHAR(255) NOT NULL,
	ardEntidadDescuento BIGINT NULL,
	ardPrioridadEntidadDescuento INT NULL,
	ardEstado VARCHAR(10) NULL,
	ardCausalAnulacion VARCHAR(21) NULL,
CONSTRAINT PK_ArchivoEntidadDescuentoSubsidioPignorado_ardId PRIMARY KEY (ardId)
);

--Creacion de la tabla SubsidioMonetarioValorPignorado
CREATE TABLE SubsidioMonetarioValorPignorado(
	smvId BIGINT NOT NULL IDENTITY(1,1),
	smvTipoIdentificacionTrabajador VARCHAR(20) NOT NULL,
	smvNumeroIdentificacionTrabajador VARCHAR(16) NOT NULL,
	smvNombreTrabajador VARCHAR(200) NOT NULL,
	smvTipoIdentificacionAdministrador VARCHAR(20) NOT NULL,
	smvNumeroIdentificacionAdministrador VARCHAR(16) NOT NULL,
	smvCodigoGrupoFamiliar SMALLINT NOT NULL,
	smvValorPignorar NUMERIC(19,5) NOT NULL,
	smvValorPignorado NUMERIC(19,5) NULL,
	smvArchivoEntidadDescuentoSubsidioPignorado BIGINT NOT NULL,
CONSTRAINT PK_SubsidioMonetarioValorPignorado_smvId PRIMARY KEY (smvId)
);

ALTER TABLE SubsidioMonetarioValorPignorado ADD CONSTRAINT FK_SubsidioMonetarioValorPignorado_smvArchivoEntidadDescuentoSubsidioPignorado FOREIGN KEY (smvArchivoEntidadDescuentoSubsidioPignorado) REFERENCES ArchivoEntidadDescuentoSubsidioPignorado(ardId);

--changeset jocampo:10
--comment: Insercion de registros en las tablas Parametro,FileDefinitionLoadType,FileDefinitionLoad
--PARAMETROS PARA CARGA DEL ARCHIVO
--Tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FILE_DEFINITION_ID_ENTIDADES_DESCUENTO','1233',0,'FILE_DEFINITION','identificador de definición de archivo de componente FileProcessing para cargue de archivo de entidad de descuento');
--Tabla FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,description,name) VALUES (1233,'Carga de archivo de entidad de descuento con los valores a pignorar','Plantilla descuentos definidos por las entidades de la caja');
--Tabla FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalseparator,nombre,tenantid,thousandsseparator,excludelines,registersread,sheetnumberxls,usecharacters,filedefinitiontype_id) VALUES (1233,'.','Solicitud de entidades de descuento',null,null,0,null,null,0,1233);

--PARAMETROS DE LA LINEA
--Tabla LineLoadCatalog
INSERT LineLoadCatalog (id,className,description,name,tenantid,lineorder,lineseparator,targetentity) VALUES (1233,'com.asopagos.entidaddescuento.persist.EntidadDescuentoPersistLine','Información de la solicitud de la entidad de descuento con los valores a pignorar','Información del la entidad de descuento',null,1,',',null);
--Tabla LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numbergroup,required,requiredingroup,rollbackorder,filedefinition_id,lineloadcatalog_id) VALUES (1233,null,null,1,null,1,1233,1233);

--PARAMETROS DE LOS CAMPOS--
--Tabla FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (12331,'STRING','Tipo de identificación del trabajador',NULL,NULL,'tipoidentificacionTrabajador',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (12332,'STRING','Número de identificación del trabajador',NULL,NULL,'numeroidentificacionTrabajador',2,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (12333,'STRING','Nombres y Apellidos del trabajador',NULL,NULL,'nombreTrabajador',3,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (12334,'STRING','Tipo de identificación del administrador',NULL,NULL,'tipoidentificacionAdministrador',4,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (12335,'STRING','Número de identificación del administrador',NULL,NULL,'numeroidentificacionAdministrador',5,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (12336,'STRING','Código Grupo Familiar',NULL,NULL,'codigoGrupoFamiliar',6,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (12337,'BIGDECIMAL','Valor a pignorar',5,0,'montoDescuento',7,0);

--Tabla FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (12331,NULL,NULL,NULL,'Tipo de identificación del trabajador',0,0,1,12331,1233);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (12332,NULL,NULL,NULL,'Número de identificación del trabajador',0,0,1,12332,1233);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (12333,NULL,NULL,NULL,'Nombres y apellidos del trabajador',0,0,1,12333,1233);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (12334,NULL,NULL,NULL,'Tipo de identificación del administrador',0,0,1,12334,1233);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (12335,NULL,NULL,NULL,'Número de identificación del administrador',0,0,1,12335,1233);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (12336,NULL,NULL,NULL,'Código Grupo Familiar',0,0,1,12336,1233);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (12337,NULL,NULL,NULL,'Valor a pignorar',0,0,1,12337,1233);

--Validador De Nombre Archivo
--Tabla ValidatorCatalog
INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) VALUES (12331,'com.asopagos.entidaddescuento.load.validator.EntidadDescuentoFileValidator','Validador de archivo de valores a pignorar por entidad de descuento','Validador de archivo','FILE',null);
--Tabla ValidatorParameter
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorcatalog_id) VALUES (12331,'STRING','Expresión regular',NULL,'filenamePattern',12331);
--Tabla ValidadorDefinition
INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id) VALUES (12331,1,NULL,1,NULL,1233,1233,12331);
--Tabla ValidatorParamValue
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (12331,'IN_[0-9]{4}_[0-9]{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)',12331,12331);

--Validadores de negocio para los campos de Tipo De identificación y Número de identificación
-- Tabla ValidatorCatalog
INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) VALUES (12332,'com.asopagos.entidaddescuento.load.validator.EntidadDescuentoLineValidator','Validador de línea para el archivo de la entidad de descuento','Validador de archivo','LINE',null);
-- Tabla ValidatorDefinition
INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id) VALUES (12332,1,NULL,1,NULL,NULL,1233,12332);

--changeset mosorio:11
--comment:Se adiciona campo en la tabla EntidadDescuento
ALTER TABLE EntidadDescuento ADD endNumeroCelular VARCHAR(10) NULL;

--changeset jocampo:11
--comment:Insercion de registros en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_NOMBRE_HOST', '10.77.187.3', 0, 'FTP_ARCHIVOS_DESCUENTO', 'Nombre del host correspondiente al servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_PUERTO', '21', 0, 'FTP_ARCHIVOS_DESCUENTO', 'Puerto del servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_NOMBRE_USUARIO', 'XKjfmbRakYpbOFOtnSbHsA==', 0, 'FTP_ARCHIVOS_DESCUENTO', 'Usuario del servidor FTP');
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_CONTRASENA', 'xPnc/tNg4jD9DEMLrTSY1g==', 0, 'FTP_ARCHIVOS_DESCUENTO', 'Constraseña para el usuario del servidor FTP');

--changeset jocampo:12 context:integracion runOnChange:true
--comment:Insercion de registros en la tabla Parametro
IF EXISTS (select * from parametro where prmNombre = 'FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS') update Parametro SET prmValor = '/subsidios/integracion'  where prmNombre = 'FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS'
ELSE INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS', '/subsidios/integracion', 0, 'FTP_ARCHIVOS_DESCUENTO', 'Ruta de los archivos de descuento en el servidor FTP');

--changeset jocampo:12 context:pruebas runOnChange:true
--comment:Insercion de registros en la tabla Parametro
IF EXISTS (select * from parametro where prmNombre = 'FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS') update Parametro SET prmValor = '/subsidios/pruebas'  where prmNombre = 'FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS'
ELSE INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('FTP_ARCHIVOS_DESCUENTO_URL_ARCHIVOS', '/subsidios/pruebas', 0, 'FTP_ARCHIVOS_DESCUENTO', 'Ruta de los archivos de descuento en el servidor FTP');


--changeset jocampo:14
--comment:Se modifica campo en la tabla Solicitud
ALTER TABLE Solicitud ALTER COLUMN solResultadoProceso VARCHAR(22) NULL;

--changeset rlopez:15
--comment:Se actualizan campos de las tablas FieldLoadCatalog, ValidatorParameter
UPDATE FieldLoadCatalog SET name = 'tipoIdentificacionTrabajador' WHERE id = 12331;
UPDATE FieldLoadCatalog SET name = 'numeroIdentificacionTrabajador' WHERE id = 12332;
UPDATE FieldLoadCatalog SET name = 'tipoIdentificacionAdministrador' WHERE id = 12334;
UPDATE FieldLoadCatalog SET name = 'numeroIdentificacionAdministrador' WHERE id = 12335;
UPDATE ValidatorParameter SET name = 'filenamePattern' WHERE id = 12331;

--changeset jocampo:16
--comment:Se modifica tamaño de campos en la tabla SolicitudLiquidacionSubsidio
ALTER TABLE SolicitudLiquidacionSubsidio ALTER COLUMN slsTipoLiquidacion VARCHAR(27) NOT NULL;
ALTER TABLE SolicitudLiquidacionSubsidio ALTER COLUMN slsTipoLiquidacionEspecifica VARCHAR(23) NULL;