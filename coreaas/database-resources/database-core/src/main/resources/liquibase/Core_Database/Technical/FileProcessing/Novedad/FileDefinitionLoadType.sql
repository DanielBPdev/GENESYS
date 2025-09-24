--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de FILEDEFINITIONLOADTYPE
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) VALUES (1221,'Carga de archivo para solicitudes de novedad web de trabajadores dependientes','Plantilla solicitud de novedades múltiples');
