--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE CalificacionPostulacion_aud ADD cafValorParte7  NUMERIC(12,6) NULL;
ALTER TABLE CalificacionPostulacion_aud ADD cafValorParte8  NUMERIC(12,6) NULL;
