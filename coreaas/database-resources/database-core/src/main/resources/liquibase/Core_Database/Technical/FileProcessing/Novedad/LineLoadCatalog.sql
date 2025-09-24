--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de LINELOADCATALOG
INSERT LINELOADCATALOG (ID,CLASSNAME,DESCRIPTION,NAME,TENANTID,LINEORDER,LINESEPARATOR,TARGETENTITY) VALUES (1222,'com.asopagos.novedades.personas.web.load.NovedadTrabajadorPersistLine','Información de la solicitud de novedad del trabajador dependiente','Información de la novedad del trabajador dependiente',null,1,'|',null);