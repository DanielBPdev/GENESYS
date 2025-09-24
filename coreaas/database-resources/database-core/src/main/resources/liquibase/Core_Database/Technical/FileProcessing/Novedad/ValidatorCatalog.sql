--liquibase formatted sql

--changeset Heinsohn:01
--comment: Inserción de VALIDATORCATALOG
INSERT VALIDATORCATALOG (ID,CLASSNAME,DESCRIPTION,NAME,SCOPE,TENANTID) VALUES (1222,'com.asopagos.novedades.personas.web.load.validator.NovedadTrabajadorLineValidator','validador de línea de carga múltiple de novedades trabajadores dependientes','Validador línea carga múltiple','LINE',null);