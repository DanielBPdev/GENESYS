--liquibase formatted sql

--changeset jzambrano:01
--comment:Insercion de registros en la tabla ValidacionProceso
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('121-122-1P','VALIDACION_PERSONA_MENOR_60','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('122-369-2P','VALIDACION_PERSONA_MENOR_60','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-380-1P','VALIDACION_PERSONA_MENOR_60','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);