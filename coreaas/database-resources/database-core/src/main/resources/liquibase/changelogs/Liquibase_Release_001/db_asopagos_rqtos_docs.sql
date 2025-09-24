--liquibase formatted sql

--changeset Heinsohn:1 stripComments:true

-- Creación de requisitos documentales

-- Se crean los requisitos para nueva afiliación
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Acta de posesión del representante legal','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia acta Asamblea/Acto de nombramiento administrador','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia de los Estatutos','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia del documento de identidad del administrador','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia del documento que certifique la personería jurídica','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia del RUT','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia documento identidad representante legal/empleador','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia resolución Ministerio con régimen compensaciones y trabajo asociado','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Documento acredita existencia/representación legal o Personería Jurídica','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Documento que acredite condición de Ley 1429 de 2010','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Documento que acredite la existencia y/o representación legal','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Formulario de solicitud de afiliación','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Información de nómina de los trabajadores a afiliar','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('La relación de los cooperados (y opcional: de sus beneficiarios)','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Paz y salvo caja de compensación afiliación anterior','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
-- Se crean los requisitos para Reintegro
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Acta de posesión del representante legal','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia acta Asamblea/Acto de nombramiento administrador','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia de los Estatutos','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia del documento de identidad del administrador','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia del documento que certifique la personería jurídica','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia del RUT','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia documento identidad representante legal/empleador','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Copia resolución Ministerio con régimen compensaciones y trabajo asociado','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Documento acredita existencia/representación legal o Personería Jurídica','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Documento que acredite condición de Ley 1429 de 2010','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Documento que acredite la existencia y/o representación legal','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Formulario de solicitud de reintegro','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Información de nómina de los trabajadores a afiliar','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('La relación de los cooperados (y opcional: de sus beneficiarios)','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT Requisito (reqDescripcion, reqTipoTransaccion) VALUES ('Paz y salvo caja de compensación afiliación anterior','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
GO