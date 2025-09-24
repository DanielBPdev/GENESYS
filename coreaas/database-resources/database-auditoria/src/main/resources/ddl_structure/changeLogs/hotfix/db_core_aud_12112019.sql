--liquibase formatted sql

--changeset dsuesca:01
ALTER TABLE AporteDetallado_aud ADD apdModalidadRecaudoAporte VARCHAR(40);
ALTER TABLE AporteDetallado_aud ADD apdMarcaCalculoCategoria BIT;