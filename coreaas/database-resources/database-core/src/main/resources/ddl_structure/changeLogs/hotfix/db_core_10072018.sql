--liquibase formatted sql

--changeset rarboleda:02
--comment: Actualizacion tabla ParametrizacionMetodoAsignacion
UPDATE ParametrizacionMetodoAsignacion SET pmaMetodoAsignacion='NUMERO_SOLICITUDES', pmaUsuario=NULL WHERE pmaProceso = 'AFILIACION_DEPENDIENTE_WEB';

--changeset rarboleda:03
--comment: Actualizacion tabla ParametrizacionMetodoAsignacion
ALTER TABLE LegalizacionDesembolso ALTER COLUMN lgdDocumentoSoporte VARCHAR(255);
ALTER TABLE LegalizacionDesembolso ALTER COLUMN lgdDocumentoSoporteBack VARCHAR(255);
ALTER TABLE aud.LegalizacionDesembolso_aud ALTER COLUMN lgdDocumentoSoporte VARCHAR(255);
ALTER TABLE aud.LegalizacionDesembolso_aud ALTER COLUMN lgdDocumentoSoporteBack VARCHAR(255);

--changeset jocorrea:04
--comment: Cambio logitud campo 
ALTER TABLE CargueMultipleSupervivencia ALTER COLUMN cmsNombreArchivo VARCHAR(255) NOT NULL;
ALTER TABLE aud.CargueMultipleSupervivencia_aud ALTER COLUMN cmsNombreArchivo VARCHAR(255);

--changeset abaquero:05
--comment: Actualizacion tabla ValidatorDefinition
UPDATE ValidatorDefinition SET state=0 WHERE id=2110213;

--changeset jvelandia:06
--comment: Insersiones tabla Constante
INSERT Constante(cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_INTEGRATION_WEB_SYSTEM_PUBLIC_CLIENT_ID','system','Nombre del cliente del realm de integracion');
INSERT Constante(cnsNombre,cnsValor,cnsDescripcion) VALUES ('IDM_INTEGRATION_WEB_SYSTEM_PUBLIC_CLIENT_SECRET','80425859-dc90-4517-9781-1d38ff4c2f23','Contraseña del  cliente de keycloak usado para obtener tokens para el cliente system');
