--liquibase formatted sql


--changeset  jcamargo:01
--comment: Creación de la constante enumeración ValidacionCoreEnum
insert into ValidacionProceso(vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden) values('121-104-1','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1);
insert into ValidacionProceso(vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden) values('121-104-3','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1);
insert into ValidacionProceso(vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden) values('121-104-4','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1);
insert into ValidacionProceso(vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden) values('REINTEGRO_AFILIADO','VALIDACION_TIEMPO_REINTEGRO_AFILIADO','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1);