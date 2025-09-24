--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE ConsolaEstadoCargueMasivo_aud ADD cecNombreArchivo VARCHAR(255) NULL
ALTER TABLE ConsolaEstadoCargueMasivo_aud ALTER COLUMN cecNumRegistroObjetivo BIGINT NULL
ALTER TABLE ConsolaEstadoCargueMasivo_aud ALTER COLUMN cecNumRegistroProcesado BIGINT NULL
ALTER TABLE ConsolaEstadoCargueMasivo_aud ALTER COLUMN cecGradoAvance NUMERIC(6,3) NULL

CREATE TABLE ResultadoHallazgoValidacionArchivo_aud (
rhvId BIGINT NOT NULL,
REV bigint NOT NULL,
REVTYPE smallint NULL,
rhvIdConsolaEstadoCargueMasivo BIGINT NOT NULL,
rhvNumeroLinea BIGINT,
rhvNombreCampo VARCHAR(255),
rhvError VARCHAR(MAX)
);