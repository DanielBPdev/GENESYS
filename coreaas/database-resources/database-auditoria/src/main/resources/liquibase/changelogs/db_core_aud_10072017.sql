--liquibase formatted sql

--changeset jzambrano:01
--comment: Se adicionan columnas dtsTipoIdentificacion y dtsNumeroIdentificacion
ALTER TABLE dbo.DatoTemporalSolicitud_aud ADD dtsTipoIdentificacion varchar (20) NULL;
ALTER TABLE dbo.DatoTemporalSolicitud_aud ADD dtsNumeroIdentificacion varchar (16) NULL;

--changeset criparra:02
--comment: Se adicionan columna FechaGestion a la tabla InformacionFaltanteAportante_aud
ALTER TABLE dbo.InformacionFaltanteAportante_aud ADD ifaFechaGestion date NULL;

--changeset mosanchez:03
--comment: Se hace la insercion en las tablas beneficio_aud y periodobeneficio_aud
CREATE TABLE Beneficio_aud(
	befId bigint IDENTITY(1,1) NOT NULL,
	befTipoBeneficio varchar(16) NOT NULL,
	befVigenciaFiscal bit NULL,
	befFechaVigenciaInicio date NULL,
	befFechaVigenciaFin date NULL,
);

CREATE TABLE PeriodoBeneficio_aud(
	pbeId bigint IDENTITY(1,1) NOT NULL,
	pbePeriodo smallint NOT NULL,
	pbePorcentaje numeric (5,5) NULL,
	pbeBeneficio bigint NOT NULL,
);
