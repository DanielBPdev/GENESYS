--liquibase formatted sql

--changeset dsuesca:01
--comment: 
CREATE TABLE CargueBloqueoCuotaMonetaria_aud (
	cabId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cabfechaCarga date NOT NULL,
	cabPeriodoInicio date NOT NULL,
	cabPeriodoFin date NOT NULL,
	cabRadicado bit
);

CREATE TABLE BloqueoBeneficiarioCuotaMonetaria_aud (
	bbcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	bbcPersona bigint,
	bbcTipoIdentificacionBeneciario varchar(20) NOT NULL,
	bbcNumeroIdentificacionBeneficiario varchar(16) NOT NULL,
	bbcAsignacionCuotaPorOtraCCF bit,
	bbcBeneficiarioComoAfiliadoOtraCCF bit,
	bbcCargueBloqueoCM bigint,
	bbcBloqueado bit not null,
);
