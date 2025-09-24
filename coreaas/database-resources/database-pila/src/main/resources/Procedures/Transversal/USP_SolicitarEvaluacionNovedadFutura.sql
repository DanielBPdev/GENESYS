-- =============================================
-- Author:		Alfonso Baquero Echeverry
-- Create date: 2018/05/15
-- Description:	Procedimiento almacenado encargado empleado para la 
-- comprobación de la aplicación de novedades futuras
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[USP_SolicitarEvaluacionNovedadFutura]
	@dFechaActual DATE
AS
BEGIN
SET NOCOUNT ON;
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
--print 'Inicia USP_SolicitarEvaluacionNovedadFutura'

DECLARE @IdTransaccion BIGINT,
       @sFase VARCHAR(100) = 'SIN_PARAMETRO',
	   @bSimulado Bit = 0,
	   @bEsManual BIT = 0,
	   @sUsuarioProceso VARCHAR(50) = 'SISTEMA'


	select regId, row_number() over (order by regId) as id
	into #procesarNovedadesFuturas
	FROM staging.RegistroGeneral AS reg
	WHERE reg.regNovedadFutura = 1
	AND ISNULL(reg.regOUTNovedadFuturaProcesada, 0) = 0
	and reg.regTransaccion is null


	declare @cont int = (select count(*) from #procesarNovedadesFuturas)
	declare @cont2 int = 1

	while @cont2 <= @cont
		begin

			declare @regId bigInt = (select regId from #procesarNovedadesFuturas where id = @cont2)

			BEGIN TRY
				print 'Generar ID transacción'
				INSERT INTO [staging].[Transaccion] (traFechaTransaccion)
				VALUES (dbo.GetLocalDate())
			
				SET @IdTransaccion = @@IDENTITY
			
				BEGIN TRANSACTION
			    			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			    			VALUES (dbo.GetLocalDate(),
			    				'@dFechaActual=' + ISNULL(CAST(@dFechaActual AS VARCHAR(20)), 'NULL') +
			    				';@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL') +
			    				';@sFase= ' + ISNULL(@sFase, 'NULL') +
			    				';@bSimulado=' + ISNULL(CAST(@bSimulado AS CHAR(1)), 'NULL') +
			    				';@sUsuarioProceso=' + ISNULL(@sUsuarioProceso, 'NULL') +
			    				';@bEsManual=' + ISNULL(CAST(@bEsManual AS VARCHAR(20)), 'NULL'),
			    				'USP_SolicitarEvaluacionNovedadFutura INICIO');
			    COMMIT TRANSACTION
			
				-- se asigna el ID de transacción a los registros generales marcados con novedad futura no procesada
				UPDATE reg
				SET reg.regTransaccion = @IdTransaccion,
					reg.regDateTimeUpdate = dbo.getLocalDate()
				FROM staging.RegistroGeneral AS reg
				WHERE reg.regNovedadFutura = 1
				AND ISNULL(reg.regOUTNovedadFuturaProcesada, 0) = 0
				and reg.regId = @regId
			
				EXEC USP_ExecutePILA2Fase3RegistrarRelacionarNovedades @IdTransaccion, @bSimulado, @bEsManual, @dFechaActual
			
				-- para los casos de planillas PILA, se toma el ID de índice de planilla para actualizar su estado de bloque de validación 9
				
				--se crea la traza histórica de estados en caso de reproceso
				EXEC USP_AlmacenarHistorialEstado @IdTransaccion, 'BLOQUE_9_OI'
				
				-- Se comenta ya que no se ve necesario realizar este update	
				/*UPDATE peb
				SET peb.pebEstadoBloque9 = 'PROCESADO_NOVEDADES', 
					peb.pebFechaBloque9 = dbo.GetLocalDate()
				FROM dbo.PilaEstadoBloque peb 
				INNER JOIN (
					SELECT reg.regRegistroControl
					FROM staging.RegistroGeneral reg
					WHERE reg.regTransaccion = @IdTransaccion
					AND reg.regNovedadFutura = 1
					AND reg.regOUTNovedadFuturaProcesada = 1
					AND reg.regRegistroControl IS NOT NULL
				) planillas ON peb.pebIndicePlanilla = planillas.regRegistroControl*/
			
				-- se eliminan las marcas de novedad futura para los casos de novedades futuras procesadas
				
				UPDATE reg
				SET reg.regNovedadFutura = 0,
					reg.regDateTimeUpdate = dbo.getLocalDate()
				FROM staging.RegistroGeneral reg
				WHERE reg.regTransaccion = @IdTransaccion
				AND reg.regNovedadFutura = 1
				AND reg.regOUTNovedadFuturaProcesada = 1
				and reg.regId = @regId
			
				BEGIN TRANSACTION
			    			INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			    			VALUES (dbo.GetLocalDate(),
			    				'@dFechaActual=' + ISNULL(CAST(@dFechaActual AS VARCHAR(20)), 'NULL') +
			    				';@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL') +
			    				';@sFase= ' + ISNULL(@sFase, 'NULL') +
			    				';@bSimulado=' + ISNULL(CAST(@bSimulado AS CHAR(1)), 'NULL') +
			    				';@sUsuarioProceso=' + ISNULL(@sUsuarioProceso, 'NULL') +
			    				';@bEsManual=' + ISNULL(CAST(@bEsManual AS VARCHAR(20)), 'NULL'),
			    				'USP_SolicitarEvaluacionNovedadFutura FIN');
			    COMMIT TRANSACTION
			
			END TRY
			BEGIN CATCH
			
				IF XACT_STATE() <> 0 AND @@TRANCOUNT > 0
				BEGIN
					ROLLBACK
				END
				
				DECLARE @ErrorMessage NVARCHAR(4000);
				SELECT @ErrorMessage = ERROR_MESSAGE();
			
				BEGIN TRANSACTION
			        INSERT INTO staging.RegistroLog (relFecha,relParametrosEjecucion,relErrorMessage)
			        VALUES (dbo.GetLocalDate(),
			            'USP_SolicitarEvaluacionNovedadFutura | @dFechaActual=' + ISNULL(CAST(@dFechaActual AS VARCHAR(20)), 'NULL') +
			            ';@IdTransaccion=' + ISNULL(CAST(@IdTransaccion AS VARCHAR(20)), 'NULL') +
			            ';@sFase= ' + ISNULL(@sFase, 'NULL') +
			            ';@bSimulado=' + ISNULL(CAST(@bSimulado AS CHAR(1)), 'NULL') +
			            ';@sUsuarioProceso=' + ISNULL(@sUsuarioProceso, 'NULL') +
			            ';@bEsManual=' + ISNULL(CAST(@bEsManual AS VARCHAR(20)), 'NULL'),
			            @ErrorMessage);
			    COMMIT TRANSACTION
			    
			END CATCH
			
			IF @IdTransaccion IS NOT NULL
			BEGIN
				-- se elimina el ID de transacción
				UPDATE reg
				SET reg.regTransaccion = NULL,
					reg.regDateTimeUpdate = dbo.getLocalDate()
				FROM staging.RegistroGeneral reg
				WHERE reg.regTransaccion = @IdTransaccion;
			
				EXEC [dbo].[USP_DeleteBloqueStaging] @IdTransaccion;
			END

		set @cont2 += 1

		end

END;