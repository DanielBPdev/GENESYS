--liquibase formatted sql

--changeset dsuesca:01
--comment:
ALTER TABLE AporteDetallado ADD apdModalidadRecaudoAporte VARCHAR(40);
ALTER TABLE aud.AporteDetallado_aud ADD apdModalidadRecaudoAporte VARCHAR(40);
ALTER TABLE AporteDetallado ADD apdMarcaCalculoCategoria BIT;
ALTER TABLE aud.AporteDetallado_aud ADD apdMarcaCalculoCategoria BIT;