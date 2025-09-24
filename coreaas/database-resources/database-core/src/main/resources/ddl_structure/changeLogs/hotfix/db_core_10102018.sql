--liquibase formatted sql

--changeset jocorrea:01
--comment: Se crea tabla CerficadoEscolarBeneficiario
CREATE TABLE CerficadoEscolarBeneficiario (
cebId bigint IDENTITY(1,1) NOT NULL,
cebBeneficiarioDetalle bigint NOT NULL,
cebFechaRecepcion date NULL,
cebFechaVencimiento date NULL,
CONSTRAINT PK_CerficadoEscolarBeneficiario_cebId PRIMARY KEY (cebId),
CONSTRAINT FK_CerficadoEscolarBeneficiario_cebBeneficiarioDetalle FOREIGN KEY (cebBeneficiarioDetalle) REFERENCES BeneficiarioDetalle (bedId)
);

--changeset jocorrea:02
--comment: Se crea tabla CerficadoEscolarBeneficiario_aud
CREATE TABLE aud.CerficadoEscolarBeneficiario_aud (
cebId bigint NOT NULL,
REV bigint NOT NULL,
REVTYPE smallint,
cebBeneficiarioDetalle bigint NOT NULL,
cebFechaRecepcion date NULL,
cebFechaVencimiento date NULL,
CONSTRAINT FK_CerficadoEscolarBeneficiario_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
);

--changeset mosorio:03
--comment: Configuracion PlantillaComunicado
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Notificación rechazo liquidación específica por fallecimiento - Trabajador','Cuerpo','Encabezado','Mensaje','Notificación rechazo liquidación específica por fallecimiento - Trabajador','Pie','NTF_RCZ_LIQ_ESP_FALL_TRA') ;	

	--INSERTS VARIABLES PLANTILLAS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresTrabajadorOPareja}','0','Nombres Trabajador o Pareja','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionTrabajadorOPareja}','0','Tipo identificación Trabajador o Pareja','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${noIdentificacionTrabajadorOPareja}','0','No identificación Trabajador o Pareja','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreBeneficiarioOTrabajador}','0','Nombre Beneficiario o Trabajador','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${causa}','0','Causa','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA') ) ;	

	--UPDATES VARIABLES PLANTILLAS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresTrabajadorOPareja}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresTrabajadorOPareja}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';	
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacionTrabajadorOPareja}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacionTrabajadorOPareja}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${noIdentificacionTrabajadorOPareja}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${noIdentificacionTrabajadorOPareja}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreBeneficiarioOTrabajador}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreBeneficiarioOTrabajador}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${causa}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${causa}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		


	--INSERTS CONSTANTES COMUNICADO
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA')) ;

	--UPDATES CONSTANTES COMUNICADO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_TRA';		
		
		-----=================== CREACIÓN PLANTILLAS, VARIABLES Y CONSTANTES COMUNICADO 138 (137) 507 ==============================-----

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Notificación rechazo liquidación específica por fallecimiento - Admin Subsidio','Cuerpo','Encabezado','Mensaje','Notificación rechazo liquidación específica por fallecimiento - Admin Subsidio','Pie','NTF_RCZ_LIQ_ESP_FALL_ADM_SUB') ;	

	--INSERTS VARIABLES PLANTILLAS
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombresAdminSubsidio}','0','Nombres Admin Subsidio','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionAdminSubsidio}','0','Tipo identificación Admin Subsidio','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${noIdentificacionAdminSubsidio}','0','No identificación Admin Subsidio','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreBeneficiarioOTrabajador}','0','Nombre Beneficiario o Trabajador','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB') ) ;	
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${causa}','0','Causa','Representa la variable de una tabla que se genera con los datos de las inconsistencias detectadas a los campos del registro del archivo de consumos','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB') ) ;	

	--UPDATES VARIABLES PLANTILLAS
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombresAdminSubsidio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombresAdminSubsidio}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';	
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacionAdminSubsidio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacionAdminSubsidio}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';	
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${noIdentificacionAdminSubsidio}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${noIdentificacionAdminSubsidio}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';	
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreBeneficiarioOTrabajador}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreBeneficiarioOTrabajador}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';	
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${causa}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${causa}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';	

	--INSERTS CONSTANTES COMUNICADO
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Telefono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Nombre de la caja de Compensación','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB')) ;

	--UPDATES CONSTANTES COMUNICADO
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RCZ_LIQ_ESP_FALL_ADM_SUB';		

--changeset jvelandia:04
--comment:  Configuracion PlantillaComunicado
UPDATE PlantillaComunicado set pcoCuerpo = 'Cuerpo<br  /> <p>${fechaDelSistema}</p><br  /> <p>${razonSocial/Nombre}</p><br  /> <p>${tipoIdentificacion}</p><br  /> <p>${numeroIdentificacion}</p><br  /> <p>${numeroPlanilla}</p><br  /> <p>${periodoAporte}</p><br  /> <p>${fechaPagoAporte}</p><br  /> <p>${montoAporteRecibido}</p><br  /> <p>${cantidadDeAportes}</p><br  /> <p>${interesMora}</p><br  /> <p>${regitrosRecibidos}</p><br  /> <p>${numeroTrabajadores}</p><br  /> <p>${numeroTrabajadoresActivo}</p><br  /> <p>${numeroTrabajadoresNoActivo}</p><br  /> <p>${nombreCcf}</p><br  /> <p>${logoDeLaCcf}</p><br  /> <p>${departamentoCcf}</p><br  /> <p>${ciudadCcf}</p><br  /> <p>${direccionCcf}</p><br  /> <p>${telefonoCcf}</p><br  /> <p>${webCcf}</p><br  /> <p>${logoSuperservicios}</p><br  /> <p>${responsableCcf}</p><br  /> <p>${cargoResponsableCcf}</p> <br  /> <p>${cotizantes}</p>'
WHERE pcoEtiqueta='NTF_REC_APT_PLA_DEP2';

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) 
VALUES ('${cotizantes}',null,'Tipo, numero y nombre del cotizante como lista','Tipo, numero y nombre del cotizante como lista','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_REC_APT_PLA_DEP2'));

INSERT PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('RESPONSABLE_AFILIACIONES_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Corresponde al username del responsable del proceso de afiliaciones');
INSERT PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('CARGO_RESPONSABLE_AFILIACIONES_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Corresponde al cargo del responsable del proceso de afiliaciones');

--changeset mosorio:05
--comment:  Configuracion PlantillaComunicado
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('LIQUIDACION_SUBSIDIO_FALLECIMIENTO','NTF_RCZ_LIQ_ESP_FALL_ADM_SUB');
INSERT DestinatarioComunicado (dcoProceso,dcoEtiquetaPlantilla) VALUES ('LIQUIDACION_SUBSIDIO_FALLECIMIENTO','NTF_RCZ_LIQ_ESP_FALL_TRA');
UPDATE dbo.PlantillaComunicado set pcoNombre = 'Notificación de dispersión de pagos subsidio fallecimiento al admin subsidio'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PAG_ADM_SUB';
UPDATE dbo.PlantillaComunicado set pcoNombre = 'Notificación de dispersión de pagos subsidio fallecimiento al trabajador ó conyuge'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PAG_TRA';
UPDATE dbo.PlantillaComunicado set pcoNombre = 'Notificación de dispersión de pagos subsidio fallecimiento programados al admin subsidio'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PRO_ADM_SUB';
UPDATE dbo.PlantillaComunicado set pcoNombre = 'Notificación de dispersión de pagos subsidio fallecimiento programados al trabajador ó conyuge'
WHERE pcoEtiqueta = 'COM_SUB_DIS_FAL_PRO_TRA';


--changeset jvelandia:06
--comment:  Configuracion PlantillaComunicado
DELETE VariableComunicado where vcoClave in('${responsableCcf}','${cargoResponsableCcf}') 
AND vcoPlantillaComunicado=(SELECT pcoId from PlantillaComunicado where pcoEtiqueta='SUS_NTF_NO_PAG');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante) 
VALUES ('${responsableAportesCcf}',null,'Responsable de aportes de la caja de Compensación','Responsable de aportes de la caja de Compensación','CONSTANTE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'),'RESPONSABLE_APORTESS_CCF');

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado, vcoNombreConstante) 
VALUES ('${cargoResponsableAportesCcf}',null,'Cargo responsable aportes','Cargo responsable de aportes de la caja de Compensación','CONSTANTE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'SUS_NTF_NO_PAG'),'CARGO_RESPONSABLE_APORTES_CCF');

UPDATE PlantillaComunicado SET pcoCuerpo = REPLACE(pcoCuerpo,'${responsableCcf}','${responsableAportesCcf}') 
WHERE pcoEtiqueta='SUS_NTF_NO_PAG';

UPDATE PlantillaComunicado SET pcoCuerpo = REPLACE(pcoCuerpo,'${cargoResponsableCcf}','${cargoResponsableAportesCcf}') 
WHERE pcoEtiqueta='SUS_NTF_NO_PAG';


--changeset jvelandia:07
--comment:  Configuracion PlantillaComunicado
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('RESPONSABLE_APORTESS_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Corresponde al username del responsable del proceso de aportes')
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('CARGO_RESPONSABLE_APORTES_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Corresponde al cargo del responsable del proceso de aportes')
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('RESPONSABLE_CARTERA_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Username del responsable del proceso de cartera')
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('CARGO_RESPONSABLE_CARTERA_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Cargo del responsable del proceso de cartera')
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('RESPONSABLE_SUBSIDIO_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Username del responsable del proceso de subsidio')
INSERT INTO PARAMETRO(prmNombre,prmValor,prmCargaInicio,prmSubCategoriaParametro, prmDescripcion) VALUES ('CARGO_RESPONSABLE_SUBSIDIO_CCF','libardo_ramirez',0,'CAJA_COMPENSACION', 'Cargo del responsable del proceso de subsidio')


UPDATE plantillaComunicado SET pcoNombre='Certificado Paz y Salvo Empleador' WHERE pcoEtiqueta='COM_GEN_CER_PYS_EMP'
UPDATE plantillaComunicado SET pcoNombre='Certificado Paz y Salvo Persona' WHERE pcoEtiqueta='COM_GEN_CER_PYS'
UPDATE plantillaComunicado SET pcoNombre='Certificado de Aportes' WHERE pcoEtiqueta='COM_GEN_CER_APO'


INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) 
VALUES ('${cotizantes}',null,'Tipo, numero y nombre del cotizante como lista','Tipo, numero y nombre del cotizante como lista','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_REC_APT_PLA_DEP4'));
UPDATE PlantillaComunicado set pcoCuerpo = 'Cuerpo<br  /> <p>${fechaDelSistema}</p><br  /> <p>${razonSocial/Nombre}</p><br  /> <p>${tipoIdentificacion}</p><br  /> <p>${numeroIdentificacion}</p><br  /> <p>${numeroPlanilla}</p><br  /> <p>${periodoAporte}</p><br  /> <p>${fechaPagoAporte}</p><br  /> <p>${montoAporteRecibido}</p><br  /> <p>${cantidadDeAportes}</p><br  /> <p>${interesMora}</p><br  /> <p>${regitrosRecibidos}</p><br  /> <p>${numeroTrabajadores}</p><br  /> <p>${numeroTrabajadoresActivo}</p><br  /> <p>${numeroTrabajadoresNoActivo}</p><br  /> <p>${nombreCcf}</p><br  /> <p>${logoDeLaCcf}</p><br  /> <p>${departamentoCcf}</p><br  /> <p>${ciudadCcf}</p><br  /> <p>${direccionCcf}</p><br  /> <p>${telefonoCcf}</p><br  /> <p>${logoSuperservicios}</p><br  /> <p>${responsableCcf}</p><br  /> <p>${cargoResponsableCcf}</p> <br  /> <p>${cotizantes}</p>'
WHERE pcoEtiqueta='NTF_REC_APT_PLA_DEP4'

