--liquibase formatted sql

--changeset jusanchez:01
--comment: Se agrega campo en la tabla AporteDetallado
ALTER TABLE AporteDetallado_aud ADD apdFechaMovimiento date;

--changeset clmarin:02
--comment: Se adiciona campo en la tabla AporteDetallado
ALTER TABLE AporteDetallado_aud ADD apdFechaCreacion date NULL;
