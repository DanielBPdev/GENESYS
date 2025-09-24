--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE PostulacionFOVIS_aud ALTER COLUMN pofPuntaje numeric(8,2);
ALTER TABLE CalificacionPostulacion_aud ALTER COLUMN cafPuntaje numeric(12,6);
ALTER TABLE CalificacionPostulacion_aud ALTER COLUMN cafValorB4 numeric(12,6);