--liquibase formatted sql

--changeset dsuesca:01
--comment: cc pila planillas N con N
ALTER TABLE AporteGeneral ADD apgRegistroGeneralUltimo BIGINT;
ALTER TABLE aud.AporteGeneral_aud ADD apgRegistroGeneralUltimo BIGINT;
ALTER TABLE AporteDetallado ADD apdRegistroDetalladoUltimo BIGINT;
ALTER TABLE aud.AporteDetallado_aud ADD apdRegistroDetalladoUltimo BIGINT;
