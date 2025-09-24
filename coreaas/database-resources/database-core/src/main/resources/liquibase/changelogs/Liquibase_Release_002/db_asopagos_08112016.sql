--liquibase formatted sql

--changeset  mgiraldo:01
--comment: Cambio de nombre de las FK de Lion
EXEC sp_rename 'FK9x09l23psfgb5nx64hb6bnrnj','FK_FieldCatalog_fieldGCatalog_id';
EXEC sp_rename 'FKifk7ca5onvs5tabqsarnougt4','FK_FieldCatalog_idLineCatalog';
EXEC sp_rename 'FK9icd6f6rd5q1ypq6l6fk3obdo','FK_FieldDefinition_lineDefinition_id';
EXEC sp_rename 'FK9sfnsi9p2knem27cygpno78hm','FK_FieldDefinition_fieldCatalog_id';
EXEC sp_rename 'FK7d5bgelfa9yqkupm47pm4v282','FK_FileDefinition_fileDefinitionType_id';
EXEC sp_rename 'FKsec52q33c5ocgga6xjhoniy2q','FK_FileDefinition_fileLocation_id';
EXEC sp_rename 'FKdyb4nqogc64lup89xhni6jwaw','FK_FileGenerated_fileDefinition_id';
EXEC sp_rename 'FKseyp6vql8y2w7pjcgoxhnb46r','FK_FileGeneratedLog_fileGenerated_id';
EXEC sp_rename 'FKjqcwbyr5pvikv0xwdt9gv9kyq','FK_GraphicFeatureDefinition_lineDefinition_id';
EXEC sp_rename 'FKo9pb92o3ipmjkyb1pb6p6owbl','FK_GraphicFeatureDefinition_fieldDefinition_id';
EXEC sp_rename 'FKtih62k3if4mlvrtuvoua2mfu0','FK_GraphicFeatureDefinition_graphicFeature_id';
EXEC sp_rename 'FKtqa9kdy81au31nhyh39tv5j5x','FK_GraphicFeatureDefinition_fileDefinition_id';
EXEC sp_rename 'FK14p6ljxtgj7ocpfno5ilqt0cb','FK_GroupFC_FieldGC_groupFieldCatalogs_id';
EXEC sp_rename 'FKghg21q8ypa1ccagxpea1c0qyr','FK_GroupFC_FieldGC_fieldGenericCatalogs_id';
EXEC sp_rename 'FK7b9199swgogisrth6watqix82','FK_LineDefinition_fileDefinition_id';
EXEC sp_rename 'FK7he7vvf12wtgunxuxv69cjch3','FK_LineDefinition_lineCatalog_id';

--changeset  mgiraldo:02
--comment: Eliminación
ALTER TABLE ProcessorParamValue DROP CONSTRAINT FK3iq3cfb1t75gk0t8gly1yh176;
ALTER TABLE ProcessorParameter DROP CONSTRAINT FKe3csec2fub8fu6l0ie3y5pub;
ALTER TABLE ProcessorDefinition DROP CONSTRAINT FK6pt77ur0s9812dyulxpkwr23s;
ALTER TABLE ProcessorParamValue DROP CONSTRAINT FK9il68rsjftao6v6l3p1fp22lo;

--changeset  mgiraldo:03
--comment: Creación IDX
CREATE INDEX IDX_Persona_RazonSocial ON Persona (perRazonSocial); 

--changeset  halzate:05
--comment: Creación de el campo reqTipoRequisito
ALTER TABLE Requisito ADD reqTipoRequisito VARCHAR(30);
