--liquibase formatted sql

--changeset squintero:01
--comment:Creacion de las tablas ParametrizacionCondicionesSubsidio y ParametrizacionLiquidacionSubsidio
CREATE TABLE ParametrizacionCondicionesSubsidio(
	pcsId BIGINT IDENTITY(1,1) NOT NULL,
	pcsAnioVigenciaParametrizacion INT NOT NULL,
	pcsPeriodoInicio DATE NOT NULL,
	pcsPeriodoFin DATE NOT NULL,
	pcsValorCuotaAnualBase NUMERIC(19, 5) NOT NULL,
	pcsValorCuotaAnualAgraria NUMERIC(19, 5) NOT NULL,
	pcsProgramaDecreto4904 BIT NOT NULL,
	pcsRetroactivoNovInvalidez BIT NOT NULL,
	pcsRetroactivoReingresoEmpleadores BIT NOT NULL,
	pcsCantidadSubsidiosLiquidados INT NULL,
	pcsMontoSubsidiosLiquidados NUMERIC(10, 0) NULL,
	pcsCantidadSubsidiosLiquidadosInvalidez INT NULL,
	pcsCantidadPeriodosRetroactivosMes INT NULL,
	pcsDiasNovedadFallecimiento INT NULL,
	pcsCodigoCajaCompensacion VARCHAR(5) NOT NULL,
CONSTRAINT PK_ParametrizacionCondicionesSubsidio_pcsId PRIMARY KEY (pcsId)
);

CREATE TABLE ParametrizacionLiquidacionSubsidio(
	plsId BIGINT IDENTITY(1,1) NOT NULL,
	plsAnioVigenciaParametrizacion INT NOT NULL,
	plsPeriodoInicio DATE NOT NULL,
	plsPeriodoFin DATE NOT NULL,
	plsFactorCuotaInvalidez NUMERIC(19, 5) NOT NULL,
	plsFactorPorDefuncion NUMERIC(19, 5) NOT NULL,
	plsHorasTrabajadas INT NOT NULL,
	plsSMLMV NUMERIC(19, 5) NULL,
	plsCodigoCajaCompensacion VARCHAR(5) NOT NULL,
CONSTRAINT PK_ParametrizacionLiquidacionSubsidio_psuId PRIMARY KEY (plsId)
);
 