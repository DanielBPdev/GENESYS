--liquibase formatted sql

--changeset squintero:01
--comment: Inserciones en la tabla ValidacionProceso
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','CAMBIO_TIPO_NUMERO_DOCUMENTO_TRABAJADOR_DEPENDIENTE','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','TRABAJADOR_DEPENDIENTE',1,'ACTIVO');
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','CAMBIO_TIPO_NUMERO_DOCUMENTO_BENEFICIARIO_CONYUGE','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','CONYUGE',1,'ACTIVO');
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','CAMBIO_TIPO_NUMERO_DOCUMENTO_HIJO_BIOLOGICO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HIJO_BIOLOGICO',1,'ACTIVO');
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','CAMBIO_TIPO_NUMERO_DOCUMENTO_HIJASTRO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HIJASTRO',1,'ACTIVO');
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','CAMBIO_TIPO_NUMERO_DOCUMENTO_HERMANO_HUERFANO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HERMANO_HUERFANO_DE_PADRES',1,'ACTIVO');
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','CAMBIO_TIPO_NUMERO_DOCUMENTO_HIJO_ADOPTIVO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HIJO_ADOPTIVO',1,'ACTIVO');
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','CAMBIO_TIPO_NUMERO_DOCUMENTO_BENEFICIARIO_EN_CUSTODIA','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','BENEFICIARIO_EN_CUSTODIA',1,'ACTIVO');

--changeset flopez:02 
--comment: Insercion en la tabla ParametrizacionEjecucionProgramada 035-134-424,135-448,136-454
INSERT ParametrizacionEjecucionProgramada (pepProceso,pepHoras,pepMinutos,pepDiaMes,pepFrecuencia) VALUES ('REGISTRO_NOVEDAD_MULTIPLE_NO_FINALIZADA','00','00','01','MENSUAL')