--liquibase formatted sql

--changeset atoro:01 stripComments:false
--Bloque 122-369-1 -
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_EXISTENCIA_PERSONA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_MISMO_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_FALLECIDA','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_BENEFICIARIO_HIJO','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_BENEFICIARIO_PADRE','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_CONDICION_INVALIDEZ','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_SOCIO_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_CONYUGE_SOCIO','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_REPRESENTANTE_LEGAL_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_MISMO_EMPLEADOR_SOLICITANTE','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_CONYUGE_EMPLEADOR','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-1','VALIDACION_PERSONA_COOPERANTE','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');

--Bloque 122-369-2 - CONYUGE
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_EXISTENCIA_PERSONA','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_FALLECIDA','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_HIJO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_PADRE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_AFILIADO_CONYUGE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_INCLUIDA_SOLICITUD','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');

--Bloque 122-369-2 - HIJO 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_EXISTENCIA_PERSONA','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_FALLECIDA','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_EMPLEADOR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_HIJO','BENEFICIARIO_EN_CUSTODIA',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_PADRE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_CONYUGE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_PENSIONADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_DEPENDIENTE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_INDEPENDIENTE','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_TIENE_19','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_PAGOS_APORTES','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_HIJO_DOS_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_HIJO_AFILIADO_PRINCIPAL','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE','HERMANO_HUERFANO_DE_PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_INCLUIDA_SOLICITUD','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_HIJASTRO_OTRO_GRUPO_FAMILIAR','HIJASTRO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_HIJO_BIOLOGICO','HIJO_ADOPTIVO',1,'ACTIVO');

--Bloque 122-369-2 - 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_EXISTENCIA_PERSONA','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_FALLECIDA','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_EMPLEADOR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_HIJO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_PADRE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_BENEFICIARIO_CONYUGE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_PENSIONADO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_DEPENDIENTE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_INDEPENDIENTE','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_PAGOS_APORTES','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_MENOR_60','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_SIN_COINCIDENCIA_APELLIDOS_AFILIADO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_AFILIADO_CON_HERMANO_HUERFANO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_FECHA_NACIMIENTO_MAYOR_FECHA_AFILIACION','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2','VALIDACION_PERSONA_INCLUIDA_SOLICITUD','PADRES',1,'ACTIVO');
