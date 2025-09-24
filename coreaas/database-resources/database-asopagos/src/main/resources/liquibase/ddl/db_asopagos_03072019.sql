--liquibase formatted sql

--changeset jocorrea:01
--comment: Creacion tabla TipoDocumentoSoporteFovis
CREATE TABLE TipoDocumentoSoporteFovis (
    tdsId bigint NOT NULL IDENTITY(1,1),
    tdsNombre varchar(50) NOT NULL,
    tdsDescripcion varchar(200) NOT NULL,
    tdsEntidad varchar(8),
    CONSTRAINT PK_TipoDocumentoSoporteFovis_tdsId PRIMARY KEY (tdsId)
);

