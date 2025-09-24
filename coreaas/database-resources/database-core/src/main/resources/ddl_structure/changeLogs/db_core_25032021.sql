--liquibase formatted sql

--changeset flopez:01
--comment: Ajustes CC706
IF NOT EXISTS (SELECT 1 FROM FieldLoadCatalog WHERE name = 'codigoReferencia')
INSERT INTO FieldLoadCatalog (id, dataType, description, name, fieldOrder, tenantId) VALUES (12338, 'STRING', 'Código Referencia', 'codigoReferencia', 8, 0);
IF NOT EXISTS (SELECT 1 FROM FieldDefinitionLoad WHERE id = 12338)
INSERT INTO FieldDefinitionLoad (id, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES (12338, 'Código referencia', 0, 0, 0, 12338, 1233)

UPDATE FieldDefinitionLoad SET required = 0 WHERE id IN (12334,12335);

UPDATE FieldCatalog SET dataType = 'BIGDECIMAL', description = 'Nuevo saldo pendiente por descontar',maxDecimalSize = 5, minDecimalSize = 0, name = 'nuevoSaldoDescontar', roundMode = 7
WHERE id = 22;

UPDATE FieldDefinition SET label = 'Nuevo saldo pendiente por descontar' WHERE id = 22;

IF NOT EXISTS (SELECT 1 FROM FieldCatalog WHERE name = 'codigoReferencia')
INSERT INTO FieldCatalog(id, dataType, description, name, idLineCatalog) VALUES(82,'STRING', 'Código Referencia', 'codigoReferencia', 4);

IF NOT EXISTS (SELECT 1 FROM FieldDefinition WHERE label = 'Código Referencia')
INSERT INTO FieldDefinition(id,label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id)
VALUES(82,'Código Referencia', 10, 'NONE', 0, 82 , 4);

IF NOT EXISTS (SELECT 1 FROM FieldCatalog WHERE name = 'tipoMedioDePago')
INSERT INTO FieldCatalog(id,dataType, description, name, idLineCatalog) VALUES(83,'STRING', 'Medio de Pago del Administrador subsidio', 'tipoMedioDePago', 4);

IF NOT EXISTS (SELECT 1 FROM FieldDefinition WHERE label = 'Medio de Pago del Administrador subsidio')
INSERT INTO FieldDefinition(id,label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id)
VALUES(83,'Medio de Pago del Administrador subsidio', 11, 'NONE', 0, 83 , 4);

IF NOT EXISTS (SELECT 1 FROM FieldCatalog WHERE name = 'idDescuento')
INSERT INTO FieldCatalog(id,dataType, description, name, idLineCatalog)
VALUES(84,'STRING', 'Identificador del Descuento', 'idDescuento', 4);

IF NOT EXISTS (SELECT 1 FROM FieldDefinition WHERE label = 'Identificador del Descuento')
INSERT INTO FieldDefinition(id,label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id)
VALUES(84,'Identificador del Descuento', 12, 'NONE', 0, 84 , 4);

IF NOT EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'desCodigoReferencia' AND Object_ID = Object_ID(N'dbo.DescuentosSubsidioAsignado'))
ALTER TABLE DescuentosSubsidioAsignado ADD desCodigoReferencia VARCHAR(20);
IF NOT EXISTS (SELECT 1 FROM sys.columns  WHERE Name = N'desFechaCargueDescuento' AND Object_ID = Object_ID(N'dbo.DescuentosSubsidioAsignado'))
ALTER TABLE DescuentosSubsidioAsignado ADD desFechaCargueDescuento DATETIME;

IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.tables where table_schema = 'dbo' and table_name = 'ArchivoSalidaDescuentoSubsidio')
CREATE TABLE ArchivoSalidaDescuentoSubsidio(
	asdId BIGINT IDENTITY(1,1) NOT NULL,
	asdSolicitudLiquidacionSubsidio BIGINT,
	asdEntidadDescuento BIGINT,
	asdNombreOUT varchar(50), 
	asdCodigoIdentificacionECMSalida VARCHAR(255), 
	asdFechaGeneracion DATETIME,
	CONSTRAINT PK_ArchivoSalidaDescuentoSubsidio_asdId PRIMARY KEY (asdId)
)
IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_ArchivoSalidaDescuentoSubsidio_asdSolicitudLiquidacionSubsidio'))
ALTER TABLE ArchivoSalidaDescuentoSubsidio ADD CONSTRAINT FK_ArchivoSalidaDescuentoSubsidio_asdSolicitudLiquidacionSubsidio FOREIGN KEY (asdSolicitudLiquidacionSubsidio) REFERENCES SolicitudLiquidacionSubsidio(slsId);
IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'FK_ArchivoSalidaDescuentoSubsidio_asdEntidadDescuento'))
ALTER TABLE ArchivoSalidaDescuentoSubsidio ADD CONSTRAINT FK_ArchivoSalidaDescuentoSubsidio_asdEntidadDescuento FOREIGN KEY (asdEntidadDescuento) REFERENCES EntidadDescuento(endId);

