--liquibase formatted sql

--changeset jocorrea:01
--comment: Se crea tabla documentoSoporte
CREATE TABLE DocumentoSoporte (dsId BIGINT NOT NULL IDENTITY(1,1),dsNombreDocumento VARCHAR (255) NOT NULL,dsDescripcionComentarios VARCHAR (255) NOT NULL,dsIdentificacionDocumento VARCHAR(255) NOT NULL,dsVersionDocumento VARCHAR(3) NOT NULL,dsFechaHoraCargue DATETIME NOT NULL CONSTRAINT PK_DocumentoSoporte_dsId PRIMARY KEY (dsId))