--liquibase formatted sql

--changeset jzambrano:01
--comment:Inserción de registros en la tabla ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapInversa) VALUES ('112-127-1','VALIDACION_EMPLEADOR_BD_CORE','AFILIACION_EMPRESAS_PRESENCIAL','ACTIVO',2,0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapInversa) VALUES ('112-127-1','VALIDACION_SOLICITUD_EMPLEADOR','AFILIACION_EMPRESAS_PRESENCIAL','ACTIVO',1,0);
