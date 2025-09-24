--liquibase formatted sql

--changeset jusanchez:01
--comment: Insercion en la tabla ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('111-059-2','VALIDACION_SOLICITUD_EMPLEADOR','AFILIACION_EMPRESAS_PRESENCIAL','ACTIVO',1,'GENERAL',0);