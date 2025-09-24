--liquibase formatted sql

--changeset jocorrea:01
--comment:Se actualiza registros en la tabla ValidacionProceso
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_DEPENDIENTE_ACTIVO',vapInversa = 1 WHERE vapValidacion = 'VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO' AND vapBloque IN ('SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL','SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_DEPWEB','VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL','VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_DEPWEB','VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL','VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_DEPWEB','VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL','VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_DEPWEB');
UPDATE ValidacionProceso SET vapValidacion = 'VALIDACION_PERSONA_INDEPENDIENTE_ACTIVO',vapInversa = 1 WHERE vapValidacion = 'VALIDACION_PERSONA_DEPENDIENTE_ACTIVO' AND vapBloque IN ('SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL','SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_WEB','VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL','VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_WEB','VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL','VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_WEB','VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL','VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_WEB');

--changeset borozco:02
--comment:Se actualizan registros en la tabla FieldDefinition
UPDATE FieldDefinition SET initialPosition=0, finalPosition=12 WHERE label='No. Afiliación';
UPDATE FieldDefinition SET initialPosition=12, finalPosition=24 WHERE label='No. de Documento';
UPDATE FieldDefinition SET initialPosition=24, finalPosition=33 WHERE label='NIT. de Entidad';
UPDATE FieldDefinition SET initialPosition=33, finalPosition=34 WHERE label='Tipo Novedad';

--changeset jocorrea:03
--comment:Eliminacion registros en la tabla VariableComunicado y actualizacion de registros en la tabla PlantillaComunicado 
DELETE vc FROM Plantillacomunicado pc INNER JOIN VariableComunicado vc ON pc.pcoId=vc.vcoPlantillaComunicado WHERE pcoEtiqueta in ('CRT_ENT_PAG');
UPDATE PlantillaComunicado SET pcoCuerpo='Cuerpo', pcoMensaje='Mensaje' WHERE pcoEtiqueta= 'CRT_ENT_PAG';

--changeset jocorrea:04
--comment: Insercion de registros en la tabla VariableComunicado - Variables
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudad}','0','Ciudad','Ciudad de la sede donde se realiza la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${fecha}','0','Fecha','Fecha radicacion ','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreEntidadPagadora}','0','Nombre entidad pagadora','Nombre de la entidad pagadora que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreEmpresa}','0','Nombre empresa','Nombre de la empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccion}','0','Direccion','Direccion de ubicación de la empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefono}','0','Teléfono','Telefono de la empresa','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdentificacionEntidadPagadora}','0','Tipo identificación Entidad Pagadora','Tipo de identificación de la entidad pagadora que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdentificacionEntidadPagadora}','0','Número identificación Entidad Pagadora','Número de identificación de la entidad pagadora que realizó la solicitud','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoGestion}','0','Tipo gestion','Tipo gestion realizado','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${estadoEntidadPagadora}','0','Estado entidad pagadora','Estado entidad pagadora','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${representanteLegal}','0','Representante Legal ','Representante legal','VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;
INSERT VariableComunicado (vcoClave,vcoOrden,vcoNombre,vcoDescripcion,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tabla}','0','Tabla','Tabla información de personas asociadas','REPORTE_VARIABLE', (SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG') ) ;

--changeset jocorrea:05
--comment: Insercion de registros en la tabla VariableComunicado - Constantes
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${nombreCcf}','Nombre CCF','Nombre de la caja de Compensación','NOMBRE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoDeLaCcf}','Logo de la CCF','Logotipo de la Caja de Compensación','LOGO_DE_LA_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${departamentoCcf}','Departamento CCF','Departamento de Ubicación de la Caja de Compensación','DEPARTAMENTO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${ciudadCcf}','Ciudad CCF','Ciudad de Ubicación de la Caja de Compensación','CIUDAD_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${direccionCcf}','Dirección CCF','Dirección de la Caja de Compensación','DIRECCION_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${telefonoCcf}','Teléfono CCF','Número telefónico de la Caja de Compensación','TELEFONO_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${tipoIdCcf}','Tipo ID CCF','Tipo documento de la Caja de Compensación','TIPO_ID_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${numeroIdCcf}','Número ID CCF','Número de documento de la Caja de Compensación','NUMERO_ID_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${webCcf}','Web CCF','Dirección del sitio web de la Caja de Compensación','WEB_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${logoSuperservicios}','Logo SuperServicios','Logotipo de la Superintendencia de Servicios','LOGO_SUPERSERVICIOS','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${firmaResponsableCcf}','Firma Responsable CCF','Imagen de la firma del responsable del envío del comunicado en la caja','FIRMA_RESPONSABLE_CCF','CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${responsableCcf}','Responsable CCF','Nombre del responsable del envío del comunicado en la caja','RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;
INSERT VariableComunicado (vcoClave,vcoNombre,vcoDescripcion,vcoNombreConstante,vcoTipoVariableComunicado,vcoPlantillaComunicado) VALUES ('${cargoResponsableCcf}','Cargo responsable CCF','Cargo del responsable del envío del comunicado en la caja','CARGO_RESPONSABLE_CCF','USUARIO_CONSTANTE',(SELECT pcoId FROM PlantillaComunicado where pcoEtiqueta = 'CRT_ENT_PAG')) ;

--changeset jocorrea:06
--comment: Actualizacion de registros en la tabla PlantillaComunicado - Variables
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudad}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudad}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${fecha}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${fecha}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreEntidadPagadora}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreEntidadPagadora}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreEmpresa}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreEmpresa}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccion}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefono}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefono}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdentificacionEntidadPagadora}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdentificacionEntidadPagadora}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdentificacionEntidadPagadora}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdentificacionEntidadPagadora}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoGestion}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoGestion}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${estadoEntidadPagadora}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${estadoEntidadPagadora}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${representanteLegal}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${representanteLegal}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tabla}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tabla}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';

--changeset jocorrea:07
--comment: Actualizacion de registros en la tabla PlantillaComunicado - Constantes
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${nombreCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${nombreCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoDeLaCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoDeLaCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${departamentoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${departamentoCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${ciudadCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${ciudadCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${direccionCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${direccionCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${telefonoCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${telefonoCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${tipoIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${tipoIdCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${numeroIdCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${numeroIdCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p><a href="${webCcf}">Web CCF</a></p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> <a href="${webCcf}">Web CCF</a>') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${logoSuperservicios}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${logoSuperservicios}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${firmaResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${firmaResponsableCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${responsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${responsableCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';
UPDATE PlantillaComunicado SET pcoCuerpo  =  CONCAT(pcoCuerpo , '<br  /> <p>${cargoResponsableCcf}</p>'), pcoMensaje = CONCAT(pcoMensaje, '<br /> ${cargoResponsableCcf}') WHERE pcoEtiqueta = 'CRT_ENT_PAG';

--changeset flopez:07
--comment:Se modifica tamaño de campo de la tabla PostulacionFovis
ALTER TABLE PostulacionFOVIS ALTER COLUMN pofEstadoHogar VARCHAR (35) NULL;
