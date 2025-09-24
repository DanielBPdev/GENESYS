--liquibase formatted sql

--changeset clamarin:01
--comment:
INSERT INTO ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('121-107-1', 'VALIDACION_PERSONA_HIJO_AFILIADO_PRINCIPAL', 'AFILIACION_PERSONAS_PRESENCIAL', 'ACTIVO', 1, 'HIJO', 0);

--changeset clamarin:02
--comment:
INSERT INTO ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('121-122-1', 'VALIDACION_EMPLEADOR_ACTIVO', 'AFILIACION_PERSONAS_PRESENCIAL', NULL, 2, 'GENERAL', 0);

--changeset clamarin:03
--comment:
INSERT INTO ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('121-122-1', 'VALIDACION_EMPLEADOR_ACTIVO', 'AFILIACION_PERSONAS_PRESENCIAL', 'ACTIVO', 1, 'PERSONA', 1);

--changeset clamarin:04
--comment:
INSERT INTO ValidacionProceso
(vapBloque, vapValidacion, vapProceso, vapEstadoProceso, vapOrden, vapObjetoValidacion, vapInversa)
VALUES('121-122-1P', 'VALIDACION_PERSONA_BENEFICIARIO_HIJO', 'AFILIACION_PERSONAS_PRESENCIAL', 'ACTIVO', 2, 'HERMANO_HUERFANO_DE_PADRES', 0);