--liquibase formatted sql

--changeset jusanchez:01
--comment: Eliminacion en la tabla Constante
DELETE FROM Constante WHERE cnsNombre='CARGO_RESPONSABLE_CCF'