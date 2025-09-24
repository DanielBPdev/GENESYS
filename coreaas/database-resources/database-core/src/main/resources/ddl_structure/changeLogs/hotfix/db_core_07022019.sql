--liquibase formatted sql

--changeset cmarin:01
--comment: 

INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Reasignar contraseña','Cuerpo','Encabezado','Mensaje','Reasignar contraseña','Pie','NTF_RES_CTRS') ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreUsuario}','0','Nombre Usuario','Nombre del usuario a reasignar contraseña','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contraseña}','0','Contraseña','Contraseña para reasignar al usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','responsable caja de compensación','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'NTF_RES_CTRS')) ;


UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreUsuario}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreUsuario}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contraseña}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contraseña}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${webCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${webCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'NTF_RES_CTRS';

