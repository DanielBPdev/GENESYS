--liquibase formatted sql

--changeSET Heinsohn:01
--comment: Inserción inicial requisitos documentales
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Acta de posesión del representante legal');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Carta del Representante Legal dirigida a CCF solicitando el trámite de la novedad');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Certificación laboral');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Certificado de afiliación a EPS');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Certificado de condición de invalidez');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Certificado de cuenta emitido por entidad bancaria reconocida');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Certificado de incapacidad');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Certificado escolar o copia boletín calificaciones (edad 12 a 18 años)');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Certificado semestral de estudios (edad 19 a 22 años)');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Comunicado de cambio de estado de autorización de envío de información de correo electrónico y/o autorización de utilización de datos personales');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Comunicado formal de cambio de tipo de independiente');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Comunicado formal de parte de la entidad pagadora ');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Comunicado formal de retiro voluntario');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia acta Asamblea/Acto de nombramiento administrador');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia de la historia laboral');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia de los Estatutos');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia de registro civil de defunción de la persona objeto de la novedad');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia de registro civil de defunción de padre y madre');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia del documento de identidad');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia del documento que certifique la personería jurídica');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia del RUT');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia documento identidad  representante legal / administrador / empleador');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia memorial DIAN manifestando intención de acogerse a beneficios Ley 590');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia registro civil de nacimiento del afiliado principal');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia registro civil de nacimiento del beneficiario');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia resolución Ministerio con régimen compensaciones y trabajo asociado');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Copia último desprendible pago de mesada pensional');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Declaración de no convivencia');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Documento acredita existencia/representación legal o Personería Jurídica');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Documento evidencia de sociedad conyugal o unión marital de hecho');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Documento que certifique la custodia del menor');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Documento que certifique la disolución y liquidación de sociedad conyugal o divorcio.');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Documento soporte para solicitar reexpedición de tarjeta');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Formato declaración juramentada MinTrabajo');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('SECUNDARIO_AFILIACION','HABILITADO','Formulario de solicitud de afiliación');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('SECUNDARIO_AFILIACION','HABILITADO','Formulario de solicitud de reintegro');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('SECUNDARIO_AFILIACION','HABILITADO','Formulario solicitud afiliación o reintegro independiente');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('SECUNDARIO_AFILIACION','HABILITADO','Formulario solicitud afiliación o reintegro pensionado');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('SECUNDARIO_AFILIACION','HABILITADO','Formulario solicitud afiliación o reintegro trabajador dependiente');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Información de nómina de los trabajadores a afiliar');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','La relación de los cooperados (y opcional: de sus beneficiarios)');
INSERT Requisito (reqTipoRequisito,reqEstado,reqDescripcion) VALUES ('ESTANDAR','HABILITADO','Paz y salvo caja de compensación  afiliación anterior');