--liquibase formatted sql

--changeset jroa:01
--comment: Creacion de constante
INSERT Constante VALUES ('GOOGLE_STORAGE_BUCKET', 'CCF10', 'Nombre del Bucket en Google Cloud donde se almacenan los archivos de la aplicación');

--changeset abaquero:02
--comment: Actualización de perfil de lectura de archivo de pensionados de aporte manual
update LineDefinitionLoad set identifier = 2 where id = 12121