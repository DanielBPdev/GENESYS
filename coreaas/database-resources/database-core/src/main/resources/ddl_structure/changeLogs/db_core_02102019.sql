--liquibase formatted sql

--changeset clmarin:01
--comment:
INSERT INTO core.dbo.ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('111-059-1', 'VALIDACION_EMPLEADOR_BD_CORE', 'AFILIACION_EMPRESAS_WEB', 'ACTIVO', 2, NULL, 0);
INSERT INTO core.dbo.ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('111-059-1', 'VALIDACION_SOLICITUD_EMPLEADOR', 'AFILIACION_EMPRESAS_WEB', 'ACTIVO', 1, NULL, 0);
INSERT INTO core.dbo.ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('111-059-1', 'VALIDACION_SOLICITUD_WEB_EMPLEADOR', 'AFILIACION_EMPRESAS_WEB', 'ACTIVO', 1, NULL, 0);