--liquibase formatted sql

--changeset dasanchez:01
--comment: Se eliminan tablas de auditoria
ALTER TABLE DatosRegistroValidacion_aud DROP CONSTRAINT FK_DatosRegistroValidacion_aud_REV;
DROP TABLE DatosRegistroValidacion_aud;
ALTER TABLE DatoTemporalSolicitud_aud DROP CONSTRAINT FK_DatoTemporalSolicitud_aud_REV;
DROP TABLE DatoTemporalSolicitud_aud;
ALTER TABLE DatoTemporalComunicado_aud DROP CONSTRAINT FK_DatoTemporalComunicado_aud_REV;
DROP TABLE DatoTemporalComunicado_aud;
ALTER TABLE DetalleDatosRegistroValidacion_aud DROP CONSTRAINT FK_DetalleDatosRegistroValidacion_aud_REV;
DROP TABLE DetalleDatosRegistroValidacion_aud;
ALTER TABLE FieldCatalog_aud DROP CONSTRAINT FK_FieldCatalog_aud_REV;
DROP TABLE FieldCatalog_aud;
ALTER TABLE FieldDefinition_aud DROP CONSTRAINT FK_FieldDefinition_aud_REV;
DROP TABLE FieldDefinition_aud;
ALTER TABLE FieldDefinitionLoad_aud DROP CONSTRAINT FK_FieldDefinitionLoad_aud_REV;
DROP TABLE FieldDefinitionLoad_aud;
ALTER TABLE FieldGenericCatalog_aud DROP CONSTRAINT FK_FieldGenericCatalog_aud_REV;
DROP TABLE FieldGenericCatalog_aud;
ALTER TABLE FieldLoadCatalog_aud DROP CONSTRAINT FK_FieldLoadCatalog_aud_REV;
DROP TABLE FieldLoadCatalog_aud;
ALTER TABLE FileDefinition_aud DROP CONSTRAINT FK_FileDefinition_aud_REV;
DROP TABLE FileDefinition_aud;
ALTER TABLE FileDefinitionLoad_aud DROP CONSTRAINT FK_FileDefinitionLoad_aud_REV;
DROP TABLE FileDefinitionLoad_aud;
ALTER TABLE FileDefinitionLoadType_aud DROP CONSTRAINT FK_FileDefinitionLoadType_aud_REV;
DROP TABLE FileDefinitionLoadType_aud;
ALTER TABLE FileDefinitionType_aud DROP CONSTRAINT FK_FileDefinitionType_aud_REV;
DROP TABLE FileDefinitionType_aud;
ALTER TABLE FileGenerated_aud DROP CONSTRAINT FK_FileGenerated_aud_REV;
DROP TABLE FileGenerated_aud;
ALTER TABLE FileGeneratedLog_aud DROP CONSTRAINT FK_FileGeneratedLog_aud_REV;
DROP TABLE FileGeneratedLog_aud;
ALTER TABLE FileLoaded_aud DROP CONSTRAINT FK_FileLoaded_aud_REV;
DROP TABLE FileLoaded_aud;
ALTER TABLE FileLoadedLog_aud DROP CONSTRAINT FK_FileLoadedLog_aud_REV;
DROP TABLE FileLoadedLog_aud;
ALTER TABLE FileLocationCommon_aud DROP CONSTRAINT FK_FileLocationCommon_aud_REV;
DROP TABLE FileLocationCommon_aud;
ALTER TABLE GraphicFeature_aud DROP CONSTRAINT FK_GraphicFeature_aud_REV;
DROP TABLE GraphicFeature_aud;
ALTER TABLE GraphicFeatureDefinition_aud DROP CONSTRAINT FK_GraphicFeatureDefinition_aud_REV;
DROP TABLE GraphicFeatureDefinition_aud;
ALTER TABLE GroupFC_FieldGC_aud DROP CONSTRAINT FK_GroupFC_FieldGC_aud_REV;
DROP TABLE GroupFC_FieldGC_aud;
DROP TABLE GroupFieldCatalogs_aud;
ALTER TABLE LineCatalog_aud DROP CONSTRAINT FK_LineCatalog_aud_REV;
DROP TABLE LineCatalog_aud;
ALTER TABLE LineDefinition_aud DROP CONSTRAINT FK_LineDefinition_aud_REV;
DROP TABLE LineDefinition_aud;
ALTER TABLE LineDefinitionLoad_aud DROP CONSTRAINT FK_LineDefinitionLoad_aud_REV;
DROP TABLE LineDefinitionLoad_aud;
ALTER TABLE LineLoadCatalog_aud DROP CONSTRAINT FK_LineLoadCatalog_aud_REV;
DROP TABLE LineLoadCatalog_aud;
ALTER TABLE ProcessorCatalog_aud DROP CONSTRAINT FK_ProcessorCatalog_aud_REV;
DROP TABLE ProcessorCatalog_aud;
ALTER TABLE ProcessorDefinition_aud DROP CONSTRAINT FK_ProcessorDefinition_aud_REV;
DROP TABLE ProcessorDefinition_aud;
ALTER TABLE ProcessorParameter_aud DROP CONSTRAINT FK_ProcessorParameter_aud_REV;
DROP TABLE ProcessorParameter_aud;
ALTER TABLE ProcessorParamValue_aud DROP CONSTRAINT FK_ProcessorParamValue_aud_REV;
DROP TABLE ProcessorParamValue_aud;
ALTER TABLE ValidatorCatalog_aud DROP CONSTRAINT FK_ValidatorCatalog_aud_REV;
DROP TABLE ValidatorCatalog_aud;
ALTER TABLE ValidatorDefinition_aud DROP CONSTRAINT FK_ValidatorDefinition_aud_REV;
DROP TABLE ValidatorDefinition_aud;
ALTER TABLE ValidatorParameter_aud DROP CONSTRAINT FK_ValidatorParameter_aud_REV;
DROP TABLE ValidatorParameter_aud;
ALTER TABLE ValidatorParamValue_aud DROP CONSTRAINT FK_ValidatorParamValue_aud_REV;
DROP TABLE ValidatorParamValue_aud;

--changeset squintero:02
--comment:Se adiciona campo en la tabla AporteGeneral_aud
ALTER TABLE AporteGeneral_aud ADD apgEmailAportante varchar(255) NULL;

--changeset dasanchez:03
--comment:Se elimina tabla de auditoria
ALTER TABLE parameter_aud DROP CONSTRAINT FK_parameter_aud_REV;
DROP TABLE parameter_aud;
ALTER TABLE ParamValue_aud DROP CONSTRAINT FK_ParamValue_aud_REV;
DROP TABLE ParamValue_aud;