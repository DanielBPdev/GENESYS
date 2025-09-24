--liquibase formatted sql

--changeset jocorrea:01
--comment: Se crea el campo epaFechaCreacion
ALTER TABLE EntidadPagadora_aud ADD epaFechaCreacion DATE;