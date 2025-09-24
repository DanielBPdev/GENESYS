--liquibase formatted sql

--changeset jocorrea:01
--comment:
ALTER TABLE ConsolaEstadoCargueMasivo ADD cecNombreArchivo VARCHAR(255) NULL
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecNumRegistroObjetivo BIGINT NULL
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecNumRegistroProcesado BIGINT NULL
ALTER TABLE ConsolaEstadoCargueMasivo ALTER COLUMN cecGradoAvance NUMERIC(6,3) NULL

ALTER TABLE aud.ConsolaEstadoCargueMasivo_aud ADD cecNombreArchivo VARCHAR(255) NULL
ALTER TABLE aud.ConsolaEstadoCargueMasivo_aud ALTER COLUMN cecNumRegistroObjetivo BIGINT NULL
ALTER TABLE aud.ConsolaEstadoCargueMasivo_aud ALTER COLUMN cecNumRegistroProcesado BIGINT NULL
ALTER TABLE aud.ConsolaEstadoCargueMasivo_aud ALTER COLUMN cecGradoAvance NUMERIC(6,3) NULL

--changeset jocorrea:02
--comment:
CREATE TABLE ResultadoHallazgoValidacionArchivo (
rhvId BIGINT NOT NULL,
rhvIdConsolaEstadoCargueMasivo BIGINT NOT NULL,
rhvNumeroLinea BIGINT,
rhvNombreCampo VARCHAR(255),
rhvError VARCHAR(MAX),
CONSTRAINT PK_ResultadoHallazgoValidacionArchivo_rhvId PRIMARY KEY (rhvId)
);
ALTER TABLE ResultadoHallazgoValidacionArchivo WITH CHECK ADD CONSTRAINT FK_ResultadoHallazgoValidacionArchivo_rhvIdConsolaEstadoCargueMasivo FOREIGN KEY(rhvIdConsolaEstadoCargueMasivo) REFERENCES ConsolaEstadoCargueMasivo (cecId);
ALTER TABLE ResultadoHallazgoValidacionArchivo CHECK CONSTRAINT FK_ResultadoHallazgoValidacionArchivo_rhvIdConsolaEstadoCargueMasivo;

CREATE TABLE aud.ResultadoHallazgoValidacionArchivo_aud (
rhvId BIGINT NOT NULL,
REV bigint NOT NULL,
REVTYPE smallint NULL,
rhvIdConsolaEstadoCargueMasivo BIGINT NOT NULL,
rhvNumeroLinea BIGINT,
rhvNombreCampo VARCHAR(255),
rhvError VARCHAR(MAX),
CONSTRAINT FK_ResultadoHallazgoValidacionArchivo_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
);

--changeset jocorrea:03
--comment:
CREATE SEQUENCE SEC_consecutivoRHV START WITH 0 INCREMENT BY 1
