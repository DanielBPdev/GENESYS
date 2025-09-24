-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2024-12-31
-- Description: Proceso encargado de mover los aportes masivos. 
-- =============================================
CREATE  or ALTER procedure [masivos].[ASP_Execute_pilaProcesarAporte]

	(@iIdIndicePlanilla Bigint,
       @sFase VARCHAR(100) = 'SIN_PARAMETRO',
	   @bSimulado Bit = 0,
	   @sUsuarioProceso VARCHAR(50) = 'SISTEMA',
	   @iIdentificadorPaquete Bigint = 0,
	   @bReanudarTransaccion BIT = 0,
	   @IdTransaccion BIGINT
	    )

as 

begin

 --SET NOCOUNT ON
 DECLARE @localDate DATETIME = dbo.getLocalDate(); 

 declare @redRegistroGeneral bigInt = (select regId from staging.RegistroGeneral with (nolock) where regTransaccion = @IdTransaccion)


	BEGIN TRY
	
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
					'INICIO MASIVO- ASP_Execute_pilaProcesarAporte');
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
				--EXEC USP_AlmacenarHistorialEstado @IdTransaccion, 'BLOQUE_7_OI', @iIdIndicePlanilla;
		
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
	  		EXEC USP_ExecutePILA2Fase2RegistrarRelacionarAportes @IdTransaccion, @bSimulado, 1;
		
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
				--EXEC USP_AlmacenarHistorialEstado @IdTransaccion, 'BLOQUE_8_OI', @iIdIndicePlanilla;
				
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
			
			execute [dbo].[USP_ValidarNovedadesEmpleadorActivoSUCURSALES] @IdTransaccion --=== Se agrega validación, para cuando la sucursal ya existe y los cambia de sucursal, con la marca de cambio de sucursal. 
	
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
				--EXEC USP_AlmacenarHistorialEstado @IdTransaccion, 'BLOQUE_9_OI', @iIdIndicePlanilla;
	
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
	
		--EXEC [dbo].[USP_DeleteBloqueStaging] @IdTransaccion;
	
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
	    
		--EXEC [dbo].[USP_DeleteBloqueStaging] @IdTransaccion;
	
	END CATCH
	
	/*
	IF @IdTransaccion IS NOT NULL
	BEGIN
		EXEC [dbo].[USP_DeleteBloqueStaging] @IdTransaccion;
	END;
	*/
end;