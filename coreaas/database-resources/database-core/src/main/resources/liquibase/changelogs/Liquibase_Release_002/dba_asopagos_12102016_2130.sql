--liquibase formatted sql

--changeset atoro:01 stripComments:false  
ALTER TABLE DetalleDatosRegistroValidacion  ALTER COLUMN [ddrValorDetalle] VARCHAR (50);
ALTER TABLE DetalleDatosRegistroValidacion drop CONSTRAINT PK_DetalleDatosRegistroValidacion_ddrvIdDato_ddrIdDetalle;
ALTER TABLE DetalleDatosRegistroValidacion  ALTER COLUMN [ddrIdDetalle] VARCHAR (50) not null;
ALTER TABLE DetalleDatosRegistroValidacion ALTER COLUMN ddrIdDato bigint NOT NULL;
ALTER TABLE DetalleDatosRegistroValidacion ADD CONSTRAINT PK_DetalleDatosRegistroValidacion_ddrvIdDato_ddrIdDetalle PRIMARY KEY (ddrIdDato,ddrIdDetalle);

--changeset alopez:02 stripComments:false  
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_DOMAIN_NAME','Integracion');
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_CLIENT_ID','realm-management');
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('IDM_INTEGRATION_WEB_CLIENT_SECRET','409e5a38-fc8c-475d-8f26-78d1fc1044b1');

--changeset hhernandez:05 stripComments:false  
ALTER TABLE RolAfiliado ADD roaFechaAfiliacion DATE;

--changeset mgiraldo:08 stripComments:false  
DROP TABLE RequisitoTipoTransaccion;

--changeset mgiraldo:09 stripComments:false 
ALTER TABLE requisitotiposolicitante ALTER COLUMN rtsClasificacion VARCHAR(100);
ALTER TABLE requisitotiposolicitante ADD rtsTipoTransaccion VARCHAR(100);


--changeset mgiraldo:11 stripComments:false 
DELETE FROM RequisitoTipoSolicitante;
DBCC CHECKIDENT (RequisitoTipoSolicitante, RESEED, 0);

DELETE FROM  ItemChequeoAfiliEmpleador;
DBCC CHECKIDENT (RequisitoTipoSolicitante, RESEED, 0);

DELETE FROM RequisitoAfiliaciEmpleador;
DBCC CHECKIDENT (RequisitoTipoSolicitante, RESEED, 0);

DELETE FROM RequisitoCajaCompensacion;
DBCC CHECKIDENT (RequisitoTipoSolicitante, RESEED, 0);

DELETE FROM Requisito;
DBCC CHECKIDENT (Requisito, RESEED, 0);


--changeset mgiraldo:12 stripComments:false 
INSERT INTO Requisito (reqDescripcion) VALUES ('Formulario de solicitud de afiliación');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia del RUT');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia documento identidad  representante legal/empleador');
INSERT INTO Requisito (reqDescripcion) VALUES ('Documento que acredite la existencia y/o representación legal');
INSERT INTO Requisito (reqDescripcion) VALUES ('Acta de posesión del representante legal');
INSERT INTO Requisito (reqDescripcion) VALUES ('Información de nómina de los trabajadores a afiliar');
INSERT INTO Requisito (reqDescripcion) VALUES ('Paz y salvo caja de compensación  afiliación anterior');
INSERT INTO Requisito (reqDescripcion) VALUES ('Documento que acredite condición de Ley 1429 de 2010');
INSERT INTO Requisito (reqDescripcion) VALUES ('Formulario de solicitud de reintegro');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia del documento que certifique la personería jurídica');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia del documento de identidad del administrador');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia acta Asamblea/Acto de nombramiento administrador');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia resolución Ministerio con régimen compensaciones y trabajo asociado');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia de los Estatutos');
INSERT INTO Requisito (reqDescripcion) VALUES ('La relación de los cooperados (y opcional: de sus beneficiarios)');
INSERT INTO Requisito (reqDescripcion) VALUES ('Documento acredita existencia/representación legal o Personería Jurídica');


INSERT INTO Requisito (reqDescripcion) VALUES ('Formulario solicitud afiliación o reintegro');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia del documento de identidad');
INSERT INTO Requisito (reqDescripcion) VALUES ('Formulario solicitud afiliación o reintegro independientes');
INSERT INTO Requisito (reqDescripcion) VALUES ('Formulario de afiliación o reintegro de pensionados');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia último desprendible pago de mesada pensional');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia de la historia laboral');
INSERT INTO Requisito (reqDescripcion) VALUES ('Documento evidencia de sociedad conyugal o unión marital de hecho');
INSERT INTO Requisito (reqDescripcion) VALUES ('Certificación laboral');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia registro civil de nacimiento del afiliado principal');
INSERT INTO Requisito (reqDescripcion) VALUES ('Declaración juramentada de dependencia económica');
INSERT INTO Requisito (reqDescripcion) VALUES ('Certificado de condición de invalidez');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia registro civil de nacimiento del hermano huérfano');
INSERT INTO Requisito (reqDescripcion) VALUES ('Certificado de escolaridad');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia del registro civil de defunción de padre y madre');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia registro civil de nacimiento del hijo biológico');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia registro civil de nacimiento del hijastro');
INSERT INTO Requisito (reqDescripcion) VALUES ('Copia registro civil de nacimiento del hijo adoptivo');
INSERT INTO Requisito (reqDescripcion) VALUES ('Certificado de custodia');

--changeset mgiraldo:13 stripComments:false 
/*AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Acta de posesión del representante legal'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento que certifique la personería jurídica'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad del administrador'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia acta Asamblea/Acto de nombramiento administrador'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia resolución Ministerio con régimen compensaciones y trabajo asociado'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de los Estatutos'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='La relación de los cooperados (y opcional: de sus beneficiarios)'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento acredita existencia/representación legal o Personería Jurídica'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_NUEVA_AFILIACION');



/*AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION'*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Acta de posesión del representante legal'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento que certifique la personería jurídica'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad del administrador'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia acta Asamblea/Acto de nombramiento administrador'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia resolución Ministerio con régimen compensaciones y trabajo asociado'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de los Estatutos'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='La relación de los cooperados (y opcional: de sus beneficiarios)'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento acredita existencia/representación legal o Personería Jurídica'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'INHABILITADO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_NUEVA_AFILIACION');



/*AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Acta de posesión del representante legal'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento que certifique la personería jurídica'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad del administrador'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia acta Asamblea/Acto de nombramiento administrador'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia resolución Ministerio con régimen compensaciones y trabajo asociado'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de los Estatutos'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='La relación de los cooperados (y opcional: de sus beneficiarios)'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento acredita existencia/representación legal o Personería Jurídica'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_PRESENCIAL_REINTEGRO');




/*AFILIACION_EMPLEADORES_WEB_REINTEGRO'*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Acta de posesión del representante legal'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OPCIONAL','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','PERSONA_JURIDICA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite condición de Ley 1429 de 2010'),'OPCIONAL','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','PERSONA_NATURAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','EMPLEADOR_DE_SERVICIO_DOMESTICO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento que certifique la personería jurídica'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad del administrador'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia acta Asamblea/Acto de nombramiento administrador'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','PROPIEDAD_HORIZONTAL','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia resolución Ministerio con régimen compensaciones y trabajo asociado'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de los Estatutos'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='La relación de los cooperados (y opcional: de sus beneficiarios)'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','COOPERATIVA_O_PRECOOPERATIVA_DE_TRABAJO_ASOCIADO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento acredita existencia/representación legal o Personería Jurídica'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','ENTIDAD_SIN_ANIMO_DE_LUCRO','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de afiliación'),'INHABILITADO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del RUT'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia documento identidad  representante legal/empleador'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento que acredite la existencia y/o representación legal'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Información de nómina de los trabajadores a afiliar'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Paz y salvo caja de compensación  afiliación anterior'),'OPCIONAL','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de solicitud de reintegro'),'OBLIGATORIO','ORGANIZACION_RELIGIOSA_O_PARROQUIA','AFILIACION_EMPLEADORES_WEB_REINTEGRO');


/*"'AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION'"*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro'),'OBLIGATORIO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_NUEVA_AFILIACION');


/*"'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO'"*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro'),'OBLIGATORIO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'INHABILITADO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'INHABILITADO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');



/*"'AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO'"*/

INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro'),'OBLIGATORIO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_DEPENDIENTE','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_INDEPENDIENTE_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario solicitud afiliación o reintegro independientes'),'OBLIGATORIO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','TRABAJADOR_INDEPENDIENTE_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'INHABILITADO','FIDELIDAD_25_ANIOS','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia de la historia laboral'),'INHABILITADO','MENOS_1_5_SM_0_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MENOS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_0_6_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','MAS_1_5_SM_2_POR_CIENTO','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Formulario de afiliación o reintegro de pensionados'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'INHABILITADO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia último desprendible pago de mesada pensional'),'OBLIGATORIO','PENSION_FAMILIAR','AFILIACION_PERSONAS_PRESENCIAL_REINTEGRO');

/*AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','CONYUGE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento evidencia de sociedad conyugal o unión marital de hecho'),'OBLIGATORIO','CONYUGE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificación laboral'),'OPCIONAL','CONYUGE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del afiliado principal'),'OBLIGATORIO','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del afiliado principal'),'OBLIGATORIO','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del afiliado principal'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hermano huérfano'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del registro civil de defunción de padre y madre'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijo biológico'),'OBLIGATORIO','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijastro'),'OBLIGATORIO','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijo adoptivo'),'OBLIGATORIO','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OBLIGATORIO','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijo adoptivo'),'OBLIGATORIO','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de custodia'),'OBLIGATORIO','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_NUEVA_AFILIACION');

/*AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO*/
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','CONYUGE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Documento evidencia de sociedad conyugal o unión marital de hecho'),'OPCIONAL','CONYUGE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificación laboral'),'OPCIONAL','CONYUGE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del afiliado principal'),'OBLIGATORIO','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','PADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del afiliado principal'),'OBLIGATORIO','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','MADRE','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del afiliado principal'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hermano huérfano'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del registro civil de defunción de padre y madre'),'OPCIONAL','HERMANO_HUERFANO_DE_PADRES','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijo biológico'),'OBLIGATORIO','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HIJO_BIOLOGICO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijastro'),'OBLIGATORIO','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HIJASTRO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijo adoptivo'),'OBLIGATORIO','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','HIJO_ADOPTIVO','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia del documento de identidad'),'OPCIONAL','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Copia registro civil de nacimiento del hijo adoptivo'),'OBLIGATORIO','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de condición de invalidez'),'OPCIONAL','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de escolaridad'),'OPCIONAL','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Declaración juramentada de dependencia económica'),'OBLIGATORIO','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');
INSERT INTO RequisitoTipoSolicitante (rtsRequisito,rtsEstado,rtsClasificacion,rtsTipoTransaccion) 
VALUES((SELECT reqId FROM Requisito  WHERE reqDescripcion='Certificado de custodia'),'OBLIGATORIO','BENEFICIARIO_EN_CUSTODIA','AFILIACION_PERSONAS_PRESENCIAL_BENEFICIARIO_REINTEGRO');


--changeset mgiraldo:14 stripComments:false 
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (1,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (2,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (3,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (4,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (5,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (6,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (7,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (8,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (9,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (10,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (11,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (12,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (13,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (14,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (15,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (16,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (17,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (18,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (19,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (20,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (21,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (22,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (23,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (24,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (25,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (26,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (27,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (28,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (29,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (30,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (31,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (32,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (33,1,'HABILITADO');
INSERT INTO RequisitoCajaCompensacion (rccRequisito,rccCajaCompensacion,rccEstado) VALUES (34,1,'HABILITADO');

--changeset jcamrgo:16 stripComments:false  
ALTER TABLE RolContactoEmpleador ALTER COLUMN  rceCorreoEnviado bit;

--changeset mgiraldo:17 stripComments:false
ALTER TABLE Empleador ADD  CONSTRAINT FK_Empleador_empId FOREIGN KEY([empId])
REFERENCES Empresa (empId);

--changeset leogiral:18 stripComments:false
INSERT [dbo].[Parametro] ([prmNombre], [prmValor])VALUES ('BPMS_PROCESS_AFIL_PERS_PRES_DEPLOYMENTID', 'com.asopagos.coreaas.bpm.afiliacion-personas-presencial:afiliacion_personas_presencial:0.0.2-SNAPSHOT');