--liquibase formatted sql

--changeset mosorio:01
--comment: creación de la tablas para pago de subsidios monetarios, para dar soporte a los requisitos definidos en las historias de usuario de liquidación masiva
CREATE TABLE ArchivoRetiroTerceroPagador(
	arrId BIGINT NOT NULL IDENTITY(1,1),
	arrIdentificacionDocumento VARCHAR(255) NOT NULL,
	arrVersionDocumento VARCHAR(4) NOT NULL,
	arrNombreDocumento VARCHAR(255) NOT NULL,
	arrFechaHoraProcesamiento datetime NOT NULL,
	arrUsuarioProcesamiento VARCHAR(50) NOT NULL,
	arrEstado VARCHAR(30) NULL,  

CONSTRAINT PK_ArchivoRetiroTerceroPagador_arrId PRIMARY KEY (arrId)
)

CREATE TABLE RegistroArchivoRetiroTerceroPagador(
	rarId BIGINT NOT NULL IDENTITY(1,1),
	rarIdCuentaAdminSubsidio BIGINT NULL, 
	rarArchivoRetiroTerceroPagador BIGINT NOT NULL,
	rarEstado VARCHAR(35) NULL,  
	rarIdTransaccionTerceroPagador VARCHAR(200) NOT NULL,
	rarTipoIdentificacionAdminSubsidio VARCHAR(20) NOT NULL,
	rarNumeroIdentificacionAdminSubsidio VARCHAR(16) NOT NULL,
	rarValorRealTransaccion NUMERIC(19,5) NOT NULL,
	rarFechaTransaccion VARCHAR(50) NOT NULL,
	rarHoraTransaccion VARCHAR(50) NOT NULL,
	rarCodigoDepartamento VARCHAR(2) NOT NULL,
	rarCodigoMunicipio VARCHAR(6) NOT NULL,	

CONSTRAINT PK_RegistroArchivoRetiroTerceroPagador_rarId PRIMARY KEY (rarId)
)
ALTER TABLE RegistroArchivoRetiroTerceroPagador ADD CONSTRAINT FK_RegistroArchivoRetiroTerceroPagador_rarArchivoRetiroTerceroPagador
 FOREIGN KEY (rarArchivoRetiroTerceroPagador) REFERENCES ArchivoRetiroTerceroPagador(arrId);
 
CREATE TABLE CampoArchivoRetiroTerceroPagador(
	carId BIGINT NOT NULL IDENTITY(1,1),
	carDescripcionCampo VARCHAR (50) NOT NULL, 
	carValorCampoArchivo VARCHAR(50) NOT NULL,
	carValorCampoCuentaAdminSubsidio VARCHAR(50) NOT NULL,
	carInconsistencia VARCHAR(80) NOT NULL, 
	carRegistroArchivoRetiroTerceroPagador BIGINT NOT NULL,
	
CONSTRAINT PK_CampoCargueArchivoRetiroTerceroPagador_carId PRIMARY KEY (carId)
)
ALTER TABLE CampoArchivoRetiroTerceroPagador ADD CONSTRAINT FK_CampoArchivoRetiroTerceroPagador_carRegistroArchivoRetiroTerceroPagador
 FOREIGN KEY (carRegistroArchivoRetiroTerceroPagador) REFERENCES RegistroArchivoRetiroTerceroPagador(rarId);

CREATE TABLE ConvenioTerceroPagador(
	conId BIGINT NOT NULL IDENTITY(1,1),
	conEmpresa  BIGINT NOT NULL,	
	conNombre VARCHAR(250) NOT NULL, 
	conEstado VARCHAR(10) NOT NULL,
	conMedioDePago VARCHAR(20) NOT NULL,
	conUsuarioGenesys VARCHAR (200) NOT NULL,
	conNombreCompletoContacto VARCHAR (30) NULL,
	conCargoContacto VARCHAR(30) NULL,
	conIndicativoTelFijoContacto VARCHAR(2) NULL,
	conTelefonoFijoContacto VARCHAR(7) NULL,
	conTelefonoCelularContacto VARCHAR(10) NULL,	
	conEmailContacto VARCHAR(255) NULL, 
	conAcuerdoDeFacturacion VARCHAR(255) NOT NULL,	

CONSTRAINT PK_ConvenioTerceroPagador_conId PRIMARY KEY (conId)
)
ALTER TABLE ConvenioTerceroPagador ADD CONSTRAINT FK_ConvenioTerceroPagador_conEmpresa
 FOREIGN KEY (conEmpresa) REFERENCES Empresa(empId);
 

CREATE TABLE CuentaAdministradorSubsidio(
	casId BIGINT NOT NULL IDENTITY(1,1),	
	casFechaHoraCreacionRegistro DATETIME NOT NULL, 
	casUsuarioCreacionRegistro VARCHAR (200) NOT NULL,
	casTipoTransaccionSubsidio VARCHAR(40) NOT NULL,
	casEstadoTransaccionSubsidio VARCHAR(25) NULL,
	casEstadoLiquidacionSubsidio VARCHAR(25) NULL,
	casOrigenTransaccion VARCHAR(30) NOT NULL,
	casMedioDePagoTransaccion VARCHAR(13) NOT NULL,
	casNumeroTarjetaAdmonSubsidio VARCHAR(50) NULL,
	casCodigoBanco VARCHAR(4) NULL,
	casNombreBanco VARCHAR(200) NULL,
	casTipoCuentaAdmonSubsidio VARCHAR(30) NULL,
	casNumeroCuentaAdmonSubsidio VARCHAR(30) NULL,
	casTipoIdentificacionTitularCuentaAdmonSubsidio VARCHAR(20) NULL,
	casNumeroIdentificacionTitularCuentaAdmonSubsidio VARCHAR(20) NULL,
	casNombreTitularCuentaAdmonSubsidio VARCHAR(200),
	casFechaHoraTransaccion datetime NOT NULL, 
	casUsuarioTransaccion VARCHAR(200) NOT NULL,
	casValorOriginalTransaccion NUMERIC(19,5) NOT NULL,
	casValorRealTransaccion NUMERIC(19,5) NOT NULL,
	casIdTransaccionOriginal BIGINT NULL,
	casIdRemisionDatosTerceroPagador VARCHAR(200) NULL,
	casIdTransaccionTerceroPagador VARCHAR(200) NULL,
	casNombreTerceroPagado VARCHAR(200) NULL,
	casIdCuentaAdmonSubsidioRelacionado BIGINT NULL,
	casFechaHoraUltimaModificacion datetime NULL,
	casUsuarioUltimaModificacion VARCHAR (200) NULL,	
	
	casIdAdministradorSubsidio BIGINT NOT NULL,	
	casIdSitioDePago  BIGINT NULL,
	casIdSitioDeCobro  BIGINT NULL,

CONSTRAINT PK_CuentaAdministradorSubsidio_casId PRIMARY KEY (casId)
)
ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT FK_CuentaAdministradorSubsidio_casIdAdministradorSubsidio
 FOREIGN KEY (casIdAdministradorSubsidio) REFERENCES AdministradorSubsidio(asuId);
ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT FK_CuentaAdministradorSubsidio_casIdSitioDePago
 FOREIGN KEY (casIdSitioDePago) REFERENCES SitioPago(sipId);
ALTER TABLE CuentaAdministradorSubsidio ADD CONSTRAINT FK_CuentaAdministradorSubsidio_casIdSitioDeCobro
 FOREIGN KEY (casIdSitioDeCobro) REFERENCES SitioPago(sipId);
  
CREATE TABLE DetalleSubsidioAsignado(
	dsaId BIGINT NOT NULL IDENTITY(1,1),
	dsaUsuarioCreador VARCHAR (200) NOT NULL,
	dsaFechaHoraCreacion datetime NOT NULL, 
	dsaEstado VARCHAR (20) NOT NULL, 
	dsaMotivoAnulacion VARCHAR(40) NULL, 
	dsaDetalleAnulacion VARCHAR(250) NULL,
	dsaOrigenRegistroSubsidio VARCHAR(30) NOT NULL,  
	dsaTipoliquidacionSubsidio VARCHAR (60) NOT NULL, 
	dsaTipoCuotaSubsidio VARCHAR (80) NOT NULL, 
	dsaValorSubsidioMonetario NUMERIC(19,5) NOT NULL,	
	dsaValorDescuento NUMERIC(19,5) NOT NULL,
	dsaValorOriginalAbonado NUMERIC (19,5) NOT NULL,
	dsaValorTotal NUMERIC(19,5) NOT NULL,
	dsaFechaTransaccionRetiro DATE  NULL,
	dsaUsuarioTransaccionRetiro VARCHAR (200) NULL,
	dsaFechaTransaccionAnulacion DATE  NULL,
	dsaUsuarioTransaccionAnulacion VARCHAR (200) NULL,
	dsaFechaHoraUltimaModificacion DATETIME NULL,
	dsaUsuarioUltimaModificacion VARCHAR(200) NULL,	
	
	dsaIdSolicitudLiquidacionSubsidio BIGINT NOT NULL,
	dsaIdEmpleador BIGINT NOT NULL,
	dsaIdAfiliadoPrincipal BIGINT NOT NULL,
	dsaIdGrupoFamiliar BIGINT NOT NULL,
	dsaIdBeneficiario BIGINT NOT NULL, 
	dsaIdAdministradorSubsidio BIGINT NOT NULL,
	dsaIdRegistroOriginalRelacionado BIGINT,
	dsaIdCuentaAdministradorSubsidio BIGINT NOT NULL,

CONSTRAINT PK_DetalleSubsidioAsignado_dsaId PRIMARY KEY (dsaId)
)
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdSolicitudLiquidacionSubsidio
 FOREIGN KEY (dsaIdSolicitudLiquidacionSubsidio) REFERENCES SolicitudLiquidacionSubsidio(slsId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdEmpleador
 FOREIGN KEY (dsaIdEmpleador) REFERENCES Empleador(empId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdAfiliadoPrincipal
 FOREIGN KEY (dsaIdAfiliadoPrincipal) REFERENCES Afiliado(afiId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdGrupoFamiliar
 FOREIGN KEY (dsaIdGrupoFamiliar) REFERENCES GrupoFamiliar(grfId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdBeneficiario
 FOREIGN KEY (dsaIdBeneficiario) REFERENCES Beneficiario(benId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdAdministradorSubsidio
 FOREIGN KEY (dsaIdAdministradorSubsidio) REFERENCES AdministradorSubsidio(asuId);
ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdRegistroOriginalRelacionado
 FOREIGN KEY (dsaIdRegistroOriginalRelacionado) REFERENCES DetalleSubsidioAsignado(dsaId);
 ALTER TABLE DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdCuentaAdministradorSubsidio
 FOREIGN KEY (dsaIdCuentaAdministradorSubsidio) REFERENCES CuentaAdministradorSubsidio(casId);
 
CREATE TABLE TransaccionesFallidasSubsidio(
	tfsId BIGINT NOT NULL IDENTITY(1,1),
	tfsFechaHoraRegistro DATETIME NOT NULL, 
	tfsCanal VARCHAR(50) NOT NULL, 
	tfsEstadoResolucion VARCHAR(9) NOT NULL,
	tfsResultadoGestion VARCHAR(11) NULL,	
	tfsAccionesRealizadaParaResolverCaso VARCHAR (200) NULL,
	tfsTipoTransaccionPagoSubsidio VARCHAR (60) NOT NULL,
	
	tfsIdCuentaAdmonSubsidio  BIGINT NOT NULL,

CONSTRAINT PK_TransaccionesFallidasSubsidio_tfsId PRIMARY KEY (tfsId)
)
ALTER TABLE TransaccionesFallidasSubsidio ADD CONSTRAINT FK_TransaccionesFallidasSubsidio_tfsIdCuentaAdmonSubsidio
 FOREIGN KEY (tfsIdCuentaAdmonSubsidio) REFERENCES CuentaAdministradorSubsidio(casId);
 CREATE UNIQUE INDEX UK_TransaccionesFallidasSubsidio_tfsIdCuentaAdmonSubsidio ON TransaccionesFallidasSubsidio(tfsIdCuentaAdmonSubsidio);
  
CREATE TABLE RegistroOperacionesSubsidio(
	rosId BIGINT NOT NULL IDENTITY(1,1),
	rosIdAdministradorSubsidio BIGINT NULL,
	rosTipoOperacion VARCHAR(65) NOT NULL, 
	rosFechaHoraOperacion datetime NOT NULL,
	rosUsuarioOperacion VARCHAR(30) NOT NULL,	
	rosParametrosIn VARCHAR(500) NOT NULL,
	rosParametrosOut VARCHAR(255) NULL	

CONSTRAINT PK_RegistroOperacionesSubsidio_rosId PRIMARY KEY (rosId)
)
ALTER TABLE RegistroOperacionesSubsidio ADD CONSTRAINT FK_RegistroOperacionesSubsidio_rosIdAdministradorSubsidio
 FOREIGN KEY (rosIdAdministradorSubsidio) REFERENCES AdministradorSubsidio(asuId);

CREATE TABLE RetiroPersonaAutorizadaCobroSubsidio(
	rpaId BIGINT NOT NULL IDENTITY(1,1),
	rosTipoOperacion VARCHAR(255) NULL, 
	rpaIdDocumentoSoporte VARCHAR(255) NULL,
	rpaVersionDocumentoSoporte VARCHAR(255) NULL,
	rpaIdPersonaAutorizada BIGINT NOT NULL,
	rpaIdCuentaAdministradorSubsidio BIGINT NOT NULL, 

CONSTRAINT PK_RetiroPersonaAutorizadaCobroSubsidio_rpaId PRIMARY KEY (rpaId)
)
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio ADD CONSTRAINT FK_RetiroPersonaAutorizadaCobroSubsidio_rpaIdPersonaAutorizada
 FOREIGN KEY (rpaIdPersonaAutorizada) REFERENCES Persona(perId);
ALTER TABLE RetiroPersonaAutorizadaCobroSubsidio ADD CONSTRAINT FK_RetiroPersonaAutorizadaCobroSubsidio_rpaIdCuentaAdministradorSubsidio
 FOREIGN KEY (rpaIdCuentaAdministradorSubsidio) REFERENCES  CuentaAdministradorSubsidio(casId);
 

CREATE TABLE DescuentosSubsidioAsignado(
	desId BIGINT NOT NULL IDENTITY(1,1),	
	desIdDetalleSubsidioAsignado  BIGINT NOT NULL,
	desIdEntidadDescuento BIGINT NOT NULL,
	desMontoDescontado NUMERIC(19,5) NULL,

CONSTRAINT PK_DescuentosSubsidioAsignado_desId PRIMARY KEY (desId)
)
ALTER TABLE DescuentosSubsidioAsignado ADD CONSTRAINT FK_DescuentosSubsidioAsignado_desIdDetalleSubsidioAsignado
 FOREIGN KEY (desIdDetalleSubsidioAsignado) REFERENCES DetalleSubsidioAsignado(dsaId);
ALTER TABLE DescuentosSubsidioAsignado ADD CONSTRAINT FK_DescuentosSubsidioAsignado_desIdEntidadDescuento
 FOREIGN KEY (desIdEntidadDescuento) REFERENCES EntidadDescuento (endId); 
 
 
CREATE TABLE SolicitudAnulacionSubsidioCobrado(
	sasId BIGINT NOT NULL IDENTITY(1,1),
	sasFechaHoraCreacionSolicitud datetime NOT NULL, 
	sasEstadoSolicitud VARCHAR(20) NOT NULL,
	sasMotivoAnulacion VARCHAR(35) NOT NULL,
	
	sasSolicitudGlobal  BIGINT NOT NULL,

CONSTRAINT PK_SolicitudAnulacionSubsidio_sasId PRIMARY KEY (sasId)
)
ALTER TABLE SolicitudAnulacionSubsidio ADD CONSTRAINT FK_SolicitudAnulacionSubsidio_sasSolicitudGlobal
 FOREIGN KEY (sgcSolicitudGlobal) REFERENCES Solicitud(solId);

CREATE TABLE DetalleSolicitudAnulacionSubsidioCobrado(
	dssId BIGINT NOT NULL IDENTITY(1,1),
	dssIdSolicitudAnulacionSubsidio BIGINT NOT NULL,	
	dssIdCuentaAdministradorSubisdio BIGINT NOT NULL,
	dssDetalleAnulacion VARCHAR (250) NOT NULL,	
	

CONSTRAINT PK_DetalleSolicitudAnulacionSubsidioCobrado_dssId PRIMARY KEY (dssId)
)
ALTER TABLE DetalleSolicitudAnulacionSubsidioCobrado ADD CONSTRAINT FK_DetalleSolicitudAnulacionSubsidioCobrado_dssIdSolicitudAnulacionSubsidio
 FOREIGN KEY (dssIdSolicitudAnulacionSubsidio) REFERENCES SolicitudAnulacionSubsidioCobrado(sasId);
ALTER TABLE DetalleSolicitudAnulacionSubsidioCobrado ADD CONSTRAINT FK_DetalleSolicitudAnulacionSubsidioCobrado_dssIdCuentaAdministradorSubisdio
 FOREIGN KEY (dssIdCuentaAdministradorSubisdio) REFERENCES CuentaAdministradorSubsidio(casId); 
 
--changeset mosorio:02
--comment: Insersión de parametrización del componetne de procesamiento de archivos    
INSERT Constante(cnsNombre,cnsValor,cnsDescripcion) values('FILE_DEFINITION_ID_ARCHIVO_RETIRO_PAGOS_SUBSIDIO_MONETARIO', '1224', 'Identificador del cargue de archivos del retiro tercero pagador');
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) values (1224,'Carga de archivo de conciliación de retiro del tercero pagador subsidio monetario', 'Conciliación de retiro del tercer pagador subsidio monetario');
INSERT FILEDEFINITIONLOAD (ID,DECIMALSEPARATOR,NOMBRE,TENANTID,THOUSANDSSEPARATOR,EXCLUDELINES,REGISTERSREAD,SHEETNUMBERXLS,USECHARACTERS,FILEDEFINITIONTYPE_ID) values (1224,'.','Solicitud conciliación retiros del tercer pagador subsidio monetario',null,null,1,null,null,0,1224);
INSERT LINELOADCATALOG (ID,CLASSNAME,DESCRIPTION,NAME,TENANTID,LINEORDER,LINESEPARATOR,TARGETENTITY) values (1234,'com.asopagos.subsidiomonetario.pagos.persist.PagosSubsidioMonetarioPersistLine','Información de la solicitud del convenio del tercero pagador','Información del convenio del tercero pagador',null,1,',',null);
INSERT LINEDEFINITIONLOAD (ID,IDENTIFIER,NUMBERGROUP,REQUIRED,REQUIREDINGROUP,ROLLBACKORDER,FILEDEFINITION_ID,LINELOADCATALOG_ID) values (1234,'1',null,1,null,1,1224,1234);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250000, 'STRING', 'Identificador transacción tercero pagador', NULL, NULL, 'identificacionTransaccionTerceroPagador', 1, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250001, 'STRING', 'Tipo identificación Administrador subsidio', NULL, NULL, 'tipoIdentificacionAdministradorSubsidio', 2, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250002, 'STRING', 'Número identificación Administrador subsidio', NULL, NULL, 'numeroIdentificacionAdministradorSubsidio', 3, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250003, 'BIGDECIMAL', 'Valor real transacción', 5, 0, 'valorRealTransaccion', 4, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250004, 'DATE', 'Fecha transacción', NULL, NULL, 'fechaTransaccion', 5, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250005, 'STRING', 'Hora transacción', NULL, NULL, 'horaTransaccion', 6, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250006, 'STRING', 'Departamento', NULL, NULL, 'departamento', 7, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250007, 'STRING', 'Municipio', NULL, NULL, 'municipio', 8, 0);
INSERT FieldLoadCatalog (id, dataType, description, maxDecimalSize, minDecimalSize, name, fieldOrder, tenantId) VALUES(250008, 'CHAR', 'Tipo subsidio', NULL, NULL, 'tipoSubsidio', 9, 0);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250000, NULL, NULL, NULL, 'identificacionTransaccionTerceroPagador', 0, 0, 1, 250000, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250001, NULL, NULL, NULL, 'tipoIdentificacionAdministradorSubsidio', 0, 0, 1, 250001, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250002, NULL, NULL, NULL, 'numeroIdentificacionAdministradorSubsidio', 0, 0, 1, 250002, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250003, NULL, NULL, NULL, 'valorRealTransaccion', 0, 0, 1, 250003, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250004, NULL, 'dd-MM-yyyy', NULL, 'fechaTransaccion', 0, 0, 1, 250004, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250005, NULL, NULL, NULL, 'horaTransaccion', 0, 0, 1, 250005, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250006, NULL, NULL, NULL, 'departamento', 0, 0, 1, 250006, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250007, NULL, NULL, NULL, 'municipio', 0, 0, 1, 250007, 1234);
INSERT FieldDefinitionLoad (id, finalPosition, formatDate, initialPosition, label, identifierLine, removeFormat, required, fieldLoadCatalog_id, lineDefinition_id) VALUES(250008, NULL, NULL, NULL, 'tipoSubsidio', 0, 0, 1, 250008, 1234);
INSERT VALIDATORCATALOG (ID,CLASSNAME,DESCRIPTION,NAME,SCOPE,TENANTID) values (1234,'com.asopagos.subsidiomonetario.pagos.load.validator.PagosSubsidioMonetarioLineValidator','validador de línea de carga de solicitud de conciliación de retiros del tercer pagador', 'Validador línea carga conciliación retiros del tercer pagador','LINE',null);
INSERT VALIDATORDEFINITION (ID,EXCLUDELINE,STOPPROCESS,VALIDATORORDER,FIELDDEFINITION_ID,FILEDEFINITIONLOAD_ID,LINEDEFINITION_ID,VALIDATORCATALOG_ID) values (1234,1,null,1,null,null,1234,1234); 
INSERT parametro (prmNombre,prmValor,prmSubCategoriaParametro,prmDescripcion) values('PARAM_DIAS_VENCIMIENTO_SUBSIDIO_MONETARIO_CCF','2','CAJA_COMPENSACION','Parámetro que contiene el número de días requeridos para que un subsidio sea anulado por fecha de vencimiento');
INSERT parametro (prmNombre,prmValor,prmSubCategoriaParametro,prmDescripcion) values('PARAM_DIAS_PRESCRIPCION_SUBSIDIO_MONETARIO_CCF','2','CAJA_COMPENSACION',' Parámetro que contiene el número de días requeridos para que un subsidio sea anulado por prescripcion');
 
--changeset dsuesca:03
--comment: Se agraga campo identificador de medio de pago
ALTER TABLE dbo.CuentaAdministradorSubsidio ADD casIdMedioDePago bigint NOT NULL CONSTRAINT FK_CuentaAdministradorSubsidio_casIdMedioDePago FOREIGN KEY (casIdMedioDePago) REFERENCES dbo.MedioDePago(mdpId);
 
--changeset dsuesca:04
--comment: Se agraga ajustes de FK a beneficiario detalle
ALTER TABLE dbo.DetalleSubsidioAsignado DROP CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdBeneficiario;
ALTER TABLE dbo.DetalleSubsidioAsignado DROP COLUMN dsaIdBeneficiario;
ALTER TABLE dbo.DetalleSubsidioAsignado ADD dsaIdBeneficiarioDetalle BIGINT NOT NULL;
ALTER TABLE dbo.DetalleSubsidioAsignado ADD CONSTRAINT FK_DetalleSubsidioAsignado_dsaIdBeneficiarioDetalle FOREIGN KEY (dsaIdBeneficiarioDetalle) REFERENCES dbo.BeneficiarioDetalle(bedId);

--changeset dsuesca:05
--comment: Se agraga campo identificador de medio de pago
ALTER TABLE dbo.DetalleSubsidioAsignado ADD dsaPeriodoLiquidado DATE NOT NULL  
 
--changeset dsuesca:06
--comment: Se agraga campo identificador el resultado de la liquidación
ALTER TABLE dbo.DetalleSubsidioAsignado ADD dsaIdResultadoValidacionLiquidacion BIGINT NOT NULL;
 
