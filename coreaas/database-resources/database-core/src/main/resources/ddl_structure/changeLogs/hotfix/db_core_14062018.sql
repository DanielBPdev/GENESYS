--liquibase formatted sql

--changeset clmarin:01
--comment: Platilla comunicado AVI_INC_PER
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Aviso de incumplimiento persona','Cuerpo','Encabezado','Mensaje','Aviso de incumplimiento persona','Pie','AVI_INC_PER') ;

--changeset clmarin:02
--comment: Plantillas comunicado AVI_INC_PER
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosAfiliadoPrincipal}','0','Nombre y Apellidos Afiliado Principal','Nombres y apellidos del afiliado principal asociado a la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','0','Dirección','Dirección de la empresa relacionada al representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Teléfono fijo o Celular capturado en Información de representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudad}','0','Ciudad','Ciudad donde se encuentra el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${periodoMora}','0','Periodo Mora','Periodos para los cuales esta en mora','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaCorte}','0','Fecha Corte','Fecha de corte para evaluar periodo en mora','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER') ) ;

UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosAfiliadoPrincipal}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudad}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${periodoMora}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${periodoMora}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaCorte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaCorte}') WHERE pcoEtiqueta = 'AVI_INC_PER';

--changeset clmarin:03
--comment: Ctes_Comunicado AVI_INC_PER
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'AVI_INC_PER')) ;

UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'AVI_INC_PER';

--changeset clmarin:04
--comment: Insert tabla DestinatarioComunicado
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('GESTION_CARTERA_FISICA_GENERAL', 'AVI_INC_PER');
INSERT DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES ( 'GESTION_COBRO_ELECTRONICO', 'AVI_INC_PER');

--changeset rarboleda:05
--comment: Insertar en tabla DestinatarioComunicado 
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('AFILIACION_EMPRESAS_WEB', 'RCHZ_AFL_EMP_PROD_NSUB');

--changeset rarboleda:06
--comment:Insertar en Tabla PrioridadDestinatario
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUB'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 1);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUB'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'), 2);
	
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUB'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='OFICINA_PRINCIPAL'), 3);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUB'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='ENVIO_DE_CORRESPONDENCIA'), 4);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUB'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='NOTIFICACION_JUDICIAL'), 5);
	
--changeset rarboleda:07
--comment:Insertar en Tabla PrioridadDestinatario
INSERT INTO DestinatarioComunicado (dcoProceso, dcoEtiquetaPlantilla) VALUES('AFILIACION_EMPRESAS_WEB', 'RCHZ_AFL_EMP_PROD_NSUBLE');

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUBLE'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='RESPONSABLE_DE_LAS_AFILIACIONES'), 1);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUBLE'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'), 2);
	
INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUBLE'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='OFICINA_PRINCIPAL'), 3);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUBLE'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='ENVIO_DE_CORRESPONDENCIA'), 4);

INSERT INTO PrioridadDestinatario(prdDestinatarioComunicado, prdGrupoPrioridad, prdPrioridad)
VALUES((
	SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='AFILIACION_EMPRESAS_WEB' 
	AND des.dcoEtiquetaPlantilla='RCHZ_AFL_EMP_PROD_NSUBLE'), 
	(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='NOTIFICACION_JUDICIAL'), 5);

--changeset rarboleda:08
--comment: actualizacion tabla DescuentoInteresMora
UPDATE DescuentoInteresMora SET dimFechaPagoFinal='2017-06-30', dimFechaPagoInicial='2017-05-17' WHERE dimId=1;

 