--liquibase formatted sql

--changeset mamonroy:01
--comment: 
CREATE TABLE TipoDocumentoSoporteFovis (
	tdsId bigint NOT NULL IDENTITY(1,1),
	tdsNombre varchar(50) NOT NULL,
	tdsDescripcion varchar(200) NOT NULL,
	tdsEntidad varchar(8),
	CONSTRAINT PK_TipoDocumentoSoporteFovis_tdsId PRIMARY KEY (tdsId)
);

ALTER TABLE DocumentoSoporte ADD dosTipoDocumentosSoporteFovis bigint NULL;
ALTER TABLE DocumentoSoporte ADD CONSTRAINT FK_DocumentoSoporte_dosTipoDocumentosSoporteFovis 
FOREIGN KEY (dosTipoDocumentosSoporteFovis) REFERENCES TipoDocumentoSoporteFovis(tdsId);


CREATE TABLE aud.TipoDocumentoSoporteFovis_aud (
	tdsId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	tdsNombre varchar(50) NOT NULL,
	tdsDescripcion varchar(200) NOT NULL,
	tdsEntidad varchar(8),
	CONSTRAINT FK_TipoDocumentoSoporteFovis_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
);

