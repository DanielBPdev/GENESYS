--------------- MASIVO TRANSFERENCIA
--begin transaction-- rollback
IF NOT EXISTS (SELECT * FROM Constante WHERE cnsNombre = 'FILE_DEFINITION_ID_CERTIFICADO_MASIVO_AFILIACION')
BEGIN
    INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) 
    VALUES ('FILE_DEFINITION_ID_CERTIFICADO_MASIVO_AFILIACION',1902,'identificador archivo de componente FileProcessing para cargue certificados de afiliacion masivos');
 END
--Tabla FileDefinitionLoadType
INSERT FileDefinitionLoadType (id,description,name) VALUES (1902,'Carga de archivo certificados de afiliacion masivos','Plantilla certificados de afiliacion masivos por ccf');
--Tabla FileDefinitionLoad
INSERT FileDefinitionLoad (id,decimalseparator,nombre,tenantid,thousandsseparator,excludelines,registersread,sheetnumberxls,usecharacters,filedefinitiontype_id) VALUES (1902,'.','Estructura del archivo certificados de afiliacion masivo',null,null,0,null,null,0,1902);
--PARAMETROS DE LA LINEA
--Tabla LineLoadCatalog
INSERT LineLoadCatalog (id,className,description,name,tenantid,lineorder,lineseparator,targetentity) VALUES (1902,'com.asopagos.novedades.personas.web.load.CertificadosMasivosPersistLine','Validador de archivo certificados de afiliacion masivo','Validador de archivo',null,1,'|',null);
--Tabla LineDefinitionLoad
INSERT LineDefinitionLoad (id,identifier,numbergroup,required,requiredingroup,rollbackorder,filedefinition_id,lineloadcatalog_id) VALUES (1902,null,null,1,null,1,1902,1902);
--PARAMETROS DE LOS CAMPOS--
--Tabla FieldLoadCatalog
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000460,'STRING','Tipo de identificación del Afiliado',NULL,NULL,'tipoIdentificacionAfiliado',1,0);
INSERT FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantid) VALUES (400000461,'STRING','Número de identificación del Afiliado',NULL,NULL,'numeroIdentificacionAfiliado',2,0);

--Tabla FieldDefinitionLoad
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000460,NULL,NULL,NULL,'Tipo de identificación del Afiliado',0,0,1,400000460,1902);
INSERT FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id) VALUES (400000461,NULL,NULL,NULL,'Número de identificación del Afiliado',0,0,1,400000461,1902);
--Validador De Nombre Archivo
INSERT ValidatorCatalog (id,className,description,name,SCOPE,tenantid) VALUES (211191,'com.asopagos.novedades.personas.web.load.validator.CertificadosMasivosAfiliacionLineValidator','Validador de Linea archivo de certificados afiliacion masivo','Validador de archivo','LINE',null);
--Tabla ValidatorParameter
INSERT ValidatorParameter (id,dataType,description,mask,name,validatorcatalog_id) VALUES (211394,'STRING','Expresión regular',NULL,'fileNamePattern',211191);
--Tabla ValidadorDefinition
INSERT ValidatorDefinition (id,excludeline,stopprocess,validatororder,fielddefinition_id,filedefinitionload_id,lineDefinition_id,validatorcatalog_id,validatorprofile,state) VALUES (2110383,1,NULL,1,NULL,NULL,1902,211191,1,1);
--Tabla ValidatorParamValue
INSERT ValidatorParamValue (id,value,validatorDefinition_id,validatorParameter_id) VALUES (2111786,'CCF[0-9]{2}CERTIFICADOS[0-9]{1}_[0-9]{4}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1]).(txt|TXT)',2110383,211394);

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CargueCertificadosMasivos' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE CargueCertificadosMasivos (
        ccmId BIGINT IDENTITY PRIMARY KEY ,
        ccmIdArchivoCargueCertificadosMasivos BIGINT,
        ccmTipoIdentificacion VARCHAR(255),
        ccmNumeroIdentificacion VARCHAR(255),
        ccmFechaYHoraCargue DATETIME2 NOT NULL,
        ccmCodigoIdentificacionECM VARCHAR(255)
    )
END


IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='CargueCertificadosMasivos_aud' AND TABLE_SCHEMA = 'aud')
BEGIN
    CREATE TABLE aud.CargueCertificadosMasivos_aud (
        ccmId BIGINT ,
        ccmIdArchivoCargueCertificadosMasivos BIGINT,
        ccmTipoIdentificacion VARCHAR(255),
        ccmNumeroIdentificacion VARCHAR(255),
        ccmFechaYHoraCargue DATETIME2 NOT NULL,
        ccmCodigoIdentificacionECM VARCHAR(255),
        REVTYPE SMALLINT,
        REV INT NOT NULL,
    )
END

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ControlCertificadosMasivos' AND TABLE_SCHEMA = 'dbo')
BEGIN
    CREATE TABLE ControlCertificadosMasivos (
        ccmId BIGINT IDENTITY(1,1) PRIMARY KEY,
        ccmIdArchivoECM VARCHAR(255) NULL,
        ccmNombreArchivo VARCHAR(255) NULL,
        ccmFechaGeneracion DATETIME NULL,
        ccmIdEmpleador BIGINT NOT NULL,
        ccmTipoCertificado varchar(50),
        CONSTRAINT FK_ControlCertificado_Empleador FOREIGN KEY (ccmIdEmpleador) REFERENCES Empleador(empId)
    )
END


IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_NAME='ControlCertificadosMasivos_aud' AND TABLE_SCHEMA = 'aud')
BEGIN
    CREATE TABLE aud.ControlCertificadosMasivos_aud (
        ccmId BIGINT NOT NULL,
        ccmIdArchivoECM VARCHAR(255) NULL,
        ccmNombreArchivo VARCHAR(255) NULL,
        ccmFechaGeneracion DATETIME NULL,
        ccmIdEmpleador BIGINT NOT NULL,
        REVTYPE SMALLINT,
        REV INT NOT NULL,
        ccmTipoCertificado varchar(50),
        CONSTRAINT FK_ControlCertificado_Empleador FOREIGN KEY (ccmIdEmpleador) REFERENCES Empleador(empId)
    )
END