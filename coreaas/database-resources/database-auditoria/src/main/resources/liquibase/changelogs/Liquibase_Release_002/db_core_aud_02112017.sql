--liquibase formatted sql

--changeset anvalbuena:01
--comment: Se adiciona campo en la tabla ParametrizacionEjecucionProgramada_aud
ALTER TABLE ParametrizacionEjecucionProgramada_aud ADD pepEstado VARCHAR(8);