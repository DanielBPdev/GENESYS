--liquibase formatted sql

--changeset atoro:01 stripComments:false
--Bloque 123-374-1 -- TRABAJADOR_INDEPENDIENTE
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_EXISTENCIA_PERSONA','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_PERSONA_FALLECIDA','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_PERSONA_INDEPENDIENTE','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
-- Bloque 123-374-1 -- PENSIONADO
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_EXISTENCIA_PERSONA','PENSIONADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_PERSONA_FALLECIDA','PENSIONADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_PERSONA_PENSIONADO','PENSIONADO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','PENSIONADO',1,'ACTIVO');