--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de LINELOADCATALOG
INSERT LINELOADCATALOG (ID,CLASSNAME,DESCRIPTION,NAME,TENANTID,LINEORDER,LINESEPARATOR,TARGETENTITY) VALUES (1221,'com.asopagos.afiliaciones.personas.web.load.TrabajadorDependientePersistLine','Información de la solicitud de afiliación del trabajador dependiente','Información del trabajador dependiente',null,1,'|',null);