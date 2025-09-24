--liquibase formatted sql

--changeset  jcamargo:01

--comment: Inserción ValidacionProceso
insert into ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden) values('REINTEGRO','VALIDACION_EMPLEADOR_BD_CORE','AFILIACION_EMPRESAS_PRESENCIAL', 'ACTIVO',3);