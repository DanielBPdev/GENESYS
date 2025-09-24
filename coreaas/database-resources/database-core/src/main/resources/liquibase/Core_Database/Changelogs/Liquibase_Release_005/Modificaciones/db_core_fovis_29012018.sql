--liquibase formatted sql

--changeset mamonroy:01
--comment:Creación de las tablas Visita y CondicionVisita
CREATE TABLE Visita(
	visId BIGINT IDENTITY(1,1) NOT NULL,
	visFecha DATE NOT NULL,
	visNombresEncargado VARCHAR(50) NOT NULL,
	visCodigoIdentificadorECM VARCHAR(255) NOT NULL,
CONSTRAINT PK_Visita_visId PRIMARY KEY (visId)
);

CREATE TABLE CondicionVisita(
	covId BIGINT IDENTITY(1,1) NOT NULL,
	covCondicion VARCHAR(42) NOT NULL,
	covCumple BIT NOT NULL,
	covObservacion VARCHAR(250) NULL,
	covVisita BIGINT NOT NULL,
CONSTRAINT PK_CondicionVisita_covId PRIMARY KEY (covId)
);
ALTER TABLE CondicionVisita ADD CONSTRAINT FK_CondicionVisita_covVisita FOREIGN KEY (covVisita) REFERENCES Visita(visId);
ALTER TABLE CondicionVisita ADD CONSTRAINT UK_CondicionVisita_covVisita_covCondicion UNIQUE (covVisita,covCondicion);

--changeset alquintero:02
--comment:Creación de las tablas Legalizacion, SolicitudLegalizacionDesembolso, IntentoLegalizacionDesembolso y IntentoLegalizacionDesembolsoRequisito
CREATE TABLE LegalizacionDesembolso(
	lgdId BIGINT IDENTITY(1,1) NOT NULL,
	lgdFormaPago VARCHAR(50) NULL,
	lgdMedioDePago BIGINT NULL,
	lgdValorDesembolsar numeric(19,5) NULL,
	lgdFechaLimitePago DATETIME NULL,
	lgdVisita BIGINT NULL,
	lgdSubsidioDesembolsado BIT NULL,
CONSTRAINT PK_LegalizacionDesembolso_legId PRIMARY KEY (lgdId)
);
ALTER TABLE LegalizacionDesembolso ADD CONSTRAINT FK_LegalizacionDesembolso_lgdMedioDePago FOREIGN KEY (lgdMedioDePago) REFERENCES MedioDePago(mdpId);
ALTER TABLE LegalizacionDesembolso ADD CONSTRAINT FK_LegalizacionDesembolso_lgdVisita FOREIGN KEY (lgdVisita) REFERENCES Visita(visId);

CREATE TABLE SolicitudLegalizacionDesembolso(
	sldId BIGINT IDENTITY(1,1) NOT NULL,
	sldSolicitudGlobal BIGINT NOT NULL,
	sldPostulacionFOVIS BIGINT NULL,
	sldEstadoSolicitud VARCHAR(42) NULL,
	sldLegalizacionDesembolso BIGINT NULL,
	sldObservaciones VARCHAR(500) NULL,
CONSTRAINT PK_SolicitudLegalizacionDesembolso_sldId PRIMARY KEY (sldId)
);
ALTER TABLE SolicitudLegalizacionDesembolso ADD CONSTRAINT FK_SolicitudLegalizacionDesembolso_sldPostulacionFOVIS FOREIGN KEY(sldPostulacionFOVIS) REFERENCES PostulacionFOVIS (pofId);
ALTER TABLE SolicitudLegalizacionDesembolso ADD CONSTRAINT FK_SolicitudLegalizacionDesembolso_sldSolicitudGlobal FOREIGN KEY(sldSolicitudGlobal) REFERENCES Solicitud (solId);
ALTER TABLE SolicitudLegalizacionDesembolso ADD CONSTRAINT FK_SolicitudLegalizacionDesembolso_sldLegalizacionDesembolso FOREIGN KEY(sldLegalizacionDesembolso) REFERENCES LegalizacionDesembolso (lgdId);

CREATE TABLE IntentoLegalizacionDesembolso(
	ildId BIGINT IDENTITY(1,1) NOT NULL,
	ildCausaIntentoFallido VARCHAR(50) NULL,
	ildFechaCreacion DATETIME NULL,
	ildSedeCajaCompensacion VARCHAR(2) NULL,
	ildUsuarioCreacion VARCHAR(255) NULL,
	ildSolicitud BIGINT NULL,
	ildProceso VARCHAR(32) NULL,
	ildTipoSolicitante VARCHAR(5) NULL,
	ildModalidad VARCHAR(33) NULL,
CONSTRAINT PK_IntentoLegalizacionDesembolso_ildId PRIMARY KEY (ildId)
);
ALTER TABLE IntentoLegalizacionDesembolso ADD CONSTRAINT FK_IntentoLegalizacionDesembolso_ildSolicitud FOREIGN KEY(ildSolicitud) REFERENCES Solicitud (solId);

CREATE TABLE IntentoLegalizacionDesembolsoRequisito(
	ilrId BIGINT IDENTITY(1,1) NOT NULL,
	ilrIntentoLegalizacionDesembolso BIGINT NULL,
	ilrRequisito BIGINT NULL,
CONSTRAINT PK_IntentoLegalizacionDesembolsoRequisito_ilrId PRIMARY KEY (ilrId)
);
ALTER TABLE IntentoLegalizacionDesembolsoRequisito ADD CONSTRAINT FK_IntentoLegalizacionDesembolsoRequisito_ilrIntentoLegalizacionDesembolso FOREIGN KEY(ilrIntentoLegalizacionDesembolso) REFERENCES IntentoLegalizacionDesembolso (ildId);
ALTER TABLE IntentoLegalizacionDesembolsoRequisito ADD CONSTRAINT FK_IntentoLegalizacionDesembolsoRequisito_ilrRequisito FOREIGN KEY(ilrRequisito) REFERENCES Requisito (reqId);

--changeset flopez:03
--comment:Creacion de la tabla DocumentoSoporte
DROP TABLE DocumentoSoporte;
CREATE TABLE DocumentoSoporte(
	dosId BIGINT NOT NULL IDENTITY(1,1),
	dosNombreDocumento VARCHAR (255) NOT NULL,
	dosDescripcionComentarios VARCHAR (255) NOT NULL,
	dosIdentificacionDocumento VARCHAR(255) NOT NULL,
	dosVersionDocumento VARCHAR(3) NOT NULL,               
	dosFechaHoraCargue DATETIME NOT NULL,
CONSTRAINT PK_DocumentoSoporte_dsId PRIMARY KEY (dosId)
);

--changeset ecastano:04
--comment:Creacion de la tabla DocumentoSoporteProyectoVivienda,DocumentoSoporteOferente y adicion de campos en las tabla Licencia,LicenciaDetalle,Oferente y SolicitudLegalizacionDesembolso
CREATE TABLE DocumentoSoporteProyectoVivienda(
	dspId BIGINT IDENTITY(1,1) NOT NULL,
	dspProyectoSolucionVivienda BIGINT NOT NULL,
	dspDocumentoSoporte  BIGINT NOT NULL,
CONSTRAINT PK_DocumentoSoporteProyectoVivienda_dspId PRIMARY KEY (dspId)
);
ALTER TABLE DocumentoSoporteProyectoVivienda ADD CONSTRAINT FK_DocumentoSoporteProyectoVivienda_dspProyectoSolucionVivienda FOREIGN KEY (dspProyectoSolucionVivienda) REFERENCES ProyectoSolucionVivienda(psvId);
ALTER TABLE DocumentoSoporteProyectoVivienda ADD CONSTRAINT FK_DocumentoSoporteProyectoVivienda_dspDocumentoSoporte FOREIGN KEY (dspDocumentoSoporte) REFERENCES DocumentoSoporte(dosId);

CREATE TABLE DocumentoSoporteOferente(
	dsoId BIGINT IDENTITY(1,1) NOT NULL,
	dsoOferente BIGINT NOT NULL,
	dsoDocumentoSoporte  BIGINT NOT NULL,
CONSTRAINT PK_DocumentoSoporteOferente_dsoId PRIMARY KEY (dsoId)
);
ALTER TABLE DocumentoSoporteOferente ADD CONSTRAINT FK_DocumentoSoporteOferente_dsoOferente FOREIGN KEY (dsoOferente) REFERENCES Oferente(ofeId);
ALTER TABLE DocumentoSoporteOferente ADD CONSTRAINT FK_DocumentoSoporteOferente_dsoDocumentoSoporte FOREIGN KEY (dsoDocumentoSoporte) REFERENCES DocumentoSoporte(dosId);

ALTER TABLE SolicitudLegalizacionDesembolso ADD sldFechaOperacion DATETIME NULL;

ALTER TABLE Licencia ADD licTipoLicencia VARCHAR(12) NULL;

ALTER TABLE LicenciaDetalle ADD lidClasificacionLicencia VARCHAR(33) NULL;
ALTER TABLE LicenciaDetalle ADD lidEstadoLicencia BIT NULL;

ALTER TABLE Oferente ADD ofeCuentaBancaria BIT NULL;
ALTER TABLE Oferente ADD ofeBanco BIGINT NULL;
ALTER TABLE Oferente ADD ofeTipoCuenta VARCHAR(30) NULL;
ALTER TABLE Oferente ADD ofeNumeroCuenta VARCHAR(30) NULL;
ALTER TABLE Oferente ADD ofeTipoIdentificacionTitular VARCHAR(20) NULL;
ALTER TABLE Oferente ADD ofeNumeroIdentificacionTitular VARCHAR(16) NULL;
ALTER TABLE Oferente ADD ofeDigitoVerificacionTitular smallint NULL;
ALTER TABLE Oferente ADD ofeNombreTitularCuenta VARCHAR(200) NULL;
ALTER TABLE Oferente ADD CONSTRAINT FK_Oferente_ofeBanco FOREIGN KEY (ofeBanco) REFERENCES Banco(banId);

--changeset alquintero:05
--comment:Se adiciona campo en la tabla SolicitudLegalizacionDesembolso
ALTER TABLE SolicitudLegalizacionDesembolso ADD sldJsonPostulacion TEXT NULL; 

--changeset ecastano:06
--comment:Se elimina columna de la tabla Oferente
ALTER TABLE Oferente DROP CONSTRAINT FK_Oferente_ofeMedioDePago;
ALTER TABLE Oferente DROP COLUMN ofeMedioDePago;

--changeset flopez:07
--comment:Insercion de registro en la tabla Constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_LEGALIZACION_FOVIS_DEPLOYMENTID','com.asopagos.coreaas.bpm.legalizacion_desembolso_fovis:0.0.2-SNAPSHOT','Identificador  de versión de proceso BPM para Legalizacion y Desembolso FOVIS');

--changeset ecastano:08
--comment: Se adiciona campo en la tabla DocumentoSoporte
ALTER TABLE DocumentoSoporte ADD dosTipoDocumento VARCHAR(22) NULL;

--changeset mamonroy:08
--comment:Insercion de registros en la tabla Requisito y RequisitoCajaClasificacion
INSERT Requisito (reqDescripcion,reqEstado) VALUES ('Escritura pública contentiva del título de adquisición del inmueble.','HABILITADO');
INSERT Requisito (reqDescripcion,reqEstado) VALUES ('Certificación de la Capacidad de Crédito o simulador de crédito.','HABILITADO');
INSERT Requisito (reqDescripcion,reqEstado) VALUES ('Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.','HABILITADO');
INSERT Requisito (reqDescripcion,reqEstado) VALUES ('Certificado de la junta medica ','HABILITADO');
INSERT Requisito (reqDescripcion,reqEstado) VALUES ('Certificado de tradición y libertad del inmueble','HABILITADO');
INSERT Requisito (reqDescripcion,reqEstado) VALUES ('Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda','HABILITADO');

INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_PAGO_CONTRA_ESCRITURA',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_PAGO_CONTRA_ESCRITURA',1,'-Fecha de expedición <br />-Autorización de cobro por parte del beneficiario','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_PAGO_CONTRA_ESCRITURA',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación de la Capacidad de Crédito o simulador de crédito.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_PAGO_CONTRA_ESCRITURA',1,'-Que sea emitida por parte de la entidad financiera respectiva, con el monto del prestamo al cual puede acceder.<br />-La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de la junta medica '),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_PAGO_CONTRA_ESCRITURA',1,'El porcentaje de la discapacidad física o mental de alguno de los miembros del hogar ','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_PAGO_CONTRA_ESCRITURA',1,'-Fecha de expedición <br />-Autorización de cobro por parte del beneficiario','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Fecha de expedición <br />-Autorización de cobro por parte del beneficiario','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Fecha de expedición<br />-Autorización de cobro por parte del beneficiario','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_CONVENIO_CCF',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','ADQUISICION_VIVIENDA_NUEVA_URBANA_GIRO_ANTICIPADO_CONVENIO_CCF',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_USADA_RURAL_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de la junta medica '),'HOGAR','ADQUISICION_VIVIENDA_USADA_RURAL_PAGO_CONTRA_ESCRITURA',1,'El porcentaje de la discapacidad física o mental de alguno de los miembros del hogar','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','ADQUISICION_VIVIENDA_USADA_URBANA_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda'),'HOGAR','ADQUISICION_VIVIENDA_USADA_URBANA_PAGO_CONTRA_ESCRITURA',1,'-Fecha de expedición <br />-Autorización de cobro por parte del beneficiario','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_PAGO_CONTRA_ESCRITURA',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Fecha de expedición <br />-Autorización de cobro por parte del beneficiario','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_PAGO_CONTRA_ESCRITURA',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Copia del documento que acredita la asignación del Subsidio Familiar de Vivienda'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Fecha de expedición<br />-Autorización de cobro por parte del beneficiario','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación de la Capacidad de Crédito o simulador de crédito.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Que sea emitida por parte de la entidad financiera respectiva, con el monto del prestamo al cual puede acceder<br />-La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_CONVENIO_CCF',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','CONSTRUCCION_SITIO_PROPIO_URBANO_GIRO_ANTICIPADO_CONVENIO_CCF',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_URBANA_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación de la Capacidad de Crédito o simulador de crédito.'),'HOGAR','MEJORAMIENTO_VIVIENDA_URBANA_PAGO_CONTRA_ESCRITURA',1,'-Que sea emitida por parte de la entidad financiera respectiva, con el monto del prestamo al cual puede acceder<br />-La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_PAGO_CONTRA_ESCRITURA',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de la junta medica '),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'El porcentaje de la discapacidad física o mental de alguno de los miembros del hogar','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificación del Fondo de empleados, cuyos recursos estén destinados a Vivienda.'),'HOGAR','MEJORAMIENTO_VIVIENDA_RURAL_GIRO_ANTICIPADO_CONVENIO_CCF',1,'Revisar el monto, fecha de apertura y fecha de inmovilización. La fecha de expedición debe ser menor a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_PAGO_CONTRA_ESCRITURA',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_PAGO_CONTRA_ESCRITURA',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_ENCARGO_FIDUCIARIO',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OBLIGATORIO',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_AVAL_BANCARIO',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Escritura pública contentiva del título de adquisición del inmueble.'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_CONVENIO_CCF',1,'-Evidenciar la adquisición de la vivienda por el hogar postulante<br />-Fecha de expedición no superior a 30 días.','ESTANDAR');
INSERT RequisitoCajaClasificacion (rtsEstado,rtsRequisito,rtsClasificacion,rtsTipoTransaccion,rtsCajaCompensacion,rtsTextoAyuda,rtsTipoRequisito) VALUES ('OPCIONAL',(SELECT reqId FROM Requisito where reqDescripcion = 'Certificado de tradición y libertad del inmueble'),'HOGAR','MEJORAMIENTO_VIVIENDA_SALUDABLE_GIRO_ANTICIPADO_CONVENIO_CCF',1,'Con una vigencia no mayor a treinta (30) días','ESTANDAR');

--changeset alquintero:09
--comment:Eliminacion y creacion de campo en la tabla LegalizacionDesembolso
ALTER TABLE LegalizacionDesembolso DROP CONSTRAINT FK_LegalizacionDesembolso_lgdMedioDePago;
ALTER TABLE LegalizacionDesembolso DROP COLUMN lgdMedioDePago;
ALTER TABLE LegalizacionDesembolso ADD lgdMediosPagoCCF BIGINT NULL;
ALTER TABLE LegalizacionDesembolso ADD CONSTRAINT FK_LegalizacionDesembolso_lgdMediosPagoCCF FOREIGN KEY (lgdMediosPagoCCF) REFERENCES  MediosPagoCCF (mpcId);

--changeset flopez:10
--comment:Se crea la tabla MedioPagoProyectoVivienda y se adiciona y elimina campo en la tabla ProyectoSolucionVivienda
CREATE TABLE MedioPagoProyectoVivienda(
	mprId BIGINT NOT NULL IDENTITY(1,1),
	mprProyectoSolucionVivienda BIGINT NOT NULL,
	mprMedioDePago BIGINT NOT NULL,
	mprActivo BIT,
CONSTRAINT PK_MedioPagoProyectoVivienda_mprId PRIMARY KEY (mprId)
);
ALTER TABLE MedioPagoProyectoVivienda ADD CONSTRAINT FK_MedioPagoProyectoVivienda_mprProyectoSolucionVivienda FOREIGN KEY (mprProyectoSolucionVivienda) REFERENCES ProyectoSolucionVivienda(psvId);
ALTER TABLE MedioPagoProyectoVivienda ADD CONSTRAINT FK_MedioPagoProyectoVivienda_mprMedioDePago FOREIGN KEY (mprMedioDePago) REFERENCES MedioDePago(mdpId);
ALTER TABLE ProyectoSolucionVivienda DROP CONSTRAINT FK_ProyectoSolucionVivienda_psvMedioDePago;
ALTER TABLE ProyectoSolucionVivienda DROP COLUMN psvMedioDePago;
ALTER TABLE ProyectoSolucionVivienda ADD psvRegistrado BIT NULL;

--changeset alquintero:11
--comment:Se elimina y adiciona campo en la tabla LegalizacionDesembolso
ALTER TABLE LegalizacionDesembolso DROP CONSTRAINT FK_LegalizacionDesembolso_lgdMediosPagoCCF;
ALTER TABLE LegalizacionDesembolso DROP COLUMN lgdMediosPagoCCF;
ALTER TABLE LegalizacionDesembolso ADD lgdTipoMedioPago VARCHAR (30) NULL;

--changeset alquintero:12
--comment:Se adiciona campo en la tabla 
ALTER TABLE PostulacionFOVIS ADD pofValorProyectoVivienda NUMERIC (19,5) NULL;

--changeset flopez:13
--comment:Se actualiza registro en la tabla Constante
UPDATE Constante SET cnsValor = 'com.asopagos.coreaas.bpm.legalizacion_desembolso_fovis:legalizacion_desembolso_fovis:0.0.2-SNAPSHOT' WHERE cnsNombre= 'BPMS_PROCESS_LEGALIZACION_FOVIS_DEPLOYMENTID';
