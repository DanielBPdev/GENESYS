--liquibase formatted sql

--changeset dsuesca:01
--comment: Ajuste cc pila planillas N con N
ALTER TABLE AporteGeneral_aud ADD apgRegistroGeneralUltimo BIGINT;
ALTER TABLE AporteDetallado_aud ADD apdRegistroDetalladoUltimo BIGINT;