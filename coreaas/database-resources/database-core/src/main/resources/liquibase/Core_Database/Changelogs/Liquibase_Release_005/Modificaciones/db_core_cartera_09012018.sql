--liquibase formatted sql

--changeset fvasquez:01
--comment: Se elimina campo de la tabla Cartera
ALTER TABLE Cartera DROP COLUMN carNumeroOperacion

--changeset clmarin:02
--comment:Se modifican y se agregan campos en la tabla ConvenioPago
EXEC sp_rename 'dbo.ConvenioPago.copNombreUsuario', 'copUsuarioAnulacion', 'COLUMN';
ALTER TABLE ConvenioPago ADD copUsuarioCreacion VARCHAR(255) NULL;

--changeset fvasquez:03
--comment:Creacion de las tablas DocumentoCartera,SolicitudGestionCobroFisico,DetalleSolicitudGestionCruce,SolicitudGestionCobroElectronico,MotivoNoGestionCobro y DetalleComunicadoEnviado 
CREATE TABLE DocumentoCartera(
	dcaId BIGINT IDENTITY(1,1) NOT NULL,
	dcaCartera BIGINT NOT NULL,	
	dcaDocumentoSoporte BIGINT NOT NULL,
CONSTRAINT PK_DocumentoCartera_dcaId PRIMARY KEY(dcaId)
);
ALTER TABLE DocumentoCartera ADD CONSTRAINT FK_DocumentoCartera_dcaCartera FOREIGN KEY (dcaCartera) REFERENCES Cartera (carId);
ALTER TABLE DocumentoCartera ADD CONSTRAINT FK_DocumentoCartera_dcaDocumentoSoporte FOREIGN KEY (dcaDocumentoSoporte) REFERENCES DocumentoSoporte (dosId);

CREATE TABLE SolicitudGestionCobroFisico(
	sgfId BIGINT IDENTITY(1,1) NOT NULL,
	sgfDocumentoCartera BIGINT NULL,
	sgfEstado VARCHAR(33) NOT NULL,
	sgfFechaRemision DATETIME NULL,
	sgfObservacionRemision VARCHAR(255) NULL,
	sgfTipoAccionCobro VARCHAR(4) NOT NULL,
	sgfSolicitud BIGINT NOT NULL,
CONSTRAINT PK_SolicitudGestionCobroFisico_sgfId PRIMARY KEY(sgfId)
);
ALTER TABLE SolicitudGestionCobroFisico ADD CONSTRAINT FK_SolicitudGestionCobroFisico_sgfDocumentoCartera FOREIGN KEY (sgfDocumentoCartera) REFERENCES DocumentoCartera (dcaId);
ALTER TABLE SolicitudGestionCobroFisico ADD CONSTRAINT FK_SolicitudGestionCobroFisico_sgfSolicitud FOREIGN KEY (sgfSolicitud) REFERENCES Solicitud (solId);

CREATE TABLE DetalleSolicitudGestionCobro(
	dsgId BIGINT IDENTITY(1,1) NOT NULL,
	dsgEnviarPrimeraRemision BIT NULL,
	dsgEnviarSegundaRemision BIT NULL,
	dsgEstado VARCHAR(37) NOT NULL,
	dsgFechaPrimeraRemision DATETIME NULL,
	dsgFechaSegundaRemision DATETIME NULL,
	dsgCartera BIGINT NOT NULL,
	dsgObservacionPrimeraEntrega VARCHAR(255) NULL,
	dsgObservacionPrimeraRemision VARCHAR(255) NULL,
	dsgObservacionSegundaEntrega VARCHAR(255) NULL,
	dsgObservacionSegundaRemision VARCHAR(255) NULL,
	dsgSolicitudPrimeraRemision BIGINT NULL,
	dsgSolicitudSegundaRemision BIGINT NULL,
CONSTRAINT PK_DetalleSolicitudGestionCobro_dsgId PRIMARY KEY(dsgId)
);
ALTER TABLE DetalleSolicitudGestionCobro ADD CONSTRAINT FK_DetalleSolicitudGestionCobro_dsgSolicitudPrimeraRemision FOREIGN KEY (dsgSolicitudPrimeraRemision) REFERENCES SolicitudGestionCobroFisico (sgfId);
ALTER TABLE DetalleSolicitudGestionCobro ADD CONSTRAINT FK_DetalleSolicitudGestionCobro_dsgSolicitudSegundaRemision FOREIGN KEY (dsgSolicitudSegundaRemision) REFERENCES SolicitudGestionCobroFisico (sgfId);

CREATE TABLE SolicitudGestionCobroElectronico(
	sgeId BIGINT IDENTITY(1,1) NOT NULL,
	sgeEstado VARCHAR(33) NOT NULL,
	sgeCartera BIGINT NOT NULL,
	sgeTipoAccionCobro VARCHAR(4) NOT NULL,
	sgeSolicitud BIGINT NOT NULL,
CONSTRAINT PK_SolicitudGestionCobroElectronico_sgeId PRIMARY KEY(sgeId)
);
ALTER TABLE SolicitudGestionCobroElectronico ADD CONSTRAINT FK_SolicitudGestionCobroElectronico_sgeSolicitud FOREIGN KEY (sgeSolicitud) REFERENCES Solicitud (solId);

CREATE TABLE MotivoNoGestionCobro(
	mgcId BIGINT IDENTITY(1,1) NOT NULL,
	mgcCartera BIGINT NOT NULL,
	mgcTipo VARCHAR(36) NULL,
CONSTRAINT PK_MotivoNoGestionCobro_mgcId PRIMARY KEY(mgcId)
);
ALTER TABLE MotivoNoGestionCobro ADD CONSTRAINT FK_MotivoNoGestionCobro_mgcCartera FOREIGN KEY (mgcCartera) REFERENCES Cartera (carId);

CREATE TABLE DetalleComunicadoEnviado(
	dceId BIGINT IDENTITY(1,1) NOT NULL,
	dceComunicado BIGINT NOT NULL,
	dceIdentificador BIGINT NULL,
	dceTipoTransaccion VARCHAR(100) NOT NULL,
CONSTRAINT PK_DetalleComunicadoEnviado_dceId PRIMARY KEY (dceId)
);
ALTER TABLE DetalleComunicadoEnviado ADD CONSTRAINT FK_DetalleComunicadoEnviado_dceComunicado FOREIGN KEY (dceComunicado) REFERENCES Comunicado (comId);

--changeset atoro:04
--comment:Creacion de las tablas DatoTemporalParametrizacion y ParametrizacionCriteriosGestionCobro
CREATE TABLE DatoTemporalParametrizacion(
	dtpId BIGINT NOT NULL IDENTITY(1,1),
	dtpParametrizacion VARCHAR(30)NULL,
	dtpJsonPayload TEXT NULL,
CONSTRAINT PK_DatoTemporalParametrizacion PRIMARY KEY (dtpId)
);

CREATE TABLE ParametrizacionCriterioGestionCobro(
	pacId BIGINT NOT NULL,
	pcrLineaCobro VARCHAR(3) NULL,
	pcrActiva BIT NULL,
	pcrMetodo VARCHAR(10) NULL,
	pcrAutomatica VARCHAR(10) NULL,
	pcrCorteEntidad BIGINT NULL,
CONSTRAINT PK_ParametrizacionCriterioGestionCobro_pacId PRIMARY KEY (pacId)
);
ALTER TABLE ParametrizacionCriterioGestionCobro ADD CONSTRAINT FK_ParametrizacionCriterioGestionCobro_pacId FOREIGN KEY (pacId) REFERENCES ParametrizacionCartera (pacId);

--changeset squintero:05
--comment:Insercion de registro en la tabla Constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_GESTION_CARTERA_FISICA_GENERAL_DEPLOYMENTID','com.asopagos.coreaas.bpm.gestion_cartera_fisica_general:gestion_cartera_fisica_general:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para gestión de cartera física general');

--changeset jusanchez:06
--comment:Se cambia nombre de campo a la tabla ParametrizacionCriterioGestionCobro
EXEC SP_RENAME 'ParametrizacionCriterioGestionCobro.pcrAutomatica', 'pcrAccion', 'COLUMN';

--changeset squintero:07
--comment:Insercion de registro en la tabla Constante
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_GESTION_CARTERA_FISICA_DETALLADA_DEPLOYMENTID','com.asopagos.coreaas.bpm.gestion_cartera_fisica_Detallada:gestion_cartera_fisica_detallada:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para gestión de cartera física detallada');
INSERT Constante (cnsNombre,cnsValor,cnsDescripcion) VALUES ('BPMS_PROCESS_GESTION_COBRO_ELECTRONICO_DEPLOYMENTID','com.asopagos.coreaas.bpm.gestion_cobro_electronico:gestion_cobro_electronico:0.0.2-SNAPSHOT','Identificador de versión de proceso BPM para gestión de cobro electrónico');

--changeset clmarin:08
--comment:Insercion de registros en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de no recaudo de aportes','Cuerpo','Encabezado','Mensaje','Notificación de no recaudo de aportes','Pie','NTF_NO_REC_APO');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Suspensión automática de servicios por mora y notificación de no recaudo','Cuerpo','Encabezado','Mensaje','Suspensión automática de servicios por mora y notificación de no recaudo','Pie','SUS_NTF_NO_PAG');

--changeset clmarin:09
--comment:Insercion de registros en la tabla VariableComunicado - Variables
--NTF_NO_REC_APO
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','0','Nombre y Apellidos Representante Legal','Nombres y apellidos del representante legal asociado a la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','0','Razón social / Nombre','Razón social de la empresa a la cual se encuentra asociado el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoRepresentanteLegal}','0','Teléfono Representante Legal','Teléfono fijo o Celular capturado en Información de representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionRepresentanteLegal}','0','Dirección Representante Legal','Dirección del Representante Legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadRepresentanteLegal}','0','Ciudad Representante Legal','Ciudad donde se encuentra el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
--SUS_NTF_NO_PAG
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','0','Nombre y Apellidos Representante Legal','Nombres y apellidos del representante legal asociado a la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','0','Razón social / Nombre','Razón social de la empresa a la cual se encuentra asociado el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoRepresentanteLegal}','0','Teléfono Representante Legal','Teléfono fijo o Celular capturado en Información de representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionRepresentanteLegal}','0','Dirección Representante Legal','Dirección del Representante Legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadRepresentanteLegal}','0','Ciudad Representante Legal','Ciudad donde se encuentra el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));

--changeset clmarin:10
--comment:Insercion de registros en la tabla VariableComunicado - Constantes
--NTF_NO_REC_APO
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO'));
--SUS_NTF_NO_PAG
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'));

--changeset clmarin:11
--comment:Se actualizan registros en la tabla PlantillaComunicado - Variables
--NTF_NO_REC_APO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoRepresentanteLegal}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionRepresentanteLegal}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadRepresentanteLegal}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
--SUS_NTF_NO_PAG
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoRepresentanteLegal}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionRepresentanteLegal}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadRepresentanteLegal}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';

--changeset clmarin:12
--comment:Se actualizan registros en la tabla PlantillaComunicado - Constantes
--NTF_NO_REC_APO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO';
--SUS_NTF_NO_PAG
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG';
