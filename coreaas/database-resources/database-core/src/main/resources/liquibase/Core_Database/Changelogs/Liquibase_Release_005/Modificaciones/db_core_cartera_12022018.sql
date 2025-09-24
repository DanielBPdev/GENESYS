--liquibase formatted sql

--changeset atoro:01
--comment:Se adicionan campos a las tablas DetalleSolicitudGestionCobro y SolicitudGestionCobroFisico 
ALTER TABLE DetalleSolicitudGestionCobro ADD dsgResultadoPrimeraEntrega VARCHAR(18) NULL;
ALTER TABLE DetalleSolicitudGestionCobro ADD dsgResultadoSegundaEntrega VARCHAR(18) NULL;
ALTER TABLE DetalleSolicitudGestionCobro ADD dsgDocumentoPrimeraRemision BIGINT NULL;
ALTER TABLE DetalleSolicitudGestionCobro ADD dsgDocumentoSegundaRemision BIGINT NULL;       
ALTER TABLE DetalleSolicitudGestionCobro ADD CONSTRAINT FK_DetalleSolicitudGestionCobro_dsgDocumentoPrimeraRemision FOREIGN KEY (dsgDocumentoPrimeraRemision) REFERENCES DocumentoSoporte(dosId);
ALTER TABLE DetalleSolicitudGestionCobro ADD CONSTRAINT FK_DetalleSolicitudGestionCobro_dsgDocumentoSegundaRemision FOREIGN KEY (dsgDocumentoSegundaRemision) REFERENCES DocumentoSoporte(dosId);
ALTER TABLE DetalleSolicitudGestionCobro ALTER COLUMN dsgEstado VARCHAR(52) NULL;
ALTER TABLE SolicitudGestionCobroFisico ALTER COLUMN sgfEstado VARCHAR(52) NULL;
ALTER TABLE SolicitudGestionCobroFisico DROP CONSTRAINT FK_SolicitudGestionCobroFisico_sgfDocumentoCartera;
EXEC sp_rename 'SolicitudGestionCobroFisico.sgfDocumentoCartera', 'sgfDocumentoSoporte', 'COLUMN';
ALTER TABLE SolicitudGestionCobroFisico ADD CONSTRAINT FK_SolicitudGestionCobroFisico_sgfDocumentoSoporte FOREIGN KEY (sgfDocumentoSoporte) REFERENCES DocumentoSoporte(dosId);

--changeset clmarin:02
--comment:Insercion de registros en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de no recaudo de aportes personas','Cuerpo','Encabezado','Mensaje','Notificación de no recaudo de aportes personas','Pie','NTF_NO_REC_APO_PER');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Suspensión automática de servicios por mora y notificación de no recaudo personas','Cuerpo','Encabezado','Mensaje','Suspensión automática de servicios por mora y notificación de no recaudo personas','Pie','SUS_NTF_NO_PAG_PER');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación de no recaudo de aportes Consolidado','Cuerpo','Encabezado','Mensaje','Notificación de no recaudo de aportes Consolidado','Pie','NTF_NO_REC_APO_CNS');
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Suspensión automática de servicios por mora y notificación de no recaudo Consolidado','Cuerpo','Encabezado','Mensaje','Suspensión automática de servicios por mora y notificación de no recaudo Consolidado','Pie','SUS_NTF_NO_PAG_CNS');

--changeset clmarin:03
--comment:Insercion de registros en la tabla VariableComunicado - Variables
--NTF_NO_REC_APO_PER
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosAfiliadoPrincipal}','0','Nombre y Apellidos Afiliado Principal','Nombres y apellidos del representante legal asociado a la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Teléfono fijo o Celular capturado en Información de representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','0','Dirección','Dirección del Representante Legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudad}','0','Ciudad','Ciudad donde se encuentra el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));

--SUS_NTF_NO_PAG_PER
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosAfiliadoPrincipal}','0','Nombre y Apellidos Afiliado Principal','Nombres y apellidos del representante legal asociado a la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Teléfono fijo o Celular capturado en Información de representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','0','Dirección','Dirección del Representante Legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudad}','0','Ciudad','Ciudad donde se encuentra el representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));

--NTF_NO_REC_APO_CNS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${consolidado}','0','Consolidado','Comunicado que se genera de forma consolidada ','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_CNS'));

--SUS_NTF_NO_PAG_CNS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${consolidado}','0','Consolidado','Comunicado que se genera de forma consolidada ','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_CNS'));

--changeset clmarin:04
--comment:Insercion de registros en la tabla VariableComunicado - Constantes
--NTF_NO_REC_APO_PER
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_NO_REC_APO_PER'));

--SUS_NTF_NO_PAG_PER
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG_PER'));


--changeset clmarin:05
--comment:Se actualizan registros en la tabla PlantillaComunicado - Variables
--NTF_NO_REC_APO_PER - Variables
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosAfiliadoPrincipal}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudad}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';

--SUS_NTF_NO_PAG_PER - Variables
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosAfiliadoPrincipal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosAfiliadoPrincipal}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudad}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';

--NTF_NO_REC_APO_CNS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${consolidado}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${consolidado}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_CNS';

--SUS_NTF_NO_PAG_CNS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${consolidado}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${consolidado}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_CNS';

--changeset clmarin:06
--comment:Se actualizan registros en la tabla PlantillaComunicado - Constantes
--NTF_NO_REC_APO_PER
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_NO_REC_APO_PER';

--SUS_NTF_NO_PAG_PER
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'SUS_NTF_NO_PAG_PER';

--changeset clmarin:07
--comment:Insercion de registros en las tablas DestinatarioComunicado y PrioridadDestinatario
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','NTF_NO_REC_APO');
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','SUS_NTF_NO_PAG');
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_COBRO_ELECTRONICO' AND des.dcoEtiquetaPlantilla='NTF_NO_REC_APO'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'),1);
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_COBRO_ELECTRONICO' AND des.dcoEtiquetaPlantilla='SUS_NTF_NO_PAG'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='REPRESENTANTE_LEGAL'),1);
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','NTF_NO_REC_APO_PER');
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_COBRO_ELECTRONICO','SUS_NTF_NO_PAG_PER');
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_COBRO_ELECTRONICO' AND des.dcoEtiquetaPlantilla='NTF_NO_REC_APO_PER'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'),1);
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_COBRO_ELECTRONICO' AND des.dcoEtiquetaPlantilla='SUS_NTF_NO_PAG_PER'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='AFILIADO_PRINCIPAL'),1);

--changeset clmarin:08
--comment:Insercion de registros en la tabla PlantillaComunicado
INSERT PlantillaComunicado (pcoAsunto,pcoCuerpo,pcoEncabezado,pcoMensaje,pcoNombre,pcoPie,pcoEtiqueta) VALUES ('Notificación interna No envío comunicado cobro','Cuerpo','Encabezado','Mensaje','Notificación interna No envío comunicado cobro','Pie','NTF_INT_NO_CBR');

--changeset clmarin:09
--comment:Insercion de registros en la tabla VariableComunicado - Variables
--NTF_INT_NO_CBR
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreDelUsuario}','0','Nombre del Usuario','Nombre del usuario que realizó la tarea de registro de remisión del comunicado','USUARIO_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreDelComunicado}','0','Nombre del Comunicado','Nombre del comunicado presente en la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${reporte}','0','Reporte','Tabla que contiene los aportantes a los cuales no se les envió notificación','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));

--changeset clmarin:10
--comment:Insercion de registros en la tabla VariableComunicado - Constantes
--NTF_INT_NO_CBR
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_INT_NO_CBR'));

--changeset clmarin:11
--comment:Se actualizan registros en la tabla PlantillaComunicado - Variables
--NTF_INT_NO_CBR
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreDelUsuario}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreDelUsuario}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreDelComunicado}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreDelComunicado}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${reporte}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${reporte}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';

--changeset clmarin:12
--comment:Se actualizan registros en la tabla PlantillaComunicado - Constantes
--NTF_INT_NO_CBR
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_INT_NO_CBR';

--changeset clmarin:13
--comment:Insercion de registros en las tablas DestinatarioComunicado y PrioridadDestinatario
INSERT DestinatarioComunicado(dcoProceso,dcoEtiquetaPlantilla) VALUES ('GESTION_CARTERA_FISICA_GENERAL','NTF_INT_NO_CBR');
INSERT GrupoPrioridad(gprNombre) VALUES ('SUPERVISOR_CARTERA');
INSERT DestinatarioGrupo(dgrGrupoPrioridad,dgrRolContacto) VALUES ((SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='SUPERVISOR_CARTERA'),'SUPERVISOR_CARTERA');
INSERT PrioridadDestinatario(prdDestinatarioComunicado,prdGrupoPrioridad,prdPrioridad) VALUES ((SELECT des.dcoId FROM DestinatarioComunicado des WHERE des.dcoProceso='GESTION_CARTERA_FISICA_GENERAL' AND des.dcoEtiquetaPlantilla='NTF_INT_NO_CBR'),(SELECT grp.gprId FROM GrupoPrioridad grp WHERE grp.gprNombre='SUPERVISOR_CARTERA'),1);
