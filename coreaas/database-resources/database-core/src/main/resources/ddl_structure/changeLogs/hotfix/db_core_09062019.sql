--liquibase formatted sql

--changeset jocorrea:01
--comment:

CREATE TABLE aud.DetalleNovedadFovis_aud (
	dnfId BIGINT NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	dnfSolicitudNovedad BIGINT NOT NULL,
	dnfValorSFVSimuladoAjuste NUMERIC(19,5) NULL,
	dnfValorEquiSFVSimuladoAjuste NUMERIC(5,2) NULL,
	dnfValorDiferenciaAjuste NUMERIC(19,5) NULL,
	dnfValorEquiDiferenciaAjuste NUMERIC(5,2) NULL,
	dnfValorSFVSimuladoAdicion NUMERIC(19,5) NULL,
	dnfValorEquiSFVSimuladoAdicion NUMERIC(5,2) NULL,
	dnfValorDiferenciaAdicion NUMERIC(19,5) NULL,
	dnfValorEquiDiferenciaAdicion NUMERIC(5,2) NULL,
	dnfValorTotalDiferencia NUMERIC(19,5) NULL,
	dnfValorEquiTotalDiferencia NUMERIC(5,2) NULL,
	dnfValorSFVAjusteAdicion NUMERIC(19,5) NULL,
	dnfValorEquiSFVAjusteAdicion NUMERIC(5,2) NULL,
	dnfIdentificadorDocumentoSoporte VARCHAR(255) NULL,
	dnfInfoCondicionHogar VARCHAR(MAX) NULL,
	CONSTRAINT FK_DetalleNovedadFovis_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
);