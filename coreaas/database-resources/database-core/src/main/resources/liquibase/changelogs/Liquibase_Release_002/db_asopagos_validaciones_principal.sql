-- Bloque principal --VALIDACION_EXISTENCIA_PERSONA
delete ValidacionProceso where vapProceso='AFILIACION_PERSONAS_PRESENCIAL' and vapValidacion='VALIDACION_EXISTENCIA_PERSONA';
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','PRINCIPAL','VALIDACION_EXISTENCIA_PERSONA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
   insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','PRINCIPAL','VALIDACION_EXISTENCIA_PERSONA','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
   insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','PRINCIPAL','VALIDACION_EXISTENCIA_PERSONA','PENSIONADO',1,'ACTIVO');
   insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','PRINCIPAL','VALIDACION_EXISTENCIA_PERSONA','HIJO',1,'ACTIVO');
   insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','PRINCIPAL','VALIDACION_EXISTENCIA_PERSONA','CONYUGE',1,'ACTIVO');
   insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','PRINCIPAL','VALIDACION_EXISTENCIA_PERSONA','PADRES',1,'ACTIVO');