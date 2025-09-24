--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de FILEDEFINITIONLOADTYPE
INSERT FILEDEFINITIONLOADTYPE (ID,DESCRIPTION,NAME) VALUES (1220,'Carga de archivo de solicitud de afiliación de múltiples trabajadores dependientes','Plantilla solicitud de afiliación de múltiples');