--liquibase formatted sql

--changeset jzambrano:01
--comment: Se resuelve incidencia
UPDATE ValidacionProceso SET vapObjetoValidacion='HIJO_BIOLOGICO' WHERE vapValidacion='VALIDACION_PERSONA_MAYOR_AFILIADO' AND vapProceso='AFILIACION_PERSONAS_PRESENCIAL'
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-380-1','VALIDACION_PERSONA_FALLECIDA','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-380-1','VALIDACION_PERSONA_EMPLEADOR','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-380-1','VALIDACION_PERSONA_BENEFICIARIO_HIJO','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-380-1','VALIDACION_PERSONA_BENEFICIARIO_PADRE','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);
INSERT ValidacionProceso (vapBloque,vapValidacion,vapProceso,vapEstadoProceso,vapOrden,vapObjetoValidacion,vapInversa) VALUES ('123-380-1','VALIDACION_PERSONA_BENEFICIARIO_CONYUGE','AFILIACION_PERSONAS_PRESENCIAL','ACTIVO',1,'PADRES',0);
