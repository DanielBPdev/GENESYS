--liquibase formatted sql

--changeset jusanchez:01 
--comment: Bloque principal proceso 1.2.2 AFILIACION DEPENDIENTE WEB
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','PRINCIPAL','VALIDACION_EXISTENCIA_PERSONA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','PRINCIPAL','VALIDACION_PERSONA_MISMO_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','PRINCIPAL','VALIDACION_PERSONA_FALLECIDA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','PRINCIPAL','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','PRINCIPAL','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','PRINCIPAL','VALIDACION_PERSONA_COOPERANTE','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');

--changeset jusanchez:02 
--comment: Bloque 122-363-1
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1','VALIDACION_EXISTENCIA_PERSONA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1','VALIDACION_PERSONA_MISMO_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1','VALIDACION_PERSONA_FALLECIDA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1','VALIDACION_PERSONA_COOPERANTE','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 

--changeset jusanchez:03 
--comment: Bloque 122-363-1N
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1N','VALIDACION_EXISTENCIA_PERSONA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1N','VALIDACION_PERSONA_MISMO_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1N','VALIDACION_PERSONA_FALLECIDA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1N','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) values('AFILIACION_DEPENDIENTE_WEB','122-363-1N','VALIDACION_PERSONA_COOPERANTE','TRABAJADOR_DEPENDIENTE',1,'ACTIVO'); 