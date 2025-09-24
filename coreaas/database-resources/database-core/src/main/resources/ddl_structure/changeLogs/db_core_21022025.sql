--Parametros para la inactivacion y cierre de sesion usuarios
IF NOT EXISTS (SELECT 1 FROM Parametro WHERE prmNombre = 'TIEMPO_INACTIVAR_USUARIO_KEYCLOAK')
BEGIN
    INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubcategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla)
    VALUES ('TIEMPO_INACTIVAR_USUARIO_KEYCLOAK', '2d', 0, 'EJECUCION_TIMER', 'Parametro que indica los días para Inactivar un Usuario en Keycloak', 'TIME', 1);
END

IF NOT EXISTS (SELECT 1 FROM Parametro WHERE prmNombre = 'TIEMPO_INICIO_SESION_USUARIO_KEYCLOAK')
BEGIN
    INSERT INTO Parametro (prmNombre, prmValor, prmCargaInicio, prmSubcategoriaParametro, prmDescripcion, prmTipoDato, prmVisualizarPantalla)
    VALUES ('TIEMPO_INICIO_SESION_USUARIO_KEYCLOAK', '15m', 0, 'EJECUCION_TIMER', 'Parametro que indica los minutos para iniciar sesión un Usuario en Keycloak', 'TIME', 1);
END

--Tarea programada para inactivar usuarios or tiempo de inactividad
IF NOT EXISTS (SELECT * FROM ParametrizacionEjecucionProgramada WHERE pepProceso = 'INACTIVAR_AUTOMATICAMENTE_USUARIOS_POR_DIAS_DE_INACTIVIDAD')
BEGIN
INSERT INTO ParametrizacionEjecucionProgramada (
pepProceso, pepHoras, pepMinutos, pepSegundos,
pepDiaSemana, pepDiaMes, pepMes, pepAnio, pepFechaInicio,
pepFechaFin, pepFrecuencia, pepEstado) VALUES (
'INACTIVAR_AUTOMATICAMENTE_USUARIOS_POR_DIAS_DE_INACTIVIDAD',
'07', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'DIARIO', 'INACTIVO'
);
END

--Tabla para la gestion de inicio de sesion usuarios
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionActividadUsuarioKeycloak' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE GestionActividadUsuarioKeycloak (
        gaukId BIGINT PRIMARY KEY IDENTITY(1,1),
        gaukIdUsuarioKeycloak VARCHAR(100),
        gaukUltimoInicioSesion DATETIME,
        gaukDireccionIpUsuario VARCHAR(100)
    );
END

--Creacion tablas auditoria en core
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionUsuariosEmpleador_aud'  AND TABLE_SCHEMA = 'aud')
BEGIN
    CREATE TABLE aud.GestionUsuariosEmpleador_aud (
        gueId BIGINT PRIMARY KEY IDENTITY(1,1),
        gueUsuarioEditado VARCHAR(100),
        gueCampoModificado VARCHAR(100),
        gueValorAnterior VARCHAR(100),
        gueNuevoValor VARCHAR(100),
        gueFechaModificacion DATETIME,
        gueModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionUsuariosTrabajador_aud'  AND TABLE_SCHEMA = 'aud')
BEGIN
    CREATE TABLE aud.GestionUsuariosTrabajador_aud (
        gutId BIGINT PRIMARY KEY IDENTITY(1,1),
        gutUsuarioEditado VARCHAR(100),
        gutCampoModificado VARCHAR(100),
        gutValorAnterior VARCHAR(100),
        gutNuevoValor VARCHAR(100),
        gutFechaModificacion DATETIME,
        gutModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ' '  AND TABLE_SCHEMA = 'aud')
BEGIN
    CREATE TABLE aud.GestionUsuariosCcf_aud (
        gucId BIGINT PRIMARY KEY IDENTITY(1,1),
        gucUsuarioEditado VARCHAR(100),
        gucCampoModificado VARCHAR(100),
        gucValorAnterior VARCHAR(100),
        gucNuevoValor VARCHAR(100),
        gucFechaModificacion DATETIME,
        gucModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GestionUsuariosTerceros_aud'  AND TABLE_SCHEMA = 'aud')
BEGIN
    CREATE TABLE aud.GestionUsuariosTerceros_aud (
        gutrId BIGINT PRIMARY KEY IDENTITY(1,1),
        gutrUsuarioEditado VARCHAR(100),
        gutrCampoModificado VARCHAR(100),
        gutrValorAnterior VARCHAR(100),
        gutrNuevoValor VARCHAR(100),
        gutrFechaModificacion DATETIME,
        gutrModificadoPor VARCHAR(100),
        REV BIGINT NOT NULL,
        REVTYPE SMALLINT
    );
END


--ARCHIVOS MASIVOS--
--Creacion de la constante que usan la creacion de masivos--
IF NOT EXISTS (SELECT * FROM Constante WHERE cnsNombre = 'FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_EMPLEADOR')
BEGIN
    INSERT Constante (cnsNombre,cnsValor,cnsDescripcion, cnsTipoDato) 
    VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_EMPLEADOR','1240','Identificador de definición de archivo de componente FileProcessing para cargue de archivo creacion usuarios empleador masivos', 'FILE');
END

IF NOT EXISTS (SELECT * FROM Constante WHERE cnsNombre = 'FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PERSONA')
BEGIN
    INSERT Constante (cnsNombre,cnsValor,cnsDescripcion, cnsTipoDato)
   VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_PERSONA','1241','Identificador de definición de archivo de componente FileProcessing para cargue de archivo creacion usuarios persona masivos', 'FILE');
END

IF NOT EXISTS (SELECT * FROM Constante WHERE cnsNombre = 'FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CCF')
BEGIN
    INSERT Constante (cnsNombre,cnsValor,cnsDescripcion, cnsTipoDato) 
    VALUES ('FILE_DEFINITION_ID_NOVEDAD_CARGUE_INFO_CCF','1242','Identificador de definición de archivo de componente FileProcessing para cargue de archivo creacion usuarios ccf masivos', 'FILE');
END

--Insertar FileDefinitionLoadType para lectra de archivos masivos
IF NOT EXISTS (SELECT * FROM FileDefinitionLoadType WHERE name = 'Archivo creacion usuarios empleador masivo')
BEGIN
	INSERT FileDefinitionLoadType (id,name,description) 
	VALUES (1240,'Archivo creacion usuarios empleador masivo','Plantilla archivo creacion usuarios empleador masivo');
END

IF NOT EXISTS (SELECT * FROM FileDefinitionLoadType WHERE name = 'Archivo creacion usuarios persona masivo')
BEGIN
	INSERT FileDefinitionLoadType (id,name,description) 
	VALUES (1241,'Archivo creacion usuarios persona masivo','Plantilla arrchivo creacion usuarios persona masivo');
END

IF NOT EXISTS (SELECT * FROM FileDefinitionLoadType WHERE name = 'Archivo creacion usuarios ccf masivo')
BEGIN
	INSERT FileDefinitionLoadType (id,name,description) 
	VALUES (1242,'Archivo creacion usuarios ccf masivo','Plantilla archivo creacion usuarios ccf masivo');
END

--Insertar FileDefinitionLoad para lectra de archivos masivos
IF NOT EXISTS (SELECT 1 FROM FileDefinitionLoad WHERE id = 1240)
BEGIN
    INSERT INTO FileDefinitionLoad (id, decimalSeparator, nombre, tenantid, thousandsSeparator, excludeLines, registersRead, sheetNumberXls, useCharacters, fileDefinitionType_id)
    VALUES (1240, '_', 'Archivo creacion usuarios empleador masivo', NULL, NULL, 0, NULL, NULL, 0, 1240);
END

IF NOT EXISTS (SELECT 1 FROM FileDefinitionLoad WHERE id = 1241)
BEGIN
    INSERT INTO FileDefinitionLoad (id, decimalSeparator, nombre, tenantid, thousandsSeparator, excludeLines, registersRead, sheetNumberXls, useCharacters, fileDefinitionType_id)
    VALUES (1241, '_', 'Archivo creacion usuarios persona masivo', NULL, NULL, 0, NULL, NULL, 0, 1241);
END

IF NOT EXISTS (SELECT 1 FROM FileDefinitionLoad WHERE id = 1242)
BEGIN
    INSERT INTO FileDefinitionLoad (id, decimalSeparator, nombre, tenantid, thousandsSeparator, excludeLines, registersRead, sheetNumberXls, useCharacters, fileDefinitionType_id)
    VALUES (1242, '_', 'Archivo creacion usuarios ccf masivo', NULL, NULL, 0, NULL, NULL, 0, 1242);
END

--Insertar LineLoadCatalog para lectura de archivos masivos
IF NOT EXISTS (SELECT * FROM LineLoadCatalog WHERE className = 'com.asopagos.novedades.personas.web.load.CargueInfoCreacionUsuariosEmpleador')
BEGIN
	INSERT LineLoadCatalog (id,className,description,name,tenantid,lineorder,lineseparator,targetentity) 
	VALUES (1240,'com.asopagos.novedades.personas.web.load.CargueInfoCreacionUsuariosEmpleador','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',null,1,'_',null);
END

IF NOT EXISTS (SELECT * FROM LineLoadCatalog WHERE className = 'com.asopagos.novedades.personas.web.load.CargueInfoCreacionUsuariosPersona')
BEGIN
	INSERT LineLoadCatalog (id,className,description,name,tenantid,lineorder,lineseparator,targetentity) 
	VALUES (1241,'com.asopagos.novedades.personas.web.load.CargueInfoCreacionUsuariosPersona','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',null,1,'_',null);
END

IF NOT EXISTS (SELECT * FROM LineLoadCatalog WHERE className = 'com.asopagos.novedades.personas.web.load.CargueInfoCreacionUsuariosCcf')
BEGIN
	INSERT LineLoadCatalog (id,className,description,name,tenantid,lineorder,lineseparator,targetentity) 
	VALUES (1242,'com.asopagos.novedades.personas.web.load.CargueInfoCreacionUsuariosCcf','Estructura del archivo de respuesta de la consulta con los registros encontrados','Estructura de respuesta con los registros encontrados',null,1,'_',null);
END

--Tabla LineDefinitionLoad
IF NOT EXISTS (SELECT 1 FROM LineDefinitionLoad WHERE id = 1240)
BEGIN
	INSERT LineDefinitionLoad (id,identifier,numbergroup,required,requiredingroup,rollbackorder,filedefinition_id,lineloadcatalog_id, excludeForValidations) 
	VALUES (1240,null,null,1,null,1,1240,1240, NULL);
END

IF NOT EXISTS (SELECT 1 FROM LineDefinitionLoad WHERE id = 1241)
BEGIN
	INSERT LineDefinitionLoad (id,identifier,numbergroup,required,requiredingroup,rollbackorder,filedefinition_id,lineloadcatalog_id, excludeForValidations) 
	VALUES (1241,null,null,1,null,1,1241,1241, NULL);
END

IF NOT EXISTS (SELECT 1 FROM LineDefinitionLoad WHERE id = 1242)
BEGIN
	INSERT LineDefinitionLoad (id,identifier,numbergroup,required,requiredingroup,rollbackorder,filedefinition_id,lineloadcatalog_id, excludeForValidations) 
	VALUES (1242,null,null,1,null,1,1242,1242, NULL);
END

--PARAMETROS DE LOS CAMPOS--
--Tabla FieldLoadCatalog
IF NOT EXISTS (SELECT 1 FROM FieldLoadCatalog WHERE id = 400000439)
BEGIN
	INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) 
	VALUES (400000439,'STRING','Tipo de identificación del Usuario Empleador',NULL,NULL,'tipoIdentificacionUsuarioEmpleador',1,0);
END
IF NOT EXISTS (SELECT 1 FROM FieldLoadCatalog WHERE id = 400000440)
BEGIN
	INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) 
	VALUES (400000440,'STRING','Número de identificación del Usuario Empleador',NULL,NULL,'numeroIdentificacionUsuarioEmpleador',2,0);
END

IF NOT EXISTS (SELECT 1 FROM FieldLoadCatalog WHERE id = 400000441)
BEGIN
	INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) 
	VALUES (400000441,'STRING','Tipo de identificación del Usuario Persona',NULL,NULL,'tipoIdentificacionUsuarioPersona',1,0);
END
IF NOT EXISTS (SELECT 1 FROM FieldLoadCatalog WHERE id = 400000442)
BEGIN
	INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) 
	VALUES (400000442,'STRING','Número de identificación del Usuario Persona',NULL,NULL,'numeroIdentificacionUsuarioPersona',2,0);
END

IF NOT EXISTS (SELECT 1 FROM FieldLoadCatalog WHERE id = 400000443)
BEGIN
	INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) 
	VALUES (400000443,'STRING','Tipo de identificación del Usuario Ccf',NULL,NULL,'tipoIdentificacionUsuarioCcf',1,0);
END
IF NOT EXISTS (SELECT 1 FROM FieldLoadCatalog WHERE id = 400000444)
BEGIN
	INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) 
	VALUES (400000444,'STRING','Número de identificación del UsuarioCcf',NULL,NULL,'numeroIdentificacionUsuarioCcf',2,0);
END

--Tabla FieldDefinitionLoad
IF NOT EXISTS (SELECT 1 FROM FieldDefinitionLoad WHERE id = 400000439)
BEGIN
	INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) 
	VALUES (400000439,NULL,NULL,NULL,'Tipo de identificación del Usuario Empleador',0,0,1,400000439,1240);
END
IF NOT EXISTS (SELECT 1 FROM FieldDefinitionLoad WHERE id = 400000440)
BEGIN
	INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) 
	VALUES (400000440,NULL,NULL,NULL,'Número de identificación del Usuario Empleador',0,0,1,400000440,1240);
END

IF NOT EXISTS (SELECT 1 FROM FieldDefinitionLoad WHERE id = 400000441)
BEGIN
	INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) 
	VALUES (400000441,NULL,NULL,NULL,'Tipo de identificación del Usuario Persona',0,0,1,400000441,1241);
END
IF NOT EXISTS (SELECT 1 FROM FieldDefinitionLoad WHERE id = 400000442)
BEGIN
	INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) 
	VALUES (400000442,NULL,NULL,NULL,'Número de identificación del Usuario Persona',0,0,1,400000442,1241);
END

IF NOT EXISTS (SELECT 1 FROM FieldDefinitionLoad WHERE id = 400000443)
BEGIN
	INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) 
	VALUES (400000443,NULL,NULL,NULL,'Tipo de identificación del Usuario Ccf',0,0,1,400000443,1242);
END
IF NOT EXISTS (SELECT 1 FROM FieldDefinitionLoad WHERE id = 400000444)
BEGIN
	INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) 
	VALUES (400000444,NULL,NULL,NULL,'Número de identificación del UsuarioCcf',0,0,1,400000444,1242);
END

--VALIDADOR DE NOMBRE ARCHIVO--
--Tabla ValidatorCatalog
IF NOT EXISTS (SELECT 1 FROM ValidatorCatalog WHERE id = 1240)
BEGIN
	INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) 
	VALUES (1240,'com.asopagos.novedades.personas.web.load.validator.CargueInfoCreacionUsuariosEmpleadorValidator','Validador de archivo de creacion usuarios empleador','Validador de archivo','LINE',null);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorCatalog WHERE id = 1241)
BEGIN
	INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) 
	VALUES (1241,'com.asopagos.novedades.personas.web.load.validator.CargueInfoCreacionUsuariosPersonaValidator','Validador de archivo de creacion usuarios persona','Validador de archivo','LINE',null);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorCatalog WHERE id = 1242)
BEGIN
	INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) 
	VALUES (1242,'com.asopagos.novedades.personas.web.load.validator.CargueInfoCreacionUsuariosCcfValidator','Validador de archivo de creacion usuarios ccf','Validador de archivo','LINE',null);
END

--Tabla ValidatorParameter
IF NOT EXISTS (SELECT 1 FROM ValidatorParameter WHERE id = 1240)
BEGIN
	INSERT ValidatorParameter (id,dataType,description,mask,name,validatorcatalog_id) 
	VALUES (1240,'STRING','Expresión regular',NULL,'fileNamePattern',1240);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorParameter WHERE id = 1241)
BEGIN
	INSERT ValidatorParameter (id,dataType,description,mask,name,validatorcatalog_id) 
	VALUES (1241,'STRING','Expresión regular',NULL,'fileNamePattern',1241);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorParameter WHERE id = 1242)
BEGIN
	INSERT ValidatorParameter (id,dataType,description,mask,name,validatorcatalog_id) 
	VALUES (1242,'STRING','Expresión regular',NULL,'fileNamePattern',1242);
END

--Tabla ValidadorDefinition
IF NOT EXISTS (SELECT 1 FROM ValidatorDefinition WHERE id = 1240)
BEGIN
	INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id) 
	VALUES (1240,0,0,1,NULL,NULL,1240,1240);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorDefinition WHERE id = 1241)
BEGIN
	INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id) 
	VALUES (1241,0,0,1,NULL,NULL,1241,1241);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorDefinition WHERE id = 1242)
BEGIN
	INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id) 
	VALUES (1242,0,0,1,NULL,NULL,1242,1242);
END

--Tabla ValidatorParamValue
IF NOT EXISTS (SELECT 1 FROM ValidatorParamValue WHERE id = 1240)
BEGIN
	INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) 
	VALUES (1240,'CCF[0-9]{3}_GESTION_USUARIO[0-9]_[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)',1240,1240);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorParamValue WHERE id = 1241)
BEGIN
	INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) 
	VALUES (1241,'CCF[0-9]{3}_GESTION_USUARIO[0-9]_[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)',1241,1241);
END

IF NOT EXISTS (SELECT 1 FROM ValidatorParamValue WHERE id = 1242)
BEGIN
	INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) 
	VALUES (1242,'CCF[0-9]{3}_GESTION_USUARIO[0-9]_[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)',1242,1242);
END