--liquibase formatted sql

--changeset clmarin:01
--comment:
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro1D1E_acdTipoCobro')) ALTER TABLE AccionCobro1D1E DROP CONSTRAINT CK_AccionCobro1D1E_acdTipoCobro;

EXEC sp_rename 'AccionCobro1D1E', 'AccionCobro1D';
EXEC sp_rename 'aud.AccionCobro1D1E_aud','AccionCobro1D_aud';

alter table AccionCobro1D drop column acdTipoCobro;
alter table aud.AccionCobro1D_aud drop column acdTipoCobro;

CREATE TABLE AccionCobro1E (
	pgcId bigint NOT NULL,
	aoeInicioDiasConteo varchar(13) NULL,
	aoeDiasTranscurridos bigint NULL,
	aoeHoraEjecucion datetime NULL,
	aoeLimiteEnvio bigint NULL,
	aoeInicioDiasConteoPersuasivo varchar(13) NULL,
	aoeDiasTranscurridosPersuasivo bigint NULL,
	aoeLimiteEnvioPersuasivo bigint NULL,
	CONSTRAINT PK_AccionCobro1E_pgcId PRIMARY KEY (pgcId),
	CONSTRAINT FK_AccionCobro1E_aoeId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId) 
);

CREATE TABLE aud.AccionCobro1E_aud (
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aoeInicioDiasConteo varchar(13) NULL,
	aoeDiasTranscurridos bigint NULL,
	aoeHoraEjecucion datetime NULL,
	aoeLimiteEnvio bigint NULL,
	aoeInicioDiasConteoPersuasivo varchar(13) NULL,
	aoeDiasTranscurridosPersuasivo bigint NULL,
	aoeLimiteEnvioPersuasivo bigint NULL,
	CONSTRAINT FK_AccionCobro1E_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
);

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'CK_AccionCobro2F2G_aofTipoCobro')) ALTER TABLE AccionCobro2F2G DROP CONSTRAINT CK_AccionCobro2F2G_aofTipoCobro;

EXEC sp_rename 'AccionCobro2F2G', 'AccionCobro2F';
EXEC sp_rename 'aud.AccionCobro2F2G_aud', 'AccionCobro2F_aud';

alter table AccionCobro2F drop column aofTipoCobro;
alter table aud.AccionCobro2F_aud drop column aofTipoCobro;

CREATE TABLE AccionCobro2G (
	pgcId bigint NOT NULL,
	aogInicioDiasConteo varchar(13) NULL,
	aogDiasTranscurridos bigint NULL,
	aogHoraEjecucion datetime NULL,
	aogLimiteEnvio bigint NULL,
	CONSTRAINT PK_AccionCobro2G_pgcId PRIMARY KEY (pgcId),
	CONSTRAINT FK_AccionCobro2G_aogId FOREIGN KEY (pgcId) REFERENCES ParametrizacionGestionCobro(pgcId)
) ;

CREATE TABLE aud.AccionCobro2G_aud (
	pgcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	aogInicioDiasConteo varchar(13) NULL,
	aogDiasTranscurridos bigint NULL,
	aogHoraEjecucion datetime NULL,
	aogLimiteEnvio bigint NULL,
	CONSTRAINT FK_AccionCobro2G_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)
) ;



DELETE FROM AccionCobro1D;
DELETE FROM AccionCobro2F;
DELETE FROM ParametrizacionGestionCobro WHERE pgcTipoParametrizacion IN ('ACCION_COBRO_1D_1E','ACCION_COBRO_2F_2G');

DELETE FROM LineaCobro WHERE lcoTipoLineaCobro in ('LC4','LC5');

CREATE TABLE LineaCobroPersona (
	lcpId bigint NOT NULL IDENTITY(1,1),
	lcpHabilitarAccionCobroA bit NULL,
	lcpDiasLimitePago bigint NULL,
	lcpDiasParametrizados bigint NULL,
	lcpHabilitarAccionCobroB bit NULL,
	lcpTipoLineaCobro varchar(3) NULL,
	lcpMetodoEnvioComunicado varchar(11) NULL,
	CONSTRAINT PK_LineaCobroPersona_lcpId PRIMARY KEY (lcpId)
);



