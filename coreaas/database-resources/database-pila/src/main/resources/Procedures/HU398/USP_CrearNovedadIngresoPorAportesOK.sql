-- =============================================
-- Author:		Diego Suesca
-- Create date: 2020/05/03
-- Description:	Procedimiento almacenado encargado  de crear novedad en el caso en que el cumpla aportes OK y no tenga tipo novedad NOVEDAD_ING 
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_CrearNovedadIngresoPorAportesOK]
(
	@IdRegistroGeneral BIGINT
)
AS
BEGIN
SET NOCOUNT ON;
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
	
	DECLARE @localDate DATETIME = dbo.getLocalDate()
	
	--print 'USP_ExecutePILA2CopiarNovedades'
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

	--===============================================================
	--============= Ajuste para tener en cuenta la población fallecida. 
	--===============================================================
	create table #CotpersonaFallecido1 (perNumeroIdentificacion varchar(20), perTipoIdentificacion varchar(30), pedFallecido bit, fechaRegistro datetime, origen varchar(250))
	insert #CotpersonaFallecido1
	execute sp_execute_remote coreReferenceData, N'select * from dbo.CotpersonaFallecido with (nolock)'
		
	DECLARE @IdRegistroDetalle BIGINT		
	DECLARE @FechaIngreso DATE	
	DECLARE @TipoAfiliado VARCHAR(50)
	
	
	--===== Se agragan condiciones para aplicar reintegro por ingreso de aportes GLPI 85287
	declare @periodoRegular varchar(7) = convert(varchar(7),convert(date,dateadd(month, -1, @localDate)))
	declare @periodoRetiro varchar(7) = convert(varchar(7),convert(date,dateadd(month, -2, @localDate)))

	drop table if exists #val_OK

	select red.redRegistroGeneral, red.redId, case when (isnull(redOUTEstadoValidacionV0,'') = 'CUMPLE' AND isnull(redOUTEstadoValidacionV1,'') = 'OK' AND ISNULL(redOUTEstadoValidacionV2, '') = 'OK' AND isnull(redOUTEstadoValidacionV3,'')='OK') then 'OK'
		when (isnull(redOUTEstadoValidacionV0,'') != 'CUMPLE' AND isnull(redOUTEstadoValidacionV1,'') = 'OK' AND ISNULL(redOUTEstadoValidacionV2, '') = 'OK' AND isnull(redOUTEstadoValidacionV3,'')='OK') then 'NO_OK_APROBADO'
		when redOUTEstadoValidacionV0 = 'CUMPLE' and redOUTEstadoValidacionV1 = 'OK'and redOUTEstadoValidacionV2 = 'NO_OK' and redOUTEstadoValidacionV3 = 'OK' then 'NO_OK_APROBADO'
		when (isnull(redOUTEstadoValidacionV2, '') = 'NO_VALIDADO_BD') then 'NO_VALIDADO_BD'
		else 'NO_OK'
		end as redOUTEstadoRegistroAporte
		,redTipoIdentificacionCotizante, redNumeroIdentificacionCotizante
		into #val_OK
	FROM staging.RegistroDetallado red with (nolock)
	WHERE red.redRegistroGeneral = @IdRegistroGeneral
	and red.redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'



SELECT 
	--isnull(MIN(redFechaIngreso),dbo.getlocaldate()) redFechaIngreso,    
	MIN(redId) redId, 
	MIN(redFechaIngreso) redFechaIngreso,
	MIN(redOUTTipoAfiliado) redOUTTipoAfiliado
    INTO #TrabajadorTMP
    FROM staging.RegistroGeneral as r with (nolock)
	inner join staging.RegistroDetallado redO (nolock) on r.regId = redO.redRegistroGeneral
	left join dbo.cotFechaIngreso as ct with (nolock) on r.regNumeroIdentificacionAportante = ct.perNumeroIdentificacionAport and redO.redTipoIdentificacionCotizante = ct.perTipoIdentificacionCot and redO.redNumeroIdentificacionCotizante = ct.perNumeroIdentificacionCot
    WHERE redO.redRegistroGeneral = @IdRegistroGeneral
	--===== Se agragan condiciones para aplicar reintegro por ingreso de aportes GLPI 85287
	and isnull(redO.redOUTEstadoSolicitante,'') in ('NO_FORMALIZADO_RETIRADO_CON_APORTES', 'INACTIVO')
	and (ct.roaFechaRetiro is not null and convert(varchar(7),ct.roaFechaRetiro) >= @periodoRetiro)
	and r.regPeriodoAporte = @periodoRegular -- Se agrega validación para que sea en periodo regular. 
	AND isnull(r.regOUTEstadoEmpleador,'') = 'ACTIVO' --=== Se agrega validación para el GLPI 58273
	and not exists (select 1 from #CotpersonaFallecido1 as fall where redO.redTipoIdentificacionCotizante = fall.perTipoIdentificacion and redO.redNumeroIdentificacionCotizante = fall.perNumeroIdentificacion)
   /*
   AND NOT EXISTS (
                    SELECT TOP 1 1 
                    FROM staging.RegistroDetallado red with (nolock)
                    WHERE ISNULL(redOUTEstadoRegistroAporte, 'NO_OK') = 'NO_OK'
                    AND red.redRegistroGeneral = @IdRegistroGeneral
                    AND red.redTipoIdentificacionCotizante = redO.redTipoIdentificacionCotizante
                    AND red.redNumeroIdentificacionCotizante = redO.redNumeroIdentificacionCotizante
                    )
	*/
	AND NOT EXISTS (select top 1 1 
					from #val_OK as red 
					WHERE ISNULL(redOUTEstadoRegistroAporte, 'NO_OK') in ('NO_OK', 'NO_VALIDADO_BD')
                    AND red.redRegistroGeneral = @IdRegistroGeneral
                    AND red.redTipoIdentificacionCotizante = redO.redTipoIdentificacionCotizante
                    AND red.redNumeroIdentificacionCotizante = redO.redNumeroIdentificacionCotizante
					)
	AND NOT EXISTS (
                    SELECT TOP 1 1 
                    FROM staging.RegistroDetallado red with (nolock)
                    WHERE red.redNovIngreso IS NOT NULL
                    AND red.redRegistroGeneral = @IdRegistroGeneral
                    AND red.redTipoIdentificacionCotizante = redO.redTipoIdentificacionCotizante
                    AND red.redNumeroIdentificacionCotizante = redO.redNumeroIdentificacionCotizante
                    )
    AND redO.redOUTEstadoSolicitante IN ('INACTIVO', 'NO_FORMALIZADO_RETIRADO_CON_APORTES')
    AND (
		redNovIngreso IS NOT NULL OR
		redNovRetiro IS NOT NULL OR
		redNovVSP IS NOT NULL OR
		redNovVST IS NOT NULL OR
		redNovSLN IS NOT NULL OR
		redNovIGE IS NOT NULL OR
		redNovLMA IS NOT NULL OR
		redNovVACLR IS NOT NULL OR
		redDiasIRL IS NOT NULL 
		)	
	AND redOUTTipoAfiliado = 'TRABAJADOR_DEPENDIENTE'
    AND ISNULL(redCorrecciones,'') <> 'A'
	AND isnull(r.regOUTEstadoEmpleador,'') = 'ACTIVO' --=== Se agrega validación para el GLPI 58273
    GROUP BY redO.redTipoIdentificacionCotizante, redO.redNumeroIdentificacionCotizante

	DECLARE @TrabajadorCursor AS CURSOR

	SET @TrabajadorCursor = CURSOR FAST_FORWARD FOR
	SELECT redId, 
            redFechaIngreso,		    
		    redOUTTipoAfiliado
	FROM #TrabajadorTMP
	
	OPEN @TrabajadorCursor
	FETCH NEXT FROM @TrabajadorCursor INTO
	@IdRegistroDetalle, 
    @FechaIngreso,
    @TipoAfiliado

	WHILE @@FETCH_STATUS = 0
	BEGIN	

		INSERT INTO #RegistroDetalladoNovedadTmp
		(rdnRegistroDetallado,
        rdnTipotransaccion,
        rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
		 rdnDateTimeInsert, rdnDateTimeUpdate)
		VALUES (@IdRegistroDetalle
		    	,NULL
				,'NOVEDAD_ING','APLICAR_NOVEDAD','Novedad por cumplimiento de aportes',@FechaIngreso,NULL,@TipoAfiliado
				,dbo.getLocalDate(),dbo.getLocalDate() 
                )		

	FETCH NEXT FROM @TrabajadorCursor INTO @IdRegistroDetalle, @FechaIngreso,@TipoAfiliado
	END
	CLOSE @TrabajadorCursor;
	DEALLOCATE @TrabajadorCursor;


	delete b
	from #val_OK as a
	inner join #RegistroDetalladoNovedadTmp as b on a.redId = b.rdnRegistroDetallado
	where a.redOUTEstadoRegistroAporte <> 'OK'

	IF((SELECT COUNT(1) FROM #RegistroDetalladoNovedadTmp) > 0)
	BEGIN
		BEGIN TRAN
		INSERT INTO staging.RegistroDetalladoNovedad
				(rdnRegistroDetallado,rdnTipotransaccion,rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
				rdnDateTimeInsert, rdnDateTimeUpdate, rdnIngresoPorAportesOK)
		SELECT rdnRegistroDetallado,rdnTipotransaccion,rdnTipoNovedad,rdnAccionNovedad,rdnMensajeNovedad,rdnFechaInicioNovedad,rdnFechaFinNovedad,rdnOUTTipoAfiliado,
				rdnDateTimeInsert, rdnDateTimeUpdate, 1
		FROM #RegistroDetalladoNovedadTmp
		COMMIT;
	END;

	DROP TABLE #val_OK
	DROP TABLE #TrabajadorTMP;
	DROP TABLE #RegistroDetalladoNovedadTmp;
END;