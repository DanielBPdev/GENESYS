--liquibase formatted sql

--changeset dsuesca:01
--comment: Actualizacion tabla 
UPDATE FieldCatalog SET label = 'Tipo de periodo liquidado' WHERE id = 48;

--changeset jvelandia:02
--comment: Insercion tabla CONSTANTE
INSERT INTO CONSTANTE(cnsNombre,cnsValor,cnsDescripcion)values('SEDE_USUARIO_SYSTEM','1','Sede para el cliente system del realm de integracion del keycloak');