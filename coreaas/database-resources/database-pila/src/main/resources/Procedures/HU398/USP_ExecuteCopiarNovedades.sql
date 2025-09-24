-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Description:	Procedimiento almacenado encargado  de copiar las novedades de los registros detallados
-- en la estructura staging.RegistroDetalladoNovedad
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecuteCopiarNovedades]
(
	@IdRegistroGeneral BIGINT
)
AS
BEGIN
SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @localDate DATETIME = dbo.getLocalDate()
	
	--create unique nonclustered index IXNC_rdnId_rdnRegistroDetallado on staging.RegistroDetalladoNovedad (rdnId, rdnRegistroDetallado) with (online = on)
	--print 'USP_ExecutePILA2CopiarNovedades'

	--==== Se deja comentareado el borrado de las novedades, para no volver a insertar la novedad ya existente y no tener que borrar el staging.registroDetalladoNovedad para evitar la duplicidad de novedades y no perder la relación de las tablas.  2024-01-02
	/*
	BEGIN TRAN
	delete rdn
	FROM staging.RegistroDetalladoNovedad as rdn with(nolock)
	left join core.SolicitudNovedadPila as spi on rdn.rdnId = spi.spiIdRegistroDetalladoNovedad and rdn.rdnRegistroDetallado = spi.spiRegistroDetallado
	WHERE rdnRegistroDetallado IN (SELECT redId FROM staging.RegistroDetallado WHERE redRegistroGeneral = @IdRegistroGeneral)
	and (spi.spiIdRegistroDetalladoNovedad is null and spi.spiRegistroDetallado is null)
	COMMIT;
	*/



	CREATE TABLE #RegistroDetalladoNovedadTmp (
		[rdnId] [int] NULL,
		[rdnRegistroDetallado] [bigint] NOT NULL,
		[rdnTipotransaccion] [varchar](100) NULL,
		[rdnTipoNovedad] [varchar](15) NOT NULL,
		[rdnAccionNovedad] [varchar](20) NOT NULL,
		[rdnMensajeNovedad] [varchar](250) NULL,
		[rdnFechaInicioNovedad] [date] NULL,
		[rdnFechaFinNovedad] [date] NULL,
		[rdnOUTTipoAfiliado] [varchar](50) NULL,
		[rdnDateTimeInsert] [datetime] NULL,
		[rdnDateTimeUpdate] [datetime] NULL
	)

	DECLARE @NovedadesCotizante AS NovedadesTYPE
		
	DECLARE @IdRegistroDetalle BIGINT
	
	DECLARE @SMMLV NUMERIC(19,5)
	DECLARE @EsAportePensionados BIT
	DECLARE @Tarifa NUMERIC(5,5)
	DECLARE @ValorMesada NUMERIC(19,5)
	DECLARE @TipoTransaccion VARCHAR(100)
	DECLARE @TipoCotizante VARCHAR(20)

	DECLARE @NovIngreso VARCHAR(1)
	DECLARE @NovRetiro VARCHAR(1)
	DECLARE @NovVSP VARCHAR(1)
	DECLARE @NovVST VARCHAR(1)
	DECLARE @NovSLN VARCHAR(1)
	DECLARE @NovIGE VARCHAR(1)
	DECLARE @NovLMA VARCHAR(1)
	DECLARE @NovVACLR VARCHAR(1)
	DECLARE @DiasIRL VARCHAR(2)
	DECLARE @NovSUS VARCHAR(1)
	DECLARE @FechaIngreso DATE
	DECLARE @FechaRetiro DATE
	DECLARE @FechaInicioVSP DATE
	DECLARE @FechaInicioSLN DATE
	DECLARE @FechaFinSLN DATE
	DECLARE @FechaInicioIGE DATE
	DECLARE @FechaFinIGE DATE
	DECLARE @FechaInicioLMA DATE
	DECLARE @FechaFinLMA DATE
	DECLARE @FechaInicioVACLR DATE
	DECLARE @FechaFinVACLR DATE
	DECLARE @FechaInicioIRL DATE
	DECLARE @FechaFinIRL DATE
	DECLARE @FechaInicioSuspension DATE
	DECLARE @FechaFinSuspension DATE
	DECLARE @PeriodoAporte VARCHAR(7)
	DECLARE @FechaInicioVST DATE
	DECLARE @FechaFinVST DATE
	DECLARE @TipoAfiliado VARCHAR(50)
	
	
	DECLARE @TablaIndependientes AS TABLE (tipoCotizante VARCHAR(2))

	INSERT INTO @TablaIndependientes
	SELECT Data FROM dbo.Split( (
		SELECT stpValorParametro FROM staging.StagingParametros 
		WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,',')

	DECLARE @NovedadesCore AS TABLE (TipoTransaccion VARCHAR(100), TipoCotizante VARCHAR(20), TipoNovedad VARCHAR(15))

	INSERT INTO @NovedadesCore VALUES
	('LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL', 'DEPENDIENTE', 'NOVEDAD_LMA'),
	('INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL', 'DEPENDIENTE', 'NOVEDAD_IGE'),
	('SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_DEPENDIENTE_PRESENCIAL', 'DEPENDIENTE', 'NOVEDAD_SLN'),
	('VACACIONES_LICENCIA_REMUNERADA_VAC_DEPENDIENTE_PRESENCIAL', 'DEPENDIENTE', 'NOVEDAD_VAC_LR'),
	('VARIACION_TRANSITORIA_SALARIO_VST_DEPENDIENTE_PRESENCIAL', 'DEPENDIENTE', 'NOVEDAD_VST'),
	('VARIACION_PERMANENTE_SALARIO_VSP_DEPENDIENTE_PRESENCIAL', 'DEPENDIENTE', 'NOVEDAD_VSP'),
	('INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL', 'DEPENDIENTE','NOVEDAD_IRP'),
	('RETIRO_TRABAJADOR_DEPENDIENTE', 'DEPENDIENTE','NOVEDAD_RET'),

	('LICENCIA_MATERNIDAD_PATERNIDAD_LMA_PERSONA_PRESENCIAL', 'INDEPENDIENTE', 'NOVEDAD_LMA'),
	('INCAPACIDAD_TEMPORAL_ENFERMEDAD_GENERAL_IGE_PERSONA_PRESENCIAL', 'INDEPENDIENTE', 'NOVEDAD_IGE'),
	('SUSPENSION_TEMPORAL_CONTRATO_TRABAJO_SLN_INDEPENDIENTE_PRESENCIAL', 'INDEPENDIENTE', 'NOVEDAD_SLN'),
	('VACACIONES_LICENCIA_REMUNERADA_VAC_INDEPENDIENTE_PRESENCIAL', 'INDEPENDIENTE', 'NOVEDAD_VAC_LR'),
	('VARIACION_TRANSITORIA_SALARIO_VST_INDEPENDIENTE_PRESENCIAL', 'INDEPENDIENTE', 'NOVEDAD_VST'),
	('VARIACION_PERMANENTE_SALARIO_VSP_INDEPENDIENTE_PRESENCIAL', 'INDEPENDIENTE', 'NOVEDAD_VSP'),
	('INCAPACIDAD_TEMPORAL_ENFERMEDAD_LABORAL_IRL_PERSONA_PRESENCIAL', 'INDEPENDIENTE','NOVEDAD_IRP'),
	('RETIRO_TRABAJADOR_INDEPENDIENTE', 'INDEPENDIENTE','NOVEDAD_RET')
		
	DECLARE @NovedadesPensionadosCore AS TABLE (TipoTransaccion VARCHAR(100), TipoNovedad VARCHAR(15), OperadorSMMLV CHAR(1), porcentajeSMMLV NUMERIC(6,5), Tarifa NUMERIC(5,5))

	
	INSERT INTO @NovedadesPensionadosCore VALUES
	('RETIRO_PENSIONADO_MAYOR_1_5SM_0_6','NOVEDAD_RET','>',1.5,0.006),
	('RETIRO_PENSIONADO_MAYOR_1_5SM_2','NOVEDAD_RET','>',1.5,0.02),
	('RETIRO_PENSIONADO_MENOR_1_5SM_0','NOVEDAD_RET','<',1.5,0),
	('RETIRO_PENSIONADO_MENOR_1_5SM_0_6','NOVEDAD_RET','<',1.5,0.006),
	('RETIRO_PENSIONADO_MENOR_1_5SM_2','NOVEDAD_RET','<',1.5,0.02),
	
	('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_0_6_PRESENCIAL','NOVEDAD_VSP','>',1.5,0.006),
	('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MAYOR_1_5SM_2_PRESENCIAL','NOVEDAD_VSP','>',1.5,0.02),
	('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_PRESENCIAL','NOVEDAD_VSP','<',1.5,0),
	('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_0_6_PRESENCIAL','NOVEDAD_VSP','<',1.5,0.006),
	('VARIACION_PERMANENTE_MESADA_PENSIONAL_VSP_PENSIONADO_MENOR_1_5SM_2_PRESENCIAL','NOVEDAD_VSP','<',1.5,0.02),

	('SUSPENSION_PENSIONADO_SUS','NOVEDAD_SUS','>',1.5,0.006),
	('SUSPENSION_PENSIONADO_SUS','NOVEDAD_SUS','>',1.5,0.02),
	('SUSPENSION_PENSIONADO_SUS','NOVEDAD_SUS','<',1.5,0),
	('SUSPENSION_PENSIONADO_SUS','NOVEDAD_SUS','<',1.5,0.006),
	('SUSPENSION_PENSIONADO_SUS','NOVEDAD_SUS','<',1.5,0.02)


	
	SELECT 
		regEsAportePensionados,
		CASE WHEN regEsAportePensionados = 0 THEN (CASE WHEN red.redTipoCotizante IN (SELECT tipoCotizante FROM @TablaIndependientes) THEN 'INDEPENDIENTE' ELSE 'DEPENDIENTE' END) ELSE 'PENSIONADO' END redTipoCotizante,
		redTarifa,
		redSalarioBasico,
		regOUTSMMLV,
		redNovIngreso,
		redNovRetiro,
		redNovVSP,
		redNovVST,
		redNovSLN,
		redNovIGE,
		redNovLMA,
		redNovVACLR,
		redDiasIRL,
		redNovSUS,
		redFechaIngreso,
		redFechaRetiro,
		redFechaInicioVSP,
		redFechaInicioVST,
		redFechaFinVST,
		redFechaInicioSLN,
		redFechaFinSLN,
		redFechaInicioIGE,
		redFechaFinIGE,
		redFechaInicioLMA,
		redFechaFinLMA,
		redFechaInicioVACLR,
		redFechaFinVACLR,
		redFechaInicioIRL,
		redFechaFinIRL,
		redFechaInicioSuspension,
		redFechaFinSuspension,
		regPeriodoAporte,
		redId, 
		redOUTTipoAfiliado
	INTO #TrabajadorTMP
	FROM staging.RegistroDetallado red
	INNER JOIN staging.RegistroGeneral reg ON red.redRegistroGeneral = reg.regId
	WHERE red.redRegistroGeneral = @IdRegistroGeneral
	AND (
		redNovIngreso IS NOT NULL OR
		redNovRetiro IS NOT NULL OR
		redNovVSP IS NOT NULL OR
		redNovVST IS NOT NULL OR
		redNovSLN IS NOT NULL OR
		redNovIGE IS NOT NULL OR
		redNovLMA IS NOT NULL OR
		redNovVACLR IS NOT NULL OR
		ISNULL(redDiasIRL, 0) != 0 OR
		redNovSUS IS NOT NULL
		)

	DECLARE @TrabajadorCursor AS CURSOR

	SET @TrabajadorCursor = CURSOR FAST_FORWARD FOR
	SELECT 
		*
	FROM #TrabajadorTMP
	
	OPEN @TrabajadorCursor
	FETCH NEXT FROM @TrabajadorCursor INTO
	@EsAportePensionados,
	@TipoCotizante,
	@Tarifa,
	@ValorMesada,
	@SMMLV,
	@NovIngreso,
	@NovRetiro,
	@NovVSP,
	@NovVST,
	@NovSLN,
	@NovIGE,
	@NovLMA,
	@NovVACLR,
	@DiasIRL,
	@NovSUS,
	@FechaIngreso,
	@FechaRetiro,
	@FechaInicioVSP,
	@FechaInicioVST,
	@FechaFinVST,
	@FechaInicioSLN,
	@FechaFinSLN,
	@FechaInicioIGE,
	@FechaFinIGE,
	@FechaInicioLMA,
	@FechaFinLMA,
	@FechaInicioVACLR,
	@FechaFinVACLR,
	@FechaInicioIRL,
	@FechaFinIRL,
	@FechaInicioSuspension,
	@FechaFinSuspension,
	@PeriodoAporte,
	@IdRegistroDetalle, 
	@TipoAfiliado
	WHILE @@FETCH_STATUS = 0
	BEGIN	

	
		DELETE FROM @NovedadesCotizante
	
		INSERT INTO @NovedadesCotizante (TipoNovedad,FechaInicio,FechaFin)
		SELECT 'NOVEDAD_ING', @FechaIngreso, NULL
		WHERE @NovIngreso IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_RET', @FechaRetiro, NULL
		WHERE @NovRetiro IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_VSP', @FechaInicioVSP, NULL
		WHERE @NovVSP IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_VST', @FechaInicioVST, @FechaFinVST
		WHERE @NovVST IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_SLN', @FechaInicioSLN, @FechaFinSLN
		WHERE @NovSLN IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_LMA', @FechaInicioLMA, @FechaFinLMA
		WHERE @NovLMA IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_VAC_LR', @FechaInicioVACLR, @FechaFinVACLR
		WHERE @NovVACLR IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_IGE', @FechaInicioIGE, @FechaFinIGE
		WHERE @NovIGE IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_SUS', @FechaInicioSuspension, @FechaFinSuspension
		WHERE @NovSus IS NOT NULL
		UNION ALL
		SELECT 'NOVEDAD_IRP', @FechaInicioIRL, @FechaFinIRL
		WHERE ISNULL(@DiasIRL,0) > 0
						
		
		SET @TipoTransaccion = NULL

		IF @EsAportePensionados = 1
		BEGIN
			INSERT INTO #RegistroDetalladoNovedadTmp
			(rdnRegistroDetallado,rdnTipotransaccion,rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
			rdnDateTimeInsert, rdnDateTimeUpdate
			)
			SELECT @IdRegistroDetalle
				,NC.TipoTransaccion
				,N.TipoNovedad,'NO_APLICADA',NULL,FechaInicio,FechaFin,@TipoAfiliado
				,@localDate,@localDate
			FROM @NovedadesCotizante N
			LEFT JOIN @NovedadesPensionadosCore NC ON	N.TipoNovedad = NC.TipoNovedad 
				AND Tarifa = @Tarifa
				AND (
					( @ValorMesada >= (@SMMLV * porcentajeSMMLV) AND OperadorSMMLV = '>') OR
					( @ValorMesada < (@SMMLV * porcentajeSMMLV) AND OperadorSMMLV = '<')
				)
		END
		ELSE
		BEGIN

			INSERT INTO #RegistroDetalladoNovedadTmp
			(rdnRegistroDetallado,rdnTipotransaccion,rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
			 rdnDateTimeInsert, rdnDateTimeUpdate)
			SELECT @IdRegistroDetalle
				,NC.TipoTransaccion
				,N.TipoNovedad,'NO_APLICADA',NULL,FechaInicio,FechaFin,@TipoAfiliado
				,dbo.getLocalDate(),dbo.getLocalDate()
			FROM @NovedadesCotizante N
			LEFT JOIN @NovedadesCore NC ON N.TipoNovedad = NC.TipoNovedad AND NC.TipoCotizante = @TipoCotizante
		END

		FETCH NEXT FROM @TrabajadorCursor INTO
		@EsAportePensionados,
		@TipoCotizante,
		@Tarifa,
		@ValorMesada,
		@SMMLV,
		@NovIngreso,
		@NovRetiro,
		@NovVSP,
		@NovVST,
		@NovSLN,
		@NovIGE,
		@NovLMA,
		@NovVACLR,
		@DiasIRL,
		@NovSUS,
		@FechaIngreso,
		@FechaRetiro,
		@FechaInicioVSP,
		@FechaInicioVST,
		@FechaFinVST,
		@FechaInicioSLN,
		@FechaFinSLN,
		@FechaInicioIGE,
		@FechaFinIGE,
		@FechaInicioLMA,
		@FechaFinLMA,
		@FechaInicioVACLR,
		@FechaFinVACLR,
		@FechaInicioIRL,
		@FechaFinIRL,
		@FechaInicioSuspension,
		@FechaFinSuspension,
		@PeriodoAporte,
		@IdRegistroDetalle, 
		@TipoAfiliado
	END
	CLOSE @TrabajadorCursor;
	DEALLOCATE @TrabajadorCursor;


	--============================
	--==== Se preparan las novedades de cambios de sucursal, para que sean evaluadas con las demás novedades. GLPI 64621.
	--============================

	declare @codigoSucursalPILA VARCHAR(10) = NULL
	declare @codigoSucursalPrincipal VARCHAR(10) = NULL
	declare @nombreSucursalPrincipal VARCHAR(100) = NULL
	declare @MarcaSucursalPILA BIT = 0
	declare @cumpleSucursal BIT = 1

	SELECT @codigoSucursalPrincipal = regOUTCodSucursalPrincipal, @nombreSucursalPrincipal = regOUTNomSucursalPrincipal
	,@MarcaSucursalPILA = regOUTMarcaSucursalPILA
	,@codigoSucursalPILA = regCodSucursal
	FROM staging.RegistroGeneral pi1 with (nolock)
	WHERE pi1.regId = @IdRegistroGeneral

	if @MarcaSucursalPILA = 1
		begin
			
			EXEC USP_VerificarCumplimientoSucursal @IdRegistroGeneral, @codigoSucursalPILA, @codigoSucursalPrincipal, @cumpleSucursal OUTPUT

			if @cumpleSucursal = 1
				begin

					declare @perReg varchar(7) = (select CONVERT(VARCHAR(7), DATEADD(MONTH,-1,dbo.getLocaldate()), 120))
					
					INSERT INTO #RegistroDetalladoNovedadTmp
					(rdnRegistroDetallado,rdnTipotransaccion,rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
					 rdnDateTimeInsert, rdnDateTimeUpdate)
					select min(red.redId) as rdnRegistroDetallado, N'CAMBIO_SUCURSAL_TRABAJADOR_DEPENDIENTE' as rdnTipotransaccion, 'NOVEDAD_SUC' as rdnTipoNovedad, 'NO_APLICADA' as rdnAccionNovedad, null rdnMensajeNovedad,
					eomonth(concat(reg.regPeriodoAporte,N'-01')) as rdnFechaInicioNovedad,null as rdnFechaFinNovedad,N'TRABAJADOR_DEPENDIENTE' as rdnOUTTipoAfiliado, dbo.getLocalDate() as rdnDateTimeInsert,dbo.getLocalDate() as rdnDateTimeUpdate
					FROM staging.RegistroDetallado as red with (nolock)
					INNER JOIN staging.RegistroGeneral as reg with (nolock) ON red.redRegistroGeneral = reg.regId
					LEFT JOIN dbo.TemNovedad ten with (nolock) ON ten.tenIdTransaccion = red.redId AND ten.tenRegistroDetalladoNovedad = -1
					WHERE reg.regId = @IdRegistroGeneral
					and reg.regPeriodoAporte = @perReg
					AND redTipoCotizante NOT IN (SELECT Data FROM dbo.Split ( (
													SELECT stpValorParametro FROM staging.StagingParametros 
													WHERE stpNombreParametro = 'SOLICITANTE_INDEPENDIENTE') ,','))													
					AND red.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
					AND regOUTMarcaSucursalPILA = 1
					AND regOUTCodSucursalPrincipal IS NOT NULL
					AND ISNULL(regCodSucursal,regOUTCodSucursalPrincipal) <> isnull(redOUTCodSucursal,'')
					AND ISNULL(regNomSucursal,regOUTNomSucursalPrincipal) <> isnull(redOUTNomSucursal,'')
					group by red.redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante, reg.regPeriodoAporte

				end

		end


	--============================
	--==== Finaliza proceso novedades sucursal. 
	--============================


	--==== Se agrega validación, para no volver a insertar la novedad ya existente y no tener que borrar el staging.registroDetalladoNovedad para evitar la duplicidad de novedades y no perder la relación de las tablas.  2024-01-02
	delete a
	from #RegistroDetalladoNovedadTmp as a
	inner join staging.RegistroDetalladoNovedad as rdn on a.rdnRegistroDetallado = rdn.rdnRegistroDetallado and a.rdnTipoNovedad = rdn.rdnTipoNovedad and isnull(a.rdnOUTTipoAfiliado,'') = isnull(rdn.rdnOUTTipoAfiliado,'')
	and isnull((case when rdn.rdnTipoNovedad = 'NOVEDAD_ING' and rdn.rdnTipotransaccion = 'NOVEDAD_REINTEGRO' then null else rdn.rdnTipotransaccion end),'') = isnull(a.rdnTipotransaccion,'')
	

	IF((SELECT COUNT(1) FROM #RegistroDetalladoNovedadTmp) > 0)
	BEGIN
		BEGIN TRAN
		INSERT INTO staging.RegistroDetalladoNovedad
				(rdnRegistroDetallado,rdnTipotransaccion,rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
				rdnDateTimeInsert, rdnDateTimeUpdate)
		SELECT rdnRegistroDetallado,rdnTipotransaccion,rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
				rdnDateTimeInsert, rdnDateTimeUpdate
		FROM #RegistroDetalladoNovedadTmp
		COMMIT;
	END;

	DROP TABLE #TrabajadorTMP;
	DROP TABLE #RegistroDetalladoNovedadTmp;
END;