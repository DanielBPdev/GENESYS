--liquibase formatted sql

--changeset jocorrea:01
--comment: Se elimina plantilla RCHZ_NVD_EMP_PROD_NSUBLE
DELETE FROM VariableComunicado WHERE vcoPlantillaComunicado = (SELECT pcoId FROM PlantillaComunicado WHERE pcoEtiqueta ='RCHZ_NVD_EMP_PROD_NSUBLE');

--changeset jocorrea:02
--comment: Se elimina plantilla RCHZ_NVD_EMP_PROD_NSUBLE
DELETE FROM PlantillaComunicado WHERE pcoEtiqueta ='RCHZ_NVD_EMP_PROD_NSUBLE';

--changeset jocorrea:03
--comment: Se elimina plantilla RCHZ_NVD_EMP_PROD_NSUBLE
INSERT INTO PlantillaComunicado (pcoAsunto, pcoCuerpo, pcoEncabezado,  pcoMensaje, pcoNombre, pcoPie, pcoEtiqueta) VALUES('Rechazo de solicitud de novedad de empleador por producto no conforme no subsanable','Cuerpo','Encabezado','Mensaje','Rechazo de solicitud de novedad de empleador por producto no conforme no subsanable','Pie','RCHZ_NVD_EMP_PROD_NSUBLE');

--changeset jocorrea:04
--comment: Actualizaciones RCHZ_NVD_EMP_PROD_NSUBLE
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de la caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de la caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Teléfono de la caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdCcf}','Tipo ID CCF','Tipo de la caja de Compensación','TIPO_ID_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdCcf}','Número ID CCF','Número de la caja de Compensación','NUMERO_ID_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Web de la caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logo de la caja de Compensación','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableNovedadesCcf}','Responsable Novedades CCF','Responsable de novedades de la caja de Compensación','RESPONSABLE_NOVEDADES_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableNovedadesCcf}','Cargo responsable Novedades CCF','Cargo del responsable de novedades la caja de Compensación','CARGO_RESPONSABLE_NOVEDADES_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE')) ;

INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadSolicitud}','0','Ciudad solicitud','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fechaDelSistema}','0','Fecha del sistema','dd/mm/aaaa proporcionado por el sistema al generar el comunicado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreYApellidosRepresentanteLegal}','0','Nombre y Apellidos Representante Legal','Nombre del representante legal del empleador que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${razonSocial/Nombre}','0','Razón social / Nombre','Nombre del empleador que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','0','Dirección','Dirección capturada en Información de ubicación y correspondencia del empleador','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Teléfono fijo o Celular capturado en Información de ubicación y correspondencia (uno de los dos)','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${resultadoNovedad}','0','Resultado novedad','Resultado de novedad "Aprobado" o "Rechazado"','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${productoNoConformeNoSubsanado}','0','Producto no conforme no subsanado','Productos no conformes que no fueron subsanados','LISTA_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${contenido}','0','Contenido','Texto que se adiciona al comunicado por parte del usuario','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE') ) ;

--changeset jocorrea:05
--comment: Actualizaciones RCHZ_NVD_EMP_PROD_NSUBLE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadSolicitud}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadSolicitud}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fechaDelSistema}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fechaDelSistema}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreYApellidosRepresentanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreYApellidosRepresentanteLegal}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${razonSocial/Nombre}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${razonSocial/Nombre}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${resultadoNovedad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${resultadoNovedad}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${productoNoConformeNoSubsanado}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${productoNoConformeNoSubsanado}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${contenido}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${contenido}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';

--changeset jocorrea:06
--comment: Actualizaciones RCHZ_NVD_EMP_PROD_NSUBLE
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableNovedadesCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableNovedadesCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableNovedadesCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableNovedadesCcf}') WHERE pcoEtiqueta = 'RCHZ_NVD_EMP_PROD_NSUBLE';

--changeset jocorrea:07
--comment: Actualizaciones RCHZ_NVD_EMP_PROD_NSUBLE
INSERT ValidacionProceso (vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa) VALUES
('VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'TRABAJADOR_DEPENDIENTE', 0), 
('VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_DEPENDIENTE_WEB', 'ACTIVO', '1', 'TRABAJADOR_DEPENDIENTE', 0), 
('VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'TRABAJADOR_INDEPENDIENTE', 0), 
('VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'TRABAJADOR_INDEPENDIENTE', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'FIDELIDAD_25_ANIOS', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_25ANIOS_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'FIDELIDAD_25_ANIOS', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'MAS_1_5_SM_0_6_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'MAS_1_5_SM_0_6_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'MAS_1_5_SM_2_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'MAS_1_5_SM_2_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'MENOS_1_5_SM_0_6_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'MENOS_1_5_SM_0_6_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'MENOS_1_5_SM_0_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'MENOS_1_5_SM_0_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'MENOS_1_5_SM_2_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'MENOS_1_5_SM_2_POR_CIENTO', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_PRESENCIAL', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_PRESENCIAL', 'ACTIVO', '1', 'PENSION_FAMILIAR', 0), 
('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_PENSION_FAMILIAR_WEB', 'VALIDACION_POSTULADO_FOVIS', 'NOVEDADES_PERSONAS_WEB', 'ACTIVO', '1', 'PENSION_FAMILIAR', 0);