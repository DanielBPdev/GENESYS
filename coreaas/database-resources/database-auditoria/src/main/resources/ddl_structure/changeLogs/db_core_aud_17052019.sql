--liquibase formatted sql

--changeset clmarin:01
--comment:
EXEC sp_rename 'AccionCobro1D1E_aud', 'AccionCobro1D_aud';
alter table AccionCobro1D_aud drop column acdTipoCobro;

CREATE TABLE AccionCobro1E_aud (
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aoeInicioDiasConteo varchar(13) NULL,
	aoeDiasTranscurridos bigint NULL,
	aoeHoraEjecucion datetime NULL,
	aoeLimiteEnvio bigint NULL,
	aoeInicioDiasConteoPersuasivo varchar(13) NULL,
	aoeDiasTranscurridosPersuasivo bigint NULL,
	aoeLimiteEnvioPersuasivo bigint NULL
);
EXEC sp_rename 'AccionCobro2F2G_aud', 'AccionCobro2F_aud';
alter table AccionCobro2F_aud drop column aofTipoCobro;

CREATE TABLE AccionCobro2G_aud (
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aogInicioDiasConteo varchar(13) NULL,
	aogDiasTranscurridos bigint NULL,
	aogHoraEjecucion datetime NULL,
	aogLimiteEnvio bigint NULL
);

CREATE TABLE LineaCobroPersona_aud (
	lcpId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	lcpHabilitarAccionCobroA bit NULL,
	lcpDiasLimitePago bigint NULL,
	lcpDiasParametrizados bigint NULL,
	lcpHabilitarAccionCobroB bit NULL,
	lcpTipoLineaCobro varchar(3) NULL,
	lcpMetodoEnvioComunicado varchar(11) NULL
);
