--liquibase formatted sql

--changeset arocha:01
--comment: Se crea la tabla staging.RegistroAfectacionAnalisisIntegral

CREATE TABLE staging.RegistroAfectacionAnalisisIntegral(
	raiId BIGINT IDENTITY NOT NULL,
	raiRegistroGeneral BIGINT NOT NULL,
	raiTipoIdentificacionCotizante VARCHAR(20) NOT NULL,
	raiNumeroIdentificacionCotizante VARCHAR(16) NOT NULL,
	raiRegistroDetalladoAfectado BIGINT NOT NULL,
CONSTRAINT PK_RegistroAfectacionAnalisisIntegral_raiId PRIMARY KEY (raiId)
);

ALTER TABLE staging.RegistroAfectacionAnalisisIntegral ADD CONSTRAINT FK_RegistroAfectacionAnalisisIntegral_RegistroDetallado FOREIGN KEY(raiRegistroDetalladoAfectado) REFERENCES staging.RegistroDetallado (redId);