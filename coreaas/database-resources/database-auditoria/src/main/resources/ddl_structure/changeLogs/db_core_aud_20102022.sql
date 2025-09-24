--liquibase formatted sql

--changeset jroa:01
--comment: Creacion de campo ubiEmailSecundario GLPI 49270- fovis
ALTER TABLE dbo.Ubicacion_aud ADD ubiEmailSecundario varchar(100);