--liquibase formatted sql

--changeset atoro:01
--comment: Se agregan tablas y campos a tablas relacionados con el proceso Cartera
--Creacion de la tabla ParametrizacionCartera
CREATE TABLE ParametrizacionCartera(
	pacId BIGINT NOT NULL IDENTITY(1,1), 
	pacAplicar BIT NOT NULL,
	pacIncluirIndependientes BIT NOT NULL,
	pacIncluirPensionados BIT NOT NULL,
	pacEstadoCartera VARCHAR(255) NOT NULL,
	pacValorPromedioAportes VARCHAR(255) NOT NULL,
	pacCantidadPeriodos SMALLINT NOT NULL,
	pacTrabajadoresActivos VARCHAR(255) NOT NULL,
	pacPeriodosMorosos VARCHAR(255) NOT NULL,
	pacMayorValorPromedio SMALLINT NOT NULL,
	pacMayorTrabajadoresActivos SMALLINT NOT NULL,
	pacMayorVecesMoroso SMALLINT NOT NULL,
	pacTipoParametrizacion VARCHAR(31) NOT NULL,
CONSTRAINT PK_ParametrizacionCartera_pacId PRIMARY KEY (pacId)
);

--Creacion de la tabla ParametrizacionPreventiva 
CREATE TABLE ParametrizacionPreventiva(
	pacId BIGINT NOT NULL, 
	pprDiasHabilesPrevios SMALLINT NOT NULL,
	pprHoraEjecucion DATETIME NOT NULL,
CONSTRAINT PK_ParametrizacionPreventiva_pacId PRIMARY KEY (pacId)
);
ALTER TABLE ParametrizacionPreventiva ADD CONSTRAINT FK_ParametrizacionPreventiva_pacId FOREIGN KEY (pacId) REFERENCES ParametrizacionCartera (pacId);


--Creacion de la tabla SolicitudPreventiva
CREATE TABLE SolicitudPreventiva(
	sprId BIGINT NOT NULL IDENTITY(1,1),
	sprActualizacionEfectiva BIT NULL,
	sprBackActualizacion VARCHAR(255) NOT NULL,
	sprContactoEfectivo BIT NULL,
	sprEstadoSolicitudPreventiva VARCHAR(255) NOT NULL,
	sprPersona BIGINT NOT NULL,
	sprRequiereFiscalizacion BIT NULL,
	sprTipoSolicitanteMovimientoAporte INT NOT NULL,
	sprSolicitudGlobal BIGINT NOT NULL,
CONSTRAINT PK_SolicitudPreventiva_sprId PRIMARY KEY (sprId)
);
ALTER TABLE SolicitudPreventiva ADD CONSTRAINT FK_ParametrizacionPreventiva_sprPersona FOREIGN KEY (sprPersona) REFERENCES Persona (perId);
ALTER TABLE SolicitudPreventiva ADD CONSTRAINT FK_ParametrizacionPreventiva_sprSolicitudGlobal FOREIGN KEY (sprSolicitudGlobal) REFERENCES Solicitud (solId);

--changeset atoro:02
--comment: Se modifica campo de la tabla SolicitudPreventiva
ALTER TABLE SolicitudPreventiva ALTER COLUMN sprTipoSolicitanteMovimientoAporte VARCHAR(14) NOT NULL;

--changeset squintero:03
--comment: Insercion de registro en la tabla Parametro
INSERT Parametro (prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro,prmDescripcion) VALUES ('BPMS_PROCESS_FISCALIZACION_CARTERA_DEPLOYMENTID','com.asopagos.coreaas.bpm.fiscalizacion_cartera:fiscalizacion_cartera:0.0.2-SNAPSHOT',0,'BPM_PROCESS','Identificador de versión de proceso BPM para fiscalización de cartera');

--changeset jusanchez:04
--comment: Se adiciona el campo en la tabla SolicitudPreventiva
ALTER TABLE SolicitudPreventiva ADD sprTipoGestionCartera VARCHAR(10) NULL;

--changeset clmarin:05
--comment: Insercion de registros en la tabla PlantillaComunicado - REC_PLZ_LMT_PAG y REC_PLZ_LMT_PAG_PER
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Recordatorio plazo límite pago aportes','Cuerpo','Encabezado','Mensaje','Recordatorio plazo límite pago aportes','Pie','REC_PLZ_LMT_PAG');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Recordatorio plazo límite pago aportes personas','Cuerpo','Encabezado','Mensaje','Recordatorio plazo límite pago aportes personas','Pie','REC_PLZ_LMT_PAG_PER');

--changeset clmarin:06
--comment: Insercion de registros en la tabla VariableComunicado - Variables
--REC_PLZ_LMT_PAG
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razon social/Nombre','Nombre del aportante que realizo la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','Dirección','Dirección de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodoAEvaluar}','Periodo a Evaluar','Periodo sobre el cual estan las fechas limites de pago para el aporte','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaVencimiento}','Fecha vencimiento','Fecha cuando se vence el limite de pago','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
--REC_PLZ_LMT_PAG_PER
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresYApellidosDelAfiliadoPrincipal}','Nombres y Apellidos del afiliado principal','Nombre completo del afiliado principal ','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razon social/Nombre','Nombre del aportante que realizo la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','Dirección','Dirección de la sede donde se realiza la solicitud','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodoAEvaluar}','Periodo a Evaluar','Periodo sobre el cual estan las fechas limites de pago para el aporte','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaVencimiento}','Fecha vencimiento','Fecha cuando se vence el limite de pago','VARIABLE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));

--changeset clmarin:07
--comment: Insercion de registros en la tabla VariableComunicado - Constantes
--REC_PLZ_LMT_PAG
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG'));
--REC_PLZ_LMT_PAG_PER
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER'));

--changeset clmarin:08
--comment: Sea actualiza registros en la tabla VariableComunicado - Variables
--REC_PLZ_LMT_PAG
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${ciudadSolicitud}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${fechaDelSistema}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${razonSocial/Nombre}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${direccion}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${direccion}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${telefono}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${telefono}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${periodoAEvaluar}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${periodoAEvaluar}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${fechaVencimiento}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${fechaVencimiento}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
--REC_PLZ_LMT_PAG_PER
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${ciudadSolicitud}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${fechaDelSistema}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${nombresYApellidosDelAfiliadoPrincipal}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${nombresYApellidosDelAfiliadoPrincipal}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${razonSocial/Nombre}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${direccion}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${direccion}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${telefono}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${telefono}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${periodoAEvaluar}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${periodoAEvaluar}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo ,'<br  /> <p>${fechaVencimiento}</p>'),pcoMensaje = CONCAT(pcoMensaje,'<br /> ${fechaVencimiento}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';

--changeset clmarin:09
--comment: Sea actualiza registros en la tabla VariableComunicado - Constantes
--REC_PLZ_LMT_PA
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG';
--REC_PLZ_LMT_PAG_PER
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'REC_PLZ_LMT_PAG_PER';

--changeset jusanchez:10
--comment: Insercion de registro en la tabla ParametrizacionMetodoAsignacion
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo,pmaSedeCajaDestino) VALUES (1,'GESTION_PREVENTIVA_CARTERA','PREDEFINIDO','juan.oviedo@asopagos.com','AnaCarApo',1);

--changeset atoro:11
--comment: Se modifican tamaño de campos de la tabla ParametrizacionCartera
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacAplicar BIT NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacIncluirIndependientes BIT NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacIncluirPensionados BIT NULL
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacEstadoCartera VARCHAR(7) NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacValorPromedioAportes VARCHAR(16) NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacCantidadPeriodos SMALLINT NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacTrabajadoresActivos VARCHAR(18) NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacPeriodosMorosos VARCHAR (21) NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacMayorValorPromedio SMALLINT NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacMayorTrabajadoresActivos SMALLINT NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacMayorVecesMoroso SMALLINT NULL;
ALTER TABLE ParametrizacionCartera ALTER COLUMN pacTipoParametrizacion VARCHAR(31) NULL;
ALTER TABLE SolicitudPreventiva ALTER COLUMN sprEstadoSolicitudPreventiva VARCHAR(34) NULL;

--changeset borozco:12
--comment: Creacion de tablas del proceso de Fiscalizacion
--Creacion de la tabla ParametrizacionFiscalizacion
CREATE TABLE ParametrizacionFiscalizacion(
	pacId BIGINT NOT NULL,
	pfiAlertaValidacionPila BIT NULL,
	pfiEstadoAporteNoOk BIT NULL,
	pfiIbcMenorUltimo BIT NULL,
	pfiNovedadRetiro BIT NULL,
	pfiPeriodosRetroactivos SMALLINT NULL,
	pfiSalarioMenorUltimo BIT NULL,
CONSTRAINT PK_ParametrizacionFiscalizacion_pacId PRIMARY KEY (pacId)
);
ALTER TABLE ParametrizacionFiscalizacion ADD CONSTRAINT FK_ParametrizacionFiscalizacion_pafId FOREIGN KEY (pacId) REFERENCES ParametrizacionCartera (pacId);

--Creacion de la tabla CicloFiscalizacion
CREATE TABLE CicloFiscalizacion(
	cfiId BIGINT IDENTITY(1,1) NOT NULL,
	cfiEstadoCicloFiscalizacion VARCHAR(10) NOT NULL,
	cfiFechaInicio DATE NOT NULL,
	cfiFechaFin DATE NOT NULL,
	cfiFechaCreacion DATETIME NOT NULL,
CONSTRAINT PK_CicloFiscalizacion_cfiId PRIMARY KEY (cfiId)	
);

--Creacion de la tabla CicloAportante
CREATE TABLE CicloAportante(
	capId BIGINT IDENTITY(1,1) NOT NULL,
	capPersona BIGINT NULL,
	capTipoSolicitante VARCHAR(14) NULL,
	capCicloFiscalizacion BIGINT NULL,
CONSTRAINT PK_CicloAportante_capId PRIMARY KEY (capId)	
);
ALTER TABLE CicloAportante ADD CONSTRAINT FK_CicloAportante_capCicloFiscalizacion FOREIGN KEY (capCicloFiscalizacion) REFERENCES CicloFiscalizacion (cfiId);
ALTER TABLE CicloAportante ADD CONSTRAINT FK_CicloAportante_capPersona FOREIGN KEY (capPersona) REFERENCES Persona (perId);

--Creacion de la tabla SolicitudFiscalizacion
CREATE TABLE SolicitudFiscalizacion(
	sfiId BIGINT IDENTITY(1,1) NOT NULL,
	sfiEstadoFiscalizacion VARCHAR(11) NULL,
	sfiSolicitudGlobal BIGINT NOT NULL,
	sfiCicloAportante BIGINT NOT NULL,
CONSTRAINT PK_SolicitudFiscalizacion_sfiId PRIMARY KEY (sfiId)
);
ALTER TABLE SolicitudFiscalizacion ADD CONSTRAINT FK_SolicitudFiscalizacion_sfiSolicitudGlobal FOREIGN KEY (sfiSolicitudGlobal) REFERENCES Solicitud (solId);
ALTER TABLE SolicitudFiscalizacion ADD CONSTRAINT FK_SolicitudFiscalizacion_sfiCicloAportante FOREIGN KEY (sfiCicloAportante) REFERENCES CicloAportante (capId);


--Creacion de la tabla ActividadFiscalizacion
CREATE TABLE ActividadFiscalizacion(
	acfId BIGINT IDENTITY(1,1) NOT NULL,
	acfActividadFiscalizacion VARCHAR(40) NOT NULL,
	acfResultadoFiscalizacion VARCHAR(33) NULL,
	acfComentarios VARCHAR(500) NULL,
	acfCicloAportante BIGINT NOT NULL,
CONSTRAINT PK_ActividadFiscalizacion_acfId PRIMARY KEY (acfId)
);
ALTER TABLE ActividadFiscalizacion ADD CONSTRAINT FK_ActividadFiscalizacion_acfCicloAportante FOREIGN KEY (acfCicloAportante) REFERENCES CicloAportante (capId);

--Creacion de la tabla AgendaFiscalizacion
CREATE TABLE AgendaFiscalizacion(
	afsId BIGINT IDENTITY(1,1) NOT NULL,
	afsVisitaAgenda VARCHAR(13) NULL,
	afsFecha DATE NOT NULL,
	afsHorario DATETIME NOT NULL,
	afsContacto VARCHAR(255) NOT NULL,
	afsTelefono VARCHAR(255) NULL,
	afsCicloAportante BIGINT NOT NULL,
CONSTRAINT PK_AgendaFiscalizacion_afsId PRIMARY KEY (afsId)
);
ALTER TABLE AgendaFiscalizacion ADD CONSTRAINT FK_AgendaFiscalizacion_afsCicloAportante FOREIGN KEY (afsCicloAportante) REFERENCES CicloAportante (capId);

--Creacion de la tabla AgendaFiscalizacion
CREATE TABLE ActividadDocumento(
	adoId BIGINT IDENTITY(1,1) NOT NULL,
	adoIdentificadorDocumento VARCHAR(255) NOT NULL,
	adoTipoDocumento VARCHAR(12) NOT NULL,
	adoActividadFiscalizacion BIGINT NOT NULL
CONSTRAINT PK_ActividadDocumento_adoId PRIMARY KEY (adoId)
);
ALTER TABLE ActividadDocumento ADD CONSTRAINT FK_ActividadDocumento_adoActividadFiscalizacion FOREIGN KEY (adoActividadFiscalizacion) REFERENCES ActividadFiscalizacion (acfId);

--changeset borozco:13
--comment: Se adiciona campo en la tabla ActividadDocumento
ALTER TABLE ActividadDocumento ADD adoFecha DATETIME NOT NULL;

--changeset borozco:14
--comment: Se adiciona campo en la tabla ActividadDocumento
ALTER TABLE ActividadDocumento ADD adoCorteEntidades BIGINT NULL;

--changeset borozco:15
--comment: Se adiciona campo en la tabla ActividadDocumento
ALTER TABLE ActividadDocumento DROP COLUMN adoCorteEntidades;
ALTER TABLE ParametrizacionFiscalizacion ADD pfiCorteEntidades BIGINT NULL;

--changeset borozco:16
--comment: Se adiciona campo en la tabla ActividadDocumento
ALTER TABLE ActividadDocumento DROP COLUMN adoFecha;
ALTER TABLE ActividadFiscalizacion ADD afsFecha DATETIME NOT NULL;

--changeset borozco:17
--comment: Se adiciona campo en la tabla ActividadFiscalizacion
ALTER TABLE ActividadFiscalizacion DROP COLUMN afsFecha;
ALTER TABLE ActividadFiscalizacion ADD acfFecha DATETIME NOT NULL;

--changeset atoro:18
--comment: Se modifica campo en la tabla SolicitudPreventiva
ALTER TABLE SolicitudPreventiva ALTER COLUMN sprBackActualizacion VARCHAR(255) NULL;

--changeset clmarin:19
--comment: Se hace insercion de registros en las tablas GrupoPrioridad,DestinatarioGrupo,DestinatarioComunicado,PrioridadDestinatario 01/11/2017
INSERT GrupoPrioridad(gprNombre) VALUES ('GRUPO 20');

INSERT DestinatarioGrupo(dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 20'),'RESPONSABLE_DE_SUBSIDIOS');
INSERT DestinatarioGrupo(dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 20'),'RESPONSABLE_DE_APORTES');
INSERT DestinatarioGrupo(dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 20'),'RESPONSABLE_DE_LAS_AFILIACIONES');
INSERT DestinatarioGrupo(dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 20'),'REPRESENTANTE_LEGAL');
INSERT DestinatarioGrupo(dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 20'),'REPRESENTANTE_LEGAL_SUPLENTE');

INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_PREVENTIVA_CARTERA','REC_PLZ_LMT_PAG');
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_PREVENTIVA_CARTERA','REC_PLZ_LMT_PAG_PER');

INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado WHERE dcoProceso='GESTION_PREVENTIVA_CARTERA' AND dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 20'),1);
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado WHERE dcoProceso='GESTION_PREVENTIVA_CARTERA' AND dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 9'),2);
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado WHERE dcoProceso='GESTION_PREVENTIVA_CARTERA' AND dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 20'),1);
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT dcoId FROM DestinatarioComunicado WHERE dcoProceso='GESTION_PREVENTIVA_CARTERA' AND dcoEtiquetaPlantilla='REC_PLZ_LMT_PAG_PER'),(SELECT gprId FROM GrupoPrioridad WHERE gprNombre='GRUPO 9'),2);

--changeset jusanchez:20
--comment:Se adiciona registro y se elimina registro de la tabla ParametrizacionMetodoAsignacion
INSERT ParametrizacionMetodoAsignacion (pmaSedeCajaCompensacion,pmaProceso,pmaMetodoAsignacion,pmaUsuario,pmaGrupo,pmaSedeCajaDestino) VALUES (1,'FISCALIZACION_CARTERA','NUMERO_SOLICITUDES',NULL,'AnaCarApo',1);
DELETE FROM ParametrizacionMetodoAsignacion WHERE pmaProceso='GESTION_PREVENTIVA_CARTERA'; 
