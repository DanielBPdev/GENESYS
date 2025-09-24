--***************************************
-- Trigger para Aporte Detallado
--***************************************
CREATE OR ALTER TRIGGER [dbo].[Trigger_AporteDetallado] ON [dbo].[AporteDetallado]
	FOR INSERT
AS 
set xact_abort on;
SET NOCOUNT ON;
DECLARE @apdId bigint;
DECLARE @idGeneral bigint;
DECLARE @apdMarcaPeriodo varchar(19);
DECLARE @apdEstadoCotizante varchar(50);
DECLARE @contadorGlpi61052 int;
declare @fechaInicio varchar(50);
declare @fechaFin varchar(50);
DECLARE @dateinicio DATETIME;
DECLARE @datefin DATETIME;
DECLARE @dateAporte DATETIME;
declare @varApdTipoCotizante varchar(200);
declare @varEmpid varchar(200);
declare @varApgPeriodoAporte varchar(200);
declare @varApgPeriodoAporteOrdenado varchar(200);
declare @varApgPersona varchar(200);
DECLARE @ESTADOFINALAPORTE varchar(200) = 'REGISTRADO';
DECLARE @aporteFuturoGap varchar(20);
DECLARE @aporteFuturo varchar(20);
DECLARE @apgEstadoDelAporte varchar(20);

		
		select @idGeneral = apdAporteGeneral, @apdId = apdId
		from inserted

		SELECT @apgEstadoDelAporte = apg.apgEstadoRegistroAporteAportante from AporteGeneral apg where apg.apgId = @idGeneral

		/*

		select	@apdMarcaPeriodo = m.apdMarcaPeriodo, 
		@apdId = m.apdId, 
		@apdEstadoCotizante = m.apdEstadoCotizante,
		@idGeneral = m.apdAporteGeneral
		from inserted m;
		

	   --GLPI 49449
	   IF @apdEstadoCotizante = 'NO_FORMALIZADO_RETIRADO_CON_APORTES' or @apdEstadoCotizante ='INACTIVO'
	   BEGIN
		update AporteDetallado set apdEstadoRegistroAporte = 'RELACIONADO', apdEstadoRegistroAporteCotizante = 'RELACIONADO' where apdId = @apdId;
	   END 
	   --GLPI 50417
	   SELECT @aporteFuturoGap =  pg.prgEstado from ParametrizacionGaps pg where pg.prgNombre = 'APORTES_FUTURO';
	   SELECT @aporteFuturo =  p.prmValor from Parametro p where p.prmNombre = 'REGISTRO_APORTES_FUTURO';
	   SELECT @apgEstadoDelAporte = apg.apgEstadoRegistroAporteAportante from AporteGeneral apg where apg.apgId = @idGeneral
	   IF (@apdMarcaPeriodo = 'PERIODO_FUTURO' AND @aporteFuturoGap ='INACTIVO')
	   BEGIN
		update AporteDetallado set apdEstadoRegistroAporte = 'RELACIONADO', apdEstadoRegistroAporteCotizante = 'RELACIONADO' where apdId = @apdId;
	   END
	   IF (@apdMarcaPeriodo = 'PERIODO_FUTURO' AND @aporteFuturoGap ='ACTIVO')
	     IF(@aporteFuturo = 'SI')
		*/
		 BEGIN
		 update AporteDetallado set apdEstadoRegistroAporte = @apgEstadoDelAporte , apdEstadoRegistroAporteCotizante = @apgEstadoDelAporte where apdId = @apdId;
		 END
		 /*
		 ELSE
		 BEGIN
		 update AporteDetallado set apdEstadoRegistroAporte = 'RELACIONADO', apdEstadoRegistroAporteCotizante = 'RELACIONADO' where apdId = @apdId;
	     END
		*/