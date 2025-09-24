--liquibase formatted sql

--changeset squintero:01
--comment: Se agrega campo apgMarcaActualizacionCartera a tabla AporteGeneral
ALTER TABLE AporteGeneral ADD apgMarcaActualizacionCartera BIT;