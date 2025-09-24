--liquibase formatted sql

--changeset atoro:01 stripComments:false
-- Bloque 121-107-1-- CONYUGE
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_EXISTENCIA_PERSONA','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_FALLECIDA','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_HIJO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_PADRE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_AFILIADO_CONYUGE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_INCLUIDA_SOLICITUD','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');
--  Bloque 121-107-1-- HIJO 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_EXISTENCIA_PERSONA','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_FALLECIDA','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_EMPLEADOR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_PADRE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_CONYUGE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_PENSIONADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_DEPENDIENTE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_INDEPENDIENTE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_PAGOS_APORTES','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_HIJO_DOS_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_INCLUIDA_SOLICITUD','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_HIJASTRO_OTRO_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_HIJO_BIOLOGICO','HIJO',1,'ACTIVO');
--  Bloque 121-107-1-- PADRE 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_EXISTENCIA_PERSONA','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_FALLECIDA','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_EMPLEADOR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_HIJO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_PADRE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_BENEFICIARIO_CONYUGE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_PENSIONADO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_DEPENDIENTE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_INDEPENDIENTE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_PAGOS_APORTES','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1','VALIDACION_PERSONA_INCLUIDA_SOLICITUD','PADRES',1,'ACTIVO');
--  Bloque 121-107-2-- TRABAJADOR_DEPENDIENTE
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_PERSONA_SOCIO_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_PERSONA_CONYUGE_SOCIO','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_PERSONA_REPRESENTANTE_LEGAL_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_FECHA_LABORES_MAYOR_FECHA_AFILIACION','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_LABORES','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
-- Bloque 121-107-2-- TRABAJADOR_INDEPENDIENTE
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
-- Bloque 121-107-2-- PENSIONADO 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-2','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','PENSIONADO',1,'ACTIVO');