--liquibase formatted sql

--changeset criparra:01
--comment: Se adicionan columna ifaFechaGestion a la tabla InformacionFaltanteAportante
ALTER TABLE dbo.InformacionFaltanteAportante ADD ifaFechaGestion date NULL;

--changeset mosanchez:01
--comment: Se crea la tabla Beneficio y PeriodoBeneficio
CREATE TABLE dbo.Beneficio(
	befId bigint IDENTITY(1,1) NOT NULL,
	befTipoBeneficio varchar(16) NOT NULL,
	befVigenciaFiscal bit NULL,
	befFechaVigenciaInicio date NULL,
	befFechaVigenciaFin date NULL,
	CONSTRAINT PK_Beneficio_befId PRIMARY KEY (befId)
);

CREATE TABLE dbo.PeriodoBeneficio(
	pbeId bigint IDENTITY(1,1) NOT NULL,
	pbePeriodo smallint NOT NULL,
	pbePorcentaje numeric (5,5) NULL,
	pbeBeneficio bigint NOT NULL,
	CONSTRAINT PK_PeriodoBeneficio_pbeId PRIMARY KEY (pbeId)
);

ALTER TABLE dbo.PeriodoBeneficio ADD CONSTRAINT FK_PeriodoBeneficio_pbeBeneficio FOREIGN KEY (pbeBeneficio) REFERENCES dbo.Beneficio (befId);
