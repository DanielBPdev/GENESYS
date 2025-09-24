--liquibase formatted sql

--changeset  alopez:01
--comment: 31/10/2016-alopez-HU-Creación Parametros

INSERT INTO Parametro (prmNombre,prmValor) VALUES ('IDM_EMPLEADORES_WEB_DOMAIN_NAME','empleadores_domain');
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('IDM_EMPLEADORES_WEB_CLIENT_ID','realm-management');
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('IDM_EMPLEADORES_WEB_CLIENT_SECRET','b9b1493b-384a-42dd-a6b8-bf4b980427f4');
INSERT INTO Parametro (prmNombre,prmValor) VALUES ('SEC_INITIAL_CHARACTERS_PASSWORD','8');

--changeset  mgiraldo:02
--comment: 31/10/2016-mgiraldo-Creación unique EtiquetaCorrespondenciaRadicado
ALTER TABLE EtiquetaCorrespondenciaRadicado ADD CONSTRAINT UK_EtiquetaCorrespondenciaRadicado_eprCodigo UNIQUE (eprCodigo);

--changeset  jcamargo:03
--comment: 31/10/2016-mgiraldo-HU-SEG
INSERT INTO PlantillaComunicado(pcoEtiqueta,pcoAsunto,pcoMensaje) VALUES ('NOTIFICACION_CREACION_USUARIO_EXITOSA','Creación de usuario exitosa','Señor (a) <br/> [nombreUsuario] <br/><br/><br/> El usuario solicitado en el portal de GENESYS ha sido creado exitosamente. <br/><br/>Para autenticarse debe hacer uso de las siguientes credenciales:<br/><br/>Usuario:[usuario]<br/>Password:[Password]<br/><br/><br/>Cordialmente,<br/><br/><br/><br/>Administrador GENESYS');