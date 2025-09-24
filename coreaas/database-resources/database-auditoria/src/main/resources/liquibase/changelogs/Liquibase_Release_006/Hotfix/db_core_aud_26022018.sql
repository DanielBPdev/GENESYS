--liquibase formatted sql

--changeset flopez:01
--comment:Se modifica campo en la tabla ProyectoSolucionVivienda
ALTER TABLE ProyectoSolucionVivienda_aud ALTER COLUMN psvModalidad VARCHAR(50) NULL;

--changeset jocorrea:02
--comment:
ALTER TABLE IntegranteHogar_aud ADD inhPostulacionFovis BIGINT