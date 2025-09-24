--liquibase formatted sql

--changeset  abaquero:01
--comment: Registros de AFP

INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('25-2', 'Caxdac');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('231001', 'Colfondos');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('25-11', 'Colpensiones');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('25-3', 'Fonprecon');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('25-7', 'Pensiones de Antioquia');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('230301', 'Porvenir');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('230201', 'Proteccion');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('230901', 'Skandia');
INSERT INTO AFP (afpCodigoPila,afpNombre) VALUES ('230904', 'Skandia alternativo');

--changeset  atoro:02
--comment: validaciones nuevos registros ValidacionProceso
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-104-2N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-104-3N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-104-5N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-104-6N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');   

--bloques Nuevo 121-107
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE','HIJO',1,'ACTIVO');  
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-107-1N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   
--bloques Nuevo 121-108
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
  values('AFILIACION_PERSONAS_PRESENCIAL','121-108-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');  
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-2N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-2N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-3N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-3N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-4N','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-4N','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-5N','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-5N','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-5N','VALIDACION_AFILIADO_CON_HERMANO_HUERFANO','PADRES',1,'ACTIVO');  
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-4N','VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE','HIJO',1,'ACTIVO');  
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-5N','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-4N','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-5N','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-5N','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-1N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-2N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');    
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-3N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-1N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-2N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-108-3N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   
--bloques Nuevo 121-122
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_AFILIADO_CON_HERMANO_HUERFANO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE','HERMANO_HUERFANO_DE_PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','121-122-1N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   
--bloques Nuevo 122-359   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-359-1N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
--bloques Nuevo 122-361      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-361-1N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');   
--bloques Nuevo 122-363  
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-363-1N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');   
--bloques Nuevo 122-365
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO_BIOLOGICO',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   
--bloques Nuevo 122-369   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_AFILIADO_CON_HERMANO_HUERFANO','PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-365-1N','VALIDACION_AFILIADO_BENEFICIARIO_TIPO_PADRE','HERMANO_HUERFANO_DE_PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','122-369-2N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   
--bloques Nuevo 122-374
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-374-1N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');   
--bloques Nuevo 122-377   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-377-1N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   
--bloques Nuevo 122-379      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-379-1N','VALIDACION_PERSONA_NO_MAYOR_15','TRABAJADOR_INDEPENDIENTE',1,'ACTIVO');
--bloques Nuevo 122-380      
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','CONYUGE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','CONYUGE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','HIJO',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_GRUPO_FAMILIAR','PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_OTRO_PARENTESCO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_AFILIADO_CON_HERMANO_HUERFANO','PADRES',1,'ACTIVO');  
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_AFILIADO_CON_HERMANO_HUERFANO','PADRES',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_MENOR_AFILIADO','PADRES',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_MAYOR_AFILIADO','HIJO',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_AFILIADO_BENEFICIARIO_PADRE','PADRE',1,'ACTIVO');
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_AFILIADO_BENEFICIARIO_MADRE','MADRE',1,'ACTIVO');   
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_PERSONA_NO_MAYOR_14','CONYUGE',1,'ACTIVO'); 
insert into ValidacionProceso(vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso)
   values('AFILIACION_PERSONAS_PRESENCIAL','123-380-1N','VALIDACION_AFILIADO_BENEFICIARIO_CONYUGE','CONYUGE',1,'ACTIVO');   

