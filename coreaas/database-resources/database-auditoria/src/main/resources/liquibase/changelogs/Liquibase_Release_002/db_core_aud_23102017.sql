--liquibase formatted sql

--changeset dasanchez:01
--comment:Se modifica campo en la tabla ParametrizacionTablaAuditable
ALTER TABLE ParametrizacionTablaAuditable ALTER COLUMN ptaId INT NOT NULL;

--changeset dasanchez:02
--comment: Se modifica campo en la tabla AhorroPrevio_aud
ALTER TABLE AhorroPrevio_aud DROP COLUMN ahp;
ALTER TABLE AhorroPrevio_aud ADD ahpId BIGINT NOT NULL;