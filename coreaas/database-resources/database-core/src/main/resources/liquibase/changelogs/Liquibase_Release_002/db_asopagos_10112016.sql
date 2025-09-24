--liquibase formatted sql

--changeset  halzate:01
--comment: Crear una tabla  HU108
CREATE TABLE GradoAcademico (
	graId smallint IDENTITY(1,1) NOT NULL,
	graNombre varchar(20) NULL,
    CONSTRAINT PK_GradoAcademico_graId PRIMARY KEY (graId) 
);
ALTER TABLE Beneficiario ADD benGradoAcademico smallint;

ALTER TABLE Beneficiario ADD CONSTRAINT FK_Beneficiario_benGradoAcademico FOREIGN KEY (benGradoAcademico) REFERENCES GradoAcademico;

--changeset  lzarate:02
--comment: Adición campos de producto no conforme
ALTER TABLE ProductoNoConforme ADD pncNombreInconsistencia VARCHAR(50);
ALTER TABLE ProductoNoConforme ADD pncDescripcionInconsistencia VARCHAR(150);

--changeset  atoro:03
--comment: bloque empleadores reintegro
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_EMPRESAS_PRESENCIAL','REINTEGRO','VALIDACION_TIEMPO_REINTEGRO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_EMPRESAS_PRESENCIAL','REINTEGRO','VALIDACION_ESTADO_CAUSAL',2,'ACTIVO');

   
--changeset  atoro:04
--comment: constante del limite de dias de reintegro
insert into Constante(cnsNombre,cnsValor) values('DIAS_REINTEGRO','30');


--changeset  mgiraldo:05
--comment: Cambio en las llaves primarias de las tablas Lion
EXECUTE('
BEGIN 
  DECLARE  @LlavePrimaria varchar(100);
  
  SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FieldCatalog''); 
  EXEC sp_rename @LlavePrimaria ,''PK_FieldCatalog_id'';


 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FieldDefinition'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_FieldDefinition_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FieldGenericCatalog'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_FieldGenericCatalog_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FileDefinition'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_FileDefinition_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FileDefinitionType'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_FileDefinitionType_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FileGenerated'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_FileGenerated_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FileGeneratedLog'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_FileGeneratedLog_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''FileLocationCommon'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_FileLocationCommon_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''GraphicFeatureDefinition'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_GraphicFeatureDefinition_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''GraphicFeature'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_GraphicFeature_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''GroupFieldCatalogs'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_GroupFieldCatalogs_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''LineCatalog'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_LineCatalog_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''LineDefinition'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_LineDefinition_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''ProcessorDefinition'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_ProcessorDefinition_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''ProcessorParamValue'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_ProcessorParamValue_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''ProcessorParameter'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_ProcessorParameter_id'';

 SET @LlavePrimaria=  (SELECT CONSTRAINT_NAME  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_TYPE=''PRIMARY KEY'' AND TABLE_NAME=''ProcessorCatalog'') ;
 EXEC sp_rename @LlavePrimaria ,''PK_ProcessorCatalog_id'';

END;
');