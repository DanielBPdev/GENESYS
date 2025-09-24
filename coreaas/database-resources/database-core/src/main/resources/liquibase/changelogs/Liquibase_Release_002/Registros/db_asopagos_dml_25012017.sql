--liquibase formatted sql

--changeset halzate:01 
--comment: Notificación de intento de afiliación
DELETE FROM VariableComunicado 
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación');

--changeset halzate:02 
--comment: Variables
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razón social / Nombre','Nombre del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${direccion}','Dirección','Dirección capturada en Información de ubicación y correspondencia del empleador', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionEmpleador}','Tipo identificación empleador','Tipo de identificación del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${numeroIdentificacionEmpleador}','Número identificación empleador','Número de identificación del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación') ) ;

--changeset halzate:03 
--comment: Constantes 
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de Ubicación de la Caja de Compensación','DEPARTAMENTO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de Ubicación de la Caja de Compensación','CIUDAD_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la Caja de Compensación','DIRECCION_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Número telefónico de la Caja de Compensación','TELEFONO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${tipoIdCcf}','Tipo ID CCF','Tipo documento de la Caja de Compensación','TIPO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${numeroIdCcf}','Número ID CCF','Número de documento de la Caja de Compensación','NUMERO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Dirección del sitio web de la Caja de Compensación','WEB_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logotipo de la Superintendencia de Servicios','LOGO_SUPERSERVICIOS',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Imagen de la firma del responsable del envío del comunicado en la caja','FIRMA_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Nombre del responsable del envío del comunicado en la caja','RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Notificación de intento de afiliación')) ;

--changeset abaquero:04 
--comment: refactorización para el uso de las enumeraciones respectivas en los campos 
alter table dbo.PilaPasoValores ALTER COLUMN ppvBloque varchar(11);