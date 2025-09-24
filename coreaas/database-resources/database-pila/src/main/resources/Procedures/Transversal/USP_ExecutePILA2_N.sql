-- =============================================
-- Author: Robinson Castillo
-- Create Date: 2024-06-19
-- Description: Procedimiento encargado de procesar las planillas N
-- =============================================
create or alter procedure [dbo].[USP_ExecutePILA2_N] (@iIdIndicePlanilla Bigint, @sFase varchar(100),@bSimulado Bit,@sUsuarioProceso varchar(50),@iIdentificadorPaquete Bigint,@bReanudarTransaccion bit, @IdTransaccion bigInt output)
AS
begin
    set nocount on;

	begin try;
	
		declare  @localDate datetime = dbo.getLocalDate();

		execute dbo.USP_ExecutePILA2CopiarPlanilla @iIdIndicePlanilla, @sFase, @bSimulado, @sUsuarioProceso, 1, @iIdentificadorPaquete, @IdTransaccion = @IdTransaccion output;
				
		--==== Se crean los cotizantes en la tabla persona que no existen en core, para evitar errores en novedades. 
		declare @redRegistroGeneralN bigInt = (select regId from staging.RegistroGeneral with (nolock) where regTransaccion = @IdTransaccion)
		execute dbo.USP_ConsultarInfoCotizantePorCrear_B7 @redRegistroGeneralN
		--==== Finaliza proceso, para crear los cotizantes.

		--=== Se agrega para el GLPI 62928
		if exists (	select 1
					from dbo.pilaIndicePlanilla as p with (nolock)
					inner join staging.registroGeneral as r with (nolock) on r.regRegistroControl = p.pipId
					inner join dbo.PilaArchivoFRegistro6 as pf6 with (nolock) on r.regNumPlanilla = pf6.pf6NumeroPlanilla and p.pipCodigoOperadorInformacion =  pf6.pf6CodOperadorInformacion and replace(r.regPeriodoAporte, N'-', N'') = pf6.pf6PeriodoPago
					where r.regId = @redRegistroGeneralN)
				begin
					update r set regcuentaBancariaRecaudo = 1
					from dbo.pilaIndicePlanilla as p with (nolock)
					inner join staging.registroGeneral as r with (nolock) on r.regRegistroControl = p.pipId
					inner join dbo.PilaArchivoFRegistro6 as pf6 with (nolock) on r.regNumPlanilla = pf6.pf6NumeroPlanilla and p.pipCodigoOperadorInformacion =  pf6.pf6CodOperadorInformacion and replace(r.regPeriodoAporte, N'-', N'') = pf6.pf6PeriodoPago
					where r.regId = @redRegistroGeneralN
				end;
		--=== Finaliza proceso.

		if (@bReanudarTransaccion = 0 OR @sFase LIKE ('PILA2_FASE_1%')) AND @sFase <> 'FINALIZAR_TRANSACCION'
			begin
				
				declare @bSuccessN bit = 0;
				execute dbo.USP_ExecuteBloqueStaging @IdTransaccion, @bSuccess = @bSuccessN output;
				
					if @bSuccessN = 0
					begin
						--print 'Fallo en bloque staging'
						raiserror ( 'Falló la ejecución del bloque staging', 11, 1);
					end;
			end;

		execute dbo.USP_ExecutePILA2Fase1Validacion @IdTransaccion, 'SIN_PARAMETRO';  
				

		update pip set  pipDateTimeupdate = @localDate, pipEstadoArchivo = reg.[regOUTEstadoArchivo]
		from dbo.PilaIndicePlanilla as pip with (nolock)
		inner join staging.RegistroGeneral as reg with (nolock) on reg.[regRegistroControl] = pip.pipId
		where reg.regTransaccion = @IdTransaccion
		and reg.regOUTEstadoArchivo in ('PROCESADO_VS_BD','PendIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD');
		
		
		update peb
		set  pebEstadoBloque7 = reg.regOUTEstadoArchivo,
			pebAccionBloque7 =	case reg.regOUTEstadoArchivo
								when 'PROCESADO_VS_BD' then 'REGISTRAR_RELACIONAR_APORTE' + (case when regEsSimulado=1 then '_MANUAL' else '' end)
								when 'PendIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD' then 'PASAR_A_BANDEJA'
								end,
			pebFechaBloque7 = @localDate
		from dbo.PilaEstadoBloque peb with (nolock)
		inner join staging.RegistroGeneral reg with (nolock) on reg.[regRegistroControl] = peb.pebIndicePlanilla
		where reg.regTransaccion = @IdTransaccion
		AND reg.regOUTEstadoArchivo in ('PROCESADO_VS_BD','PendIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD');
		

		IF (@sFase = 'SIN_PARAMETRO' OR @sFase = 'PILA2_FASE_2' OR @sFase = 'PILA2_FASE_2_FASE_3')
		BEGIN
			if exists (select 1 from staging.registroGeneral as r with (nolock) where r.regTransaccion = @IdTransaccion and isnull(regOUTEstadoArchivo,'') in ('PROCESADO_VS_BD','PENDIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES'))
				begin
					execute dbo.USP_ExecuteRegistrarRelacionarAportesRegistro_N @IdRegistroGeneral = @redRegistroGeneralN, @EsSimulado = 0, @EsProcesoManual = 0
				end
		END


		update reg set  regDateTimeupdate = @localDate, reg.regOUTEstadoArchivo='REGISTRADO_O_RELACIONADO_LOS_APORTES'
		from dbo.PilaIndicePlanilla pip with (nolock)
		inner join staging.RegistroGeneral reg with (nolock) on reg.[regRegistroControl] = pip.pipId
		where reg.regTransaccion = @IdTransaccion
		
		update peb
			set pebEstadoBloque8 = reg.[regOUTEstadoArchivo]
				,pebAccionBloque8 = case reg.[regOUTEstadoArchivo]
									when 'PendIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES' then 'REGISTRAR_RELACIONAR_APORTE' + (case when regEsSimulado=1 then '_MANUAL' else '' end)
									when 'REGISTRADO_O_RELACIONADO_LOS_APORTES' then 'REGISTRAR_NOVEDADES_PILA' + (case when regEsSimulado=1 then '_MANUAL' else '' end)
									end
				,pebFechaBloque8 = @localDate
		from dbo.PilaEstadoBloque peb with (nolock)
		inner join staging.RegistroGeneral reg with (nolock) on reg.[regRegistroControl] = peb.pebIndicePlanilla
		where reg.regTransaccion = @IdTransaccion
		AND reg.regOUTEstadoArchivo in ('PendIENTE_POR_REGISTRO_Y_RELACION_DE_APORTES','REGISTRADO_O_RELACIONADO_LOS_APORTES');
		
		
		declare @FechaReferenciaNovedadFutura date = dbo.getLocalDate()
		execute dbo.USP_ExecuteRegistrarRelacionarNovedadesRegistro_N @redRegistroGeneralN, @EsSimulado = 1, @FechaReferenciaNovedadFutura = @FechaReferenciaNovedadFutura
		
		
		update peb
			set pebEstadoBloque9 = reg.[regOUTEstadoArchivo]
				,pebAccionBloque9 =  'NOTIFICAR_RECAUDO' + (case when regEsSimulado=1 then '_MANUAL' else '' end)
				,pebFechaBloque9 = @localDate
		from dbo.PilaEstadoBloque peb with (nolock)
		inner join staging.RegistroGeneral reg with (nolock) on reg.[regRegistroControl] = peb.pebIndicePlanilla
		where reg.regTransaccion = @IdTransaccion
		AND reg.regOUTEstadoArchivo in ('PROCESADO_NOVEDADES','PROCESADO_SIN_NOVEDADES');
		
		
		update pip set  pipDateTimeupdate = @localDate, pipEstadoArchivo = reg.[regOUTEstadoArchivo]
		from dbo.PilaIndicePlanilla pip with (nolock)
		inner join staging.RegistroGeneral reg with (nolock) on reg.[regRegistroControl] = pip.pipId
		where reg.regTransaccion = @IdTransaccion
		AND reg.regOUTEstadoArchivo in ('PROCESADO_NOVEDADES','PROCESADO_SIN_NOVEDADES')
		AND pip.pipEstadoArchivo != 'RECAUDO_NOTIFICADO';

	end try
	begin catch
		 SELECT   
         ERROR_NUMBER() AS ErrorNumber  
        ,ERROR_SEVERITY() AS ErrorSeverity  
        ,ERROR_STATE() AS ErrorState  
        ,ERROR_LINE () AS ErrorLine  
        ,ERROR_PROCEDURE() AS ErrorProcedure  
        ,ERROR_MESSAGE() AS ErrorMessage;
	end catch;
		
end;