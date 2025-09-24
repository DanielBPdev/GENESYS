-- =============================================
-- Author:		Andres Julian Rocha Cruz
-- Create date: 2017/06/16
-- Update:      Juan Diego Ocampos Q
-- Update date: 2020/09/15
-- Description:	Procedimiento almacenado encargado  de orquestar las ejecuciones
-- de las fases de PILA Mundo2 por bloque o por planilla (proceso manual)
-- Se realiza ajuste, para cambiar el flujo de las planillas N
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_ExecutePILA2]
(
       @iIdIndicePlanilla Bigint = 0,
       @sFase VARCHAR(100) = 'SIN_PARAMETRO',
	   @bSimulado Bit = 0,
	   @sUsuarioProceso VARCHAR(50) = 'SISTEMA',
	   @iIdentificadorPaquete Bigint = 0,
	   @bReanudarTransaccion BIT = 0,
	   @IdTransaccion BIGINT OUTPUT
)
AS
BEGIN
SET NOCOUNT ON;

DECLARE @localDate DATETIME = dbo.getLocalDate(); 
BEGIN TRY

	BEGIN TRAN
			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			VALUES (@localDate, 
				'@iIdIndicePlanilla=' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR(20)), 'NULL') + 
				';@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL') + 
				';@sFase= ' + ISNULL(@sFase, 'NULL') + 
				';@bSimulado=' + ISNULL(CAST(@bSimulado AS CHAR(1)), 'NULL') + 
				';@sUsuarioProceso=' + ISNULL(@sUsuarioProceso, 'NULL') + 
				';@iIdentificadorPaquete=' + ISNULL(CAST(@iIdentificadorPaquete AS VARCHAR(20)), 'NULL'), 
				'INICIO');
	COMMIT;

		--===== Se cambia la variable simulado en cero, cuando es planilla N, para que siga el flujo de DB. 
	if (select distinct 1
			from PilaIndicePlanilla as p with (nolock)
			inner join PilaArchivoIRegistro1 as pi1 with (nolock) on p.pipId = pi1.pi1IndicePlanilla
			left join staging.registroGEneral as r with (nolock) on p.pipId = r.regRegistroControl
			where p.pipId = @iIdIndicePlanilla and pi1.pi1TipoPlanilla = 'N' and pi1.pi1NumPlanillaAsociada is not null 
			and isnull(p.pipEstadoArchivo, '') not in ('ANULADO','RECAUDO_NOTIFICADO')
			and r.regRegistroControl is null
			) = 1
		begin
			execute dbo.USP_ExecutePILA2_N @iIdIndicePlanilla, @sFase,@bSimulado,@sUsuarioProceso,@iIdentificadorPaquete,@bReanudarTransaccion,@IdTransaccion = null
		end
	else
		--====================================================
		begin --==== Inicia proceso para las demás planillas. 		
		--====================================================

	-- Crear registro general y detallados
	EXEC USP_ExecutePILA2CopiarPlanilla @iIdIndicePlanilla, @sFase, @bSimulado, @sUsuarioProceso, 0, @iIdentificadorPaquete, @IdTransaccion = @IdTransaccion OUTPUT

	--==== Se crean los cotizantes en la tabla persona que no existen en core, para evitar errores en novedades. 
	declare @redRegistroGeneral bigInt = (select regId from staging.RegistroGeneral with (nolock) where regTransaccion = @IdTransaccion)
	execute [dbo].[USP_ConsultarInfoCotizantePorCrear_B7] @redRegistroGeneral
	--==== Finaliza proceso, para crear los cotizantes.

	--=== Se agrega para el GLPI 62928
	if exists (	select 1
				from dbo.pilaIndicePlanilla as p with (nolock)
				inner join staging.registroGeneral as r with (nolock) on r.regRegistroControl = p.pipId
				inner join [dbo].[PilaArchivoFRegistro6] as pf6 with (nolock) on r.regNumPlanilla = pf6.pf6NumeroPlanilla and p.pipCodigoOperadorInformacion =  pf6.pf6CodOperadorInformacion and replace(r.regPeriodoAporte, N'-', N'') = pf6.pf6PeriodoPago
				where r.regId = @redRegistroGeneral)
			begin
				update r set regcuentaBancariaRecaudo = 1
				from dbo.pilaIndicePlanilla as p with (nolock)
				inner join staging.registroGeneral as r with (nolock) on r.regRegistroControl = p.pipId
				inner join [dbo].[PilaArchivoFRegistro6] as pf6 with (nolock) on r.regNumPlanilla = pf6.pf6NumeroPlanilla and p.pipCodigoOperadorInformacion =  pf6.pf6CodOperadorInformacion and replace(r.regPeriodoAporte, N'-', N'') = pf6.pf6PeriodoPago
				where r.regId = @redRegistroGeneral
			end
	--=== Finaliza proceso.
		
    IF (@bReanudarTransaccion = 0 OR @sFase LIKE ('PILA2_FASE_1%')) AND @sFase <> 'FINALIZAR_TRANSACCION'
	BEGIN

		DECLARE @bSuccess BIT = 0;
		EXEC [dbo].[USP_ExecuteBloqueStaging] @IdTransaccion, @bSuccess = @bSuccess OUTPUT;
    
		IF @bSuccess = 0
		BEGIN
			--print 'Fallo en bloque staging'
			RAISERROR ( 'Falló la ejecución del bloque staging', 11, 1);
		END
	END
	
	BEGIN TRAN
			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			VALUES (@localDate, 
				'@iIdIndicePlanilla=' + ISNULL(CAST(@iIdIndicePlanilla AS VARCHAR(20)), 'NULL') + 
				';@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL') + 
				';@sFase= ' + ISNULL(@sFase, 'NULL') + 
				';@bSimulado=' + ISNULL(CAST(@bSimulado AS CHAR(1)), 'NULL') + 
				';@sUsuarioProceso=' + ISNULL(@sUsuarioProceso, 'NULL') + 
				';@iIdentificadorPaquete=' + ISNULL(CAST(@iIdentificadorPaquete AS VARCHAR(20)), 'NULL'), 
				'INICIO - USP_ExecutePILA2CopiarPlanilla - USP_ExecuteBloqueStaging');
	COMMIT;

	-- Ejecutar Fase 1
	IF (@sFase = 'SIN_PARAMETRO' OR @sFase LIKE ('PILA2_FASE_1%'))
	BEGIN
		EXEC USP_ExecutePILA2Fase1Validacion @IdTransaccion, @sFase;
		
		IF @bSimulado = 0 --solo actualiza estado de archivo para los procesos que no son simulados
		BEGIN
		BEGIN TRAN
			--Se actualizan estados de planillas para Bloque 7 en PilaEstadoBloque
			UPDATE peb
				SET  pebEstadoBloque7 = reg.[regOUTEstadoArchivo],
					pebAccionBloque7 =	CASE reg.[regOUTEstadoArchivo]
										WHEN 'PROCESADO_VS_BD' THEN 'REGISTRAR_RELACIONAR_APORTE' + (CASE WHEN regEsSimulado=1 THEN '_MANUAL' ELSE '' END)
										WHEN 'PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD' THEN 'PASAR_A_BANDEJA'
										END,
					pebFechaBloque7 = @localDate
			FROM PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN [staging].[RegistroGeneral] reg WITH (NOLOCK) ON reg.[regRegistroControl] = peb.pebIndicePlanilla
			WHERE reg.[regTransaccion] = @IdTransaccion
			AND reg.[regOUTEstadoArchivo] IN ('PROCESADO_VS_BD','PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD');
			
			--se crea la traza histórica de estados en caso de reproceso
			EXEC USP_AlmacenarHistorialEstado @IdTransaccion, 'BLOQUE_7_OI', @iIdIndicePlanilla;
	
			UPDATE pip
				SET  pipDateTimeUpdate = @localDate, pipEstadoArchivo = reg.[regOUTEstadoArchivo]
			FROM PilaIndicePlanilla pip WITH (NOLOCK)
			INNER JOIN [staging].[RegistroGeneral] reg WITH (NOLOCK) ON reg.[regRegistroControl] = pip.pipId
			WHERE reg.[regTransaccion] = @IdTransaccion
			AND reg.[regOUTEstadoArchivo] IN ('PROCESADO_VS_BD','PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD');
		COMMIT;
		END;
	END;

	-- Ejecutar Fase 2
	IF (@sFase = 'SIN_PARAMETRO' OR @sFase = 'PILA2_FASE_2' OR @sFase = 'PILA2_FASE_2_FASE_3')
	BEGIN
  		EXEC USP_ExecutePILA2Fase2RegistrarRelacionarAportes @IdTransaccion, @bSimulado;
	
		IF @bSimulado = 0 --solo actualiza estado de archivo para los procesos que no son simulados
		BEGIN
		BEGIN TRAN
			--Se actualizan estados de planillas para Bloque 8 en PilaEstadoBloque
			UPDATE peb
				SET pebEstadoBloque8 = reg.[regOUTEstadoArchivo]
					,pebAccionBloque8 = CASE reg.[regOUTEstadoArchivo]
										WHEN 'PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES' THEN 'REGISTRAR_RELACIONAR_APORTE' + (CASE WHEN regEsSimulado=1 THEN '_MANUAL' ELSE '' END)
										WHEN 'REGISTRADO_O_RELACIONADO_LOS_APORTES' THEN 'REGISTRAR_NOVEDADES_PILA' + (CASE WHEN regEsSimulado=1 THEN '_MANUAL' ELSE '' END)
										END
					,pebFechaBloque8 = @localDate
			FROM PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN [staging].[RegistroGeneral] reg WITH (NOLOCK) ON reg.[regRegistroControl] = peb.pebIndicePlanilla
			WHERE reg.[regTransaccion] = @IdTransaccion
			AND reg.[regOUTEstadoArchivo] IN ('PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES','REGISTRADO_O_RELACIONADO_LOS_APORTES');

			--se crea la traza histórica de estados en caso de reproceso
			EXEC USP_AlmacenarHistorialEstado @IdTransaccion, 'BLOQUE_8_OI', @iIdIndicePlanilla;
			
			UPDATE pip
				SET  pipDateTimeUpdate = @localDate, pipEstadoArchivo = reg.[regOUTEstadoArchivo]
			FROM PilaIndicePlanilla pip WITH (NOLOCK)
			INNER JOIN [staging].[RegistroGeneral] reg WITH (NOLOCK) ON reg.[regRegistroControl] = pip.pipId
			WHERE reg.[regTransaccion] = @IdTransaccion
			AND reg.[regOUTEstadoArchivo] IN ('PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES','REGISTRADO_O_RELACIONADO_LOS_APORTES');
		COMMIT;
		END;
	END;

	-- Ejecutar Fase 3
	IF (@sFase = 'SIN_PARAMETRO' OR @sFase = 'PILA2_FASE_3' OR @sFase = 'PILA2_FASE_2_FASE_3')
	BEGIN
		EXEC USP_ExecutePILA2Fase3RegistrarRelacionarNovedades @IdTransaccion, @bSimulado
		
		IF @bSimulado = 0 --solo actualiza estado de archivo para los procesos que no son simulados
		BEGIN
		BEGIN TRAN
			UPDATE peb
				SET pebEstadoBloque9 = reg.[regOUTEstadoArchivo]
					,pebAccionBloque9 =  'NOTIFICAR_RECAUDO' + (CASE WHEN regEsSimulado=1 THEN '_MANUAL' ELSE '' END)
					,pebFechaBloque9 = @localDate
			FROM PilaEstadoBloque peb WITH (NOLOCK)
			INNER JOIN [staging].[RegistroGeneral] reg WITH (NOLOCK) ON reg.[regRegistroControl] = peb.pebIndicePlanilla
			WHERE reg.[regTransaccion] = @IdTransaccion
			AND reg.[regOUTEstadoArchivo] IN ('PROCESADO_NOVEDADES','PROCESADO_SIN_NOVEDADES');
			--AND reg.regNovedadFutura = 0
			--AND reg.regOUTNovedadFuturaProcesada = 0
			
			--se crea la traza histórica de estados en caso de reproceso
			EXEC USP_AlmacenarHistorialEstado @IdTransaccion, 'BLOQUE_9_OI', @iIdIndicePlanilla;

			UPDATE pip
				SET  pipDateTimeUpdate = @localDate, pipEstadoArchivo = reg.[regOUTEstadoArchivo]
			FROM PilaIndicePlanilla pip WITH (NOLOCK)
			INNER JOIN [staging].[RegistroGeneral] reg WITH (NOLOCK) ON reg.[regRegistroControl] = pip.pipId
			WHERE reg.[regTransaccion] = @IdTransaccion
			AND reg.[regOUTEstadoArchivo] IN ('PROCESADO_NOVEDADES','PROCESADO_SIN_NOVEDADES')
			AND pip.pipEstadoArchivo != 'RECAUDO_NOTIFICADO';
			--AND reg.regNovedadFutura = 0
			--AND reg.regOUTNovedadFuturaProcesada = 0

			-- se eliminan las marcas de novedad futura para los casos de novedades futuras procesadas
			UPDATE reg
			SET reg.regNovedadFutura = 0,
				reg.regDateTimeUpdate = @localDate
			FROM staging.RegistroGeneral reg WITH (NOLOCK)
			WHERE reg.regTransaccion = @IdTransaccion
			AND reg.regNovedadFutura = 1
			AND reg.regOUTNovedadFuturaProcesada = 1;
		COMMIT;
		END;
	END;
		
	-- sí es simulado, se dejan los registros por fuera de procseso
	IF @bSimulado = 1 
	BEGIN
	BEGIN TRAN
		UPDATE staging.RegistroGeneral
		SET regOUTEnProceso = 0,
			regDateTimeUpdate = @localDate
		WHERE regTransaccion = @IdTransaccion;
	COMMIT;
	END

	--====================================================
	end --==== Finaliza proceso para las demás planillas. 		
	--====================================================

	EXEC [dbo].[USP_DeleteBloqueStaging] @IdTransaccion;

END TRY
BEGIN CATCH
	IF XACT_STATE() <> 0 AND @@TRANCOUNT > 0
	BEGIN
		ROLLBACK
	END
	-- los registros de la transacción se marcan por fuera del proceso
	BEGIN TRAN
		UPDATE staging.RegistroGeneral
		SET regOUTEnProceso = 0,
			regDateTimeUpdate = @localDate
		WHERE regTransaccion = @IdTransaccion
	COMMIT;

	DECLARE @ErrorMessage NVARCHAR(4000);
	SELECT @ErrorMessage = ERROR_MESSAGE();

	--print @ErrorMessage;

	INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
	VALUES (@localDate, '@iIdIndicePlanilla=' + CAST(@iIdIndicePlanilla AS VARCHAR(20)) + ';@sFase= ' + @sFase + ';@bSimulado=' + CAST(@bSimulado AS CHAR(1)) + ';@sUsuarioProceso=' + @sUsuarioProceso + ';@iIdentificadorPaquete=' + CAST(@iIdentificadorPaquete AS VARCHAR(20)), @ErrorMessage);
    
	EXEC [dbo].[USP_DeleteBloqueStaging] @IdTransaccion;

END CATCH

IF @IdTransaccion IS NOT NULL
BEGIN
	EXEC [dbo].[USP_DeleteBloqueStaging] @IdTransaccion;
END;
END;