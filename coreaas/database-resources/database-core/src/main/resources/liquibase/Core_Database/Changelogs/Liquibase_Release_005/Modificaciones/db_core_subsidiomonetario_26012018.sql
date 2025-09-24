--liquibase formatted sql

--changeset rlopez:01
--comment: creación de la tabla ArchivoLiquidacionSubsidio, para dar soporte a los requisitos definidos en las historias de usuario de liquidación masiva
CREATE TABLE ArchivoLiquidacionSubsidio(
  alsId BIGINT NOT NULL IDENTITY(1,1),
  alsIdentificadorECMLiquidacion VARCHAR(255) NULL,
  alsIdentificadorECMPersonasSinDerecho VARCHAR(255) NULL,
  alsIdentificadorECMConsignacionesBancos VARCHAR(255) NULL,
  alsIdentificadorECMPagosJudiciales VARCHAR(255) NULL,
  alsSolicitudLiquidacionSubsidio BIGINT NOT NULL,
CONSTRAINT PK_ArchivoLiquidacionSubsidio_alsId PRIMARY KEY (alsId)
)
ALTER TABLE ArchivoLiquidacionSubsidio ADD CONSTRAINT FK_ArchivoLiquidacionSubsidio_alsSolicitudLiquidacionSubsidio
FOREIGN KEY (alsSolicitudLiquidacionSubsidio) REFERENCES SolicitudLiquidacionSubsidio(slsId);

--changeset dsuesca:02
--comment: creación de la tabla ParametroEjecucionLiquidacion, para dar soporte a parametros tecnicos de la ejecución de la liquidacion
CREATE TABLE dbo.ParametroEjecucionLiquidacion (
	pelId int NOT NULL IDENTITY(1,1),
	pelNombre varchar(100),
	pelValor varchar(150),
	pelDescripcion varchar(250) NOT NULL,
	CONSTRAINT PK_ParametroEjecucionLiquidacion_pelId PRIMARY KEY (pelId)
)
CREATE UNIQUE INDEX UK_ParametroEjecucionLiquidacion_prmNombre ON dbo.ParametroEjecucionLiquidacion (pelNombre)

INSERT INTO dbo.ParametroEjecucionLiquidacion (pelNombre, pelValor, pelDescripcion) VALUES('REGISTROS_POR_TRANSACCION', '1000', 'Número de registros que se copiaran entre diferentes bases de datos en una misma transacción');

--changeset dsuesca:03
--comment: Se elimna tabla ParametroEjecucionLiquidacion, (se pasa a base de datos de subsisio)
DROP TABLE dbo.ParametroEjecucionLiquidacion;

--changeset rlopez:04
--comment: parámetros para la generación del archivo de personas sin derecho, HU 317-266 Descargar archivo de personas sin derecho
INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion) VALUES('FILE_DEFINITION_ID_RESULTADOS_LIQUIDACION', '1235', 'Identificador de definición de archivo de componente FileProcessing para generación de archivo de liquidación');
INSERT INTO FileDefinitionType (id, description, name) VALUES(1235, 'Archivo con los resultados del proceso de liquidación', 'Estructura del archivo de salida de la liquidación');
INSERT INTO FileDefinition (id, decimalSeparator, nombre, tenantId, thousandsSeparator, activeHistoric, compressAll, compressEachFile, creationDate, encryptedFileExtension, encrypterClass, finalConditionsClass, nextLineSeparator, registersBlockSize, signedFileExtension, signerClass, fileDefinitionType_id, fileLocation_id) VALUES(1235, '.', 'Estructura del archivo de liquidación', null, null, null, 0, 0, null, null, null, null, null, null, null, null, 1235, null);
INSERT INTO LineCatalog (id, className, description, name, tenantId, paginated, query, queryType) VALUES(5, 'com.asopagos.subsidiomonetario.load.source.DataSourceLineLiquidacion', 'Información de la liquidación', 'Estructura archivo de liquidación', null, 0, null, null);
INSERT INTO LineDefinition (id, alternateDetail, generateLineFooter, generaterHeaderLine, lineOrder, masterField, masterFieldReference, masterLine, numberGroup, parentLine, query, required, fileDefinition_id, lineCatalog_id) VALUES(5, null, 0, 1, 1, null, null, null, null, null, null, 1, 1235, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(23, 'DATE', 'Fecha de liquidación', null, null, 'fechaLiquidacion', 'Fecha de liquidación', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(24, 'STRING', 'Tipo de identificación del empleador', null, null, 'tipoIdentificacionEmpleador', 'Tipo de identificación del Empleador', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(25, 'STRING', 'Número de identificación del empleador', null, null, 'numeroIdentificacionEmpleador', 'Número de identificación del Empleador', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(26, 'STRING', 'Nombre del empleador', null, null, 'nombreEmpleador', 'Nombre del Empleador', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(27, 'STRING', 'Clasificación de actividad económica CIIU', null, null, 'ciiu', 'CIIU', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(28, 'STRING', 'Condición agraria', null, null, 'condicionAgraria', 'Condición Agraria', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(29, 'STRING', 'Código sucursal', null, null, 'codigoSucursal', 'Código sucursal', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(30, 'INT', 'Año del beneficio 1429', null, null, 'anioBeneficio1429', 'Año del beneficio 1429', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(31, 'STRING', 'Tipo de identificación del trabajador', null, null, 'tipoIdentificacionTrabajador', 'Tipo de identificación del Trabajador', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(32, 'STRING', 'Número de identificación del trabajador', null, null, 'numeroIdentificacionTrabajador', 'Número de identificación del Trabajador', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(33, 'STRING', 'Nombre del trabajador', null, null, 'nombreTrabajador', 'Nombre del Trabajador', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(34, 'STRING', 'Tipo de identificación del beneficiario', null, null, 'tipoIdentificacionBeneficiario', 'Tipo de identificación del Beneficiario', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(35, 'STRING', 'Número de identificación del beneficiario', null, null, 'numeroIdentificacionBeneficiario', 'Número de identificación del Beneficiario', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(36, 'STRING', 'Nombre del beneficiario', null, null, 'nombreBeneficiario', 'Nombre del Beneficiario', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(37, 'STRING', 'Tipo de solicitante', null, null, 'tipoSolicitante', 'Tipo de solicitante', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(38, 'STRING', 'Clasificación', null, null, 'clasificacion', 'Clasificación', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(39, 'BIGDECIMAL', 'Valor cuota', 5, 0, 'valorCuota', 'Valor cuota', null, null, 7, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(40, 'BIGDECIMAL', 'Descuento', 5, 0, 'descuento', 'Descuento', null, null, 7, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(41, 'BIGDECIMAL', 'Valor a pagar', 5, 0, 'valorPagar', 'Valor a pagar', null, null, 7, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(42, 'STRING', 'Invalidez', null, null, 'invalidez', 'Invalidez', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(43, 'STRING', 'Tipo de identificación del administrador de subsidio', null, null, 'tipoIdentificacionAdministrador', 'Tipo de identificación del administrador del subsidio', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(44, 'STRING', 'Número de identificación del administrador de subsidio', null, null, 'numeroIdentificacionAdministrador', 'Número de identificación del administrador del subsidio', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(45, 'STRING', 'Nombre del administrador de subsidio', null, null, 'nombreAdministrador', 'Nombre y apellido del administrador del subsidio', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(46, 'STRING', 'Código del convenio', null, null, 'codigoConvenio', 'Código del convenio', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(47, 'STRING', 'Periodo liquidado', null, null, 'periodoLiquidado', 'Periodo liquidado', null, null, null, null, 5);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(48, 'STRING', 'Tipo de periodo', null, null, 'tipoPeriodo', 'Tipo de periodo regular o retroactivo', null, null, null, null, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(23, null, 'yyyy-MM-dd', null, 'Fecha de liquidación', 1, 'NONE', 0, 23, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(24, null, null, null, 'Tipo de identificación del Empleador', 2, 'NONE', 0, 24, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(25, null, null, null, 'Número de identificación del Empleador', 3, 'NONE', 0, 25, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(26, null, null, null, 'Nombre del Empleador', 4, 'NONE', 0, 26, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(27, null, null, null, 'CIIU', 5, 'NONE', 0, 27, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(28, null, null, null, 'Condición Agraria', 6, 'NONE', 0, 28, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(29, null, null, null, 'Código sucursal', 7, 'NONE', 0, 29, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(30, null, null, null, 'Año del beneficio 1429', 8, 'NONE', 0, 30, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(31, null, null, null, 'Tipo de identificación del Trabajador', 9, 'NONE', 0, 31, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(32, null, null, null, 'Número de identificación del Trabajador', 10, 'NONE', 0, 32, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(33, null, null, null, 'Nombre del Trabajador', 11, 'NONE', 0, 33, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(34, null, null, null, 'Tipo de identificación del Beneficiario', 12, 'NONE', 0, 34, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(35, null, null, null, 'Número de identificación del Beneficiario', 13, 'NONE', 0, 35, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(36, null, null, null, 'Nombre del Beneficiario', 14, 'NONE', 0, 36, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(37, null, null, null, 'Tipo de solicitante', 15, 'NONE', 0, 37, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(38, null, null, null, 'Clasificación', 16, 'NONE', 0, 38, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(39, null, null, null, 'Valor cuota', 17, 'NONE', 0, 39, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(40, null, null, null, 'Descuento', 18, 'NONE', 0, 40, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(41, null, null, null, 'Valor a pagar', 19, 'NONE', 0, 41, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(42, null, null, null, 'Invalidez', 20, 'NONE', 0, 42, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(43, null, null, null, 'Tipo de identificación del administrador del subsidio', 21, 'NONE', 0, 43, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(44, null, null, null, 'Número de identificación del administrador del subsidio', 22, 'NONE', 0, 44, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(45, null, null, null, 'Nombre del administrador del subsidio', 23, 'NONE', 0, 45, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(46, null, null, null, 'Código del convenio', 24, 'NONE', 0, 46, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(47, null, null, null, 'Periodo liquidado', 25, 'NONE', 0, 47, 5);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(48, null, null, null, 'Tipo de periodo regular o retroactivo', 26, 'NONE', 0, 48, 5);

--changeset rlopez:05
--comment: parámetros para la generación del archivo de liquidación, HU 311-442 Descargar archivo de liquidación
INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion) VALUES('FILE_DEFINITION_ID_PERSONAS_SIN_DERECHO', '1236', 'Identificador de definición de archivo de componente FileProcessing para generación de archivo de personas sin derecho');
INSERT INTO FileDefinitionType (id, description, name) VALUES(1236, 'Archivo con los resultados del proceso de liquidación', 'Estructura del archivo de salida de la liquidación');
INSERT INTO FileDefinition (id, decimalSeparator, nombre, tenantId, thousandsSeparator, activeHistoric, compressAll, compressEachFile, creationDate, encryptedFileExtension, encrypterClass, finalConditionsClass, nextLineSeparator, registersBlockSize, signedFileExtension, signerClass, fileDefinitionType_id, fileLocation_id) VALUES(1236, '.', 'Estructura del archivo de personas sin derecho', null, null, null, 0, 0, null, null, null, null, null, null, null, null, 1236, null);
INSERT INTO LineCatalog (id, className, description, name, tenantId, paginated, query, queryType) VALUES(6, 'com.asopagos.subsidiomonetario.load.source.DataSourceLineSinDerecho', 'Información de la persona sin derecho', 'Estructura archivo de personas sin derecho', null, 0, null, null);
INSERT INTO LineDefinition (id, alternateDetail, generateLineFooter, generaterHeaderLine, lineOrder, masterField, masterFieldReference, masterLine, numberGroup, parentLine, query, required, fileDefinition_id, lineCatalog_id) VALUES(6, null, 0, 1, 1, null, null, null, null, null, null, 1, 1236, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(49, 'DATE', 'Fecha de liquidación', null, null, 'fechaLiquidacion', 'Fecha de liquidación', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(50, 'STRING', 'Tipo de liquidación', null, null, 'tipoLiquidacion', 'Tipo de liquidación', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(51, 'STRING', 'Subtipo de liquidación', null, null, 'subtipoLiquidacion', 'Subtipo de liquidación', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(52, 'STRING', 'Tipo de identificación del empleador', null, null, 'tipoIdentificacionEmpleador', 'Tipo de identificación del Empleador', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(53, 'STRING', 'Número de identificación del empleador', null, null, 'numeroIdentificacionEmpleador', 'Número de identificación del Empleador', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(54, 'STRING', 'Nombre del empleador', null, null, 'nombreEmpleador', 'Nombre del Empleador', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(55, 'STRING', 'Clasificación de actividad económica CIIU', null, null, 'ciiu', 'CIIU', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(56, 'STRING', 'Condición agraria', null, null, 'condicionAgraria', 'Condición Agraria', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(57, 'STRING', 'Código sucursal', null, null, 'codigoSucursal', 'Código sucursal', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(58, 'INT', 'Año del beneficio 1429', null, null, 'anioBeneficio1429', 'Año del beneficio 1429', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(59, 'STRING', 'Tipo de identificación del trabajador', null, null, 'tipoIdentificacionTrabajador', 'Tipo de identificación del Trabajador', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(60, 'STRING', 'Número de identificación del trabajador', null, null, 'numeroIdentificacionTrabajador', 'Número de identificación del Trabajador', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(61, 'STRING', 'Nombre del trabajador', null, null, 'nombreTrabajador', 'Nombre del Trabajador', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(62, 'STRING', 'Tipo de solicitante', null, null, 'tipoSolicitante', 'Tipo de solicitante', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(63, 'STRING', 'Clasificación', null, null, 'clasificacion', 'Clasificación', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(64, 'STRING', 'Causal de no cumplimiento', null, null, 'razonesSinDerecho', 'Causal de no cumplimiento', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(65, 'STRING', 'Periodo liquidado', null, null, 'periodoLiquidado', 'Periodo liquidado', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(66, 'STRING', 'Tipo de periodo', null, null, 'tipoPeriodo', 'Tipo de periodo regular o retroactivo', null, null, null, null, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(49, null, 'yyyy-MM-dd', null, 'Fecha de liquidación', 1, 'NONE', 0, 49, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(50, null, null, null, 'Tipo de liquidación', 2, 'NONE', 0, 50, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(51, null, null, null, 'Subtipo de liquidación', 3, 'NONE', 0, 51, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(52, null, null, null, 'Tipo de identificación del Empleador', 4, 'NONE', 0, 52, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(53, null, null, null, 'Número de identificación del Empleador', 5, 'NONE', 0, 53, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(54, null, null, null, 'Nombre del Empleador', 6, 'NONE', 0, 54, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(55, null, null, null, 'CIIU', 7, 'NONE', 0, 55, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(56, null, null, null, 'Condición Agraria', 8, 'NONE', 0, 56, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(57, null, null, null, 'Código sucursal', 9, 'NONE', 0, 57, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(58, null, null, null, 'Año del beneficio 1429', 10, 'NONE', 0, 58, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(59, null, null, null, 'Tipo de identificación del Trabajador', 11, 'NONE', 0, 59, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(60, null, null, null, 'Número de identificación del Trabajador', 12, 'NONE', 0, 60, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(61, null, null, null, 'Nombre del Trabajador', 13, 'NONE', 0, 61, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(62, null, null, null, 'Tipo de solicitante', 14, 'NONE', 0, 62, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(63, null, null, null, 'Clasificación', 15, 'NONE', 0, 63, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(64, null, null, null, 'Causal de no cumplimiento', 16, 'NONE', 0, 64, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(65, null, null, null, 'Periodo liquidado', 17, 'NONE', 0, 65, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(66, null, null, null, 'Tipo de periodo liquidado', 18, 'NONE', 0, 66, 6);

--changeset rlopez:06
--comment: se ajusta parametrizacion de la generacion del archivo xls de personas sin derecho y el archivo de liquidacion
UPDATE FieldCatalog SET maxDecimalSize = 0, minDecimalSize = 0 WHERE id = 58;
UPDATE FieldCatalog SET maxDecimalSize = 0, minDecimalSize = 0 WHERE id = 30;
UPDATE GraphicFeature SET name = 'sheetName' WHERE name = 'sheetname' AND id = 4;

--changeset rlopez:07
--comment: se ajusta parametrizacion de la generacion del archivo xls de personas sin derecho y el archivo de liquidacion
UPDATE FieldCatalog SET roundMode = 7 WHERE id = 58
UPDATE FieldCatalog SET roundMode = 7 WHERE id = 30

--changeset rlopez:08
--comment: se ajusta parametrizacion para agregar marca de fecha de dispersion de la liquidacion (pagos)
ALTER TABLE SolicitudLiquidacionSubsidio ADD slsFechaDispersion DATE;

--changeset rlopez:09
--comment: se ajusta tipo de fecha
ALTER TABLE SolicitudLiquidacionSubsidio ALTER COLUMN slsFechaDispersion DATETIME;

--changeset rlopez:10
--comment: Se ajusta parametrizacion de personas sin derecho
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(67, 'STRING', 'Tipo de identificación del beneficiario', null, null, 'tipoIdentificacionBeneficiario', 'Tipo de identificación del beneficiario', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(68, 'STRING', 'Número de identificación del beneficiario', null, null, 'numeroIdentificacionBeneficiario', 'Número de identificación del beneficiario', null, null, null, null, 6);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(69, 'STRING', 'Nombre del beneficiario', null, null, 'nombreBeneficiario', 'Nombre del beneficiario', null, null, null, null, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(67, null, null, null, 'Tipo de identificación del beneficiario', 14, 'NONE', 0, 67, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(68, null, null, null, 'Número de identificación del beneficiario', 15, 'NONE', 0, 68, 6);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(69, null, null, null, 'Nombre del beneficiario', 16, 'NONE', 0, 69, 6);
UPDATE FieldDefinition SET fieldOrder = 17 WHERE id = 62;
UPDATE FieldDefinition SET fieldOrder = 18 WHERE id = 63;
UPDATE FieldDefinition SET fieldOrder = 19 WHERE id = 64;
UPDATE FieldDefinition SET fieldOrder = 20 WHERE id = 65;
UPDATE FieldDefinition SET fieldOrder = 21 WHERE id = 66;

--changeset rlopez:11
--comment: Parametrización de archivo para dispersar a medios de pago de subsidio
INSERT INTO Constante (cnsNombre, cnsValor, cnsDescripcion) VALUES('FILE_DEFINITION_ID_DISPERSION_BANCOS', '1237', 'Identificador de definición de archivo de componente FileProcessing para generación de archivo de dispersión a bancos');
INSERT INTO FileDefinitionType (id, description, name) VALUES(1237, 'Archivo con las consignaciones o pagos judiciales a bancos', 'Estructura del archivo de salida de dispersión a bancos');
INSERT INTO FileDefinition (id, decimalSeparator, nombre, tenantId, thousandsSeparator, activeHistoric, compressAll, compressEachFile, creationDate, encryptedFileExtension, encrypterClass, finalConditionsClass, nextLineSeparator, registersBlockSize, signedFileExtension, signerClass, fileDefinitionType_id, fileLocation_id) VALUES(1237, '.', 'Estructura del archivo de consignaciones o pagos judiciales', null, null, null, 0, 0, null, null, null, null, null, null, null, null, 1237, null);
INSERT INTO LineCatalog (id, className, description, name, tenantId, paginated, query, queryType) VALUES(7, 'com.asopagos.subsidiomonetario.pagos.load.source.DataSourceLinePagoBanco', 'Información de la consignación o pago judicial', 'Estructura archivo de dispersión a bancos', null, 0, null, null);
INSERT INTO LineDefinition (id, alternateDetail, generateLineFooter, generaterHeaderLine, lineOrder, masterField, masterFieldReference, masterLine, numberGroup, parentLine, query, required, fileDefinition_id, lineCatalog_id) VALUES(7, null, 0, 0, 1, null, null, null, null, null, null, 1, 1237, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(70, 'STRING', 'Código de la empresa', null, null, 'codigoEmpresa', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(71, 'STRING', 'Número de cuenta a debitar banco sudameris', null, null, 'numeroCuentaSudameris', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(72, 'STRING', 'Tipo de cuenta banco sudameris', null, null, 'tipoCuentaSudameris', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(73, 'STRING', 'Nombre empresa', null, null, 'nombreEmpresa', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(74, 'STRING', 'Descripción general', null, null, 'descripcionGeneral', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(75, 'STRING', 'Código del banco receptor', null, null, 'codigoBancoReceptor', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(76, 'STRING', 'Tipo de cuenta receptora', null, null, 'tipoCuentaReceptora', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(77, 'STRING', 'Número de cuenta receptora - beneficiario', null, null, 'numeroCuentaReceptora', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(78, 'STRING', 'Número identificacion destinatario', null, null, 'numeroIdentificacion', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(79, 'STRING', 'Nombre del destinatario - beneficiario del pago', null, null, 'nombreDestinatario', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(80, 'STRING', 'Descripción del pago - Información adicional', null, null, 'descripcionPago', null, null, null, null, null, 7);
INSERT INTO FieldCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, label, paginated, query, roundMode, fieldGCatalog_id, idLineCatalog) VALUES(81, 'BIGDECIMAL', 'Valor del pago', 2, 0, 'valorPago', null, null, null, 7, null, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(70, 6, null, 0, 'Código de la empresa', 1, 'NONE', 0, 70, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(71, 22, null, 6, 'Número de cuenta a debitar banco sudameris', 2, 'NONE', 0, 71, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(72, 23, null, 22, 'Tipo de cuenta banco sudameris', 3, 'NONE', 0, 72, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(73, 58, null, 23, 'Nombre empresa', 4, 'NONE', 0, 73, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(74, 103, null, 58, 'Descripción general', 5, 'NONE', 0, 74, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(75, 109, null, 103, 'Código del banco receptor', 6, 'NONE', 0, 75, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(76, 110, null, 109, 'Tipo de cuenta receptora', 7, 'NONE', 0, 76, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(77, 126, null, 110, 'Número de cuenta receptora - beneficiario', 8, 'NONE', 0, 77, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(78, 138, null, 126, 'Número identificacion destinatario', 9, 'NONE', 0, 78, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(79, 173, null, 138, 'Nombre del destinatario - beneficiario del pago', 10, 'NONE', 0, 79, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(80, 253, null, 173, 'Descripción del pago - Información adicional', 11, 'NONE', 0, 80, 7);
INSERT INTO FieldDefinition (id, finalPosition, formatDate, initialPosition, label, fieldOrder, footerOperation, generateEvidence, fieldCatalog_id, lineDefinition_id) VALUES(81, 266, null, 253, 'Valor del pago', 12, 'NONE', 0, 81, 7);
INSERT INTO ProcessorCatalog (id, className, description, name, "scope", tenantId) VALUES(1, 'com.asopagos.subsidiomonetario.pagos.processor.ValorConsignacionBancoFieldProcessor', 'ValorConsignacionBancoFieldProcessor', 'Procesador del campo valor consignación', 'FIELD', NULL);
INSERT INTO ProcessorDefinition (id, fieldDefinitionId, fieldDefinitionLoadId, lineDefinitionId, lineDefinitionLoadId, processorOrder, processorCatalog_id) VALUES(1, 81, NULL, 7, NULL, 1, 1);

--changeset dsuesca:12
--comment: Se agrega identificador
ALTER TABLE dbo.CuentaAdministradorSubsidio ADD casSolicitudLiquidacionSubsidio BIGINT;
