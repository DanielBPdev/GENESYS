--liquibase formatted sql

--changeset abaquero:01
--comment:Se adiciona campo en la tabla AporteGeneral_aud
ALTER TABLE AporteGeneral_aud ADD apgEmpresaTramitadoraAporte BIGINT NULL;