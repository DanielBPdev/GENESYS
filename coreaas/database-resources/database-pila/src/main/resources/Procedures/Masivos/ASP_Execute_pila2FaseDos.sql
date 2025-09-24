-- =============================================
-- Author: Robinson Castillo 
-- Create Date: 2023-07-23
-- Description: procedimiento encargado de procesar el bloque staging USP_ExecuteBloqueStaging 
-- =============================================
create or alter procedure [masivos].[ASP_Execute_pila2FaseDos] (@nombreArchivo varchar(300))
as 
begin

    SET XACT_ABORT ON
	SET NOCOUNT ON
	SET QUOTED_IDENTIFIER ON

	begin try

		begin transaction

			declare @tblTransaccion as table (id int identity(1,1), transaccion bigInt)
			insert @tblTransaccion (transaccion)
			select regIdTransaccion
			from masivos.SimilacionLogGeneral
			where maaNombreArchivo = @nombreArchivo
			group by regIdTransaccion
			
			declare @cant int = (select count(*) from @tblTransaccion)
			declare @cont int = 1
			while @cont <= @cant
			begin
				declare @tran bigInt = (select transaccion from @tblTransaccion where id = @cont)
				execute USP_ExecuteBloqueStaging @tran
				set @cont += 1
			end



			--==== Se hace proceso de validación. 
			declare @cont2 int = 1
			while @cont2 <= @cant
			begin
				declare @tran2 bigInt = (select transaccion from @tblTransaccion where id = @cont2)
				execute USP_ExecutePILA2Fase1Validacion @tran2
				set @cont2 += 1
			end


			update rd set rd.redOUTEstadoValidacionV0 = 'NO_VALIDADO_BD', rd.redOUTEstadoValidacionV1 = 'NO_VALIDADO_BD', rd.redOUTEstadoValidacionV2 = 'NO_VALIDADO_BD', rd.redOUTEstadoValidacionV3 = 'NO_VALIDADO_BD'
			from staging.RegistroGeneral as r
			inner join staging.RegistroDetallado as rd on r.regId = rd.redRegistroGeneral
			where exists (select 1 from @tblTransaccion where transaccion = r.regTransaccion)
			and r.regOUTEstadoArchivo = 'PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD'
			and r.regOUTEsEmpleador = 1


			update rd set rd.redOUTEstadoValidacionV1 = 'NO_VALIDADO_BD', rd.redOUTEstadoValidacionV2 = 'NO_VALIDADO_BD'
			from staging.RegistroGeneral as r
			inner join staging.RegistroDetallado as rd on r.regId = rd.redRegistroGeneral
			where exists (select 1 from @tblTransaccion where transaccion = r.regTransaccion)
			and r.regOUTEstadoArchivo = 'PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD'
			and r.regOUTEsEmpleador <> 1


			update r set r.regOUTEstadoArchivo = 'PROCESADO_VS_BD'
			from staging.RegistroGeneral as r
			inner join staging.RegistroDetallado as rd on r.regId = rd.redRegistroGeneral
			where exists (select 1 from @tblTransaccion where transaccion = r.regTransaccion)
			and r.regOUTEstadoArchivo = 'PENDIENTE_POR_GESTIONAR_ERROR_EN_VALIDACION_VS_BD'

			/*
			--==== Se hace proceso de aportes. 
			declare @cont3 int = 1
			while @cont3 <= @cant
			begin
				declare @tran3 bigInt = (select transaccion from @tblTransaccion where id = @cont3)
				execute USP_ExecutePILA2Fase2RegistrarRelacionarAportes @tran3
				set @cont3 += 1
			end
			*/
			-- sigue este USP_ExecutePILA2Fase3RegistrarRelacionarNovedades


		commit transaction


	end try
	begin catch
		if @@TRANCOUNT > 0  rollback transaction;
		select 'Se genero error'
	end catch
end