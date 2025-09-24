--liquibase formatted sql

--changeset dsuesca:01
--comment: Mantis 0259343
ALTER TABLE TempArchivoRetiroTerceroPagadorEfectivo ALTER COLUMN tatTipoIdentificacionAdmin VARCHAR (20) NULL;
ALTER TABLE TempArchivoRetiroTerceroPagadorEfectivo ALTER COLUMN tatNumeroIdentificacionAdmin VARCHAR (16) NULL;