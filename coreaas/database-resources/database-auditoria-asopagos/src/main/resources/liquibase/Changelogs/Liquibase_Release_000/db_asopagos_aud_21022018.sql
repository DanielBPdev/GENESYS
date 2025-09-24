--liquibase formatted sql

--changeset squintero:01
--comment:Creacion de las tablas ParametrizacionCondicionesSubsidio_aud y ParametrizacionLiquidacionSubsidio_aud
CREATE TABLE ParametrizacionCondicionesSubsidio_aud(
	pcsId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
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
);
ALTER TABLE ParametrizacionCondicionesSubsidio_aud ADD CONSTRAINT FK_ParametrizacionCondicionesSubsidio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);

CREATE TABLE ParametrizacionLiquidacionSubsidio_aud(
	plsId BIGINT NOT NULL,
	REV int NOT NULL,
	REVTYPE smallint NULL,
	plsAnioVigenciaParametrizacion INT NOT NULL,
	plsPeriodoInicio DATE NOT NULL,
	plsPeriodoFin DATE NOT NULL,
	plsFactorCuotaInvalidez NUMERIC(19, 5) NOT NULL,
	plsFactorPorDefuncion NUMERIC(19, 5) NOT NULL,
	plsHorasTrabajadas INT NOT NULL,
	plsSMLMV NUMERIC(19, 5) NULL,
	plsCodigoCajaCompensacion VARCHAR(5) NOT NULL,
);
ALTER TABLE ParametrizacionLiquidacionSubsidio_aud ADD CONSTRAINT FK_ParametrizacionLiquidacionSubsidio_aud_REV FOREIGN KEY (REV) REFERENCES Revision (revId);
