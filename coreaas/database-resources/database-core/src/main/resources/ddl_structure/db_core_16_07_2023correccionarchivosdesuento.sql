UPDATE LineDefinition SET fileDefinition_id=1238 WHERE lineCatalog_id=8

insert INTO FieldLoadCatalog (id,dataType,description,maxDecimalSize,minDecimalSize,name,fieldOrder,tenantId)
values 
(32133189,'STRING','Identificador transacción tercero pagador',NULL,NULL,'identificacionTransaccionTerceroPagador',1,0),
(32133190,'STRING','Tipo identificación Administrador subsidio',NULL,NULL,'tipoIdentificacionAdministradorSubsidio',2,0),
(32133191,'STRING','Número identificación Administrador subsidio',NULL,NULL,'numeroIdentificacionAdministradorSubsidio',3,0),
(32133192,'BIGDECIMAL','Valor real transacción',5,0,'valorRealTransaccion',4,0),
(32133193,'DATE','Fecha transacción',NULL,NULL,'fechaTransaccion',5,0),
(32133194,'STRING','Hora transacción',NULL,NULL,'horaTransaccion',6,0),
(32133195,'STRING','Departamento',NULL,NULL,'departamento',7,0),
(32133196,'STRING','Municipio',NULL,NULL,'municipio',8,0),
(32133197,'CHAR','Tipo subsidio',NULL,NULL,'tipoSubsidio',9,0)

IF NOT EXISTS(SELECT * FROM LineDefinitionLoad  WHERE id=1238)
begin 
	insert into LineDefinitionLoad (id,identifier,numberGroup,required,requiredInGroup,rollbackOrder,fileDefinition_id,lineLoadCatalog_id,
	excludeForValidations) values
	(1238,1,NULL,1,NULL,1,1238,1238,NULL)
end
IF NOT EXISTS(SELECT * FROM FieldDefinitionLoad  WHERE lineDefinition_id=1238)
BEGIN
	INSERT INTO FieldDefinitionLoad (id,finalPosition,formatDate,initialPosition,label,identifierLine,removeFormat,required,fieldLoadCatalog_id,lineDefinition_id)
VALUES

(32133189,NULL,NULL,NULL,'identificacionTransaccionTerceroPagador',0,0,1,32133189,1238),
(32133190,NULL,NULL,NULL,'tipoIdentificacionAdministradorSubsidio',0,0,1,32133190,1238),
(32133191,NULL,NULL,NULL,'numeroIdentificacionAdministradorSubsidio',0,0,1,32133191,1238),
(32133192,NULL,NULL,NULL,'valorRealTransaccion',0,0,1,32133192,1238),
(32133193,NULL,'dd-MM-yyyy',NULL,'fechaTransaccion',0,0,1,32133193,1238),
(32133194,NULL,NULL,NULL,'horaTransaccion',0,0,1,32133194,1238),
(32133195,NULL,NULL,NULL,'departamento',0,0,1,32133195,1238),
(32133196,NULL,NULL,NULL,'municipio',0,0,1,32133196,1238),
(32133197,NULL,NULL,NULL,'tipoSubsidio',0,0,1,32133197,1238)
END


