--liquibase formatted sql

--changeset arocha:01
--comment: Se adiciona campos en las tablas staging.cotizante y staging.sucursalempresa
ALTER TABLE staging.cotizante ALTER COLUMN cotCodigoSucursal VARCHAR(10) NULL;
ALTER TABLE staging.sucursalempresa ALTER COLUMN sueCodigoSucursal VARCHAR(10) NULL;

--changeset arocha:02
--comment: Se actualizan registros en la tabla staging.StagingParametros
UPDATE staging.StagingParametros SET stpValorParametro = '3,4,16,53,57,59' WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE';

--changeset mosanchez:03
--comment: Se alteran campos a tipo de dato numeric(19,5)
ALTER TABLE dbo.PilaArchivoFRegistro6 ALTER COLUMN pf6ValorPlanilla numeric(19,5) NOT NULL;

ALTER TABLE dbo.PilaArchivoFRegistro8 ALTER COLUMN pf8ValorRecaudado numeric(19,5) NOT NULL;

ALTER TABLE dbo.PilaArchivoFRegistro9 ALTER COLUMN pf9ValorTotalRecaudo numeric(19,5) NOT NULL;

ALTER TABLE dbo.PilaArchivoIPRegistro2 ALTER COLUMN ip2ValorAporte numeric (19,5) NOT NULL;
ALTER TABLE dbo.PilaArchivoIPRegistro2 ALTER COLUMN ip2ValorMesada numeric (19,5) NOT NULL;

ALTER TABLE dbo.PilaArchivoIPRegistro3 ALTER COLUMN ip3ValorTotalAporte numeric (19,5) NOT NULL;
ALTER TABLE dbo.PilaArchivoIPRegistro3 ALTER COLUMN ip3ValorTotalPagar numeric (19,5) NOT NULL;
ALTER TABLE dbo.PilaArchivoIPRegistro3 ALTER COLUMN ip3ValorMora numeric (19,5) NOT NULL;

ALTER TABLE dbo.PilaArchivoIRegistro2 ALTER COLUMN pi2SalarioBasico numeric (19,5) NOT NULL;
ALTER TABLE dbo.PilaArchivoIRegistro2 ALTER COLUMN pi2ValorIBC numeric (19,5) NOT NULL;
ALTER TABLE dbo.PilaArchivoIRegistro2 ALTER COLUMN pi2AporteObligatorio numeric (19,5) NOT NULL;

ALTER TABLE dbo.PilaArchivoIRegistro3 ALTER COLUMN pi3ValorTotalIBC numeric (19,5) NULL;
ALTER TABLE dbo.PilaArchivoIRegistro3 ALTER COLUMN pi3ValorTotalAporteObligatorio numeric (19,5) NULL;
ALTER TABLE dbo.PilaArchivoIRegistro3 ALTER COLUMN pi3ValorTotalAportes numeric (19,5) NULL;
ALTER TABLE dbo.PilaArchivoIRegistro3 ALTER COLUMN pi3ValorMora numeric (19,5) NULL;

ALTER TABLE staging.RegistroGeneral ALTER COLUMN regValTotalApoObligatorio numeric (19,5);
ALTER TABLE staging.RegistroGeneral ALTER COLUMN regValorIntMora numeric (19,5);

ALTER TABLE staging.RegistroDetallado ALTER COLUMN redSalarioBasico numeric (19,5) NULL;
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redValorIBC numeric (19,5)NULL;
ALTER TABLE staging.RegistroDetallado ALTER COLUMN redAporteObligatorio numeric (19,5)NULL; 

ALTER TABLE dbo.TemAporte ALTER COLUMN temValTotalApoObligatorio numeric (19,5);
ALTER TABLE dbo.TemAporte ALTER COLUMN temValorIntMoraGeneral numeric (19,5);
ALTER TABLE dbo.TemAporte ALTER COLUMN temSalarioBasico numeric (19,5);
ALTER TABLE dbo.TemAporte ALTER COLUMN temValorIBC numeric (19,5);
ALTER TABLE dbo.TemAporte ALTER COLUMN temAporteObligatorio numeric (19,5);
ALTER TABLE dbo.TemAporte ALTER COLUMN temValorSaldoAporte numeric (19,5);
ALTER TABLE dbo.TemAporte ALTER COLUMN temValorIntMoraDetalle numeric (19,5);