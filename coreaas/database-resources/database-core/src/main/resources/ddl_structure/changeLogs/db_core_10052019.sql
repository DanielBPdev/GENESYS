--liquibase formatted sql

--changeset dsuesca:01
--comment: Se crea tabla control de cambios subsidios
CREATE TABLE CargueBloqueoCuotaMonetaria (
	cabId bigint IDENTITY(1,1) NOT NULL,	
	cabfechaCarga date NOT NULL,
	cabPeriodoInicio date NOT NULL,
	cabPeriodoFin date NOT NULL,
	cabRadicado bit,
	CONSTRAINT PK_CargueBloqueoCuotaMonetaria_cabId PRIMARY KEY (cabId)	
);

CREATE TABLE BloqueoBeneficiarioCuotaMonetaria (
	bbcId bigint NOT NULL,	
	bbcPersona bigint,
	bbcTipoIdentificacionBeneciario varchar(20) NOT NULL,
	bbcNumeroIdentificacionBeneficiario varchar(16) NOT NULL,	
	bbcAsignacionCuotaPorOtraCCF bit,
	bbcBeneficiarioComoAfiliadoOtraCCF bit,
	bbcCargueBloqueoCuotaMonetaria bigint,
	bbcBloqueado bit not null,
	CONSTRAINT PK_BloqueoBeneficiarioCuotaMonetaria_bbcId PRIMARY KEY (bbcId),
	CONSTRAINT FK_BloqueoBeneficiarioCuotaMonetaria_bbcCargueBloqueoCuotaMonetaria FOREIGN KEY (bbcCargueBloqueoCuotaMonetaria) REFERENCES CargueBloqueoCuotaMonetaria(cabId),
	CONSTRAINT FK_BloqueoBeneficiarioCuotaMonetaria_bbcPersona FOREIGN KEY (bbcPersona) REFERENCES Persona(perId)
);

CREATE SEQUENCE SEC_BloqueoBeneficiarioCuotaMonetaria START WITH 1 INCREMENT BY 1;

CREATE TABLE aud.CargueBloqueoCuotaMonetaria_aud (
	cabId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	cabfechaCarga date NOT NULL,
	cabPeriodoInicio date NOT NULL,
	cabPeriodoFin date NOT NULL,
	cabRadicado bit,	
	CONSTRAINT FK_CargueBloqueoCuotaMonetaria_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)	
);

CREATE TABLE aud.BloqueoBeneficiarioCuotaMonetaria_aud (
	bbcId bigint NOT NULL,
	REV bigint NOT NULL,
	REVTYPE smallint NULL,
	bbcPersona bigint,
	bbcTipoIdentificacionBeneciario varchar(20) NOT NULL,
	bbcNumeroIdentificacionBeneficiario varchar(16) NOT NULL,	
	bbcAsignacionCuotaPorOtraCCF bit,
	bbcBeneficiarioComoAfiliadoOtraCCF bit,
	bbcCargueBloqueoCuotaMonetaria bigint,
	bbcBloqueado bit not null,
	CONSTRAINT FK_BloqueoBeneficiarioCuotaMonetaria_aud_REV FOREIGN KEY (REV) REFERENCES aud.Revision(revId)	
);
