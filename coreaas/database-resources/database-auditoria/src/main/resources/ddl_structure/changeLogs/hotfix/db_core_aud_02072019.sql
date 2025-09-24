--liquibase formatted sql

--changeset jocorrea:01
--comment:
CREATE TABLE TipoDocumentoSoporteFovis_aud (
	tdsId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	tdsNombre varchar(50) NOT NULL,
	tdsDescripcion varchar(200) NOT NULL,
	tdsEntidad varchar(8)	
);