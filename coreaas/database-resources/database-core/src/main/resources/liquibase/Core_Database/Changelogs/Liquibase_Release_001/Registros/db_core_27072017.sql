--liquibase formatted sql

--changeset jzambrano:01
--comment: Insercion en la tabla ValidacionProces
--validaciones para solicitudes simultaneas afiliar y 360 111-059-1
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('111-059-1','VALIDACION_SOLICITUD_WEB_EMPLEADOR','AFILIACION_EMPRESAS_PRESENCIAL','ACTIVO',1,null,0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('111-059-1','VALIDACION_SOLICITUD_EMPLEADOR','AFILIACION_EMPRESAS_PRESENCIAL','ACTIVO',1,null,0);

--validaciones para solicitudes simultaneas afiliar 66 111-066-1
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('111-066-1','VALIDACION_SOLICITUD_EMPLEADOR','AFILIACION_EMPRESAS_PRESENCIAL',null,1,null,0);

--validaciones para solicitudes simultaneas afiliar 110 112-110-1
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('112-110-1','VALIDACION_SOLICITUD_WEB_EMPLEADOR','AFILIACION_EMPRESAS_WEB','ACTIVO',1,null,0);

--validaciones para solicitudes simultaneas afiliar121 112-121-1
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('112-121-1','VALIDACION_SOLICITUD_EMPLEADOR','AFILIACION_EMPRESAS_WEB','ACTIVO',1,null,0);
