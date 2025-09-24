--liquibase formatted sql

--changeset halzate:01
--comment: insert VariableComunicado
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable') ) ;

--changeset lzarate:02
--comment: insert Parametro KEYCLOAK_ENDPOINT
insert into Parametro (prmNombre, prmValor) VALUES ('KEYCLOAK_ENDPOINT','http://10.77.187.5:8082/auth/realms/{realm}');

--changeset halzate:03
--comment: Eliminar VariableComunicado Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable
-- Comunicado: Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable

DELETE FROM VariableComunicado 
WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable');

--changeset halzate:04
--comment: insert VariableComunicado Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable
-- Comunicado: Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable
-- Variables:
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','Razón social / Nombre','Nombre del empleador que realizó la solicitud', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${direccion}','Dirección','Dirección capturada en Información de ubicación y correspondencia del empleador', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable') ) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoPlantillaComunicado) VALUES ('${telefono}','Teléfono','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)', (SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable') ) ;

-- Constantes: 
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logo de la caja de Compensación','LOGO_DE_LA_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${tipoIdCcf}','Tipo ID CCF','Tipo de la caja de Compensación','TIPO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${numeroIdCcf}','Número ID CCF','Número de la caja de Compensación','NUMERO_ID_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Firma de la caja de Compensación','FIRMA_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Responsable de la caja de Compensación','RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo de la caja de Compensación','CARGO_RESPONSABLE_CCF',(SELECT pcoId FROM PlantillaComunicado where pcoNombre = 'Rechazo de solicitud de afiliación de empleador por producto no conforme no subsanable')) ;


