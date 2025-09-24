--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve mantis #0224404
UPDATE RequisitoCajaClasificacion SET rtsEstado='OBLIGATORIO' WHERE rtsRequisito =(SELECT reqid FROM Requisito WHERE reqDescripcion='Copia último desprendible pago de mesada pensional');

--changeset atoro:02
--comment: Se resuelve mantis #0224283
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso,vapInversa) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDAD_CAMBIO_TIPO_NUMERO_DOCUMENTO_BENEFICIARIO_CONYUGE','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','CONYUGE',1,'ACTIVO',0);
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso,vapInversa) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDAD_CAMBIO_TIPO_NUMERO_DOCUMENTO_BENEFICIARIO_EN_CUSTODIA','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','BENEFICIARIO_EN_CUSTODIA',1,'ACTIVO',0);
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso,vapInversa) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDAD_CAMBIO_TIPO_NUMERO_DOCUMENTO_HERMANO_HUERFANO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HERMANO_HUERFANO_DE_PADRES',1,'ACTIVO',0);
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso,vapInversa) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDAD_CAMBIO_TIPO_NUMERO_DOCUMENTO_HIJASTRO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HIJASTRO',1,'ACTIVO',0);
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso,vapInversa) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDAD_CAMBIO_TIPO_NUMERO_DOCUMENTO_HIJO_ADOPTIVO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HIJO_ADOPTIVO',1,'ACTIVO',0);
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso,vapInversa) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDAD_CAMBIO_TIPO_NUMERO_DOCUMENTO_HIJO_BIOLOGICO','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','HIJO_BIOLOGICO',1,'ACTIVO',0);
INSERT ValidacionProceso (vapProceso,vapBloque,vapValidacion,vapObjetoValidacion,vapOrden,vapEstadoProceso,vapInversa) VALUES ('NOVEDADES_PERSONAS_PRESENCIAL','NOVEDAD_CAMBIO_TIPO_NUMERO_DOCUMENTO_TRABAJADOR_DEPENDIENTE','VALIDACION_CAMBIO_TIPO_DOC_IDENTIDAD','TRABAJADOR_DEPENDIENTE',1,'ACTIVO',0);