--liquibase formatted sql

--changeset fvasquez:01
--comment:Se elimina campo en la tabla Empleador_aud
ALTER TABLE AporteGeneral_aud ADD apgFechaReconocimiento DATETIME NULL;
ALTER TABLE AporteGeneral_aud ADD apgFormaReconocimientoAporte VARCHAR(75) NULL;