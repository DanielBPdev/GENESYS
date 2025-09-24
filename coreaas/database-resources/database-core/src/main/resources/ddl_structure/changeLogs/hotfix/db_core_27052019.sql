--liquibase formatted sql

--changeset clmarin:01
--comment:
CREATE TABLE aud.LineaCobroPersona_aud (
	lcpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	lcpHabilitarAccionCobroA bit NULL,
	lcpDiasLimitePago bigint NULL,
	lcpDiasParametrizados bigint NULL,
	lcpHabilitarAccionCobroB bit NULL,
	lcpTipoLineaCobro varchar(3) NULL,
	lcpMetodoEnvioComunicado varchar(11) NULL,
	CONSTRAINT FK_LineaCobroPersona_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
);
