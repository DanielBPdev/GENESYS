--liquibase formatted sql

--changeset atoro:01
--comment: Se resuelve mantis 0224000
UPDATE ValidacionProceso SET vapBloque='NOVEDAD_CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_2_PRESENCIAL' WHERE vapBloque='NOVEDAD_CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_2_PRESENCIAL';
UPDATE ValidacionProceso SET vapBloque='CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_2_PRESENCIAL' WHERE vapBloque='CAMBIO_ESTADO_CIVIL_MAS_1_5SM_0_2_PRESENCIAL';

--changeset mosanchez:02
--comment: Se resuelve incidencia
ALTER TABLE Afiliado ADD CONSTRAINT UK_Afiliado_afiPersona UNIQUE (afiPersona); 