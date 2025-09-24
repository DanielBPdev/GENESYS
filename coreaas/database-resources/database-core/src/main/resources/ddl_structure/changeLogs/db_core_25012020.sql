--liquibase formatted sql

--changeset dsuesca:01
--comment: CC archivo tercero pagador efectivo
ALTER TABLE ArchivoRetiroTerceroPagadorEfectivo ADD ateResultadoCargueArchivo VARCHAR(20);
ALTER TABLE ArchivoRetiroTerceroPagadorEfectivo DROP COLUMN ateTipoInconsistenciaArchivo;
ALTER TABLE ArchivoRetiroTerceroPagadorEfectivo ADD ateMotivo VARCHAR(20);